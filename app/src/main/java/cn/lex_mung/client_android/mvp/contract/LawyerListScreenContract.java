package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.ui.activity.LawyerListScreenActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListScreenAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface LawyerListScreenContract {
    interface View extends IView {

        LawyerListScreenActivity getActivity();

        void initRecyclerView(LawyerListScreenAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<LawyerListScreenEntity>>> getPeerSearchList();
    }
}
