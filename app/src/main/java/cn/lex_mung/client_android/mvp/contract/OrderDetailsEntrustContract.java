package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface OrderDetailsEntrustContract {
    interface View extends IView {
        void setEntity(EntrustListEntity entity);

        void callUserPhone(String str);
    }

    interface Model extends IModel {
        Observable<BaseResponse<EntrustListEntity>> caseorderDetail(int id);

        Observable<BaseResponse> casesourceUpdate(RequestBody body);

        Observable<BaseResponse<RemainEntity>> casesourceUserphone(int id, int lawyerId);
    }
}
