package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;

public class RechargeCouponAdapter extends BaseQuickAdapter<RechargeCouponEntity, BaseViewHolder> {

    public RechargeCouponAdapter() {
        super(R.layout.item_recharge_coupon);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeCouponEntity item) {
        helper.setText(R.id.tv_title,item.getCouponName());
        helper.setText(R.id.tv_num,item.getNumStr());
        helper.setText(R.id.tv_content,item.getPreferentialContentStr());
    }
}
