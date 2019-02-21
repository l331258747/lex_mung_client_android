package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;

import me.zl.mvp.utils.AppUtils;

/**
 * 常去机构-法院/检察院
 */
public class ResortInstitutionAdapter extends BaseQuickAdapter<InstitutionListEntity, BaseViewHolder> {

    public ResortInstitutionAdapter() {
        super(R.layout.item_resort_institutions);
    }

    @Override
    protected void convert(BaseViewHolder helper, InstitutionListEntity item) {
        helper.setText(R.id.item_tv_title, item.getInstitutionName());
        helper.setImageResource(R.id.item_iv, R.drawable.ic_item_select);
        if (item.isSelect()) {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_06a66a));
            helper.setVisible(R.id.item_iv, true);
        } else {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_323232));
            helper.setVisible(R.id.item_iv, false);
        }
    }
}