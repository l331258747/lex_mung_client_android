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
        String json = new Gson().toJson(map);
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
