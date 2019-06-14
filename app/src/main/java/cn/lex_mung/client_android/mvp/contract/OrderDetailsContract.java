package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.view.View;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;

import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface OrderDetailsContract {
    interface View extends IView {
        void setOrderNo(String orderNo);

        void setOrderTime(String conversationStart);

        void setOrderName(String businessType);

        void setOrderStatus(String statusName);

        void setOrderType(String serviceType);

        void setOrderAmount(String s);

        void setOrderPayPrice(String s);

        void setPayType(String s);

        void setOrderRemain(int remain,String countDownStr);

        void call(String phone);

        void setTalkTime(String s);
        void setTalkRecord(String s);

        void setCouponLayout(int useCoupon, String couponPrice, String couponType);

        void setInfoContent(String s);

        void setOrderDetailView(int index);

        void setBottomStatus(String btnName, int textColor, int bgColor, android.view.View.OnClickListener onClick);
        void setBottomStatus(String btnName,int textColor,int bgColor,android.view.View.OnClickListener onClick,boolean isHide);
        void setLawyerLayout(int id,String name,String nameContent,String headUrl);
        void setLawyerClick(boolean isClick);

        Activity getActivity();

        void setPhone(String phone);
        void setIsReceipt(int isReceipt);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderDetailsEntity>> getOrderDetail(RequestBody body);

        Observable<BaseResponse<RemainEntity>> getUserPhone(String orderNo);

        Observable<BaseResponse<List<RequirementDetailEntity>>> requirementDetail(int requirementId,String orderNo);


    }
}
