package com.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class OrgTagAdapter extends BaseQuickAdapter<OrgTagsEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public OrgTagAdapter(ImageLoader imageLoader) {
        super(R.layout.item_org_tag);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgTagsEntity item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
        helper.setText(R.id.item_tv_title, item.getTagName());
    }
}