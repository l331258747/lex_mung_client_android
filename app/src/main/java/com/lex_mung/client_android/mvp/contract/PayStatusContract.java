package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface PayStatusContract {
    interface View extends IView {
        void showSuccessLayout();

        void showFailLayout(String s);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderStatusEntity>> checkOrderStatus(RequestBody body);
    }
}
