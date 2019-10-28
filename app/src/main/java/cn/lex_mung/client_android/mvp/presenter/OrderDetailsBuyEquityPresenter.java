package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
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

import cn.lex_mung.client_android.mvp.contract.OrderDetailsBuyEquityContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsBuyEquityPresenter extends BasePresenter<OrderDetailsBuyEquityContract.Model, OrderDetailsBuyEquityContract.View> {
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
    public OrderDetailsBuyEquityPresenter(OrderDetailsBuyEquityContract.Model model, OrderDetailsBuyEquityContract.View rootView) {
        super(model, rootView);
    }

    public void legalAdviserOrderDetail(){
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", 7);
        map.put("id", id);
        mModel.legalAdviserOrderDetail(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LegalAdviserOrderDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LegalAdviserOrderDetailEntity> baseResponse) {
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

    //取消
    public void legalAdviserOrderCancel(){
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        mModel.legalAdviserOrderCancel(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            legalAdviserOrderDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //完成
    public void legalAdviserOrderComplete(){
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        mModel.legalAdviserOrderComplete(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            legalAdviserOrderDetail();
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //拒绝
    public void legalAdviserOrderUnComplete(int complaintId){
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("complaintId", complaintId);
        mModel.legalAdviserOrderUnComplete(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            legalAdviserOrderDetail();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
