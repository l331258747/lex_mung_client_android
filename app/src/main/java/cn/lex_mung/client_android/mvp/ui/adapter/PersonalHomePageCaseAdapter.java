package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;

public class PersonalHomePageCaseAdapter extends BaseQuickAdapter<CaseListEntity, BaseViewHolder> {

    public PersonalHomePageCaseAdapter() {
        super(R.layout.item_personal_home_page_case);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseListEntity item) {
        helper.setText(R.id.item_tv_title, item.getTitle());
        helper.setText(R.id.item_tv_content, item.getCourtopinion());
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.item_view_line).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_view_line).setVisibility(View.VISIBLE);
        }
    }
}
