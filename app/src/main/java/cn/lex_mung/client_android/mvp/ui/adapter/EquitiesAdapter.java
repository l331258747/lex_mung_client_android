package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class EquitiesAdapter extends BaseQuickAdapter<EquitiesListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private boolean isLogin;

    public EquitiesAdapter(ImageLoader imageLoader, boolean isLogin) {
        super(R.layout.item_equities);
        this.mImageLoader = imageLoader;
        this.isLogin = isLogin;
    }

    @Override
    protected void convert(BaseViewHolder helper, EquitiesListEntity item) {
        if (!TextUtils.isEmpty(item.getSmallImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getSmallImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .isCenterCrop(false)
                            .build());
        }
        if (isLogin) {
            helper.getView(R.id.item_tv_certified).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_tv_certified).setVisibility(View.GONE);
        }
    }
}