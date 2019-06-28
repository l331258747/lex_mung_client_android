package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;

/**
 * 常去机构-法院/检察院
 */
public class HelpStep2Adapter extends BaseQuickAdapter<SolutionTypeChildBean, BaseViewHolder> {

    private int typeId = -1;

    public HelpStep2Adapter() {
        super(R.layout.item_help_step_2);
    }

    public void setSelection(int typeId){
        if(typeId <= 0) return;
        this.typeId = typeId;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, SolutionTypeChildBean item) {

        helper.setText(R.id.tv_content,item.getSolutionTypeName());
        helper.setText(R.id.tv_content2,item.getDescription());

        if(typeId == item.getSolutionTypeId()){
            helper.setImageResource(R.id.iv_radio,R.drawable.ic_radio_yes);
        }else{
            helper.setImageResource(R.id.iv_radio,R.drawable.ic_radio_un);
        }

    }
}