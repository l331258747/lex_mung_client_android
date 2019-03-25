package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class ActiveAdapter extends BaseQuickAdapter<LawsHomePagerBaseEntity.DynamicInfoBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public ActiveAdapter(ImageLoader imageLoader) {
        super(R.layout.item_active);
        mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, LawsHomePagerBaseEntity.DynamicInfoBean item) {
        helper.setText(R.id.item_tv_time, item.getCreateTime());
        helper.setText(R.id.item_tv_title, item.getLawyerDynamicContent());
        if (item.getLawyerDynamicType() == 2) {
            helper.getView(R.id.item_group).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getRelatedSummary().getIcon())) {
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(item.getRelatedSummary().getIcon())
                                .isCircle(true)
                                .imageView(helper.getView(R.id.item_iv_icon))
                                .build());
            } else {
                helper.setImageResource(R.id.item_iv_icon, R.drawable.ic_avatar);
            }
            helper.setText(R.id.item_tv_content, item.getRelatedSummary().getText());
        } else {
            helper.getView(R.id.item_group).setVisibility(View.GONE);
        }
    }
}