package io.playcode.streambox.ui.pandastream;

import android.text.TextUtils;

import com.blankj.aloglibrary.ALog;
import com.google.gson.Gson;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.future.Cancellable;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.playcode.streambox.data.bean.PandaDanmuEntity;
import io.playcode.streambox.data.bean.PandaStreamDanmuServerEntity;
import io.playcode.streambox.data.bean.PandaStreamEntity;
import io.playcode.streambox.data.source.AppRepository;
import io.playcode.streambox.event.PandaDanmuEvent;
import io.playcode.streambox.util.PandaDanmuUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by anpoz on 2017/4/14.
 */

public class PandaStreamPresenter implements PandaStreamContract.Presenter {
    private PandaStreamContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private String roomId;
    private String address;
    private Cancellable mCancellable;

    public PandaStreamPresenter(PandaStreamContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
        mCancellable.cancel();
        EventBus.getDefault().removeStickyEvent(PandaDanmuEvent.class);
    }

    @Override
    public void setRoomId(String id) {
        roomId = id;
        ALog.d(id);
        AppRepository.getInstance()
                .getPandaStreamRoom(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PandaStreamEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PandaStreamEntity pandaStreamEntity) {
                        address = pandaStreamEntity.getData().getVideoinfo().getAddress();
                        mView.updateStreamAddress(address, pandaStreamEntity.getData().getRoominfo().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        AppRepository.getInstance()
                .getPandaDanmuServer(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PandaStreamDanmuServerEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PandaStreamDanmuServerEntity pandaStreamDanmuServerEntity) {
                        connectDanmuSocket(pandaStreamDanmuServerEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void connectDanmuSocket(PandaStreamDanmuServerEntity danmuServerEntity) {
        String[] address = danmuServerEntity.getData().getChat_addr_list().get(0).split(":");
        mCancellable = AsyncServer.getDefault().connectSocket(address[0], Integer.valueOf(address[1]), (ex, socket) ->
                Util.writeAll(socket, PandaDanmuUtil.getConnectData(danmuServerEntity.getData()), ex1 -> {
                    socket.setDataCallback((emitter, bb) ->
                            parseDanmu(bb.getAllByteArray()));
                }));
    }

    private void parseDanmu(byte[] data) {
        Flowable.create((FlowableOnSubscribe<String>) e -> {
            //弹幕协议头
            if (data[0] == PandaDanmuUtil.RECEIVE_MSG[0] &&
                    data[1] == PandaDanmuUtil.RECEIVE_MSG[1] &&
                    data[2] == PandaDanmuUtil.RECEIVE_MSG[2] &&
                    data[3] == PandaDanmuUtil.RECEIVE_MSG[3]) {

                //{"type":"1","time":1477356608,"data":{"from":{"__plat":"android","identity":"30","level":"4","msgcolor":"","nickName":"看了还说了","rid":"45560306","sp_identity":"0","userName":""},"to":{"toroom":"15161"},"content":"我去"}}
                String content = new String(data, "UTF-8");

                //第一条弹幕
                int danmuFromIndex = content.indexOf("{\"type");
                int danmuToIndex = content.indexOf("}}");

                //第二条弹幕（可有）
                int danmuFromIndex_2 = content.lastIndexOf("{\"type");
                int danmuToIndex_2 = content.lastIndexOf("}}");

                String danmu;//存放弹幕

                danmu = content.substring(danmuFromIndex, danmuToIndex + 2);
                if (TextUtils.isEmpty(danmu)) {//为空不发射事件
                    e.onComplete();
                }
                e.onNext(danmu);

                //如果存在第二条弹幕
                if (!(danmuFromIndex == danmuFromIndex_2 &&
                        danmuToIndex == danmuToIndex_2)) {
                    danmu = content.substring(danmuFromIndex_2, danmuToIndex_2 + 2);
                    if (TextUtils.isEmpty(danmu)) {
                        e.onComplete();
                    }
                    e.onNext(danmu);
                }
                e.onComplete();
            } else if (data[0] == PandaDanmuUtil.HEART_BEAT_RESPONSE[0] &&//心跳包
                    data[1] == PandaDanmuUtil.HEART_BEAT_RESPONSE[1] &&
                    data[2] == PandaDanmuUtil.HEART_BEAT_RESPONSE[2] &&
                    data[3] == PandaDanmuUtil.HEART_BEAT_RESPONSE[3]) {
                e.onComplete();
            }
        }, BackpressureStrategy.DROP)
                .flatMap(new Function<String, Publisher<PandaDanmuEntity>>() {
                    @Override
                    public Publisher<PandaDanmuEntity> apply(@NonNull String s) throws Exception {
                        return Flowable.create(e -> {
                            //解析弹幕Json
                            PandaDanmuEntity danmu = new Gson().fromJson(s, PandaDanmuEntity.class);
                            e.onNext(danmu);
                            e.onComplete();
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new FlowableSubscriber<PandaDanmuEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(10);
                    }

                    @Override
                    public void onNext(PandaDanmuEntity danmuEntity) {
                        EventBus.getDefault().post(new PandaDanmuEvent(danmuEntity));
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
