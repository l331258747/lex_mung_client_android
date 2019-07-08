package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;

import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface MyOrderContract {
    interface View extends IView {

        void initRecyclerView(MyOrderAdapter2 adapter);

        void setEmptyView(MyOrderAdapter2 adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<OrderEntity>>> getOrderList(RequestBody body);
    }
}
