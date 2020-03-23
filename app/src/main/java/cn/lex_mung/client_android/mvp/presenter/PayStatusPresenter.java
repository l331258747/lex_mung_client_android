package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.view.View;

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
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.contract.PayStatusContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.HashMap;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH_1;

@ActivityScope
public class PayStatusPresenter extends BasePresenter<PayStatusContract.Model, PayStatusContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    View.OnClickListener onClickListener;

    @Inject
    public PayStatusPresenter(PayStatusContract.Model model, PayStatusContract.View rootView) {
        super(model, rootView);
    }

    public void setGoCouponListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void checkOrderStatus(String orderNo) {
        UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        Map<String, Object> map = new HashMap<>();
        map.put("orderno", orderNo);
        map.put("memberid", entity.getMemberId());
        long time = System.currentTimeMillis();
        map.put("timestamp", time);
        String sign = "memberid=" + entity.getMemberId() + "&orderno=" + orderNo + "&timestamp=" + time;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.checkOrderStatus(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在查询支付状态..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OrderStatusEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OrderStatusEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            switch (baseResponse.getData().getStatus()) {
                                case 1:
                                    switch (DataHelper.getIntergerSF(mApplication, DataHelperTags.PAY_TYPE)) {
                                        case PayStatusTags.PAY:
                                            mRootView.showSuccessLayout("充值成功","查看账户余额");
                                            mRootView.setImg(R.drawable.ic_pay_success2);
                                            break;
                                        case PayStatusTags.PAY_COUPON:
                                            mRootView.showSuccessLayout("充值成功","查看账户余额");
                                            mRootView.setContentLayout("充值赠送的优惠券已经放入您的卡包，您可以在我的-优惠券中找到  去看看",onClickListener);
                                            mRootView.setImg(R.drawable.ic_pay_success2);
                                            break;
                                        case PayStatusTags.PAY_EXPERT:
                                            mRootView.showSuccessLayout("充值成功","专家咨询");
                                            break;
                                        case PayStatusTags.RELEASE_DEMAND:
                                            mRootView.showReleaseDemandLayout("律师会尽快与您联系，请留意站内消息"
                                                    , orderNo
                                                    , baseResponse.getData().getPayTime()
                                                    , DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_TYPE)
                                                    , DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_MONEY));
                                            mRootView.showSuccessLayout("支付成功");
                                            break;
                                        case PayStatusTags.FAST_CONSULT:
                                            mRootView.showReleaseDemandLayout("律师会尽快与您联系，请留意站内消息"
                                                    , orderNo
                                                    , baseResponse.getData().getPayTime()
                                                    , DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_TYPE)
                                                    , DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_MONEY));
                                            mRootView.showSuccessLayout("支付成功");
                                            break;
                                        case PayStatusTags.ONLINE_LAWYER:
                                            mRootView.showSuccessLayout("支付成功","返回首页","预约服务");
                                            mRootView.setContentLayout("您已成功购买在线法律顾问的服务权益，点击预约服务去使用。");
                                            mRootView.setImg(R.drawable.ic_pay_success3);

                                            AppUtils.post(EQUITIES_REFRESH, EQUITIES_REFRESH_1);//支付成功后刷新权益列表页面
                                            break;
                                        case PayStatusTags.PRIVATE_LAWYER:
                                            mRootView.showSuccessLayout("支付成功","返回首页","预约服务");
                                            mRootView.setContentLayout("我们已根据律师的专业程度、活跃度、好评度为您匹配合适的律师。律师按约定时间与您联系，请保持电话畅通！");
                                            mRootView.setImg(R.drawable.ic_pay_success3);

                                            AppUtils.post(EQUITIES_REFRESH, EQUITIES_REFRESH_1);//支付成功后刷新权益列表页面
                                            break;
                                        case PayStatusTags.CORPORATE:
                                            mRootView.showSuccessLayout("支付成功","返回首页","预约服务");
                                            mRootView.setContentLayout("您已成功购买企业应收款年度会员服务，点击预约服务去使用。");
                                            mRootView.setImg(R.drawable.ic_pay_success3);

                                            AppUtils.post(EQUITIES_REFRESH, EQUITIES_REFRESH_1);//支付成功后刷新权益列表页面
                                            break;
                                    }
                                    break;
                                default:
                                    switch (DataHelper.getIntergerSF(mApplication, DataHelperTags.PAY_TYPE)) {
                                        case PayStatusTags.PAY:
                                        case PayStatusTags.PAY_EXPERT:
                                        case PayStatusTags.PAY_COUPON:
                                            mRootView.showFailLayout("充值失败","重新充值");
                                            mRootView.setImg(R.drawable.ic_pay_failure2);
                                            break;
                                        case PayStatusTags.RELEASE_DEMAND:
                                            mRootView.showFailLayout("支付失败");
                                            break;
                                        case PayStatusTags.FAST_CONSULT:
                                            mRootView.showFailLayout("支付失败");
                                            break;
                                        case PayStatusTags.ONLINE_LAWYER:
                                        case PayStatusTags.PRIVATE_LAWYER:
                                        case PayStatusTags.CORPORATE:
                                            mRootView.showSuccessLayout("支付失败","返回首页","账户充值");
                                            mRootView.setContentLayout("如果因银行卡每日支付上限导致无法完成交易，您可以先通过充值到平台账户中，在使用余额支付完成交易。");
                                            mRootView.setImg(R.drawable.ic_pay_failure3);
                                            break;
                                    }
                                    break;
                            }
                        } else {
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
