package io.playcode.streambox.ui.info;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.playcode.streambox.event.StreamInfoEvent;

/**
 * Created by anpoz on 2017/4/16.
 */

public class StreamInfoPresenter implements StreamInfoContract.Presenter {
    private StreamInfoContract.View mView;

    public StreamInfoPresenter(StreamInfoContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void unSubscribe() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStreamInfoEvent(StreamInfoEvent event) {
        mView.updateInfo(event.getStreamInfoEntity());
    }
}
