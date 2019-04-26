package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.ui.adapter.ServicePriceAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface ServicePriceContract {
    interface View extends IView {
        void initRecyclerView(ServicePriceAdapter adapter);

        void showToPayDialog(String s);
        void showToErrorDialog(String s);

        void showDialDialog(ExpertPriceEntity entity);

        void showDial1Dialog(String s);
    }

    interface Model extends IModel {
        Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id);

        Observable<BaseResponse<AgreementEntity>> sendCall(int id);
    }
}