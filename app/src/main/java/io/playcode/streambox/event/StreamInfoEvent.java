package io.playcode.streambox.event;

import io.playcode.streambox.data.bean.StreamInfoEntity;

/**
 * Created by anpoz on 2017/4/16.
 */

public class StreamInfoEvent {
    private StreamInfoEntity mStreamInfoEntity;

    public StreamInfoEvent(StreamInfoEntity streamInfoEntity) {
        mStreamInfoEntity = streamInfoEntity;
    }

    public StreamInfoEntity getStreamInfoEntity() {
        return mStreamInfoEntity;
    }

    public void setStreamInfoEntity(StreamInfoEntity streamInfoEntity) {
        mStreamInfoEntity = streamInfoEntity;
    }
}
