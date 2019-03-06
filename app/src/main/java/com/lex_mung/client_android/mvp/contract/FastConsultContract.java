package com.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import com.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.PayEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface FastConsultContract {
    interface View extends IView {

        void setPhone(String mobile);

        void setBalance(String balance);

        void setOrderMoney(String money);

        void showToAppInfoDialog();

        void showLackOfBalanceDialog();

        Activity getActivity();

        void setMoney(String money);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
    }
}
