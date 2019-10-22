package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CouponsMainEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;

public interface MyCardContract {
    interface View extends IView {

        void initRecyclerView(MyCardAdapter adapter);

        void setEmptyView(MyCardAdapter adapter);

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<CouponsMainEntity>> getCouponsList(int pageNum);
    }
}
