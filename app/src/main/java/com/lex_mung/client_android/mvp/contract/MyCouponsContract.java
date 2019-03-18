package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import com.lex_mung.client_android.mvp.ui.adapter.MyCouponsAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MyCouponsContract {
    interface View extends IView {

        void initRecyclerView(MyCouponsAdapter adapter);

        void setEmptyView(MyCouponsAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<CouponsEntity>> getCouponsList(int pageNum);
    }
}
