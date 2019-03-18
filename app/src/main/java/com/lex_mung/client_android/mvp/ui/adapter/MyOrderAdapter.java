package com.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.OrderEntity;

public class MyOrderAdapter extends BaseQuickAdapter<OrderEntity.ListBean, BaseViewHolder> {

    public MyOrderAdapter() {
        super(R.layout.item_my_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity.ListBean item) {
        helper.setText(R.id.item_tv_time, item.getCreateDate());
        helper.setText(R.id.item_tv_status, item.getStatusValue());
        helper.setText(R.id.item_tv_title, item.getOrderType());
        helper.setText(R.id.item_tv_content, "咨询类型:" + item.getTypeName());
    }
}