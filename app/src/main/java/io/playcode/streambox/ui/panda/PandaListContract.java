package io.playcode.streambox.ui.panda;

import java.util.List;

import io.playcode.streambox.data.bean.PandaStreamListEntity;
import io.playcode.streambox.ui.base.BasePresenter;
import io.playcode.streambox.ui.base.BaseView;

/**
 * Created by anpoz on 2017/4/13.
 */

public interface PandaListContract {
    interface Presenter extends BasePresenter {
        void setGameType(String gameType);
        void requestRefresh();
        void requestUpdate();
    }

    interface View extends BaseView<Presenter> {
        void update(List<PandaStreamListEntity.DataEntity.ItemsEntity> pandaRoomEntities);
        void cancelRefreshing();
    }
}
