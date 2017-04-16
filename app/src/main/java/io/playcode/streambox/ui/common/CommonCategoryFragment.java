package io.playcode.streambox.ui.common;

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
import io.playcode.streambox.ui.panda.PandaCategoryFragment;
import io.playcode.streambox.ui.panda.PandaListFragment;

public class CommonCategoryFragment extends Fragment {
    private static final String LIVE_TYPE = "live type";
    @BindView(R.id.tab_cate)
    TabLayout mTabCate;
    @BindView(R.id.vp_cate)
    ViewPager mVpCate;
    Unbinder unbinder;
    private String liveType;

    public CommonCategoryFragment() {
        // Required empty public constructor
    }

    public static CommonCategoryFragment newInstance(String liveType) {
        CommonCategoryFragment fragment = new CommonCategoryFragment();
        Bundle args = new Bundle();
        args.putString(LIVE_TYPE, liveType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        liveType = getArguments().getString(LIVE_TYPE);
        View view = inflater.inflate(R.layout.fragment_common_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mVpCate.setAdapter(new PagerAdapter(getChildFragmentManager(), liveType));
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

        public PagerAdapter(FragmentManager fm, String liveType) {
            super(fm);
            mTitles = new ArrayList<>();
            mFragmentList = new ArrayList<>();
            mFragmentList.add(CommonListFragment.newInstance(liveType, "lol"));
            mFragmentList.add(CommonListFragment.newInstance(liveType, "ow"));
            mFragmentList.add(CommonListFragment.newInstance(liveType, "dota2"));
            mFragmentList.add(CommonListFragment.newInstance(liveType, "hs"));
            mFragmentList.add(CommonListFragment.newInstance(liveType, "csgo"));

            mTitles.add("英雄联盟");
            mTitles.add("守望先锋");
            mTitles.add("Dota2");
            mTitles.add("炉石传说");
            mTitles.add("CS:GO");
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
