package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface OrderCouponContract {
    interface View extends IView {

        void initRecyclerView(OrderCouponAdapter2 adapter);

        void setEmptyView(OrderCouponAdapter2 adapter);

        Activity getActivity();

        int getType();

        void showDetailDialog(String str);

    }

    interface Model extends IModel {

        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(int pageNum, double orderAmount);

        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> requireCoupon(int pageNum);

        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(int pageNum, double orderAmount,int productId);

        Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> legalAdviserServerCoupon(int pageNum,double priceTotal);//优惠券列表

    }
}
