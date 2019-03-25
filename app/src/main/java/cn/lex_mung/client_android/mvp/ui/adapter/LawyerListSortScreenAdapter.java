package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.ConsultTypeEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class LawyerListSortScreenAdapter extends BaseQuickAdapter<ConsultTypeEntity, BaseViewHolder> {
    private int position;

    public LawyerListSortScreenAdapter(List<ConsultTypeEntity> list, int position) {
        super(R.layout.item_lawyer_list_sort_screen, list);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultTypeEntity item) {
        helper.setText(R.id.item_tv_title, item.getTypeName());
        if (position == helper.getLayoutPosition()) {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_06a66a));
            helper.getView(R.id.item_iv_icon).setVisibility(View.VISIBLE);
        } else {
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_323232));
            helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
        }
    }
}