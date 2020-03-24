package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.corporate.CorporateDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderComplaintEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface OrderDetailsAnnualContract {
    interface View extends IView {
        void setEntity(CorporateDetailEntity entity);
        void call(String phone);
        void refuseDialog(List<String> lists);
    }

    interface Model extends IModel {

        Observable<BaseResponse<CorporateDetailEntity>> corporateDetail(int id);

        Observable<BaseResponse<RemainEntity>> corporateUserphone(int id);

        Observable<BaseResponse<List<LegalAdviserOrderComplaintEntity>>> legalAdviserOrderComplaint(RequestBody body);

        Observable<BaseResponse> corporateUncomplete(RequestBody body);
        Observable<BaseResponse> corporateComplete(RequestBody body);

        Observable<BaseResponse> corporateCancel(RequestBody body);

    }
}
