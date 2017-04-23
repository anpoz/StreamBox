package io.playcode.streambox.data.source;

import io.playcode.streambox.data.bean.CommonStreamEntity;
import io.playcode.streambox.data.bean.CommonStreamListEntity;
import io.playcode.streambox.data.bean.PandaStreamDanmuServerEntity;
import io.playcode.streambox.data.bean.PandaStreamListEntity;
import io.playcode.streambox.data.bean.PandaStreamEntity;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by anpoz on 2017/4/13.
 */

public interface API {
    //http://api.m.panda.tv/ajax_get_live_list_by_cate?cate=war3&pageno=1&pagenum=20&sproom=1&banner=1&slider=1&__version=3.0.6.3203&__plat=android&__channel=wandoujia
    @GET("ajax_get_live_list_by_cate?pageno=1&pagenum=20&sproom=1&banner=1&slider=1&__version=3.0.6.3203&__plat=android&__channel=wandoujia")
    Observable<PandaStreamListEntity> getPandaStreamList(@Query("cate") String category,
                                                         @Query("pageno") String pageno,
                                                         @Query("pagenum") String pagenum);

    //http://room.api.m.panda.tv/index.php?method=room.shareapi&roomid=7000
    @GET("index.php")
    Observable<PandaStreamEntity> getPandaStreamRoom(@Query("method") String method,
                                                     @Query("roomid") String roomid);

    //http://api.m.panda.tv/ajax_get_liveroom_baseinfo?roomid=769965&slaveflag=1&type=json&__version=3.0.6.3203&__plat=android&__channel=wandoujia
    @GET("ajax_get_liveroom_baseinfo?slaveflag=1&type=json&__version=3.0.6.3203&__plat=android&__channel=wandoujia")
    Observable<PandaStreamEntity> getPandaStreamRoomNewApi(@Query("roomid") String roomid);

    //http://riven.panda.tv/chatroom/getinfo?roomid=7000
    @GET("chatroom/getinfo")
    Observable<PandaStreamDanmuServerEntity> getDanmuServer(@Query("roomid") String id);

    //http://api.maxjia.com/api/live/list/?offset=0&limit=20&live_type=douyu&game_type=lol
    @GET("api/live/list/")
    Observable<CommonStreamListEntity> getCommonStreamList(@Query("offset") int offset,
                                                           @Query("limit") String limit,
                                                           @Query("live_type") String live_type,
                                                           @Query("game_type") String game_type);

    //http://api.maxjia.com/api/live/detail/?live_type=douyu&live_id=56040&game_type=lol
    @GET("/api/live/detail/")
    Observable<CommonStreamEntity> getCommonStream(@Query("live_type") String live_type,
                                                   @Query("live_id") String live_id,
                                                   @Query("game_type") String game_type);
}
