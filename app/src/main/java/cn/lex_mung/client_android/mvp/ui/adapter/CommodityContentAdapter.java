package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.CommodityContentEntity;

public class CommodityContentAdapter extends BaseQuickAdapter<CommodityContentEntity, BaseViewHolder> {

    public CommodityContentAdapter() {
        super(R.layout.item_commodity_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityContentEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_price, item.getPriceStr());

    }
}