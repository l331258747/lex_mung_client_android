package com.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.PayEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface AccountPayContract {
    interface View extends IView {
        void setPriceAdapter(List<String> priceList);

        Activity getActivity();

        void showToAppInfoDialog();

        void setOrderMoney(String format);
    }

    interface Model extends IModel {
        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
    }
}
