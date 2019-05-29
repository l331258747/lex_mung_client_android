package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.RecommendLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandHistoryAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;


public interface RecommendLawyerContract {
    interface View extends IView {
        void initRecyclerView(RecommendLawyerAdapter adapter);
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyersOther(int regionId,
                                                                                      int solutionTypeId,
                                                                                      int amountId,
                                                                                      int requireTypeId);

        Observable<BaseResponse<GeneralEntity>> releaseRequirement2(RequestBody body);
    }
}
