package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;

/**
 * 选择常去机构地区
 */
public class ResortInstitutionsResultAdapter extends BaseQuickAdapter<RegionEntity, BaseViewHolder> {

    public ResortInstitutionsResultAdapter() {
        super(R.layout.item_resort_institutions_result);
    }

    @Override
    protected void convert(BaseViewHolder helper, RegionEntity item) {
        helper.setText(R.id.item_tv_title, item.getName());
        helper.setImageResource(R.id.item_iv, R.drawable.ic_right_arrow);
    }
}