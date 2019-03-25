package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;

public class CaseAdapter extends BaseQuickAdapter<CaseListEntity.ListBean, BaseViewHolder> {

    public CaseAdapter() {
        super(R.layout.item_case);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseListEntity.ListBean item) {
        helper.setText(R.id.item_tv_title, item.getTitle());
        helper.getView(R.id.item_tv_content).setVisibility(View.GONE);
        helper.setText(R.id.item_tv_content, item.getCourtopinion());
        helper.setText(R.id.item_tv_time, item.getCasenumber() + " | " + item.getCourtname());
        if (!TextUtils.isEmpty(item.getKeywords())) {
            helper.setText(R.id.item_tv_des, "关键字:" + item.getKeywords() + "," + item.getCasetype() + "," + item.getJudgementtype());
        } else {
            helper.setText(R.id.item_tv_des, "关键字:" + item.getCasetype() + "," + item.getJudgementtype());
        }
    }
}