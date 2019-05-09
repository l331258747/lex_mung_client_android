package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface OrderCouponContract {
    interface View extends IView {

        void initRecyclerView(OrderCouponAdapter adapter);

        void setEmptyView(OrderCouponAdapter adapter);

    }

    interface Model extends IModel {

    }
}
