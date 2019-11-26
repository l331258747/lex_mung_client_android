package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.OrderPrivateLawyersDetailEntity;
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

import cn.lex_mung.client_android.mvp.contract.OrderDetailsPrivateLawyerContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsPrivateLawyerPresenter extends BasePresenter<OrderDetailsPrivateLawyerContract.Model, OrderDetailsPrivateLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    int id;
    String orderId;

    List<LegalAdviserOrderComplaintEntity> complaintEntitys;

    public List<LegalAdviserOrderComplaintEntity> getComplaintEntitys() {
        return complaintEntitys;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public OrderDetailsPrivateLawyerPresenter(OrderDetailsPrivateLawyerContract.Model model, OrderDetailsPrivateLawyerContract.View rootView) {
        super(model, rootView);
    }

    //详情
    public void orderPrivateLawyersDetail(){
        mModel.orderPrivateLawyersDetail(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderPrivateLawyersDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderPrivateLawyersDetailEntity> baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() != null){
                                mRootView.setEntity(baseResponse.getData());
                                orderId = baseResponse.getData().getOrderId();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //私人律师团-拨打电话
    public void privateLawyersUserphone() {
        mModel.privateLawyersUserphone(orderId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RemainEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RemainEntity> baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            mRootView.call(baseResponse.getData().getPhone());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //拒绝理由
    public void legalAdviserOrderComplaint(){
        Map<String, Object> map = new HashMap<>();
        mModel.legalAdviserOrderComplaint(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LegalAdviserOrderComplaintEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<LegalAdviserOrderComplaintEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            complaintEntitys = baseResponse.getData();
                            if (complaintEntitys == null || complaintEntitys.size() == 0) {
                                mRootView.showMessage("无拒绝理由");
                                return;
                            }

                            List<String> lists = new ArrayList<>();
                            for (LegalAdviserOrderComplaintEntity item : complaintEntitys) {
                                lists.add(item.getComplaintContent());
                            }
                            mRootView.refuseDialog(lists);
                        }
                    }
                });
    }

    //取消
    public void privateLawyersOrderCancel(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        mModel.privateLawyersOrderCancel(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            orderPrivateLawyersDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //完成
    public void orderComplete(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("receiptStatus", 50);
        mModel.privateLawyersOrderUpdate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            orderPrivateLawyersDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //未完成
    public void orderUnComplete(int complaintId){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("complaintId", complaintId);
        map.put("receiptStatus", 70);
        mModel.privateLawyersOrderUpdate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.hideLoading();
                        if (baseResponse.isSuccess()) {
                            orderPrivateLawyersDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
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
