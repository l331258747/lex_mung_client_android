package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface MainContract {
    interface View extends IView {
        void startDownload(VersionEntity data);

        Activity getActivity();

        void showHelpDialog();

        void showActivityDialog(List<ActivityEntity> entities);
    }

    interface Model extends IModel {
        Observable<BaseResponse<VersionEntity>> checkVersion();
        Observable<BaseResponse<OnlineUrlEntity>> clientOnlineUrl();

        Observable<BaseResponse<BaseListEntity<ActivityEntity>>> popupList(RequestBody body);
    }
}
