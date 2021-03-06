package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeEntity;
import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter2;
import cn.lex_mung.client_android.utils.DecimalUtil;
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
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MyAccountContract;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import me.zl.mvp.utils.StringUtils;
import okhttp3.RequestBody;

@ActivityScope
public class MyAccountPresenter extends BasePresenter<MyAccountContract.Model, MyAccountContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    private double allBalance;//总余额
    private double realBalance;//
    private double giveBalance;

    private MyAccountPayAdapter2 myAccountPayAdapter;

    private double payMoney;
    private int payType = 1;

    private boolean flag = false;
//    private ExpertPriceEntity entity;
    //    private int myAccountPayAdapterPosition;
//    private List<RechargeEntity> priceList;
    private int rechargeId;
    private boolean isCoupon;//是否有券，用来在支付成功页面显示提示
    private boolean isExpert;

    @Inject
    public MyAccountPresenter(MyAccountContract.Model model, MyAccountContract.View rootView) {
        super(model, rootView);
    }

    public double getAllBalance() {
        return allBalance;
    }

    public void setExpert(boolean isExpert) {
        this.isExpert = isExpert;
    }

    public double getRealBalance() {
        return realBalance;
    }

    public double getGiveBalance() {
        return giveBalance;
    }

    public void withdrawVerify(){
        mModel.withdrawVerify()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Boolean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Boolean> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.withdrawVerifyLayout(baseResponse.getData());
                        }
                    }
                });
    }

    public void getUserBalance() {
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        mModel.getUserBalance(userInfoDetailsEntity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            realBalance = baseResponse.getData().getBalanceAmount();
                            giveBalance = baseResponse.getData().getGiveAmount();

                            allBalance = baseResponse.getData().getAllBalanceAmount();
                            mRootView.setBalance(String.format(
                                    AppUtils.getString(mApplication, R.string.text_yuan_money)
                                    , StringUtils.getStringNum(allBalance)));
                        }
                    }
                });
    }

    public void getRechargeList() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", null);
        mModel.rechargeList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RechargeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RechargeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
//                            initAdapter(priceList = baseResponse.getData());
                            initAdapter(baseResponse.getData());
                        }
                    }
                });
    }

    public void getRechargeCouponList(RechargeEntity entity) {
        mModel.rechargeCouponList(entity.getCouponPackId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RechargeCouponEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RechargeCouponEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (entity.getGive() == 1) {
                                mRootView.showPriceDialog(1,
                                        entity.getAmountStr2(),
                                        entity.getGiveAmountStr(),
                                        baseResponse.getData());
                            } else {
                                mRootView.showPriceDialog(1,
                                        entity.getAmountStr2(),
                                        "",
                                        baseResponse.getData());
                            }

                        }
                    }
                });
    }

    private void setRechargeId(int rechargeId) {
        this.rechargeId = rechargeId;
    }

    public void setCoupon(boolean isCoupon) {
        this.isCoupon = isCoupon;
    }

    private void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
//        mRootView.setOrderMoney(StringUtils.getStringNum(payMoney));

//        if (entity != null) {
//            long time;
//            double SelectMoney;
//            String string2;
//
////            if(entity.getMinimumRecharge() > 0 && myAccountPayAdapterPosition == priceList.size() - 1){//如果最小充值金额大于0，和选择最后一个
////                SelectMoney = DecimalUtil.add(allBalance, payMoney);
////                string2 = "充值后即可与%1$s通话%2$s。";
////            }else{
////                SelectMoney = payMoney;
////                string2 = "充值后可增加与%1$s%2$s通话时长。";
////            }
//
//            SelectMoney = payMoney;
//            string2 = "充值后可增加与%1$s%2$s通话时长。";
//
//
//            if (!TextUtils.isEmpty(entity.getOrgnizationName())) {
//                time = (long) DecimalUtil.divide(SelectMoney, entity.getFavorablePrice());
//            } else {
//                time = (long) DecimalUtil.divide(SelectMoney, entity.getLawyerPrice());
//            }
//
//            String timeStr = StringUtils.getStringNum(time) + entity.getPriceUnit();
//            if (entity.getPriceUnit().equals("分钟") && time / 60 > 0) {
//                timeStr = (time / 60) + "小时" + (time % 60) + "分钟";
//            }
//            mRootView.setTip2(String.format(string2, entity.getLawyerName(), timeStr));
//        }
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

//    public void onCreate(ExpertPriceEntity entity) {
//        this.entity = entity;
//        getRechargeList();
//    }
    public void onCreate() {
        getRechargeList();
    }

    private void initAdapter(List<RechargeEntity> priceList) {
        if (priceList == null || priceList.size() == 0) return;

        myAccountPayAdapter = new MyAccountPayAdapter2(priceList);
        myAccountPayAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            RechargeEntity entity = myAccountPayAdapter.getItem(position);
            if (entity == null) return;
//            myAccountPayAdapterPosition = position;

            setPayMoney(entity.getAmount());
            setRechargeId(entity.getId());
            setGiveLayout(entity.getAmount(),entity.getActivity(),entity.getGive(), entity.getGiveAmount(),entity.getGiveCoupon(),entity.getActivityDescription());
            setCoupon((entity.getActivity() == 1 && entity.getGiveCoupon() == 1));

            myAccountPayAdapter.setPos(position);
            myAccountPayAdapter.notifyDataSetChanged();

            showPriceDetail(entity);

        });
        mRootView.initRecyclerView(myAccountPayAdapter);
//        myAccountPayAdapterPosition = 0;
        setPayMoney(priceList.get(0).getAmount());
        setRechargeId(priceList.get(0).getId());
        setCoupon((priceList.get(0).getActivity() == 1 && priceList.get(0).getGiveCoupon() == 1));
        setGiveLayout(priceList.get(0).getAmount(),
                priceList.get(0).getActivity(),
                priceList.get(0).getGive(),
                priceList.get(0).getGiveAmount(),
                priceList.get(0).getGiveCoupon(),
                priceList.get(0).getActivityDescription());
    }

    private void setGiveLayout(double amount,int isActivity, int isGive, double givePrice,int isCoupon,String couponName) {
        if(isActivity != 1){
            mRootView.setBottomStr("充值金额: ¥ " + StringUtils.getStringNum(amount));
        }else{
            if(isGive == 1 && isCoupon == 1){
                mRootView.setBottomStr("充值金额: ¥ " + StringUtils.getStringNum(amount) + "，赠送金额: ¥ " + StringUtils.getStringNum(givePrice) + "，" + couponName);
            }else if(isGive == 1 && isCoupon != 1){
                mRootView.setBottomStr("充值金额: ¥ " + StringUtils.getStringNum(amount) + "\n赠送金额: ¥ " + StringUtils.getStringNum(givePrice));
            }else if(isGive != 1 && isCoupon == 1){
                mRootView.setBottomStr("充值金额: ¥ " + StringUtils.getStringNum(amount) + "\n" + couponName);
            }else{
                mRootView.setBottomStr("充值金额: ¥ " + StringUtils.getStringNum(amount));
            }
        }
    }

    private void showPriceDetail(RechargeEntity entity) {
        if (entity.getActivity() != 1) //是否有优惠活动
            return;
        if (entity.getGiveCoupon() == 1) {//是否有优惠券包
            getRechargeCouponList(entity);
        }else if (entity.getGive() == 1) {//是否有赠送金额
            mRootView.showPriceDialog(1,
                    entity.getAmountStr2(),
                    entity.getGiveAmountStr(),
                    null);
        }
    }

    private void getPermission(String ua) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                pay(ua);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法前往支付宝支付");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    public void pay(String ua) {
        if (payMoney == 0) {
            mRootView.showMessage("充值金额错误");
            return;
        }
        switch (payType) {
            case 1:
                if (!AppUtils.isWeixinAvilible(mRootView.getActivity())) {
                    AppUtils.makeText(mRootView.getActivity(), "微信未安装");
                    return;
                }
                break;
            case 2:
                if (!flag) {
                    getPermission(ua);
                    return;
                }
                break;
        }
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", payType);
        map.put("other", "{\"rechargeId\":" + rechargeId + "}");//新增，充值金额id
        map.put("source", 2);
        map.put("product", 4);
        map.put("ua", ua);
        String sign = "money=" + money + "&type=" + payType + "&source=" + 2 + "&ua=" + ua;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.pay(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在获取支付信息..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_NO, baseResponse.getData().getOrderNo());
                            if (payType == 1) {//微信
                                DataHelper.setStringSF(mApplication, DataHelperTags.APP_ID, baseResponse.getData().getAppid());
                                IWXAPI api = WXAPIFactory.createWXAPI(mApplication, baseResponse.getData().getAppid(), true);
                                api.registerApp(baseResponse.getData().getAppid());
                                PayReq request = new PayReq();
                                request.appId = baseResponse.getData().getAppid();
                                request.partnerId = baseResponse.getData().getPartnerId();
                                request.prepayId = baseResponse.getData().getPrepayId();
                                request.packageValue = baseResponse.getData().getPkg();
                                request.nonceStr = baseResponse.getData().getNonceStr();
                                request.timeStamp = baseResponse.getData().getTimestamp();
                                request.sign = baseResponse.getData().getSign();
                                api.sendReq(request);
                                if (isExpert) {
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.PAY_EXPERT);
                                } else {
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, isCoupon?PayStatusTags.PAY_COUPON:PayStatusTags.PAY);
                                }
                            } else if (payType == 2) {//支付宝
                                Runnable payRunnable = () -> {
                                    PayTask payTask = new PayTask(mRootView.getActivity());
                                    Map<String, String> result = payTask.payV2(baseResponse.getData().getOrderInfo(), true);

                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {//余额
                                if (isExpert) {
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.PAY_EXPERT);
                                } else {
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, isCoupon?PayStatusTags.PAY_COUPON:PayStatusTags.PAY);
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                                Intent intent = new Intent(mApplication, PayStatusActivity.class);
                                intent.putExtras(bundle);
                                mRootView.launchActivity(intent);
                                mRootView.killMyself();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    if (isExpert) {
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.PAY_EXPERT);
                    } else {
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, isCoupon?PayStatusTags.PAY_COUPON:PayStatusTags.PAY);
                    }
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                    bundle.putString(BundleTags.ZFB, payResult.getResultStatus());
                    Intent intent = new Intent(mApplication, PayStatusActivity.class);
                    intent.putExtras(bundle);
                    mRootView.launchActivity(intent);
                    break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
