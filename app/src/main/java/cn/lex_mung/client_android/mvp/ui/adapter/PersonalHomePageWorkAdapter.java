package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.WorkexpEntity;

public class PersonalHomePageWorkAdapter extends BaseQuickAdapter<WorkexpEntity, BaseViewHolder> {

    public PersonalHomePageWorkAdapter() {
        super(R.layout.item_personal_home_page_work);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkexpEntity item) {
        try {
            helper.setText(R.id.item_tv_title, item.getInstitutionName());
            helper.setText(R.id.item_tv_position, item.getPositionName());
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
            helper.setText(R.id.item_tv_date, startTime + "-" + endTime);
        } catch (Exception ignored) {
        }
    }
}
