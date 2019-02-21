package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class PeerSelectRegionAdapter extends BaseQuickAdapter<RegionEntity, BaseViewHolder> {
    private int position = -1;

    public PeerSelectRegionAdapter(List<RegionEntity> list, int position) {
        super(R.layout.item_peer_select_region, list);
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, RegionEntity item) {
        helper.setText(R.id.item_tv_title, item.getName());
        if (position == helper.getLayoutPosition()) {
            helper.setBackgroundColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_f4f4f4));
        } else {
            helper.setBackgroundColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_ff));
        }
    }
}