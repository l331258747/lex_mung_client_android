package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class BusinessConfigurationChildAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {
    private boolean isShow;

    BusinessConfigurationChildAdapter(List<BusinessEntity> list, boolean isShow) {
        super(R.layout.item_business_configuration_child, list);
        this.isShow = isShow;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {
        if (isShow) {
            helper.getView(R.id.item_iv_arrow).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_iv_arrow).setVisibility(View.GONE);
        }
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
        if (item.getRstatus() == 1) {
            helper.setText(R.id.item_tv_no_price, AppUtils.getString(mContext, R.string.text_no_price));
            helper.setTextColor(R.id.item_tv_no_price, AppUtils.getColor(mContext, R.color.c_b5b5b5));
        } else {
            helper.setText(R.id.item_tv_no_price, AppUtils.formatAmount(mContext, item.getMinAmount()) + "元/" + item.getUnit());
            helper.setTextColor(R.id.item_tv_no_price, AppUtils.getColor(mContext, R.color.c_323232));
        }
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.setVisible(R.id.item_view_line, false);
        } else {
            helper.setVisible(R.id.item_view_line, true);
        }
    }
}