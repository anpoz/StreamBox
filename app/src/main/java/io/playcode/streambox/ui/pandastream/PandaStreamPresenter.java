package io.playcode.streambox.ui.pandastream;

import android.text.TextUtils;

import com.blankj.aloglibrary.ALog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import io.playcode.streambox.data.bean.PandaDanmuEntity;
import io.playcode.streambox.data.bean.PandaStreamDanmuServerEntity;
import io.playcode.streambox.data.bean.PandaStreamEntity;
import io.playcode.streambox.data.bean.StreamInfoEntity;
import io.playcode.streambox.data.source.AppRepository;
import io.playcode.streambox.event.PandaDanmuEvent;
import io.playcode.streambox.event.StreamInfoEvent;
import io.playcode.streambox.util.PandaDanmuUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anpoz on 2017/4/14.
 */

public class PandaStreamPresenter implements PandaStreamContract.Presenter {
    private PandaStreamContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private String roomId;
    private String address;
    private Subscription mSubscription;
    private Socket mSocket;
    private DataInputStream mInputStream;
    private DataOutputStream mOutputStream;
    private boolean isSocketConnect;

    public PandaStreamPresenter(PandaStreamContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        isSocketConnect = false;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        isSocketConnect = false;
        mCompositeDisposable.dispose();
        if (mSubscription != null) {
            mSubscription.cancel();
        }
        EventBus.getDefault().removeStickyEvent(PandaDanmuEvent.class);
    }

    @Override
    public void setRoomId(String id) {
        roomId = id;
        ALog.d(id);
        AppRepository.getInstance()
                .getPandaStreamRoomNewApi(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PandaStreamEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PandaStreamEntity pandaStreamEntity) {
                        //根据对熊猫tv新客户端的抓包分析，使用拼接的方式获取更稳定的直播流
                        //标清_small.flv,高清_mid.flv
                        //http://pl21.live.panda.tv/live_panda/370daaa1e9ec694600b3dfe962fcab04.flv?sign=38153f226814ed9969edd0419d0d30ee&time=&ts=58fcf80c&rid=-66778604
                        String cdn = pandaStreamEntity.getData().getInfo().getVideoinfo().getPlflag().split("_")[1];
                        address = "http://pl" + cdn + ".live.panda.tv/live_panda/" +
                                pandaStreamEntity.getData().getInfo().getVideoinfo().getRoom_key() +
                                ".flv" +
                                "?sign=" + pandaStreamEntity.getData().getInfo().getVideoinfo().getSign() +
                                "&time=" + pandaStreamEntity.getData().getInfo().getVideoinfo().getTs();
                        ALog.d(address);
                        StreamInfoEntity infoEntity = new StreamInfoEntity();
                        infoEntity.setLive_id(pandaStreamEntity.getData().getInfo().getRoominfo().getId());
                        infoEntity.setLive_img(pandaStreamEntity.getData().getInfo().getHostinfo().getAvatar());
                        infoEntity.setLive_nickname(pandaStreamEntity.getData().getInfo().getHostinfo().getName());
                        infoEntity.setLive_title(pandaStreamEntity.getData().getInfo().getRoominfo().getName());
                        infoEntity.setLive_online(pandaStreamEntity.getData().getInfo().getRoominfo().getPerson_num());
                        infoEntity.setPush_time(pandaStreamEntity.getData().getInfo().getRoominfo().getStart_time());
                        infoEntity.setLive_type("pandatv");
                        EventBus.getDefault().postSticky(new StreamInfoEvent(infoEntity));
                        mView.updateStreamAddress(address, pandaStreamEntity.getData().getInfo().getRoominfo().getName());
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
        isSocketConnect = true;
        Observable.create((ObservableOnSubscribe<ByteBuffer>) e -> {
            mSocket = new Socket(address[0], Integer.valueOf(address[1]));
            mInputStream = new DataInputStream(mSocket.getInputStream());
            mOutputStream = new DataOutputStream(mSocket.getOutputStream());
            mOutputStream.write(PandaDanmuUtil.getConnectData(danmuServerEntity.getData()));
            mOutputStream.flush();
            byte[] bytes;
            try {
                while (isSocketConnect) {
                    bytes = new byte[512];
                    mInputStream.read(bytes);
                    e.onNext(ByteBuffer.wrap(bytes));
                }
            } catch (SocketException e1) {
                e.onError(e1);
            }
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ByteBuffer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ByteBuffer s) {
                        parseDanmu(s.array());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        try {
                            if (mSocket != null && mSocket.isConnected()) {
                                mSocket.close();
                            }
                            if (mInputStream != null) {
                                mInputStream.close();
                            }
                            if (mOutputStream != null) {
                                mOutputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
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

                int danmuFromIndex = content.indexOf("{\"type");
                int danmuToIndex = content.indexOf("}}");

                String danmu;//存放弹幕

                danmu = content.substring(danmuFromIndex, danmuToIndex + 2);
                if (TextUtils.isEmpty(danmu)) {//为空不发射事件
                    e.onComplete();
                }
                e.onNext(danmu);
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
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<PandaDanmuEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        mSubscription = s;
                        s.request(10);
                    }

                    @Override
                    public void onNext(PandaDanmuEntity danmuEntity) {
                        EventBus.getDefault().post(new PandaDanmuEvent(danmuEntity, roomId));
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
