package com.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class LawyerListAdapter extends BaseQuickAdapter<LawyerEntity.LawyerBean.ListBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public LawyerListAdapter(ImageLoader imageLoader) {
        super(R.layout.item_lawyer_list);
        mImageLoader = imageLoader;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, LawyerEntity.LawyerBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIconImage())
                            .imageView(helper.getView(R.id.item_iv_avatar))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
        }
        helper.setText(R.id.item_tv_score, "综合得分:" + item.getLawyerWeight());
        helper.setText(R.id.item_tv_name, item.getMemberName());
        helper.setText(R.id.item_tv_job, item.getMemberPositionName());
        if (!TextUtils.isEmpty(item.getRegion())) {
            if (!TextUtils.isEmpty(item.getInstitutionName())) {
                helper.getView(R.id.item_view_1).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_view_2).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.item_view_1).setVisibility(View.GONE);
                helper.getView(R.id.item_view_2).setVisibility(View.GONE);
            }
            helper.setText(R.id.item_tv_area, item.getRegion());
        } else {
            helper.getView(R.id.item_view_1).setVisibility(View.GONE);
            helper.getView(R.id.item_view_2).setVisibility(View.GONE);
        }
        helper.setText(R.id.item_tv_law_firms, item.getInstitutionName());
        helper.setText(R.id.item_tv_field, item.getDescription());
        helper.setText(R.id.item_tv_practice_num, item.getPractice());
        if (item.getOrgTags() != null
                && item.getOrgTags().size() > 0) {
            helper.getView(R.id.item_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_tv_label).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_recycler_view).setVisibility(View.VISIBLE);
            RecyclerView recyclerView = helper.getView(R.id.item_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            LabelAdapter adapter = new LabelAdapter(mImageLoader);
            recyclerView.setAdapter(adapter);
            adapter.setNewData(item.getOrgTags());
        } else {
            helper.getView(R.id.item_view).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_tv_label).setVisibility(View.GONE);
            helper.getView(R.id.item_recycler_view).setVisibility(View.GONE);
        }
    }
}