package io.playcode.streambox.ui.panda;


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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.playcode.streambox.R;
import io.playcode.streambox.data.bean.PandaStreamListEntity;
import io.playcode.streambox.ui.pandastream.PandaStreamActivity;
import io.playcode.streambox.util.FormatUtil;
import io.playcode.streambox.util.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class PandaListFragment extends Fragment implements PandaListContract.View {
    private static final String GAME_TYPE = "game type";
    @BindView(R.id.rv_panda_list)
    RecyclerView mRvPandaList;
    Unbinder unbinder;
    @BindView(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyrefreshlayout;
    private PandaListContract.Presenter mPresenter;

    public PandaListFragment() {
        // Required empty public constructor
    }

    public static PandaListFragment newInstance(String liveType) {
        PandaListFragment fragment = new PandaListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_TYPE, liveType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String type = (String) getArguments().getCharSequence(GAME_TYPE);

        new PandaListPresenter(this);
        mPresenter.setGameType(type);
        mPresenter.subscribe();
        mPresenter.requestRefresh();

        View view = inflater.inflate(R.layout.fragment_panda_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        mRvPandaList.setHasFixedSize(true);
        mRvPandaList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mSwipyrefreshlayout.setOnRefreshListener(direction -> {
            if (direction == SwipyRefreshLayoutDirection.TOP) {//上拉
                mPresenter.requestRefresh();
            } else {//下拉
                mPresenter.requestUpdate();
            }
        });
    }

    @Override
    public void setPresenter(PandaListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void update(List<PandaStreamListEntity.DataEntity.ItemsEntity> pandaRoomEntities) {
        mSwipyrefreshlayout.setRefreshing(false);
        mRvPandaList.setAdapter(new ListAdapter(getContext(), pandaRoomEntities, R.layout.item_live_list));
        mRvPandaList.setHasFixedSize(true);
        mRvPandaList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (pandaRoomEntities.size() > 20) {
            int endPosition = pandaRoomEntities.size() - pandaRoomEntities.size() % 20;
            mRvPandaList.scrollToPosition(endPosition);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class ListAdapter extends SuperAdapter<PandaStreamListEntity.DataEntity.ItemsEntity> {

        public ListAdapter(Context context, List<PandaStreamListEntity.DataEntity.ItemsEntity> items, @LayoutRes int layoutResId) {
            super(context, items, layoutResId);
        }

        @Override
        public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PandaStreamListEntity.DataEntity.ItemsEntity item) {
            holder.setText(R.id.tv_room_title, item.getName());
            holder.setText(R.id.tv_person_num, FormatUtil.formatPersonNum(item.getPerson_num()));
            holder.setText(R.id.tv_nickname, item.getUserinfo().getNickName());
            ImageLoader.withCenterCrop(getContext(), item.getPictures().getImg(), holder.findViewById(R.id.iv_room_cover));
            holder.findViewById(R.id.card_root).setOnClickListener(v -> PandaStreamActivity.startActivity(getActivity(), item.getId()));
        }
    }
}
