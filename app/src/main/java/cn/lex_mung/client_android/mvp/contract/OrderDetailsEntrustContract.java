package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface OrderDetailsEntrustContract {
    interface View extends IView {
        void setOrderDetailView(int index);
        void setEntity(EntrustListEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<EntrustDetailEntity>> caseorderDetail(int id);
    }
}
