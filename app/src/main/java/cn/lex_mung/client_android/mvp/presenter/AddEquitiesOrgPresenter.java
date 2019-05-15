package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.RegexUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.mvp.contract.AddEquitiesOrgContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.Map;

@ActivityScope
public class AddEquitiesOrgPresenter extends BasePresenter<AddEquitiesOrgContract.Model, AddEquitiesOrgContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isService1 = false;
    private boolean isService2 = false;
    private boolean isService3 = false;

    @Inject
    public AddEquitiesOrgPresenter(AddEquitiesOrgContract.Model model, AddEquitiesOrgContract.View rootView) {
        super(model, rootView);
    }

    public boolean isService1() {
        return isService1;
    }

    public void setService1(boolean service1) {
        isService1 = service1;
    }

    public boolean isService2() {
        return isService2;
    }

    public void setService2(boolean service2) {
        isService2 = service2;
    }

    public boolean isService3() {
        return isService3;
    }

    public void setService3(boolean service3) {
        isService3 = service3;
    }

    public void submit(String name, String mobile, String orgName) {
        if (TextUtils.isEmpty(name)) {
            mRootView.showMessage("请输入您的称呼");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            mRootView.showMessage("请输入您的手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(mobile)) {
            mRootView.showMessage("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(orgName)) {
            mRootView.showMessage("请输入您的组织名称");
            return;
        }
        String string = name + "," + mobile + "," + orgName + "," + (isService1 ? "专属服务" : "") + "," + (isService2 ? "企业顾问" : "") + "," + (isService3 ? "尽职调查" : "");
        Map<String, Object> map = new HashMap<>();
        map.put("feedbackContent", string);
        mModel.addEquitiesOrg(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("提交成功");
                            mRootView.killMyself();
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
