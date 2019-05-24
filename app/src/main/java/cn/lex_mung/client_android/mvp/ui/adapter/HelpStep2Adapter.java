package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;

/**
 * 常去机构-法院/检察院
 */
public class HelpStep2Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HelpStep2Adapter() {
        super(R.layout.item_help_step_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_content2,item);
    }
}