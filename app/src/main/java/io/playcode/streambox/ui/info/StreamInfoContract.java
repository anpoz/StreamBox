package io.playcode.streambox.ui.info;

import io.playcode.streambox.data.bean.StreamInfoEntity;
import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/16.
 */

public interface StreamInfoContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void updateInfo(StreamInfoEntity infoEntity);
    }
}
