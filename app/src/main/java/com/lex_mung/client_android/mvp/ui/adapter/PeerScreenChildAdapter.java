package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class PeerScreenChildAdapter extends BaseQuickAdapter<PeerScreenEntity.ItemsBean, BaseViewHolder> {
    private int pos;

    PeerScreenChildAdapter(List<PeerScreenEntity.ItemsBean> list, int pos) {
        super(R.layout.item_peer_screen_child, list);
        this.pos = pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, PeerScreenEntity.ItemsBean item) {
        if (pos == helper.getLayoutPosition()) {
            helper.setBackgroundRes(R.id.item_tv_title, R.drawable.round_10_06a66a_all);
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_ff));
        } else {
            helper.setBackgroundRes(R.id.item_tv_title, R.drawable.round_10_withe_all_f4f4f4);
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_b5b5b5));
        }
        helper.setText(R.id.item_tv_title, item.getText());
    }
}
