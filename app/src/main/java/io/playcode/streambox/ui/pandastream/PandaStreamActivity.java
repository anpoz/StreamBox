package io.playcode.streambox.ui.pandastream;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blankj.aloglibrary.ALog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.playcode.streambox.R;
import io.playcode.streambox.ui.pandastream.chatroom.PandaChatroomFragment;
import io.playcode.streambox.ui.pandastream.steamer.PandaStreamerInfoFragment;

public class PandaStreamActivity extends AppCompatActivity implements PandaStreamContract.View {

    @BindView(R.id.jc_player)
    JCVideoPlayerStandard mJcPlayer;
    @BindView(R.id.tab_room_switch)
    TabLayout mTabRoomSwitch;
    @BindView(R.id.vp_switch)
    ViewPager mVpSwitch;

    private static final String TAG_ROOM_ID = "tag room id";
    private PandaStreamContract.Presenter mPresenter;

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

        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        String id = getIntent().getStringExtra(TAG_ROOM_ID);
        new PandaStreamPresenter(this);
        mPresenter.subscribe();
        mPresenter.setRoomId(id);

        mVpSwitch.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabRoomSwitch.setupWithViewPager(mVpSwitch);
    }

    @Override
    public void onBackPressed() {
        if (mJcPlayer.backPress()) {
            return;
        }
        mPresenter.unSubscribe();
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mJcPlayer.releaseAllVideos();
    }

    @Override
    public void setPresenter(PandaStreamContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateStreamAddress(String url, String title) {
        ALog.d(url);
        mJcPlayer.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_NORMAL, title);
        mJcPlayer.startButton.performClick();
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;
        private String[] titles = {"聊天", "主播"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mFragmentList.add(new PandaChatroomFragment());
            mFragmentList.add(new PandaStreamerInfoFragment());
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
