package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertReserveEntity;
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

import cn.lex_mung.client_android.mvp.contract.PhoneSubContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class PhoneSubPresenter extends BasePresenter<PhoneSubContract.Model, PhoneSubContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PhoneSubPresenter(PhoneSubContract.Model model, PhoneSubContract.View rootView) {
        super(model, rootView);
    }

    public void expertReserve(int lawyerId,String start,String end,int len){
        Map<String, Object> map = new HashMap<>();
        map.put("lawyerId", lawyerId);
        map.put("start", start);
        map.put("end", end);
        map.put("len", len);
        mModel.expertReserve(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ExpertReserveEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ExpertReserveEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showBalanceYesDialog();
                        }else{
                             /*
                            70001：余额不足
                            70002：您好，当前律师可能正在繁忙，建议您改天再联系或者联系平台其他律师进行咨询。
                            70003：您好，该律师暂时无法接听您的电话，建议您联系平台其他律师或拨打客服热线400-811-3060及时处理。
                             */
                            switch (baseResponse.getCode()) {
                                case 70001:
                                    // 充值
                                    mRootView.showBalanceNoDialog();
                                    break;
                                case 70002:
                                case 70003:
                                    mRootView.showToErrorDialog(baseResponse.getMessage());
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
