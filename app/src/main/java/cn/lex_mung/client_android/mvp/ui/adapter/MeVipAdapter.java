package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.RightsVipEntity;

public class MeVipAdapter extends BaseQuickAdapter<RightsVipEntity, BaseViewHolder> {

    public MeVipAdapter() {
        super(R.layout.item_me_vip);
    }

    @Override
    protected void convert(BaseViewHolder helper, RightsVipEntity item) {
        helper.setText(R.id.tv_name, item.getEquityName());
    }
}