package io.playcode.streambox.ui.commonstream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.aloglibrary.ALog;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.playcode.streambox.R;
import io.playcode.streambox.ui.chatroom.ChatroomFragment;
import io.playcode.streambox.ui.info.StreamInfoFragment;

public class CommonStreamActivity extends AppCompatActivity implements CommonStreamContract.View {

    @BindView(R.id.player)
    StandardGSYVideoPlayer mPlayer;
    @BindView(R.id.tab_room_switch)
    TabLayout mTabRoomSwitch;
    @BindView(R.id.vp_switch)
    ViewPager mVpSwitch;

    private static final String TAG_LIVE_ID = "tag live id";
    private static final String TAG_LIVE_TYPE = "tag live type";
    private static final String TAG_GAME_TYPE = "tag game type";
    private CommonStreamContract.Presenter mPresenter;
    private OrientationUtils mOrientationUtils;

    public static void startActivity(Activity activity, String live_type, String live_id, String game_type) {
        Intent intent = new Intent(activity, CommonStreamActivity.class);
        intent.putExtra(TAG_LIVE_ID, live_id);
        intent.putExtra(TAG_LIVE_TYPE, live_type);
        intent.putExtra(TAG_GAME_TYPE, game_type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_stream);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        boolean mediaCodec = preferences.getBoolean(getString(R.string.enableMediaCodec), false);
        boolean needShowWifiTip = preferences.getBoolean(getString(R.string.enableNeedShowWifiTip), true);
        boolean rotateViewAuto = preferences.getBoolean(getString(R.string.enableRotateViewAuto), false);
        boolean showFullAnimation = preferences.getBoolean(getString(R.string.enableShowFullAnimation), false);

        mOrientationUtils = new OrientationUtils(this, mPlayer);
        mPlayer.setIsTouchWiget(true);
        mPlayer.setRotateViewAuto(rotateViewAuto);
        mPlayer.setLockLand(true);
        mPlayer.setShowFullAnimation(showFullAnimation);
        mPlayer.setNeedShowWifiTip(needShowWifiTip);
        mPlayer.setNeedLockFull(true);
        mPlayer.findViewById(R.id.progress).setVisibility(View.INVISIBLE);
        mPlayer.findViewById(R.id.total).setVisibility(View.INVISIBLE);
        if (mediaCodec) {
            GSYVideoType.enableMediaCodec();
        } else {
            GSYVideoType.disableMediaCodec();
        }
        mPlayer.getFullscreenButton().setOnClickListener(v -> {
            mOrientationUtils.resolveByClick();
            mPlayer.startWindowFullscreen(CommonStreamActivity.this, true, true);
        });

        String liveId = getIntent().getStringExtra(TAG_LIVE_ID);
        String liveType = getIntent().getStringExtra(TAG_LIVE_TYPE);
        String gameType = getIntent().getStringExtra(TAG_GAME_TYPE);
        new CommonStreamPresenter(this);
        mPresenter.setLiveId(liveId);
        mPresenter.setLiveType(liveType);
        mPresenter.setGameType(gameType);
        mPresenter.subscribe();

        mVpSwitch.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabRoomSwitch.setupWithViewPager(mVpSwitch);
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        mPresenter.unSubscribe();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe();
        GSYVideoPlayer.releaseAllVideos();
        mOrientationUtils.releaseListener();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setPresenter(CommonStreamContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateStreamAddress(String url, String title) {
        ALog.d(url);
        try {
            mPlayer.setUp(url,false,null,title);
            mPlayer.startPlayLogic();
        } catch (Exception e) {
            e.printStackTrace();
            showError("线路不佳，直播流无法播放");
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mVpSwitch, error, Snackbar.LENGTH_LONG)
                .setAction("知道啦", v -> CommonStreamActivity.this.finish())
                .show();
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;
        private String[] titles = {"聊天", "主播"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mFragmentList.add(new ChatroomFragment());
            mFragmentList.add(new StreamInfoFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
