package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;

import me.zl.mvp.utils.AppUtils;

public class MyAccountPayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int pos;

    public MyAccountPayAdapter(List<String> priceList) {
        super(R.layout.item_my_account_pay, priceList);
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (pos == helper.getLayoutPosition()) {
            helper.setBackgroundRes(R.id.ll_price, R.drawable.round_10_f6e4ca_all_edca95);
            helper.setTextColor(R.id.item_tv_price,AppUtils.getColor(mContext, R.color.c_ff));
            helper.setTextColor(R.id.item_tv_price_unit,AppUtils.getColor(mContext, R.color.c_ff));
        } else {
            helper.setBackgroundRes(R.id.ll_price, R.drawable.round_10_fbf4ea_all_edca95);
            helper.setTextColor(R.id.item_tv_price,AppUtils.getColor(mContext, R.color.c_ecc994));
            helper.setTextColor(R.id.item_tv_price_unit,AppUtils.getColor(mContext, R.color.c_ecc994));
        }
        helper.setText(R.id.item_tv_price, item);
    }
}