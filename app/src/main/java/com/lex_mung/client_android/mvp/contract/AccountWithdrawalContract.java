package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface AccountWithdrawalContract {
    interface View extends IView {
        void setBalance(String balance);
    }

    interface Model extends IModel {
        Observable<BaseResponse> withdraw(RequestBody body);
    }
}
