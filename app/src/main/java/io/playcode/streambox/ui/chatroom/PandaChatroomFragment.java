package io.playcode.streambox.ui.chatroom;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.playcode.streambox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PandaChatroomFragment extends Fragment implements PandaChatroomContract.View {


    @BindView(R.id.rv_panda_chatroom)
    RecyclerView mRvPandaChatroom;
    Unbinder unbinder;

    private PandaChatroomContract.Presenter mPresenter;

    public PandaChatroomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_panda_chatroom, container, false);
        unbinder = ButterKnife.bind(this, view);
        new PandaChatroomPresenter(this);
        mPresenter.subscribe();

        //init
        mRvPandaChatroom.setHasFixedSize(true);
        mRvPandaChatroom.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(PandaChatroomContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void addDanmu() {

    }

    @Override
    public void addCommit(List<CharSequence> charSequenceList) {
        if (getContext() != null) {
            mRvPandaChatroom.setAdapter(new ListAdapter(getContext(), charSequenceList, R.layout.item_chat_list));
            mRvPandaChatroom.scrollToPosition(charSequenceList.size() - 1);
        }
    }

    private static class ListAdapter extends SuperAdapter<CharSequence> {

        public ListAdapter(Context context, List<CharSequence> items, @LayoutRes int layoutResId) {
            super(context, items, layoutResId);
        }

        @Override
        public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CharSequence item) {
            holder.setText(R.id.tv_commit, item);
        }
    }
}
