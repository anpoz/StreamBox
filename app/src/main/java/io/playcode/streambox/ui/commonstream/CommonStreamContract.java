package io.playcode.streambox.ui.commonstream;

import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/16.
 */

public interface CommonStreamContract {
    interface Presenter extends BasePresenter {
        void setLiveId(String liveId);

        void setLiveType(String liveType);

        void setGameType(String gameType);
    }

    interface View extends BaseView<Presenter> {
        void updateStreamAddress(String url, String title);
        void showError(String error);
    }
}
