package cn.lex_mung.client_android.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.JobSkillsEntity;

/**
 * 个人展示页面的专业技能
 */
public class PHPSkillsAdapter extends BaseQuickAdapter<JobSkillsEntity, BaseViewHolder> {

    public PHPSkillsAdapter() {
        super(R.layout.item_php_skill);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobSkillsEntity item) {
        helper.setText(R.id.item_tv_title, item.getSkillName());
    }
}
