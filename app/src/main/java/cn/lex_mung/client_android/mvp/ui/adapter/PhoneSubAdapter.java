package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceTimeEntity;

public class PhoneSubAdapter extends BaseQuickAdapter<ExpertPriceTimeEntity, BaseViewHolder> {

    int position = -1;

    public PhoneSubAdapter() {
        super(R.layout.item_phone_sub);
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertPriceTimeEntity item) {
        helper.setText(R.id.view_time_1, item.getTimeStr());

        if(helper.getLayoutPosition() == position){
            helper.setBackgroundRes(R.id.view_time_1,R.drawable.ic_select);
        }else{
            helper.setBackgroundRes(R.id.view_time_1,R.drawable.round_15_78faa0_all_d6fde2);
        }

    }
}
