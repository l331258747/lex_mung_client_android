package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandHistoryAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;


public interface ReleaseDemandHistoryContract {
    interface View extends IView {
        void initRecyclerView(ReleaseDemandHistoryAdapter adapter);
        Activity getActivity();
        void setEmptyView(ReleaseDemandHistoryAdapter adapter);

        void setTipHide(boolean isShow);
    }

    interface Model extends IModel {
        Observable<BaseResponse<GeneralEntity>> releaseRequirement2(RequestBody body);

        Observable<BaseResponse<HirstoryDemandEntity>> clientRequirementOne(int pageNum);
    }
}
