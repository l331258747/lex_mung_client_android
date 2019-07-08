package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface SolutionLIstContract {
    interface View extends IView {

        void setAdapter(List<SolutionListEntity> list, boolean isAdd);
    }

    interface Model extends IModel {

        Observable<BaseResponse<BaseListEntity<SolutionListEntity>>> getSolutionList(RequestBody body);
    }
}
