package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;

import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class ReleaseDemandServiceTypeAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {
    private int pos = -1;
    private int type;

    public ReleaseDemandServiceTypeAdapter(int type) {
        super(R.layout.item_release_demand_service_type);
        this.type = type;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
        helper.setText(R.id.item_tv_content, item.getRequireTypeDescription());
        helper.setText(R.id.item_tv_money, item.getMinAmountStr());
        if (type == 1) {//固定价格
            helper.setText(R.id.item_tv_unit, String.format("元/%s", item.getUnit()));
            helper.getView(R.id.item_iv_select).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_iv_select).setVisibility(View.GONE);
            helper.setText(R.id.item_tv_unit, "元起");
        }
        if (getItemCount() == 1) {
        } else {
        }
        if (item.getMinAmount() > 0) {//价格已经配置
            if (pos == helper.getLayoutPosition()) {//选中
                helper.setImageResource(R.id.item_iv_select, R.drawable.ic_show_select);
            } else {
                helper.setImageResource(R.id.item_iv_select, R.drawable.ic_hide_select);
            }
        } else {//价格未配置
            helper.setImageResource(R.id.item_iv_select, R.drawable.ic_no_select);
        }
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.item_view).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_view).setVisibility(View.VISIBLE);
        }
    }
}