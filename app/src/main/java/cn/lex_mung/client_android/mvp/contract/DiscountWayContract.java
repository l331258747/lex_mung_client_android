package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.DiscountWayAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface DiscountWayContract {
    interface View extends IView {
        void initRecyclerView(DiscountWayAdapter adapter);

        void setIvSelect(int ic_hide_select);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<ReleaseDemandOrgMoneyEntity>>> getOrgList(RequestBody body);
    }
}
