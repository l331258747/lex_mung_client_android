package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MessageChatActivity;
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
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import zlc.season.rxdownload3.core.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActivityScope
public class OrderDetailsPresenter extends BasePresenter<OrderDetailsContract.Model, OrderDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int type;
    private int id;
    private int isHot;
    private String orderNo;

    @Inject
    public OrderDetailsPresenter(OrderDetailsContract.Model model, OrderDetailsContract.View rootView) {
        super(model, rootView);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void getUserPhone() {
        mModel.getUserPhone(orderNo)
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
                        if (baseResponse.isSuccess()) {
                            mRootView.call(baseResponse.getData().getPhone());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void getRequirementDetail(int anInt, String orderNo) {
        mModel.requirementDetail(anInt, orderNo)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RequirementDetailEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RequirementDetailEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() == null || baseResponse.getData().size() < 1)
                                return;

                            RequirementDetailEntity bean = baseResponse.getData().get(0);

                            mRootView.setPhone(bean.getLmobile());
                            mRootView.setIsReceipt(bean.getIsReceipt());

                            //必要
                            if (!TextUtils.isEmpty(bean.getOrderNo())) {//单号
                                mRootView.setOrderNo(bean.getOrderNo());
                            }
                            if (!TextUtils.isEmpty(bean.getCreateDate())) {//时间
                                mRootView.setOrderTime(bean.getCreateDate());
                            }
                            if (!TextUtils.isEmpty(bean.getTypeName())) {//商品名称
                                mRootView.setOrderType(bean.getTypeName());
                            }
                            if (!TextUtils.isEmpty(bean.getStatusValue())) {//订单状态
                                mRootView.setOrderStatus(bean.getStatusValue());
                            }

                            if (bean.getBuyerPayAmount() > 0) {
                                mRootView.setOrderAmount(bean.getBuyerPayAmountStr());//订单价格
                            } else {
                                mRootView.setOrderAmount(null);//订单价格
                            }

                            if (bean.getUserPayAmount() > 0) {
                                mRootView.setOrderPayPrice(bean.getUserPayAmountStr());//支付价格
                            } else {
                                mRootView.setOrderPayPrice(null);//订单价格
                            }

                            if (!TextUtils.isEmpty(bean.getPayType()) && !bean.getPayType().equals("0")) {
                                mRootView.setPayType(bean.getPayTypeStr());//支付方式
                            } else {
                                mRootView.setPayType(null);
                            }

                            //优惠
                            mRootView.setCouponLayout(bean.getUseCoupon()
                                    , bean.getCouponDeductionAmountStr()
                                    , bean.getCouponTypeStr());

                            mRootView.setInfoContent(bean.getDescription());//需求内容

                            mRootView.setLawyerLayout(bean.getLawyerMemberId(), bean.getLmemberNameStr(), bean.getLMemeberName2(), bean.getIconImage());//显示律师

                            //热门需求
                            if (isHot == 1) {
                                if (bean.getStatus() == -1) {
                                    mRootView.setOrderDetailView(0);
                                    mRootView.setBottomStatus("重新支付", R.color.c_ff, R.color.c_1EC88B, null, false);//改成回调
                                } else if (bean.getStatus() == 1 && bean.getIsReceipt() == 0) {
                                    mRootView.setOrderDetailView(1);
                                    mRootView.setBottomStatus("平台正在优选服务律师", R.color.c_ff, R.color.c_f0f0f0, null);//改成回调
                                } else if (bean.getIsReceipt() == 1) {
                                    mRootView.setOrderDetailView(2);
                                    mRootView.setBottomStatus("联系律师", R.color.c_ff, R.color.c_1EC88B, v -> {
                                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getLmobile()));
//                                        mRootView.getActivity().startActivity(dialIntent);
                                        mRootView.launchActivity(dialIntent);
                                    });
                                    mRootView.setLawyerClick(true);

                                    mRootView.setOrderRemain(bean.getRemainingTime() * 1000, "服务倒计时");
                                } else if (bean.getIsReceipt() == 2) {

                                } else if (bean.getIsReceipt() == 3) {
                                    mRootView.setOrderDetailView(3);
                                    mRootView.setBottomStatus("已完成", R.color.c_737373, R.color.c_f0f0f0, null);
                                    mRootView.setLawyerClick(true);
                                }
                            } else {
                                if (bean.getStatus() == -1) {
                                    mRootView.setOrderDetailView(-1);
                                    mRootView.setBottomStatus("重新支付", R.color.c_ff, R.color.c_1EC88B, null, false);//改成回调
                                    mRootView.setLawyerClick(true);
                                } else {
                                    mRootView.setOrderDetailView(-1);
                                    mRootView.setBottomStatus("查看详情", R.color.c_ff, R.color.c_1EC88B, v -> {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(BundleTags.ID, bean.getLawyerMemberId());
                                        bundle.putString(BundleTags.TITLE, bean.getMemberName());
                                        mRootView.launchActivity(new Intent(mApplication, MessageChatActivity.class), bundle);
                                    });//改成回调
                                    mRootView.setLawyerClick(true);
                                }

                            }
                        }
                    }
                });
    }

    public void getOrderDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", type);
        map.put("id", id);
        mModel.getOrderDetail(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            OrderDetailsEntity.ListBean bean;
                            bean = baseResponse.getData().getList().get(0);

                            //必要
                            if (!TextUtils.isEmpty(bean.getOrderNo())) {//单号
                                mRootView.setOrderNo(bean.getOrderNo());
                            }
                            if (!TextUtils.isEmpty(bean.getCreateDate())) {//时间
                                mRootView.setOrderTime(bean.getCreateDate());
                            }
                            if (!TextUtils.isEmpty(bean.getOrderType())) {//商品名称
                                mRootView.setOrderName(bean.getOrderType());
                            }
                            if (!TextUtils.isEmpty(bean.getStatusValue())) {//订单状态
                                mRootView.setOrderStatus(bean.getStatusValue());
                            }

                            if (bean.getBuyerPayAmount() > 0) {
                                mRootView.setOrderAmount(bean.getBuyerPayAmountStr());//订单价格
                            } else {
                                mRootView.setOrderAmount(null);//订单价格
                            }

                            if (bean.getPayAmount() > 0) {
                                mRootView.setOrderPayPrice(bean.getPayAmountStr());//支付价格
                            } else {
                                mRootView.setOrderPayPrice(null);//订单价格
                            }

                            if (!TextUtils.isEmpty(bean.getPayType()) && !bean.getPayType().equals("0")) {
                                mRootView.setPayType(bean.getPayTypeStr());//支付方式
                            } else {
                                mRootView.setPayType(null);//订单价格
                            }

                            //优惠
                            mRootView.setCouponLayout(bean.getUseCoupon()
                                    , bean.getCouponDeductionAmountStr()
                                    , bean.getCouponTypeStr());

                            mRootView.setInfoContent(null);

                            mRootView.setLawyerLayout(bean.getLawyerMemberId(), bean.getLmemberName(), bean.getLMemeberName2(), bean.getIconImage());//显示律师

                            if (bean.getTypeId() == 4) {//快速抢单

                                switch (bean.getOrderStatus()) {
                                    case 2:
                                        mRootView.setOrderDetailView(0);//改变topview 字和进度
                                        mRootView.setBottomStatus("重新支付", R.color.c_ff, R.color.c_1EC88B, null, false);//改成回调
                                        break;
                                    case 4:
                                        mRootView.setOrderDetailView(1);
                                        mRootView.setBottomStatus("平台正在优选服务律师", R.color.c_ff, R.color.c_f0f0f0, null);
                                        break;
                                    case 5:
                                        mRootView.setOrderRemain(bean.getRemainingTime() * 1000, "剩余回电时间");


                                        mRootView.setOrderDetailView(2);
                                        mRootView.setBottomStatus("联系律师", R.color.c_ff, R.color.c_1EC88B, v -> {
                                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getLmobile()));
                                            mRootView.launchActivity(dialIntent);
                                        });

                                        if (bean.getCallback() == 1) {
                                            mRootView.setLawyerClick(true);
                                        }else{
                                            mRootView.setLawyerClick(false);
                                        }
                                        break;
                                    case 7:
                                        mRootView.setOrderDetailView(3);
                                        mRootView.setBottomStatus("已完成", R.color.c_737373, R.color.c_f0f0f0, null);
                                        mRootView.setLawyerClick(true);
                                        break;
                                }
                            } else if (bean.getTypeId() == 3) {//专家咨询
                                mRootView.setOrderDetailView(-1);
                                mRootView.setLawyerClick(true);

                                mRootView.setBottomStatus("继续拨打", R.color.c_ff, R.color.c_1EC88B, v -> {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BundleTags.ID, bean.getLawyerMemberId());
                                    mRootView.launchActivity(new Intent(mRootView.getActivity(), LawyerHomePageActivity.class), bundle);
                                });

                                mRootView.setOrderStatus(null);//不需要

                                if (bean.getCallTime() <= 0) {
                                    mRootView.setTalkTime("请等待律师接单");
                                } else {
                                    mRootView.setTalkTime(bean.getCallTimeStr());
                                    if (!TextUtils.isEmpty(bean.getBeginTime()))
                                        mRootView.setTalkRecord(bean.getBeginTime() + "    " + bean.getCallTimeStr());
                                }
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
