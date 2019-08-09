package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;

public interface HelpStepLawyerContract {

    interface View extends IView {
        void setData(HelpStepLawyerEntity entity);

//        void showToErrorDialog(String s);
//        void showBalanceNoDialog(ExpertPriceEntity entity);
//        void showBalanceYesDialog(ExpertPriceEntity entity);
//        void GoCall(String str);
        void showExpertPrice(ExpertPriceEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyers(int regionId,
                                                                                 int solutionTypeId,
                                                                                 int amountId,
                                                                                 int requireTypeId,
                                                                                 int memberId);

        Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id);

        Observable<BaseResponse<ExpertCallEntity>> sendCall(int id);
    }
}
