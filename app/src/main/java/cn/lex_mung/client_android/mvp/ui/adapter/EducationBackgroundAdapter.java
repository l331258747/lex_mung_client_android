package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.EducationBackgroundEntity;

public class EducationBackgroundAdapter extends BaseQuickAdapter<EducationBackgroundEntity, BaseViewHolder> {

    private int type;//是否显示圆点和竖线

    public EducationBackgroundAdapter(int type) {
        super(R.layout.item_education_background);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationBackgroundEntity item) {
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
        helper.setText(R.id.item_tv_name, item.getEducationTitle());
        helper.setText(R.id.item_tv_specialty, item.getEducationDescription());
        helper.setText(R.id.item_tv_education, item.getEducationName());
        switch (type) {
            case 1:
                helper.getView(R.id.item_view_1).setVisibility(View.GONE);
                helper.getView(R.id.item_iv_arrow).setVisibility(View.GONE);
                helper.getView(R.id.item_iv_point).setVisibility(View.VISIBLE);
                helper.getView(R.id.view).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_vertical_view).setVisibility(View.VISIBLE);
                break;
            case 2:
                helper.getView(R.id.item_view_1).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_iv_arrow).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_iv_point).setVisibility(View.GONE);
                helper.getView(R.id.view).setVisibility(View.GONE);
                helper.getView(R.id.item_vertical_view).setVisibility(View.GONE);
                break;
            case 3:
                helper.getView(R.id.item_view_1).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_iv_arrow).setVisibility(View.GONE);
                helper.getView(R.id.item_iv_point).setVisibility(View.GONE);
                helper.getView(R.id.view).setVisibility(View.GONE);
                helper.getView(R.id.item_vertical_view).setVisibility(View.GONE);
                break;
        }
    }
}