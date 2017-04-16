package io.playcode.streambox.event;

import io.playcode.streambox.data.bean.PandaDanmuEntity;

/**
 * Created by anpoz on 2017/4/15.
 */

public class PandaDanmuEvent {
    private PandaDanmuEntity mPandaDanmuEntity;

    public PandaDanmuEvent(PandaDanmuEntity pandaDanmuEntity) {
        mPandaDanmuEntity = pandaDanmuEntity;
    }

    public PandaDanmuEntity getPandaDanmuEntity() {
        return mPandaDanmuEntity;
    }

    public void setPandaDanmuEntity(PandaDanmuEntity pandaDanmuEntity) {
        mPandaDanmuEntity = pandaDanmuEntity;
    }
}
