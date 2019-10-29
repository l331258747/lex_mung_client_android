package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface OrderDetailsBuyEquityContract {
    interface View extends IView {
        void setEntity(LegalAdviserOrderDetailEntity entity);
        void refuseDialog(List<String> lists);
        void call(String phone);
    }

    interface Model extends IModel {
        Observable<BaseResponse<LegalAdviserOrderDetailEntity>> legalAdviserOrderDetail(RequestBody body);

        Observable<BaseResponse> legalAdviserOrderCancel(RequestBody body);

        Observable<BaseResponse> legalAdviserOrderComplete(RequestBody body);

        Observable<BaseResponse> legalAdviserOrderUnComplete(RequestBody body);

        Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body);

        Observable<BaseResponse<RemainEntity>> legalAdviserOrderUserPhone(String orderNo);
    }
}
