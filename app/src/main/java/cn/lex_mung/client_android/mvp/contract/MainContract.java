package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MainContract {
    interface View extends IView {
        void startDownload(VersionEntity data);

        Activity getActivity();

        void showHelpDialog();
    }

    interface Model extends IModel {
        Observable<BaseResponse<VersionEntity>> checkVersion();
        Observable<BaseResponse<OnlineUrlEntity>> clientOnlineUrl();
    }
}
