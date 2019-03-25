package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
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

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

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
                .retryWhen(new RetryWithDelay(1, 2))
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
                            if (entity.getBalance() > (entity.getLawyerPrice() / 60)) {
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
                                mRootView.showDialDialog(string);
                            } else {
                                mRootView.showToPayDialog();
                            }
                        }
                    }
                });
    }

    public void sendCall() {
        mModel.sendCall(entity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AgreementEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AgreementEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showDial1Dialog(String.format(mApplication.getString(R.string.text_call_consult_tip_3), baseResponse.getData().getPhone()));
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
