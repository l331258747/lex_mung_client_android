package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;

public class MoreSocialPositionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MoreSocialPositionAdapter(List<String> list) {
        super(R.layout.item_more_social_position, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_tv_title, item);
    }
}