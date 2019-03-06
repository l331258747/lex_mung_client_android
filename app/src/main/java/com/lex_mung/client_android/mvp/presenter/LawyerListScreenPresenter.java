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

import com.lex_mung.client_android.mvp.contract.LawyerListScreenContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;


@ActivityScope
public class LawyerListScreenPresenter extends BasePresenter<LawyerListScreenContract.Model, LawyerListScreenContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<LawyerListScreenEntity> lawyerListScreenEntityList = new ArrayList<>();

    private int pos;
    private boolean flag;
    private int regionId1;
    private int regionId2;

    @Inject
    public LawyerListScreenPresenter(LawyerListScreenContract.Model model, LawyerListScreenContract.View rootView) {
        super(model, rootView);
    }

    public void setLawyerListScreenEntityList(List<LawyerListScreenEntity> lawyerListScreenEntityList) {
        this.lawyerListScreenEntityList.addAll(lawyerListScreenEntityList);
        if (lawyerListScreenEntityList.size() <= 0) {
            getPeerSearchList();
        } else {
            mRootView.setAdapter(lawyerListScreenEntityList);
        }
    }

    public List<LawyerListScreenEntity> getPeerSearchEntityList() {
        return lawyerListScreenEntityList;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setRegionId1(int regionId1) {
        this.regionId1 = regionId1;
    }

    public void setRegionId2(int regionId2) {
        this.regionId2 = regionId2;
    }

    public boolean isFlag() {
        return flag;
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LawyerListScreenEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<LawyerListScreenEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            lawyerListScreenEntityList.addAll(baseResponse.getData());
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
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO)
    private void refresh(Message message) {
        LawyerListScreenEntity entity;
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_TYPE:
                LawyerListScreenEntity.ItemsBean bean = (LawyerListScreenEntity.ItemsBean) message.obj;
                entity = lawyerListScreenEntityList.get(pos);
                entity.setContent(bean.getText());
                entity.setId(bean.getId());
                mRootView.setAdapterItem(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                InstitutionListEntity institutionListEntity = (InstitutionListEntity) message.obj;
                entity = lawyerListScreenEntityList.get(pos);
                entity.setContent(institutionListEntity.getInstitutionName());
                entity.setId(institutionListEntity.getInstitutionId());
                mRootView.setAdapterItem(pos, entity);
                break;
        }
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO_1)
    private void refresh_1(Message message) {
        LawyerListScreenEntity entity;
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_TYPE:
                LawyerListScreenEntity.ItemsBean bean = (LawyerListScreenEntity.ItemsBean) message.obj;
                entity = lawyerListScreenEntityList.get(pos);
                entity.setContent(bean.getText());
                entity.setId(bean.getId());
                mRootView.setAdapterItem(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                InstitutionListEntity institutionListEntity = (InstitutionListEntity) message.obj;
                entity = lawyerListScreenEntityList.get(pos);
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
