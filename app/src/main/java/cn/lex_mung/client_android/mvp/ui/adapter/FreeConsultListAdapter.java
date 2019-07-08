package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;

public class FreeConsultListAdapter extends BaseQuickAdapter<CommonFreeTextEntity, BaseViewHolder> {


    public FreeConsultListAdapter() {
        super(R.layout.item_free_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonFreeTextEntity item) {
        helper.setText(R.id.tv_status,item.getConsultationStatusStr());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_comment,item.getReplyCountStr());
        helper.setText(R.id.tv_time,item.getDateAddedStr());
    }
}