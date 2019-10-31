package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.TradingListEntity;

import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class TradingListAdapter extends BaseQuickAdapter<TradingListEntity, BaseViewHolder> {

    public TradingListAdapter() {
        super(R.layout.item_trading_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradingListEntity item) {
        helper.setText(R.id.item_tv_title, item.getOrderType());
        helper.setText(R.id.item_tv_time, item.getCreateDate());
        helper.setText(R.id.item_tv_status, "(" + item.getPayStatus() + ")");

        helper.setGone(R.id.item_tv_title2,false);
        if(!TextUtils.isEmpty(item.getTypeAliasName())){
            helper.setGone(R.id.item_tv_title2,true);
            helper.setText(R.id.item_tv_title2,item.getTypeAliasName());
        }

        if(item.getBuyerPayAmount() >= 0){
            helper.setText(R.id.item_tv_price, "+" + StringUtils.getStringNum(item.getBuyerPayAmount()));
            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_ea5514));
        }else if(item.getBuyerPayAmount() < 0){
            helper.setText(R.id.item_tv_price, StringUtils.getStringNum(item.getBuyerPayAmount()));
            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_06a66a));
        }

//        if ("充值".equals(item.getOrderType())) {
//            helper.setText(R.id.item_tv_price, "+" + StringUtils.getStringNum(item.getBuyerPayAmount()));
//        } else {
//            helper.setText(R.id.item_tv_price, StringUtils.getStringNum(item.getBuyerPayAmount()));
//        }
//        if (item.getOrderStatus() == 2
//                || item.getOrderStatus() == 4) {
//            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_06a66a));
//        } else {
//            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_ea5514));
//        }
    }
}