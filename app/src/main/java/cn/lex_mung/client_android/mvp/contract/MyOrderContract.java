package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface MyOrderContract {
    interface View extends IView {

        void initRecyclerView(MyOrderAdapter adapter);

        void setEmptyView(MyOrderAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderEntity>> getOrderList(RequestBody body);
    }
}
