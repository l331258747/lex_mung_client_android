package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.contract.ServicePriceContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import com.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

import java.util.List;

@FragmentScope
public class ServicePricePresenter extends BasePresenter<ServicePriceContract.Model, ServicePriceContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawsHomePagerBaseEntity entity;

    private boolean isLogin;

    @Inject
    public ServicePricePresenter(ServicePriceContract.Model model, ServicePriceContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        this.entity = entity;
        mRootView.setAdapter(entity.getRequireInfo());
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public LawsHomePagerBaseEntity getEntity() {
        return entity;
    }

    public void expertPrice() {
        mModel.expertPrice(entity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ExpertPriceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ExpertPriceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            ExpertPriceEntity entity = baseResponse.getData();
                            if (entity.getBalance() > entity.getLawyerPrice()) {
                                String string;
                                if (!TextUtils.isEmpty(entity.getOrgnizationName())) {//有权益
                                    string = String.format(mApplication.getString(R.string.text_call_consult_tip_1)
                                            , AppUtils.formatAmount(mApplication, entity.getLawyerPrice())
                                            , entity.getPriceUnit()
                                            , entity.getOrgnizationName()
                                            , AppUtils.formatAmount(mApplication, entity.getFavorablePrice())
                                            , entity.getPriceUnit()
                                            , entity.getFreeTime()
                                            , entity.getFavorableTimeLen());
                                } else {//无权益
                                    string = String.format(mApplication.getString(R.string.text_call_consult_tip_2)
                                            , AppUtils.formatAmount(mApplication, entity.getLawyerPrice())
                                            , entity.getPriceUnit()
                                            , entity.getFreeTime()
                                            , entity.getOriginTimeLen());
                                }
                                mRootView.showDialDialog(string, entity.getCallCenterNo());
                            } else {
                                mRootView.showToPayDialog();
                            }
                        }
                    }
                });
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
