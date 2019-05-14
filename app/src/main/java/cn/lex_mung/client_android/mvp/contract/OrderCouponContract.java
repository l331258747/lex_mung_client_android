package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;
import retrofit2.http.Body;


public interface OrderCouponContract {
    interface View extends IView {

        void initRecyclerView(OrderCouponAdapter adapter);

        void setEmptyView(OrderCouponAdapter adapter);

        Activity getActivity();

        int getType();

    }

    interface Model extends IModel {

        Observable<BaseResponse<OrderCouponEntity>> quickCoupon(int pageNum);

    }
}
