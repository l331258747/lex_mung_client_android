package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import cn.lex_mung.client_android.mvp.model.entity.other.LaunchLocationEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface LaunchContract {
    interface View extends IView {
        Activity getActivity();

        void launch();
    }

    interface Model extends IModel {
        Observable<BaseResponse> appStartUp();

        Observable<LaunchLocationEntity> cityjson();
    }
}
