package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class LawyerListScreenChildAdapter extends BaseQuickAdapter<LawyerListScreenEntity.ItemsBean, BaseViewHolder> {
    private int pos;

    LawyerListScreenChildAdapter(List<LawyerListScreenEntity.ItemsBean> list, int pos) {
        super(R.layout.item_lawyer_list_screen_child, list);
        this.pos = pos;
    }

    void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, LawyerListScreenEntity.ItemsBean item) {
        if (pos == helper.getLayoutPosition()) {
            helper.setBackgroundRes(R.id.item_tv_title, R.drawable.round_10_06a66a_all);
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_ff));
        } else {
            helper.setBackgroundRes(R.id.item_tv_title, R.drawable.round_10_ffffff_all_f4f4f4);
            helper.setTextColor(R.id.item_tv_title, AppUtils.getColor(mContext, R.color.c_b5b5b5));
        }
        helper.setText(R.id.item_tv_title, item.getText());
    }
}
