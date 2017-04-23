package io.playcode.streambox.ui.info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.aloglibrary.ALog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.playcode.streambox.R;
import io.playcode.streambox.data.bean.StreamInfoEntity;
import io.playcode.streambox.util.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreamInfoFragment extends Fragment implements StreamInfoContract.View {


    @BindView(R.id.iv_room_cover)
    ImageView mIvRoomCover;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_room_title)
    TextView mTvRoomTitle;
    @BindView(R.id.tv_stream_time)
    TextView mTvStreamTime;
    @BindView(R.id.tv_person_num)
    TextView mTvPersonNum;
    @BindView(R.id.tv_room_id)
    TextView mTvRoomId;
    Unbinder unbinder;

    private StreamInfoContract.Presenter mPresenter;


    public StreamInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stream_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        new StreamInfoPresenter(this);
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.unSubscribe();
        super.onDestroyView();
        mPresenter.unSubscribe();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(StreamInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateInfo(StreamInfoEntity infoEntity) {
        mTvRoomTitle.setText(infoEntity.getLive_title());
        mTvNickname.setText(infoEntity.getLive_nickname());
        mTvRoomId.setText("房间ID:" + infoEntity.getLive_id());
        mTvPersonNum.setText("在线人数:" + infoEntity.getLive_online());
        ImageLoader.withCenterCrop(getContext(), infoEntity.getLive_img(), mIvRoomCover);

//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//        mTvStreamTime.setText(formatter.format(new Date(Long.valueOf(infoEntity.getPush_time()))));
    }
}
