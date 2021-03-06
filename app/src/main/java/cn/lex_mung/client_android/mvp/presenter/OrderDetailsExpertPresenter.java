package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextTwoBtnDialog;
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

import cn.lex_mung.client_android.mvp.contract.OrderDetailsExpertContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class OrderDetailsExpertPresenter extends BasePresenter<OrderDetailsExpertContract.Model, OrderDetailsExpertContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int id;
    private int typeId = 3;

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public OrderDetailsExpertPresenter(OrderDetailsExpertContract.Model model, OrderDetailsExpertContract.View rootView) {
        super(model, rootView);
    }

    public void expertFinish(String orderNo){
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        mModel.expertFinish(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            getOrderDetail(orderNo);
                        }else{
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void expertCancel(String orderNo){
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        mModel.expertCancel(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            getOrderDetail(orderNo);
                        }else{
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void getOrderDetail(String orderNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("id", id);
        map.put("orderNo", orderNo);
        mModel.getOrderDetail(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderDetailsEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderDetailsEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            OrderDetailsEntity bean;
                            bean = baseResponse.getData().getList().get(0);

                            if (!TextUtils.isEmpty(bean.getLmemberName())) {//律师
                                mRootView.setLawyer(bean.getLmemberNameStr());
                            }
                            if (!TextUtils.isEmpty(bean.getCreateDate())) {//下单时间
                                mRootView.setTime(bean.getCreateDate());
                            }
                            if (bean.getMinAmount() > 0) {
                                mRootView.setPrice(bean.getMinAmountStr());//咨询单价
                            } else {
                                mRootView.setPrice(null);
                            }

                            mRootView.setOrderPrice(bean.getExperAmoutStr());//订单金额

                            if (!TextUtils.isEmpty(bean.getCouponNameStr())) {//优惠方式
                                mRootView.setCouponType(bean.getCouponNameStr());
                            } else {
                                mRootView.setCouponType(null);
                            }

                            if (!TextUtils.isEmpty(bean.getPayType()) && !bean.getPayType().equals("0")) {
                                mRootView.setPayType(bean.getPayTypeStr());//支付方式
                            } else {
                                mRootView.setPayType(null);
                            }

                            if (!TextUtils.isEmpty(bean.getOrderNo())) {//订单编号
                                mRootView.setOrderNo(bean.getOrderNo());
                            }
                            if (!TextUtils.isEmpty(bean.getOrderNo())) {//订单编号
                                mRootView.setTalkTime(bean.getOrderNo());
                            }
                            if (bean.getQuickTime() == null || bean.getQuickTime().size() == 0) {
                                mRootView.setTalkTime("无");
                                mRootView.setTalkRecordList(null);
                            } else {
                                mRootView.setTalkTime("总计" + bean.getCallTimeStr());
                                mRootView.setTalkRecordList(bean.getQuickTime());
                            }

                            switch (bean.getOrderStatus()) {
                                case 4://未接单
                                    bean.getCallOrderTime();
                                    mRootView.setBottomStatus(true,
                                            "您已发起咨询邀约，预计咨询时间为" + bean.getCallOrderTime().getClientTimeStr() + "，预计时长为" + bean.getAppointmentLength() + "分钟，请等待律师确认。",
                                            true,
                                            "取消订单",
                                            v -> mRootView.showCancelDialog());
                                    mRootView.setOrderDetailView(0);
                                    break;
                                case 5://接单，未联系
                                    mRootView.setBottomStatus(true,
                                            "预约成功，律师将于" + bean.getCallOrderTime().getLawyerTimeStr() + "联系你，请注意接听陌生电话。",
                                            true,
                                            "取消订单",
                                            v -> mRootView.showCancelDialog());
                                    mRootView.setOrderDetailView(1);
                                    break;
                                case 6://接单，已联系
                                    mRootView.setBottomStatus(true,
                                            "订单将于"+ bean.getRemainingTimeStr() +"内自动结束，您也可以主动结束订单。",
                                            true,
                                            "结束订单",
                                            v -> mRootView.showFinishDialog());
                                    mRootView.setOrderDetailView(1);
                                    break;
                                case 14://律师取消
                                    mRootView.setBottomStatus(true,
                                            "律师在该时间段内不方便接单，本次服务已结束。",
                                            false,
                                            null,
                                            null);
                                    mRootView.setOrderDetailView(2);
                                    break;
                                default: //7已完成，13用户取消
                                    mRootView.setBottomStatus(true,
                                            "本次服务已结束，欢迎下次使用绿豆圈。",
                                            false,
                                            null,
                                            null);
                                    mRootView.setOrderDetailView(2);
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
