package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface LawyerListScreenContract {
    interface View extends IView {

        void setAdapter(List<LawyerListScreenEntity> data);

        void setAdapterItem(int pos, LawyerListScreenEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<LawyerListScreenEntity>>> getPeerSearchList();
    }
}
