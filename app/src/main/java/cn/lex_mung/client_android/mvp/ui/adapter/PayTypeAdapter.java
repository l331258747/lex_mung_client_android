package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;

public class PayTypeAdapter extends BaseQuickAdapter<PayTypeEntity, BaseViewHolder> {

    public PayTypeAdapter() {
        super(R.layout.item_pay_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayTypeEntity item) {
        helper.setImageResource(R.id.iv_icon, item.getIcon());
        helper.setText(R.id.tv_name,item.getTitle());

        if(item.getBalance() < 0){
            helper.setGone(R.id.tv_balance,false);
        }else{
            helper.setGone(R.id.tv_balance,true);
            helper.setText(R.id.tv_balance,item.getBalanceStr());
        }

        if(item.isSelected()){
            helper.setImageResource(R.id.iv_select,R.drawable.ic_show_select);
        }else{
            helper.setImageResource(R.id.iv_select,R.drawable.ic_hide_select);
        }
    }
}