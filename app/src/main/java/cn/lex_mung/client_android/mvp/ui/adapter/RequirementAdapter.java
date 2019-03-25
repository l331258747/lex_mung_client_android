package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class RequirementAdapter extends BaseQuickAdapter<RequirementStatusEntity, BaseViewHolder> {

    public RequirementAdapter() {
        super(R.layout.item_requirement);
    }

    public RequirementAdapter(List<RequirementStatusEntity> list) {
        super(R.layout.item_requirement, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, RequirementStatusEntity item) {
        if (!TextUtils.isEmpty(item.getRegion())) {
            helper.setText(R.id.item_tv_area, item.getRegion());
            helper.getView(R.id.item_ll_area).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_ll_area).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getRequirementBusiTypeName())) {
            helper.getView(R.id.item_ll_type).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tv_type, item.getRequirementBusiTypeName());
        } else {
            helper.getView(R.id.item_ll_type).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getRequirementTypeName())) {
            helper.setText(R.id.item_tv_service_type, item.getRequirementTypeName());
        }
        if (!TextUtils.isEmpty(item.getAmount())) {
            helper.setText(R.id.item_tv_price, AppUtils.formatAmount(mContext, item.getAmount()) + "元");
            helper.getView(R.id.item_ll_price).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_ll_price).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getRequirementDescriptionValue())) {
            helper.setText(R.id.item_tv_content, item.getRequirementDescriptionValue());
            helper.getView(R.id.item_ll_content).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_ll_content).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getCreateTime())) {
            helper.setText(R.id.item_tv_time, item.getCreateTime());
        }
        if (!TextUtils.isEmpty(item.getSkillName())) {
            helper.setText(R.id.item_tv_field, item.getSkillName());
            helper.getView(R.id.item_ll_field).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_ll_field).setVisibility(View.GONE);
        }
        if (item.getTags() != null
                && item.getTags().size() > 0) {
            StringBuilder tag = new StringBuilder();
            for (RequirementStatusEntity.TagsBean bean : item.getTags()) {
                tag.append(bean.getTagName()).append(",");
            }
            tag.delete(tag.length() - 1, tag.length());
            helper.setText(R.id.item_tv_tag, tag);
            helper.getView(R.id.item_ll_tag).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_ll_tag).setVisibility(View.GONE);
        }
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.item_tv_status, "未接单");
                helper.setTextColor(R.id.item_tv_status, AppUtils.getColor(mContext, R.color.c_ea5514));
                break;
            case 1:
                helper.setText(R.id.item_tv_status, "进行中");
                helper.setTextColor(R.id.item_tv_status, AppUtils.getColor(mContext, R.color.c_06a66a));
                break;
            case 2:
                helper.setText(R.id.item_tv_status, "已拒绝");
                helper.setTextColor(R.id.item_tv_status, AppUtils.getColor(mContext, R.color.c_b5b5b5));
                break;
            case 3:
                helper.setText(R.id.item_tv_status, "已关闭");
                helper.setTextColor(R.id.item_tv_status, AppUtils.getColor(mContext, R.color.c_b5b5b5));
                break;
        }
    }
}
