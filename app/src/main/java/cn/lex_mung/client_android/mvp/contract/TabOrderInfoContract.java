package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface TabOrderInfoContract {
    interface View extends IView {
        void refreshOrderInfo(RequirementDetailEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<RequirementDetailEntity>>> requirementDetail(int requirementId);
    }
}
