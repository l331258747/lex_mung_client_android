package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.help.RequireTypeBean;

/**
 * 常去机构-法院/检察院
 */
public class HelpStep4Adapter extends BaseQuickAdapter<RequireTypeBean, BaseViewHolder> {

    private int typeId = -1;

    public void setSelection(int typeId){
        this.typeId = typeId;
        notifyDataSetChanged();
    }

    public HelpStep4Adapter() {
        super(R.layout.item_help_step_4);
    }

    @Override
    protected void convert(BaseViewHolder helper, RequireTypeBean item) {

        helper.setText(R.id.tv_content,item.getRequireTypeName());

        if(typeId == item.getRequireTypeId()){
            helper.setImageResource(R.id.iv_radio,R.drawable.ic_radio_yes);
        }else{
            helper.setImageResource(R.id.iv_radio,R.drawable.ic_radio_un);
        }
    }
}