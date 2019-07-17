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
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import me.zl.mvp.utils.StringUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.mvp.contract.AccountWithdrawalContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.Map;


@ActivityScope
public class AccountWithdrawalPresenter extends BasePresenter<AccountWithdrawalContract.Model, AccountWithdrawalContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private double balance;

    @Inject
    public AccountWithdrawalPresenter(AccountWithdrawalContract.Model model, AccountWithdrawalContract.View rootView) {
        super(model, rootView);
    }

    public void setBalance(double balance) {
        this.balance = balance;
        mRootView.setBalance(StringUtils.getStringNum(balance));
    }

    public void withdrawal(String name, String account, String ua) {
        if (TextUtils.isEmpty(name)) {
            mRootView.showMessage("请输入收款人姓名");
            return;
        }
        if (TextUtils.isEmpty(account)) {
            mRootView.showMessage("请输入支付宝账号");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("money", balance);
        map.put("account", account);
        map.put("name", name);
        map.put("ua", ua);
        String sign = "money=" + balance + "&account=" + account + "&ua=" + ua;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.withdraw(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            mRootView.showSuccessDialog("我们将在3-7个工作日内转账到您的支付宝账户中，请注意查收.");
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
