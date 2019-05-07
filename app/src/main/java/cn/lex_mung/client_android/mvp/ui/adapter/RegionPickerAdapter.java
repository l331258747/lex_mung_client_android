package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import me.zl.mvp.utils.AppUtils;

public class RegionPickerAdapter extends BaseQuickAdapter<RegionEntity, BaseViewHolder> {
    private int pos = -1;

    public RegionPickerAdapter() {
        super(R.layout.item_region_picker);
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, RegionEntity item) {
        if (pos == helper.getLayoutPosition()) {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_ea5514));
            helper.getView(R.id.item_iv_check).setVisibility(View.VISIBLE);
        } else {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_323232));
            helper.getView(R.id.item_iv_check).setVisibility(View.GONE);
        }
        helper.setText(R.id.item_tv_title, item.getName());
    }
}