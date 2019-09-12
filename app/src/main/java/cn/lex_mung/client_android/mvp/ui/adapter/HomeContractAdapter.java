package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.ui.widget.FolderTextView;
import cn.lex_mung.client_android.mvp.ui.widget.Head3View;
import me.zl.mvp.http.imageloader.ImageLoader;

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