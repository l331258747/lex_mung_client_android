package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface FreeConsultContract {
    interface View extends IView {
        Activity getActivity();

        void showToAppInfoDialog();

        void getLocation();
    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeConsultEntity>> releaseFreeConsult(@Body RequestBody body);
    }
}
