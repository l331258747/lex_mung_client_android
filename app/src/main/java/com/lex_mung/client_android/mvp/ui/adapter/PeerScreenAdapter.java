package com.lex_mung.client_android.mvp.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;
import com.lex_mung.client_android.mvp.ui.activity.PeerScreenActivity;
import me.zl.mvp.utils.AppUtils;

import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO_TYPE;

public class PeerScreenAdapter extends BaseQuickAdapter<PeerScreenEntity, BaseViewHolder> {
    private PeerScreenActivity activity;

    public PeerScreenAdapter() {
        super(R.layout.item_peer_screen);
    }

    public void setActivity(PeerScreenActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PeerScreenEntity item) {
        helper.setText(R.id.item_tv_title, item.getText());
        View view = helper.getView(R.id.item_35);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
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
            AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mContext, 4));
            PeerScreenChildAdapter adapter = new PeerScreenChildAdapter(item.getItems(), item.getPos());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter1, view1, position) -> {
                PeerScreenEntity.ItemsBean bean = adapter.getItem(position);
                if (bean == null) return;
                adapter.setPos(position);
                activity.setPos(helper.getLayoutPosition());
                item.setPos(position);
                item.setId(bean.getId());
                item.setContent(bean.getText());
                adapter.notifyDataSetChanged();
                AppUtils.post(PEER_SCREEN_INFO, PEER_SCREEN_INFO_TYPE, bean);
            });
        } else {
            if (!TextUtils.isEmpty(item.getContent())) {
                helper.setText(R.id.item_tv_content, item.getContent());
                helper.getView(R.id.item_tv_content).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.item_tv_content).setVisibility(View.GONE);
            }
            helper.getView(R.id.item_iv_icon).setVisibility(View.VISIBLE);
            helper.getView(R.id.recycler_view).setVisibility(View.GONE);
        }
    }
}
