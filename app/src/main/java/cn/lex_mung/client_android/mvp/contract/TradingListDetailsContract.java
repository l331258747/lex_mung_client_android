package cn.lex_mung.client_android.mvp.contract;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface TradingListDetailsContract {
    interface View extends IView {
        void setOrderNo(String orderNo);

        void setOrderTime(String conversationStart);

        void setOrderName(String businessType);

        void setOrderAmount(String s, String equals, int color, String money);

        void setOrderStatus(String statusName);

        void setOrderCustomer(String memberName);

        void setOrderStartTime(String conversationStart);

        void setOrderEndTime(String conversationEnd);

        void setOrderTotal(String duration);

        void showLayout();

        void setOrderStatusColor(int color);
    }

    interface Model extends IModel {

    }
}
