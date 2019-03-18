package com.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class LawyerLIstFieldScreenAdapter extends BaseQuickAdapter<BusinessTypeEntity, BaseViewHolder> {
    private int position;
    private boolean isLeft;

    public LawyerLIstFieldScreenAdapter(List<BusinessTypeEntity> list, int position, boolean isLeft) {
        super(R.layout.item_lawyer_list_field_screen, list);
        this.position = position;
        this.isLeft = isLeft;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessTypeEntity item) {
        helper.setText(R.id.item_tv_title, item.getBusinessTypeName());
        if (isLeft) {
            helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
            if (position == helper.getLayoutPosition()) {
                helper.setBackgroundColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_ff));
            } else {
                helper.setBackgroundColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_f4f4f4));
            }
        } else {
            if (position == helper.getLayoutPosition()) {
                helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_06a66a));
                helper.getView(R.id.item_iv_icon).setVisibility(View.VISIBLE);
            } else {
                helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_323232));
                helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
            }
        }
    }
}