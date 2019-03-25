package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface LawsCaseContract {
    interface View extends IView {
        void setAdapter(List<CaseListEntity.ListBean> list, boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<CaseListEntity>> getCaseList(RequestBody body);
    }
}
