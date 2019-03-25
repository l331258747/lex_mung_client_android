package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.TokenEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface LoginContract {
    interface View extends IView {
        void showCode(String code);

        void setCodeButtonStatus(boolean b, String s, int color);
    }

    interface Model extends IModel {
        Observable<BaseResponse> getCode(RequestBody body);

        Observable<BaseResponse<TokenEntity>> login(RequestBody body);

        Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail();

        Observable<BaseResponse<TokenEntity>> getCode(String phone);

        Observable<BaseResponse<AgreementEntity>> lawyerRegisterAgreementUrl();

    }
}
