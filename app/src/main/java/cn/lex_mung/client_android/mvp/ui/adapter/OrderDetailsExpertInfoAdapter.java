package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;

public class OrderDetailsExpertInfoAdapter extends BaseQuickAdapter<QuickTimeBean, BaseViewHolder> {

    public OrderDetailsExpertInfoAdapter() {
        super(R.layout.item_order_details_expter_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuickTimeBean item) {
        helper.setText(R.id.tv_title, item.getBeginTime());
        helper.setText(R.id.tv_content,"通话"+item.getCalllength());
    }
}