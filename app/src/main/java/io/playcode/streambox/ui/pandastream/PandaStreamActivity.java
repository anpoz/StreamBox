package io.playcode.streambox.ui.pandastream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.SharedPreferencesCompat;
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

import javax.xml.transform.sax.TransformerHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.playcode.streambox.R;
import io.playcode.streambox.ui.chatroom.ChatroomFragment;
import io.playcode.streambox.ui.info.StreamInfoFragment;
import io.playcode.streambox.widget.DanmuVideoPlayer;

public class PandaStreamActivity extends AppCompatActivity implements PandaStreamContract.View {

    @BindView(R.id.tab_room_switch)
    TabLayout mTabRoomSwitch;
    @BindView(R.id.vp_switch)
    ViewPager mVpSwitch;
    @BindView(R.id.player)
    StandardGSYVideoPlayer mPlayer;

    private static final String TAG_ROOM_ID = "tag room id";

    private PandaStreamContract.Presenter mPresenter;
    private OrientationUtils mOrientationUtils;

    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, PandaStreamActivity.class);
        intent.putExtra(TAG_ROOM_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panda_stream);
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
            mPlayer.startWindowFullscreen(PandaStreamActivity.this, true, true);
        });

        String id = getIntent().getStringExtra(TAG_ROOM_ID);
        new PandaStreamPresenter(this);
        mPresenter.subscribe();
        mPresenter.setRoomId(id);

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
    public void setPresenter(PandaStreamContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateStreamAddress(String url, String title) {
        ALog.d(url);
        try {
            mPlayer.setUp(url, false, null, title);
            mPlayer.startPlayLogic();
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(mVpSwitch, "线路不佳，直播流无法播放", Snackbar.LENGTH_LONG)
                    .setAction("知道啦", v -> PandaStreamActivity.this.finish())
                    .show();
        }
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
