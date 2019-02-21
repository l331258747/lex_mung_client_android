package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface PeerScreenContract {
    interface View extends IView {

        void setAdapter(List<PeerScreenEntity> data);

        void setAdapterItem(int pos, PeerScreenEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<PeerScreenEntity>>> getPeerSearchList();
    }
}
