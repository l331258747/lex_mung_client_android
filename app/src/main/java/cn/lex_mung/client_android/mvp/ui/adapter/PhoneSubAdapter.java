package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;

public class PhoneSubAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PhoneSubAdapter() {
        super(R.layout.item_phone_sub);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.view_time_1, item);


    }
}
