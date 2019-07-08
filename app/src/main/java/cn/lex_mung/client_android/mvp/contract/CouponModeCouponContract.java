package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface CouponModeCouponContract {
    interface View extends IView {
        void initRecyclerView(OrderCouponAdapter adapter);

        void setEmptyView(OrderCouponAdapter adapter);

        Fragment getFragment();
    }

    interface Model extends IModel {

    }
}
