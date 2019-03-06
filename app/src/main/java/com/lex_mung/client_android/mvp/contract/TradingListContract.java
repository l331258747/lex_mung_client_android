package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface TradingListContract {
    interface View extends IView {
        void setAdapter(List<TradingListEntity.ListBean> list, boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<TradingListEntity>> getTradingList(RequestBody body);
    }
}
