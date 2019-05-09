package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;

public class OrderCouponAdapter extends BaseQuickAdapter<OrderCouponEntity, BaseViewHolder> {

    public OrderCouponAdapter() {
        super(R.layout.item_order_coupon);
    }
    @Override
    protected void convert(BaseViewHolder helper, OrderCouponEntity item) {

    }
}
