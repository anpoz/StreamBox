package io.playcode.streambox.ui.pandastream;

import io.playcode.streambox.data.bean.BaseDanmu;
import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/14.
 */

public interface PandaStreamContract {
    interface Presenter extends BasePresenter {
        void setRoomId(String id);
    }

    interface View extends BaseView<Presenter> {
        void updateStreamAddress(String url, String title);
        void addDanmu(BaseDanmu danmu);
    }
}
