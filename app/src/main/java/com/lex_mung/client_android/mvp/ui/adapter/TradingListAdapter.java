package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;

import me.zl.mvp.utils.AppUtils;

public class TradingListAdapter extends BaseQuickAdapter<TradingListEntity.ListBean, BaseViewHolder> {

    public TradingListAdapter() {
        super(R.layout.item_trading_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradingListEntity.ListBean item) {
        helper.setText(R.id.item_tv_title, item.getOrderType());
        helper.setText(R.id.item_tv_time, item.getCreateDate());
        helper.setText(R.id.item_tv_status, "(" + item.getPayStatus() + ")");

        if (item.getOrderStatus() == 2
                || item.getOrderStatus() == 4) {
            if ("支出".equals(item.getBudget())) {
                helper.setText(R.id.item_tv_price, AppUtils.formatAmount(mContext, item.getBuyerPayAmount()));
                helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_323232));
            } else {
                helper.setText(R.id.item_tv_price, "+" + AppUtils.formatAmount(mContext, item.getBuyerPayAmount()));
                helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_06a66a));
            }
        } else {
            helper.setTextColor(R.id.item_tv_price, AppUtils.getColor(mContext, R.color.c_ea5514));
            if ("支出".equals(item.getBudget())) {
                helper.setText(R.id.item_tv_price, AppUtils.formatAmount(mContext, item.getBuyerPayAmount()) + "");
            } else {
                helper.setText(R.id.item_tv_price, "+" + AppUtils.formatAmount(mContext, item.getBuyerPayAmount()));
            }
        }
    }
}