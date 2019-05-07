package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.EducationBackgroundEntity;

public class PersonalHomePageEducationAdapter extends BaseQuickAdapter<EducationBackgroundEntity, BaseViewHolder> {

    public PersonalHomePageEducationAdapter() {
        super(R.layout.item_personal_home_page_education);
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationBackgroundEntity item) {
        try {
            helper.setText(R.id.item_tv_title, item.getEducationTitle());
            helper.setText(R.id.item_tv_position, item.getEducationName()+"  |  "+item.getEducationDescription());
            if (helper.getLayoutPosition() == getItemCount() - 1) {
                helper.getView(R.id.item_view_line).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.item_view_line).setVisibility(View.VISIBLE);
            }
            String startTime = "";
            if (!TextUtils.isEmpty(item.getStartDate())
                    && item.getStartDate().length() >= 7) {
                startTime = item.getStartDate().substring(0, 7).replace("-", "/");
            }
            String endTime = "";
            if ("2099-12-31 23:59:59".equals(item.getEndDate())) {
                endTime = "至今";
            } else if (!TextUtils.isEmpty(item.getEndDate())
                    && item.getEndDate().length() >= 7) {
                endTime = item.getEndDate().substring(0, 7).replace("-", "/");
            }
            helper.setText(R.id.item_tv_time, startTime + "-" + endTime);
        } catch (Exception ignored) {
        }
    }
}
