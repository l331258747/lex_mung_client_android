package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface WebContract {
    interface View extends IView {
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail();
    }
}
