package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.OrderPrivateLawyersDetailEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface OrderDetailsPrivateLawyerContract {
    interface View extends IView {
        void setEntity(OrderPrivateLawyersDetailEntity entity);
        void call(String phone);
        void refuseDialog(List<String> lists);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderPrivateLawyersDetailEntity>> orderPrivateLawyersDetail(int id);

        Observable<BaseResponse<RemainEntity>> privateLawyersUserphone(String orderNo);

        Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body);

        Observable<BaseResponse> privateLawyersOrderCancel(RequestBody body);

        Observable<BaseResponse> privateLawyersOrderUpdate(RequestBody body);
    }
}
