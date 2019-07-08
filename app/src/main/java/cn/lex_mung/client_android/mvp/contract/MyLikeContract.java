package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.MyLikeEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface MyLikeContract {
    interface View extends IView {
        void setAdapter(List<MyLikeEntity> list, boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<MyLikeEntity>>> getMyLikeList(RequestBody body);
    }
}
