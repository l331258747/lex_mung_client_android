package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MyCardContract {
    interface View extends IView {

        void initRecyclerView(MyCardAdapter adapter);

        void setEmptyView(MyCardAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<CouponsEntity>>> getCouponsList(int pageNum);
    }
}
