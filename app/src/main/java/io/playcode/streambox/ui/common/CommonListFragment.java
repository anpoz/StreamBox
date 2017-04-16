package io.playcode.streambox.ui.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.aloglibrary.ALog;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.playcode.streambox.R;
import io.playcode.streambox.data.bean.CommonStreamListEntity;
import io.playcode.streambox.ui.commonstream.CommonStreamActivity;
import io.playcode.streambox.util.FormatUtil;
import io.playcode.streambox.util.ImageLoader;

public class CommonListFragment extends Fragment implements CommonListContract.View {
    private static final String LIVE_TYPE = "live type";
    private static final String GAME_TYPE = "game type";
    @BindView(R.id.rv_common_list)
    RecyclerView mRvCommonList;
    @BindView(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyrefreshlayout;
    Unbinder unbinder;

    private CommonListContract.Presenter mPresenter;

    public CommonListFragment() {
        // Required empty public constructor
    }

    public static CommonListFragment newInstance(String liveType, String gameType) {
        CommonListFragment fragment = new CommonListFragment();
        Bundle args = new Bundle();
        args.putString(LIVE_TYPE, liveType);
        args.putString(GAME_TYPE, gameType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String liveType = getArguments().getString(LIVE_TYPE);
        String gameType = getArguments().getString(GAME_TYPE);

        new CommonListPresenter(this);
        mPresenter.setLiveType(liveType);
        mPresenter.setGameType(gameType);
        mPresenter.subscribe();
        mPresenter.requestRefresh();

        View view = inflater.inflate(R.layout.fragment_common_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mRvCommonList.setHasFixedSize(true);
        mRvCommonList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mSwipyrefreshlayout.setOnRefreshListener(direction -> {
            if (direction == SwipyRefreshLayoutDirection.TOP) {//上拉
                mPresenter.requestRefresh();
            } else {//下拉
                mPresenter.requestUpdate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(CommonListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void update(List<CommonStreamListEntity.ResultEntity> resultEntityList) {
        mSwipyrefreshlayout.setRefreshing(false);
        mRvCommonList.setAdapter(new ListAdapter(getContext(), resultEntityList, R.layout.item_live_list));

        if (resultEntityList.size() > 20) {
            int endPosition = resultEntityList.size() - resultEntityList.size() % 20;
            mRvCommonList.scrollToPosition(endPosition);
        }
    }

    private class ListAdapter extends SuperAdapter<CommonStreamListEntity.ResultEntity> {

        public ListAdapter(Context context, List<CommonStreamListEntity.ResultEntity> items, @LayoutRes int layoutResId) {
            super(context, items, layoutResId);
        }

        @Override
        public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CommonStreamListEntity.ResultEntity item) {
            holder.setText(R.id.tv_room_title, item.getLive_title());
            holder.setText(R.id.tv_person_num, FormatUtil.formatPersonNum(String.valueOf(item.getLive_online())));
            holder.setText(R.id.tv_nickname, item.getLive_nickname());
            ImageLoader.withCenterCrop(getContext(), item.getLive_img(), holder.findViewById(R.id.iv_room_cover));
            holder.findViewById(R.id.card_root).setOnClickListener(
                    v -> CommonStreamActivity.startActivity(getActivity(),
                            item.getLive_name(),
                            item.getLive_id(),
                            item.getGame_type()));
        }
    }
}
