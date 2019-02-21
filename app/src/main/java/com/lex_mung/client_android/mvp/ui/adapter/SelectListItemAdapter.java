package com.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.HonorTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.LawsJobEntity;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;
import com.lex_mung.client_android.mvp.model.entity.QualificationTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.SelectList;
import com.lex_mung.client_android.mvp.model.entity.SocialTypeEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

/**
 * 选择item的adapter
 */
public class SelectListItemAdapter extends BaseQuickAdapter<SelectList, BaseViewHolder> {
    private int type;
    private int id;

    public SelectListItemAdapter(int type, int id, List<SelectList> lists) {
        super(R.layout.item_select_list_item, lists);
        this.id = id;
        this.type = type;
    }

    public SelectListItemAdapter(int type, List<SelectList> lists) {
        super(R.layout.item_select_list_item, lists);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectList item) {
        switch (type) {
            case 0:
                helper.setText(R.id.item_tv_title, ((LawsJobEntity) item).getMemberPositionName());
                break;
            case 1:
                helper.setText(R.id.item_tv_title, ((SocialTypeEntity) item).getMemberSocialFunctionName());
                break;
            case 2:
                helper.setText(R.id.item_tv_title, ((HonorTypeEntity) item).getMemberHonorName());
                break;
            case 3:
                helper.setText(R.id.item_tv_title, ((QualificationTypeEntity) item).getQualificationTypeName());
                break;
            case 4:
                helper.setText(R.id.item_tv_title, ((FeedbackTypeEntity) item).getFeedbackTypeName());
                helper.setText(R.id.item_tv_content, ((FeedbackTypeEntity) item).getFeedbackTypeDescription());
                helper.getView(R.id.item_tv_content).setVisibility(View.VISIBLE);
                break;
            case 5:
                PeerScreenEntity.ItemsBean bean = (PeerScreenEntity.ItemsBean) item;
                if (id == bean.getId()) {
                    helper.getView(R.id.item_iv_select).setVisibility(View.VISIBLE);
                    helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_06a66a));
                } else {
                    helper.getView(R.id.item_iv_select).setVisibility(View.GONE);
                    helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_323232));
                }
                helper.setText(R.id.item_tv_title, bean.getText());
                break;
        }
    }
}