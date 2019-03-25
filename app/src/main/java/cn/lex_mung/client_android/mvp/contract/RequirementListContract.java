package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface RequirementListContract {
    interface View extends IView {

        void initRecyclerView(RequirementAdapter adapter);
    }

    interface Model extends IModel {

    }
}
