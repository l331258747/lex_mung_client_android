package cn.lex_mung.client_android.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;

public class OrderCouponAdapter extends BaseQuickAdapter<OrderCouponEntity.ListBean, BaseViewHolder> {

    Context context;
    private int couponId = -1;

    public OrderCouponAdapter(Context context) {
        super(R.layout.item_order_coupon);
        this.context = context;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCouponEntity.ListBean item) {
        helper.setText(R.id.tv_user_rule, item.getCouponName());//名字
        helper.setText(R.id.tv_type, item.getPreferentialContent());//内容
        helper.setText(R.id.tv_end_time, item.getTimeStr());//截止时间
        helper.setText(R.id.tv_coupon_usable, item.getCouponStatusStr());//状态

        //券状态（1可使用2不可使用）

        if(item.getCouponStatus() == 1){
            helper.setBackgroundRes(R.id.view_bg,R.drawable.ic_coupon_usable_bg);
            if (couponId == item.getCouponId()) {
                helper.setBackgroundRes(R.id.view_bg,R.drawable.ic_coupon_select_bg);
            }
            helper.setImageDrawable(R.id.iv_coupon_usable,ContextCompat.getDrawable(context,R.drawable.ic_coupon_usable));
        }else if(item.getCouponStatus() == 2){
            helper.setBackgroundRes(R.id.view_bg,R.drawable.ic_coupon_usable_un_bg);
            helper.setImageDrawable(R.id.iv_coupon_usable,ContextCompat.getDrawable(context,R.drawable.ic_coupon_usable_un));
        }

        //	优惠方式（2满减3折扣）
        if(item.getPreferentialWay() == 2){
            helper.setText(R.id.tv_price, item.getReduceNumStr());//面值
            helper.setVisible(R.id.iv_price_unit,true);
            helper.setGone(R.id.tv_price_unit,false);
        }else if(item.getPreferentialWay() == 3){
            helper.setText(R.id.tv_price, item.getPreferentialDiscountStr());//面值
            helper.setVisible(R.id.iv_price_unit,false);
            helper.setGone(R.id.tv_price_unit,true);
        }
    }
}
