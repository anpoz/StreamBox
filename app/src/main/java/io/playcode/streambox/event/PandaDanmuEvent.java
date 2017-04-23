package io.playcode.streambox.event;

import io.playcode.streambox.data.bean.PandaDanmuEntity;

/**
 * Created by anpoz on 2017/4/15.
 */

public class PandaDanmuEvent {
    private PandaDanmuEntity mPandaDanmuEntity;
    private String live_id;

    public PandaDanmuEvent(PandaDanmuEntity pandaDanmuEntity, String live_id) {
        mPandaDanmuEntity = pandaDanmuEntity;
        this.live_id = live_id;
    }

    public PandaDanmuEntity getPandaDanmuEntity() {
        return mPandaDanmuEntity;
    }

    public void setPandaDanmuEntity(PandaDanmuEntity pandaDanmuEntity) {
        mPandaDanmuEntity = pandaDanmuEntity;
    }

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }
}
