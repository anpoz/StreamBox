package io.playcode.streambox.data.source;

import io.playcode.streambox.data.bean.CommonStreamEntity;
import io.playcode.streambox.data.bean.CommonStreamListEntity;
import io.playcode.streambox.data.bean.PandaStreamDanmuServerEntity;
import io.playcode.streambox.data.bean.PandaStreamListEntity;
import io.playcode.streambox.data.bean.PandaStreamEntity;
import io.reactivex.Observable;

/**
 * Created by anpoz on 2017/4/13.
 */

public class AppRepository {
    private static AppRepository mAppRepository;
    private static final String PANDA_LIST_BASE_URL = "http://api.m.panda.tv/";
    private static final String PANDA_ROOM_BASE_URL = "http://room.api.m.panda.tv/";
    private static final String PANDA_RIVEN_BASE_URL = "http://riven.panda.tv/";
    private static final String LIVE_BASE_URL = "http://api.maxjia.com/";
    private static final String PAGE_LIMIT = "20";

    private AppRepository() {

    }

    public static AppRepository getInstance() {
        if (mAppRepository == null) {
            synchronized (AppRepository.class) {
                if (mAppRepository == null) {
                    mAppRepository = new AppRepository();
                }
            }
        }
        return mAppRepository;
    }

    public Observable<PandaStreamListEntity> getPandaStreamList(String category, String pageno) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, PANDA_LIST_BASE_URL)
                .getPandaStreamList(category, pageno, PAGE_LIMIT);
    }

    public Observable<PandaStreamEntity> getPandaStreamRoom(String roomid) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, PANDA_ROOM_BASE_URL)
                .getPandaStreamRoom("room.shareapi", roomid);
    }

    public Observable<PandaStreamEntity> getPandaStreamRoomNewApi(String roomid) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, PANDA_LIST_BASE_URL)
                .getPandaStreamRoomNewApi(roomid);
    }

    public Observable<PandaStreamDanmuServerEntity> getPandaDanmuServer(String roomid) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, PANDA_RIVEN_BASE_URL)
                .getDanmuServer(roomid);
    }

    public Observable<CommonStreamListEntity> getCommonStreamList(int offset,
                                                                  String live_type,
                                                                  String game_type) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, LIVE_BASE_URL)
                .getCommonStreamList(offset, PAGE_LIMIT, live_type, game_type);
    }

    public Observable<CommonStreamEntity> getCommonStreamDetail(String live_type,
                                                                String live_id,
                                                                String game_type) {
        return RetrofitHelper.getInstance()
                .configRetrofit(API.class, LIVE_BASE_URL)
                .getCommonStream(live_type, live_id, game_type);
    }
}
