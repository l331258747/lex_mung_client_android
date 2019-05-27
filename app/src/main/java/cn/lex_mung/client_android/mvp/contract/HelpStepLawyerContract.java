package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface HelpStepLawyerContract {
    interface View extends IView {
        void setData(HelpStepLawyerEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyers(int regionId,
                                                                                 int solutionTypeId,
                                                                                 int amountId,
                                                                                 int requireTypeId);
    }
}
