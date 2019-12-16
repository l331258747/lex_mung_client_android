package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;

public class HomeContractAdapter extends BaseQuickAdapter<CommonPageContractsEntity, BaseViewHolder> {

    public HomeContractAdapter() {
        super(R.layout.item_home_contract);

    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, CommonPageContractsEntity item) {
        helper.setText(R.id.tv_title,item.getContent());
        helper.setText(R.id.tv_price,item.getAmountStr());
        helper.setText(R.id.tv_price_unit,item.getUnit());
        helper.setText(R.id.tv_content,item.getDescription());


    }
}