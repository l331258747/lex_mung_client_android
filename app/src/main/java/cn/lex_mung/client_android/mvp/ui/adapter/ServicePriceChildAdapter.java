package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import me.zl.mvp.utils.AppUtils;

public class ServicePriceChildAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {

    ServicePriceChildAdapter(List<BusinessEntity> list) {
        super(R.layout.item_service_price_child, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
        if (item.getRstatus() == 1) {
            helper.setText(R.id.item_tv_price, AppUtils.getString(mContext, R.string.text_no_price));
            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_b5b5b5));
        } else {
            helper.setText(R.id.item_tv_price, AppUtils.formatAmount(mContext, item.getMinAmount()) + "元/" + item.getUnit());
            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_323232));
        }
    }
}