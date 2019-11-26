package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.CommodityContentEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderConfirmEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.PrivategroupBuyEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface RushLoanPayContract {
    interface View extends IView {
        Activity getActivity();
        void showToAppInfoDialog();
        void showLackOfBalanceDialog();

        void setCouponLayout(OrderCouponEntity bean, boolean showToast);
        int getCouponId();
        float getCouponPrice();
        float getOrderPrice();

        void setPriceLayout(float couponPrice,double payPrice);

        void setAllBalance(AmountBalanceEntity entity);

        void setPayTypeViewSelect(double money);

        double getTypeBalance(int payType,int payTypeGroup);

        void setCouponCountLayout(int couponCount);

        void setCommodityContent(List<CommodityContentEntity> entities);
    }

    interface Model extends IModel {

        //快速咨询
        Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(RequestBody body);//创建订单
        Observable<BaseResponse<QuickPayEntity>> quickPay(int couponId, double orderAmount);//实付金额
        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(float orderAmount);//优惠券列表

        //热门需求
        Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(RequestBody body);//创建订单
        Observable<BaseResponse<QuickPayEntity>> optimalRequire(int couponId, double orderAmount,int productId);//实付金额
        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(float orderAmount,int productId);//优惠券列表

        Observable<BaseResponse<AmountBalanceEntity>> amountBalance(RequestBody body);//获取余额
        Observable<BaseResponse<Integer>> couponCount(RequestBody body);//优惠券数量
        Observable<BaseResponse<PayEntity>> pay(RequestBody body);//支付

        //付费权益
        Observable<BaseResponse<LegalAdviserOrderConfirmEntity>> legalAdviserOrderConfirm(RequestBody body);//获取子项数据
        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> legalAdviserServerCoupon(float priceTotal);//优惠券列表
        Observable<BaseResponse<LegalAdviserOrderPayEntity>> legalAdviserOrderPay(RequestBody body);//创建
        Observable<BaseResponse<QuickPayEntity>> legalAdviserOrderAmount(int couponId, double orderAmount);//实付金额

        //在线法律顾问
        Observable<BaseResponse<PrivategroupBuyEntity>> privategroupBuy(RequestBody body);
    }
}
