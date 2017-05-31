package io.playcode.streambox.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;

import io.playcode.streambox.R;
import io.playcode.streambox.data.bean.BaseDanmu;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by guoshuyu on 2017/2/16.
 * <p>
 * 配置弹幕使用的播放器，目前使用的是本地模拟数据。
 * <p>
 * 模拟数据的弹幕时常比较短，后面的时长点是没有数据的。
 * <p>
 * 注意：这只是一个例子，演示如何集合弹幕，需要完善如弹出输入弹幕等的，可以自行完善。
 * 注意：b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤。
 */

public class DanmakuVideoPlayer extends StandardGSYVideoPlayer {

	private BaseDanmakuParser mParser;//解析器对象
	private IDanmakuView mDanmakuView;//弹幕view
	private DanmakuContext mDanmakuContext;
	private long mDanmakuStartSeekPosition = -1;

	private boolean mDanmakuShow = true;

	public DanmakuVideoPlayer(Context context, Boolean fullFlag) {
		super(context, fullFlag);
	}

	public DanmakuVideoPlayer(Context context) {
		super(context);
	}

	public DanmakuVideoPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public int getLayoutId() {
		return R.layout.view_danmu_video;
	}


	@Override
	protected void init(Context context) {
		super.init(context);
		mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
		SwitchCompat toggleDanmaku = (SwitchCompat) findViewById(R.id.switch_danmu);
		//初始化弹幕显示
		initDanmaku();

		toggleDanmaku.setOnCheckedChangeListener((buttonView, isChecked) -> {
			mDanmakuShow = isChecked;
			resolveDanmakuShow();
		});
	}

	@Override
	public void onPrepared() {
		super.onPrepared();
		onPrepareDanmaku(this);
	}

	@Override
	public void onVideoPause() {
		super.onVideoPause();
		if (mDanmakuView != null && mDanmakuView.isPrepared()) {
			mDanmakuView.pause();
		}
	}

	@Override
	public void onVideoResume() {
		super.onVideoResume();
		if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
			mDanmakuView.resume();
		}
	}


	@Override
	public void onCompletion() {
		releaseDanmaku(this);
	}


	@Override
	public void onSeekComplete() {
		super.onSeekComplete();
		int time = mProgressBar.getProgress() * getDuration() / 100;
		//如果已经初始化过的，直接seek到对于位置
		if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
			resolveDanmakuSeek(this, time);
		}
		else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
			//如果没有初始化过的，记录位置等待
			setDanmakuStartSeekPosition(time);
		}
	}

	/**
	 * 处理播放器在全屏切换时，弹幕显示的逻辑
	 * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
	 */
	@Override
	public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
		GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
		if (gsyBaseVideoPlayer != null) {
			DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
			//对弹幕设置偏移记录
			gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
			gsyVideoPlayer.setDanmakuShow(getDanmakuShow());
			onPrepareDanmaku(gsyVideoPlayer);
		}
		return gsyBaseVideoPlayer;
	}

	/**
	 * 处理播放器在退出全屏时，弹幕显示的逻辑
	 * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
	 */
	@Override
	protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
		super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
		if (gsyVideoPlayer != null) {
			DanmakuVideoPlayer gsyDanmaVideoPlayer = (DanmakuVideoPlayer) gsyVideoPlayer;
			setDanmakuShow(gsyDanmaVideoPlayer.getDanmakuShow());
			if (gsyDanmaVideoPlayer.getDanmakuView() != null &&
					gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
				resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
				resolveDanmakuShow();
				releaseDanmaku(gsyDanmaVideoPlayer);
			}
		}
	}


	private void initDanmaku() {
		// 设置最大显示行数
		HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
		maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
		// 设置是否禁止重叠
		HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
		overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
		overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

		mDanmakuContext = DanmakuContext.create();
		mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
				.setMaximumLines(maxLinesPair)
				.preventOverlapping(overlappingEnablePair);
		if (mDanmakuView != null) {
			mParser = new BaseDanmakuParser() {
				@Override
				protected Danmakus parse() {
					return new Danmakus();
				}
			};
			mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
				@Override
				public void updateTimer(DanmakuTimer timer) {
				}

				@Override
				public void drawingFinished() {

				}

				@Override
				public void danmakuShown(BaseDanmaku danmaku) {
				}

				@Override
				public void prepared() {
					if (getDanmakuView() != null) {
						getDanmakuView().start();
						if (getDanmakuStartSeekPosition() != -1) {
							resolveDanmakuSeek(DanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
							setDanmakuStartSeekPosition(-1);
						}
						resolveDanmakuShow();
					}
				}
			});
			mDanmakuView.enableDanmakuDrawingCache(true);
		}
	}

	/**
	 * 弹幕的显示与关闭
	 */
	private void resolveDanmakuShow() {
		post(() -> {
			if (mDanmakuShow) {
				if (!getDanmakuView().isShown())
					getDanmakuView().show();
			}
			else {
				if (getDanmakuView().isShown()) {
					getDanmakuView().hide();
				}
			}
		});
	}

	/**
	 * 开始播放弹幕
	 */
	private void onPrepareDanmaku(DanmakuVideoPlayer gsyVideoPlayer) {
		if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared()) {
			gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),
					gsyVideoPlayer.getDanmakuContext());
		}
	}

	/**
	 * 弹幕偏移
	 */
	private void resolveDanmakuSeek(DanmakuVideoPlayer gsyVideoPlayer, long time) {
		if (GSYVideoManager.instance().getMediaPlayer() != null && mHadPlay
				&& gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
			gsyVideoPlayer.getDanmakuView().seekTo(time);
		}
	}

	/**
	 * 释放弹幕控件
	 */
	private void releaseDanmaku(DanmakuVideoPlayer danmakuVideoPlayer) {
		if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
			Debuger.printfError("release Danmaku!");
			danmakuVideoPlayer.getDanmakuView().release();
		}
	}

	public BaseDanmakuParser getParser() {
		return mParser;
	}

	public DanmakuContext getDanmakuContext() {
		return mDanmakuContext;
	}

	public IDanmakuView getDanmakuView() {
		return mDanmakuView;
	}

	public long getDanmakuStartSeekPosition() {
		return mDanmakuStartSeekPosition;
	}

	public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
		this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
	}

	public void setDanmakuShow(boolean danmakuShow) {
		mDanmakuShow = danmakuShow;
	}

	public boolean getDanmakuShow() {
		return mDanmakuShow;
	}

	/**
	 * 模拟添加弹幕数据
	 */
	public void addDanmaku(BaseDanmu baseDanmu) {
		BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
		if (danmaku == null || mDanmakuView == null) {
			return;
		}
		danmaku.text = baseDanmu.getText();
		danmaku.padding = 5;
		danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
		danmaku.isLive = true;
		danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
		danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
		switch (baseDanmu.getType()) {
			case 1:
				danmaku.textColor = Color.WHITE;
				break;
			case 2:
				danmaku.textColor = Color.parseColor("#F06292");
				break;
			case 3:
				danmaku.textColor = Color.parseColor("#FF8A65");
				break;
			case 4:
				danmaku.textColor = Color.parseColor("#E57373");
				break;
			default:
				danmaku.textColor = Color.WHITE;
		}
		mDanmakuView.addDanmaku(danmaku);
	}

}
