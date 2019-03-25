package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface AddEquitiesOrgContract {
    interface View extends IView {

    }

    interface Model extends IModel {
        Observable<BaseResponse> addEquitiesOrg( RequestBody body);
    }
}
