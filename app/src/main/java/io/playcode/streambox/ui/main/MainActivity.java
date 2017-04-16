package io.playcode.streambox.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.playcode.streambox.R;
import io.playcode.streambox.ui.common.CommonCategoryFragment;
import io.playcode.streambox.ui.panda.PandaCategoryFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    private MenuItem preMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_about:
                    return true;
                case R.id.menu_search:
                    return true;
            }
            return false;
        });

        mViewpager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //关联viewpager与BottomNavigationView
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (preMenuItem != null) {
                    preMenuItem.setChecked(false);
                } else {
                    mBottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                mBottomNavigation.getMenu().getItem(position).setChecked(true);
                preMenuItem = mBottomNavigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_panda:
                    mViewpager.setCurrentItem(0);
                    break;
                case R.id.menu_douyu:
                    mViewpager.setCurrentItem(1);
                    break;
                case R.id.menu_quanmin:
                    mViewpager.setCurrentItem(2);
                    break;
                case R.id.menu_zhanqi:
                    mViewpager.setCurrentItem(3);
                    break;
            }
            return false;
        });
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mFragmentList.add(new PandaCategoryFragment());

            mFragmentList.add(CommonCategoryFragment.newInstance("douyu"));
            mFragmentList.add(CommonCategoryFragment.newInstance("quanmin"));
            mFragmentList.add(CommonCategoryFragment.newInstance("zhanqi"));
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
