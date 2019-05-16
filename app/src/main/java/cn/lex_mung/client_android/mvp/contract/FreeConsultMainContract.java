package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface FreeConsultMainContract {
    interface View extends IView {
        void initRecyclerView(FreeConsultMainAdapter adapter);

        void setEmptyView(FreeConsultMainAdapter adapter);

        Activity getActivity();
    }

    interface Model extends IModel {

    }
}
