package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertReserveEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface PhoneSubContract {
    interface View extends IView {
        void showToErrorDialog(String s);
        void showBalanceNoDialog();
        void showBalanceYesDialog();
    }

    interface Model extends IModel {
        Observable<BaseResponse<ExpertReserveEntity>> expertReserve(RequestBody body);
    }
}
