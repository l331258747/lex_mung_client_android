package cn.lex_mung.client_android.mvp.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.other.LawyerHomeVerifyDailogEntity;

/**
 * 任务团队成员列表
 */
public class LawyerHomeVerifyDialogAdapter extends BaseQuickAdapter<LawyerHomeVerifyDailogEntity, BaseViewHolder> {

    public LawyerHomeVerifyDialogAdapter() {
        super(R.layout.item_lawyer_home_verify);
    }

    @Override
    protected void convert(BaseViewHolder helper, LawyerHomeVerifyDailogEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        helper.setImageResource(R.id.iv_img, item.getRes());
    }
}