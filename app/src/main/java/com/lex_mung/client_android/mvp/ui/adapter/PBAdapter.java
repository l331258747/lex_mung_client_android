package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.LawyerEvaluationEntity;
import com.lex_mung.client_android.mvp.ui.widget.ProgressBar;

public class PBAdapter extends BaseQuickAdapter<LawyerEvaluationEntity, BaseViewHolder> {

    public PBAdapter() {
        super(R.layout.item_pb);
    }

    @Override
    protected void convert(BaseViewHolder helper, LawyerEvaluationEntity item) {
        ProgressBar progressBar = helper.getView(R.id.item_pb);
        progressBar.setProgress(item.getScore());
        helper.setText(R.id.item_tv_title, item.getText());
    }
}