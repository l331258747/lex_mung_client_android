package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultListAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface FreeConsultListContract {
    interface View extends IView {
        void initRecyclerView(FreeConsultListAdapter adapter);

        void setEmptyView(FreeConsultListAdapter adapter);

        Activity getActivity();
    }

    interface Model extends IModel {

    }
}
