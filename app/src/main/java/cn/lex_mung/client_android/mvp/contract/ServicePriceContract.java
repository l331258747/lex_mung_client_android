package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ServicePriceAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface ServicePriceContract {
    interface View extends IView {
        void initRecyclerView(ServicePriceAdapter adapter);

        void showToErrorDialog(String s);

        void showBalanceNoDialog(ExpertPriceEntity entity);
        void showBalanceYesDialog(ExpertPriceEntity entity);

        void GoCall(String str);

        LawyerHomePageActivity getLawyerHomePageActivity();

    }

    interface Model extends IModel {
        Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id);

        Observable<BaseResponse<ExpertCallEntity>> sendCall(int id);
    }
}
