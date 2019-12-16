package cn.lex_mung.client_android.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;


public class OrderCouponAdapter2 extends BaseQuickAdapter<OrderCouponEntity, BaseViewHolder> {

    Context context;
    private int couponId = -1;
    private int type;//1我的优惠券，0快速咨询，2热门需求

    public OrderCouponAdapter2(Context context, int type) {
        super(R.layout.item_order_coupon2);
        this.context = context;
        this.type = type;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCouponEntity item) {
        helper.setText(R.id.tv_user_rule, item.getCouponName());//名字
        helper.setText(R.id.tv_type, item.getPreferentialContent());//内容
        helper.setText(R.id.tv_end_time, item.getTimeStr());//截止时间
        helper.setText(R.id.tv_coupon_usable, item.getCouponStatusStr());//状态

        if(type == 1){//我的优惠券查看详情
            helper.addOnClickListener(R.id.tv_type);
        }

        //券状态（1可使用2不可使用）
        helper.setGone(R.id.view_select, false);
        if (item.getCouponStatus() == 1) {//1可使用
            helper.setTextColor(R.id.tv_price, ContextCompat.getColor(context,R.color.c_FF7000));
            helper.setTextColor(R.id.tv_price_unit, ContextCompat.getColor(context,R.color.c_FF7000));
            helper.setTextColor(R.id.tv_price_tip, ContextCompat.getColor(context,R.color.c_FF7000));
            helper.setBackgroundRes(R.id.tv_price_tip, R.drawable.round_5_33ff7000_all);
            helper.setBackgroundRes(R.id.view_right_bg,R.drawable.ic_coupon_bg_right);
            helper.setGone(R.id.iv_coupon_cannot, false);
            if (couponId == item.getCouponId()) {
                helper.setGone(R.id.view_select, true);
            }
        } else if (item.getCouponStatus() == 2) {//2不可使用
            helper.setTextColor(R.id.tv_price, ContextCompat.getColor(context,R.color.c_BFBFC3));
            helper.setTextColor(R.id.tv_price_unit, ContextCompat.getColor(context,R.color.c_BFBFC3));
            helper.setTextColor(R.id.tv_price_tip, ContextCompat.getColor(context,R.color.c_BFBFC3));
            helper.setBackgroundRes(R.id.tv_price_tip, R.drawable.round_5_33bfbfc3_all);
            helper.setBackgroundRes(R.id.view_right_bg,R.drawable.ic_coupon_bg_right_un);
            helper.setGone(R.id.iv_coupon_cannot, true);
        }

        helper.setText(R.id.tv_price_tip, item.getRule());

        //	优惠方式（2满减3折扣）
        if (item.getPreferentialWay() == 2) {
            helper.setText(R.id.tv_price, item.getReduceNumStr());//面值
            helper.setText(R.id.tv_price_unit, "元");
        } else if (item.getPreferentialWay() == 3) {
            helper.setText(R.id.tv_price, item.getPreferentialDiscountStr());//面值
            helper.setText(R.id.tv_price_unit, "折");
        }
    }
}
