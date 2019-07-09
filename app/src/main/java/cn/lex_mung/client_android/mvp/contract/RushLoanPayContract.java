package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface RushLoanPayContract {
    interface View extends IView {
        Activity getActivity();
        void showToAppInfoDialog();
        void showLackOfBalanceDialog();
        void setBalance(String balance);
        void setOrderMoney(String money);
        void showPayLayout();

        void setCouponLayout(OrderCouponEntity bean, boolean showToast);
        int getCouponId();
        float getCouponPrice();
        void setPriceLayout(double orderPrice,float couponPrice,double payPrice);
        void setOrderMoney(String money,double orderMoney);

    }

    interface Model extends IModel {
        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
        Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(RequestBody body);
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(RequestBody body);

        Observable<BaseResponse<QuickPayEntity>> quickPay(int couponId, double orderAmount);
        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon();

    }
}
