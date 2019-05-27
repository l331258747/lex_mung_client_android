package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.help.RequireInfoChildBean;
import me.zl.mvp.utils.AppUtils;

public class HelpStepLawyerAdapter extends BaseQuickAdapter<RequireInfoChildBean, BaseViewHolder> {

    public HelpStepLawyerAdapter(List<RequireInfoChildBean> list) {
        super(R.layout.item_help_step_lawyer, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, RequireInfoChildBean item) {
        helper.setText(R.id.tv_name, item.getRequireTypeName());
        helper.setText(R.id.tv_content,item.getRequireTypeDescription());

        if (item.getRequirementType() == 1) {
            helper.setText(R.id.item_tv_release, "发布需求");
        } else {
            helper.setText(R.id.item_tv_release, "拨打电话");
        }

        if (item.getRstatus() == 1) {
            helper.setText(R.id.tv_money, AppUtils.getString(mContext, R.string.text_no_price));
            helper.setTextColor(R.id.tv_money, AppUtils.getColor(mContext, R.color.c_b5b5b5));
        } else {
            helper.setText(R.id.tv_money, AppUtils.formatAmount(mContext, item.getMinAmount()) + "元/" + item.getUnit());
            helper.setTextColor(R.id.tv_money, AppUtils.getColor(mContext, R.color.c_323232));
        }

        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.view_line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }

    }
}
