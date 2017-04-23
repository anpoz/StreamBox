package io.playcode.streambox.ui.panda;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.playcode.streambox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PandaCategoryFragment extends Fragment {


    @BindView(R.id.tab_cate)
    TabLayout mTabCate;
    @BindView(R.id.vp_cate)
    ViewPager mVpCate;
    Unbinder unbinder;

    public PandaCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_panda_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mVpCate.setAdapter(new PagerAdapter(getChildFragmentManager()));
        mTabCate.setupWithViewPager(mVpCate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;
        private List<String> mTitles;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mTitles = new ArrayList<>();

            mFragmentList.add(PandaListFragment.newInstance("lol"));
            mFragmentList.add(PandaListFragment.newInstance("hwzb"));
            mFragmentList.add(PandaListFragment.newInstance("yzdr"));
            mFragmentList.add(PandaListFragment.newInstance("overwatch"));
            mFragmentList.add(PandaListFragment.newInstance("kingglory"));
            mFragmentList.add(PandaListFragment.newInstance("hearthstone"));
            mFragmentList.add(PandaListFragment.newInstance("zhuji"));

            mTitles.add("英雄联盟");
            mTitles.add("户外直播");
            mTitles.add("熊猫星秀");
            mTitles.add("守望先锋");
            mTitles.add("王者荣耀");
            mTitles.add("炉石传说");
            mTitles.add("主机游戏");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
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
