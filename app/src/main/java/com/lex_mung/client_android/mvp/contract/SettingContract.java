package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.VersionEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface SettingContract {
    interface View extends IView {

        void startDownload(VersionEntity data);
    }

    interface Model extends IModel {

        Observable<BaseResponse> logout();

        Observable<BaseResponse<VersionEntity>> checkVersion();
    }
}
