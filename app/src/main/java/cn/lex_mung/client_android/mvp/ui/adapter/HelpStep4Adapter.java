package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import me.zl.mvp.utils.AppUtils;

/**
 * 常去机构-法院/检察院
 */
public class HelpStep4Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HelpStep4Adapter() {
        super(R.layout.item_help_step_4);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


    }
}