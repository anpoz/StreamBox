package io.playcode.streambox.ui.common;

import java.util.List;

import io.playcode.streambox.data.bean.CommonStreamListEntity;
import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/16.
 */

public interface CommonListContract {
    interface Presenter extends BasePresenter {
        void setLiveType(String liveType);

        void setGameType(String gameType);

        void requestRefresh();

        void requestUpdate();
    }

    interface View extends BaseView<Presenter> {
        void update(List<CommonStreamListEntity.ResultEntity> resultEntityList);

        void showError(String error);
    }
}
