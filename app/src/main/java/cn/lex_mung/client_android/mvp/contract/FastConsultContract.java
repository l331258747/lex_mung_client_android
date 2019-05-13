package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;

import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface FastConsultContract {
    interface View extends IView {

        void setPhone(String mobile);

        void setBalance(String balance);

        void setOrderMoney(String money,double orderMoney);

        void showToAppInfoDialog();

        void showLackOfBalanceDialog();

        Activity getActivity();

//        void setMoney(String money);

        void setCouponLayout(OrderCouponEntity.ListBean bean, boolean showToast);

        int getCouponId();

        double getCouponPrice();

        void setPriceLayout(double orderPrice,double couponPrice,double payPrice);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<PayEntity>> pay(RequestBody body);

        Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(RequestBody body);

        Observable<BaseResponse<AgreementEntity>> tariffExplanationUrl();

        Observable<BaseResponse<OrderCouponEntity>> quickCoupon();

        Observable<BaseResponse<QuickPayEntity>> quickPay(int couponId, double orderAmount);
    }
}
