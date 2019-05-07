package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.ui.adapter.MoreSocialPositionAdapter;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MoreSocialPositionContract {
    interface View extends IView {
        void initRecyclerView(MoreSocialPositionAdapter socialPositionAdapter);
    }

    interface Model extends IModel {
    }
}
