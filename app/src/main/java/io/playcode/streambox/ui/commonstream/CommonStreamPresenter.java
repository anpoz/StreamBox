package io.playcode.streambox.ui.commonstream;

import org.greenrobot.eventbus.EventBus;

import io.playcode.streambox.data.bean.CommonStreamEntity;
import io.playcode.streambox.data.bean.StreamInfoEntity;
import io.playcode.streambox.data.source.AppRepository;
import io.playcode.streambox.event.StreamInfoEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anpoz on 2017/4/16.
 */

public class CommonStreamPresenter implements CommonStreamContract.Presenter {
    private CommonStreamContract.View mView;
    private String liveId;
    private String liveType;
    private String gameType;
    private String address;
    private CompositeDisposable mCompositeDisposable;

    public CommonStreamPresenter(CommonStreamContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        AppRepository.getInstance()
                .getCommonStreamDetail(liveType, liveId, gameType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonStreamEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CommonStreamEntity commonStreamEntity) {
                        address = commonStreamEntity.getResult().getStream_list().get(0).getUrl();
                        StreamInfoEntity infoEntity = new StreamInfoEntity();
                        infoEntity.setLive_title(commonStreamEntity.getResult().getLive_title());
                        infoEntity.setLive_type(liveType);
                        infoEntity.setPush_time(commonStreamEntity.getResult().getPush_time());
                        infoEntity.setLive_id(commonStreamEntity.getResult().getLive_id());
                        infoEntity.setLive_online(commonStreamEntity.getResult().getLive_online() + "");
                        infoEntity.setLive_nickname(commonStreamEntity.getResult().getLive_nickname());
                        infoEntity.setLive_img(commonStreamEntity.getResult().getLive_img());
                        EventBus.getDefault().postSticky(new StreamInfoEvent(infoEntity));
                        mView.updateStreamAddress(address, commonStreamEntity.getResult().getLive_title());
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

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    @Override
    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    @Override
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
