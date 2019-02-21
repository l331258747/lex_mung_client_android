package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.lex_mung.client_android.mvp.contract.PeerScreenContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO_INSTITUTIONS;
import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO_TYPE;


@ActivityScope
public class PeerScreenPresenter extends BasePresenter<PeerScreenContract.Model, PeerScreenContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<PeerScreenEntity> peerScreenEntityList = new ArrayList<>();

    private int pos;
    private int regionId1;
    private int regionId2;

    @Inject
    public PeerScreenPresenter(PeerScreenContract.Model model, PeerScreenContract.View rootView) {
        super(model, rootView);
    }

    public void setPeerScreenEntityList(List<PeerScreenEntity> peerScreenEntityList) {
        this.peerScreenEntityList.addAll(peerScreenEntityList);
        if (peerScreenEntityList.size() <= 0) {
            getPeerSearchList();
        } else {
            mRootView.setAdapter(peerScreenEntityList);
        }
    }

    public List<PeerScreenEntity> getPeerSearchEntityList() {
        return peerScreenEntityList;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setRegionId1(int regionId1) {
        this.regionId1 = regionId1;
    }

    public void setRegionId2(int regionId2) {
        this.regionId2 = regionId2;
    }

    public int getRegionId1() {
        return regionId1;
    }

    public int getRegionId2() {
        return regionId2;
    }

    private void getPeerSearchList() {
        mModel.getPeerSearchList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<PeerScreenEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<PeerScreenEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            peerScreenEntityList.addAll(baseResponse.getData());
                            mRootView.setAdapter(baseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = PEER_SCREEN_INFO)
    private void refresh(Message message) {
        PeerScreenEntity entity;
        switch (message.what) {
            case PEER_SCREEN_INFO_TYPE:
                PeerScreenEntity.ItemsBean bean = (PeerScreenEntity.ItemsBean) message.obj;
                entity = peerScreenEntityList.get(pos);
                entity.setContent(bean.getText());
                entity.setId(bean.getId());
                mRootView.setAdapterItem(pos, entity);
                break;
            case PEER_SCREEN_INFO_INSTITUTIONS:
                InstitutionListEntity institutionListEntity = (InstitutionListEntity) message.obj;
                entity = peerScreenEntityList.get(pos);
                entity.setContent(institutionListEntity.getInstitutionName());
                entity.setId(institutionListEntity.getInstitutionId());
                mRootView.setAdapterItem(pos, entity);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
