package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.contract.TradingListDetailsContract;
import cn.lex_mung.client_android.mvp.model.entity.TradingListEntity;

@ActivityScope
public class TradingListDetailsPresenter extends BasePresenter<TradingListDetailsContract.Model, TradingListDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TradingListDetailsPresenter(TradingListDetailsContract.Model model, TradingListDetailsContract.View rootView) {
        super(model, rootView);
    }

    public void setBean(TradingListEntity bean) {
//        if ("快速咨询".equals(bean.getOrderType())
//                || "专家咨询".equals(bean.getOrderType())) {
        if ("专家咨询".equals(bean.getOrderType())) {
            mRootView.showLayout();
        }
        mRootView.setOrderNo(bean.getOrderNo());
        if (!TextUtils.isEmpty(bean.getOrderNo())) {
            mRootView.setOrderNo(bean.getOrderNo());
        }
        if (!TextUtils.isEmpty(bean.getCreateDate())) {
            mRootView.setOrderTime(bean.getCreateDate());
        }
        if (!TextUtils.isEmpty(bean.getOrderType())) {
            mRootView.setOrderName(bean.getOrderType());
        }
        if ("充值".equals(bean.getOrderType())) {
            mRootView.setOrderAmount(bean.getBuyerPayAmount() + "元"
                    , "收入金额"
                    , AppUtils.getColor(mApplication, R.color.c_06a66a)
                    , "+  " + AppUtils.formatAmount(mApplication, bean.getBuyerPayAmount()));
        } else {
            mRootView.setOrderAmount(bean.getBuyerPayAmount() + "元"
                    , "支出金额"
                    , AppUtils.getColor(mApplication, R.color.c_323232)
                    , AppUtils.formatAmount(mApplication, bean.getBuyerPayAmount()));
        }
        if (!TextUtils.isEmpty(bean.getPayStatus())) {
            mRootView.setOrderStatus(bean.getPayStatus());
        }
        if (bean.getOrderStatus() == 2
                || bean.getOrderStatus() == 4) {
            mRootView.setOrderStatusColor(AppUtils.getColor(mApplication, R.color.c_323232));
        } else {
            mRootView.setOrderStatusColor(AppUtils.getColor(mApplication, R.color.c_ea5514));
        }
        if (!TextUtils.isEmpty(bean.getMemberName())) {
            mRootView.setOrderCustomer(bean.getMemberName());
        }
        if (!TextUtils.isEmpty(bean.getBeginTime())) {
            mRootView.setOrderStartTime(bean.getBeginTime() + "  开始");
        }
        if (!TextUtils.isEmpty(bean.getEndTime())) {
            mRootView.setOrderEndTime(bean.getEndTime() + "  结束");
        }
        mRootView.setOrderTotal(bean.getTalkTime());
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
