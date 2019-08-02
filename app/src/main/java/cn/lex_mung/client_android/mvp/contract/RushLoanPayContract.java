package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;


public interface RushLoanPayContract {
    interface View extends IView {
        Activity getActivity();
        void showToAppInfoDialog();
        void showLackOfBalanceDialog();

        void setCouponLayout(OrderCouponEntity bean, boolean showToast);
        int getCouponId();
        float getCouponPrice();
        void setPriceLayout(float couponPrice,double payPrice);

        void setAllBalance(AmountBalanceEntity entity);

        void setPayTypeViewSelect(double money);

        double getTypeBalance(int payType,int payTypeGroup);

        void setCouponCountLayout(int couponCount);

    }

    interface Model extends IModel {
        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
        Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(RequestBody body);

        Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(RequestBody body);

        Observable<BaseResponse<QuickPayEntity>> quickPay(int couponId, double orderAmount);
        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(float orderAmount);

        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(float orderAmount,int productId);
        Observable<BaseResponse<QuickPayEntity>> optimalRequire(int couponId, double orderAmount,int productId);


        Observable<BaseResponse<AmountBalanceEntity>> amountBalance(RequestBody body);

        Observable<BaseResponse<Integer>> couponCount(RequestBody body);
    }
}
