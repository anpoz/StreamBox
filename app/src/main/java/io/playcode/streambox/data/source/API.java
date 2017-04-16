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
    //http://api.m.panda.tv/ajax_get_live_list_by_cate
    @POST("ajax_get_live_list_by_cate")
    @FormUrlEncoded
    Observable<PandaStreamListEntity> getPandaStreamList(@Field("plat") String platform,
                                                         @Field("version") String version,
                                                         @Field("cate") String category,
                                                         @Field("order") String order,
                                                         @Field("pageno") String pageNo,
                                                         @Field("pagenum") int pageNum,
                                                         @Field("status") String status);

    //http://room.api.m.panda.tv/index.php?method=room.shareapi&roomid=7000
    @GET("index.php")
    Observable<PandaStreamEntity> getPandaStreamRoom(@Query("method") String method,
                                                     @Query("roomid") String roomid);

    //http://riven.panda.tv/chatroom/getinfo?roomid=7000
    @GET("chatroom/getinfo")
    Observable<PandaStreamDanmuServerEntity> getDanmuServer(@Query("roomid") String id);

    //http://api.maxjia.com/api/live/list/?offset=0&limit=20&live_type=douyu&game_type=lol
    @GET("api/live/list/")
    Observable<CommonStreamListEntity> getCommonStreamList(@Query("offset") int offset,
                                                           @Query("limit") int limit,
                                                           @Query("live_type") String live_type,
                                                           @Query("game_type") String game_type);

    //http://api.maxjia.com/api/live/detail/?live_type=douyu&live_id=56040&game_type=lol
    @GET("/api/live/detail/")
    Observable<CommonStreamEntity> getCommonStream(@Query("live_type") String live_type,
                                                   @Query("live_id") String live_id,
                                                   @Query("game_type") String game_type);
}
