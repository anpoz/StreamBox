package io.playcode.streambox.ui.common;

import java.util.ArrayList;
import java.util.List;

import io.playcode.streambox.data.bean.CommonStreamListEntity;
import io.playcode.streambox.data.source.AppRepository;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anpoz on 2017/4/16.
 */

public class CommonListPresenter implements CommonListContract.Presenter {
    private CommonListContract.View mView;
    private String liveType;
    private String gameType;
    private List<CommonStreamListEntity.ResultEntity> mResultEntityList;
    private int offset;
    private CompositeDisposable mCompositeDisposable;

    public CommonListPresenter(CommonListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mResultEntityList = new ArrayList<>();
        offset = 0;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    @Override
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public void requestRefresh() {
        offset = 0;
        AppRepository.getInstance()
                .getCommonStreamList(offset, liveType, gameType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonStreamListEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CommonStreamListEntity commonStreamListEntity) {
                        mResultEntityList.clear();
                        mResultEntityList.addAll(commonStreamListEntity.getResult());
                        mView.update(mResultEntityList);
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
    public void requestUpdate() {
        offset++;
        AppRepository.getInstance()
                .getCommonStreamList(offset, liveType, gameType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonStreamListEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CommonStreamListEntity commonStreamListEntity) {
                        mResultEntityList.clear();
                        mResultEntityList.addAll(commonStreamListEntity.getResult());
                        mView.update(mResultEntityList);
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
}
