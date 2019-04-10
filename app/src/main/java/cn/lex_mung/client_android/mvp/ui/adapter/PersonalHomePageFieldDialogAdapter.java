package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import me.zl.mvp.utils.AppUtils;

public class PersonalHomePageFieldDialogAdapter extends BaseQuickAdapter<LawsHomePagerBaseEntity.ChildBean, BaseViewHolder> {

    public PersonalHomePageFieldDialogAdapter(List<LawsHomePagerBaseEntity.ChildBean> list) {
        super(R.layout.item_personal_home_page_field_dialog, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, LawsHomePagerBaseEntity.ChildBean item) {
        helper.setText(R.id.item_tv_title, item.getSolutionMarkName());
        helper.setText(R.id.item_tv_count, item.getScore() + "");
        try {
            int w = AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_300));
            int w1 = new BigDecimal(item.getScore()).divide(new BigDecimal(100)).multiply(new BigDecimal(w)).intValue();
            ProgressBar progressBar = helper.getView(R.id.item_progress_bar);
            progressBar.setMax(item.getScore());
            progressBar.setProgress(item.getScore());
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) progressBar.getLayoutParams();
            layoutParams.width = w1;
            progressBar.setLayoutParams(layoutParams);
        } catch (Exception ignored) {
        }
    }
}
