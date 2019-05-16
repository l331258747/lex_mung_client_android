package cn.lex_mung.client_android.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;

public class FreeConsultListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    TextView tv_content;
    Context context;

    public FreeConsultListAdapter(Context context) {
        super(R.layout.item_free_list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}