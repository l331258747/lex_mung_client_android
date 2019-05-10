package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;

public class OrderCouponAdapter extends BaseQuickAdapter<OrderCouponEntity.ListBean, BaseViewHolder> {

    public OrderCouponAdapter() {
        super(R.layout.item_order_coupon);
    }
    @Override
    protected void convert(BaseViewHolder helper, OrderCouponEntity.ListBean item) {
        helper.setText(R.id.tv_price, item.getReduceNumStr());//面值
        helper.setText(R.id.tv_user_rule, item.getRule());//规则
        helper.setText(R.id.tv_type, item.getPreferentialContent());//内容
        helper.setText(R.id.tv_end_time, item.getCouponName());//截止时间
        helper.setText(R.id.tv_coupon_usable, item.getCouponStatusStr());//状态
    }
}
