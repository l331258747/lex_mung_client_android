package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerListScreenActivity;

import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

public class LawyerListScreenAdapter extends BaseQuickAdapter<LawyerListScreenEntity, BaseViewHolder> {
    private LawyerListScreenActivity activity;

    public LawyerListScreenAdapter() {
        super(R.layout.item_lawyer_list_screen);
    }

    public void setActivity(LawyerListScreenActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, LawyerListScreenEntity item) {
        helper.setText(R.id.item_tv_title, item.getText());
        View view = helper.getView(R.id.item_35);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (item.getMargin() == 35) {
            layoutParams.height = AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_35));
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        } else {
            layoutParams.height = AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_1));
            layoutParams.leftMargin = AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_35));
            layoutParams.rightMargin = AppUtils.dip2px(mContext, AppUtils.getXmlDef(mContext, R.dimen.qb_px_35));
        }
        view.setLayoutParams(layoutParams);
        if (item.getIsTile() == 1) {
            helper.getView(R.id.item_tv_content).setVisibility(View.GONE);
            helper.getView(R.id.item_iv_icon).setVisibility(View.GONE);
            helper.getView(R.id.recycler_view).setVisibility(View.VISIBLE);

            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mContext, 3));
            LawyerListScreenChildAdapter adapter = new LawyerListScreenChildAdapter(item.getItems(), item.getPos());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter1, view1, position) -> {
                LawyerListScreenEntity.ItemsBean bean = adapter.getItem(position);
                if (bean == null) return;
                adapter.setPos(position);
                activity.setPos(helper.getLayoutPosition());
                item.setPos(position);
                item.setId(bean.getId());
                item.setContent(bean.getText());
                adapter.notifyDataSetChanged();
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_TYPE, bean);
            });
        } else {
            ((TextView) helper.getView(R.id.item_tv_content)).setHint(String.format(mContext.getString(R.string.text_please_select), item.getText()));
            helper.setText(R.id.item_tv_content, item.getContent());
            helper.getView(R.id.item_tv_content).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_iv_icon).setVisibility(View.VISIBLE);
            helper.getView(R.id.recycler_view).setVisibility(View.GONE);
        }
    }
}
