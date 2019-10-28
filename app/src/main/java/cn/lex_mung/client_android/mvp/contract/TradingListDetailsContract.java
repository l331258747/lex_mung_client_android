package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;
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

        void setOrderTotal(String duration);

        void setOrderStatusColor(int color);

        void setTalkRecordList(List<QuickTimeBean> lists);
    }

    interface Model extends IModel {

    }
}
