package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface OrderDetailsContract {
    interface View extends IView {
        void setOrderNo(String orderNo);

        void setOrderTime(String conversationStart);

        void setOrderName(String businessType);

        void setOrderType(String serviceType);

        void setOrderAmount(String s);

        void setOrderStatus(String statusName);

        void setOrderCustomer(String memberName);

        void setOrderStartTime(String conversationStart);

        void setOrderEndTime(String conversationEnd);

        void setOrderTotal(String duration);

        void showLayout(int i);

        void setOrderRemain(int remain);

        void call(String phone);

        void setCouponLayout(String couponPrice, String amountPrice);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderDetailsEntity>> getOrderDetail(RequestBody body);

        Observable<BaseResponse<RemainEntity>> getUserPhone(String orderNo);
    }
}
