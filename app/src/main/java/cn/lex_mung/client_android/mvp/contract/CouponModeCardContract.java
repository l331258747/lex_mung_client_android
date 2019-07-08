package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface CouponModeCardContract {
    interface View extends IView {

        void initRecyclerView(MyCardAdapter adapter);

        void setEmptyView(MyCardAdapter adapter);
    }

    interface Model extends IModel {

    }
}
