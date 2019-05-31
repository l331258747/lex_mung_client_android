package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStepLawyerContract;
import me.zl.mvp.utils.RxLifecycleUtils;


@ActivityScope
public class HelpStepLawyerPresenter extends BasePresenter<HelpStepLawyerContract.Model, HelpStepLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HelpStepLawyerPresenter(HelpStepLawyerContract.Model model, HelpStepLawyerContract.View rootView) {
        super(model, rootView);
    }

    public void getData(int regionId,
                        int solutionTypeId,
                        int amountId,
                        int requireTypeId,
                        int memberId) {
        mModel.assistantRecommendLawyers(regionId, solutionTypeId, amountId, requireTypeId,memberId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HelpStepLawyerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HelpStepLawyerEntity> baseResponse) {
                        mRootView.setData(baseResponse.getData());
                    }
                });
    }

    public void expertPrice(int memeberId) {
        mModel.expertPrice(memeberId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
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
                                mRootView.showDialDialog(entity);
                            } else {
                                String string = String.format(mApplication.getString(R.string.text_call_consult_tip_4)
                                        , entity.getLawyerPriceStr()
                                        , entity.getBalanceUnit()
                                        , entity.getPriceUnit());
                                mRootView.showToPayDialog(string);
                            }
                        }
                    }
                });
    }

    public void sendCall(int memeberId,String phone) {
        mRootView.showDial1Dialog(String.format(mApplication.getString(R.string.text_call_consult_tip_3), phone));

        mModel.sendCall(memeberId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AgreementEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AgreementEntity> baseResponse) {
                        if (!baseResponse.isSuccess()) {
                            /*
                            70001：余额不足
                            70002：您好，当前律师可能正在繁忙，建议您改天再联系或者联系平台其他律师进行咨询。
                            70003：您好，该律师暂时无法接听您的电话，建议您联系平台其他律师或拨打客服热线400-811-3060及时处理。
                             */
                            switch (baseResponse.getCode()) {
                                case 70001:
                                    // 充值
                                    break;
                                case 70002:
                                    mRootView.showToErrorDialog(baseResponse.getMessage());
                                    break;
                                case 70003:
                                    mRootView.showToErrorDialog(baseResponse.getMessage());
                                    break;
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
