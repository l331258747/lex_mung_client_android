package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;

public class ReleaseDemandHistoryAdapter extends BaseQuickAdapter<HirstoryDemandEntity, BaseViewHolder> {


    public ReleaseDemandHistoryAdapter() {
        super(R.layout.item_release_demand_history);
    }


    @Override
    protected void convert(BaseViewHolder helper, HirstoryDemandEntity item) {
        helper.setText(R.id.tv_type_content,item.getTypeName());
        helper.setText(R.id.tv_money_content,item.getRequirementExtendValueStr());
        helper.setText(R.id.tv_question_content,item.getContent());

        helper.addOnClickListener(R.id.tv_btn);
    }
}
