package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.mvp.model.entity.home.NormalBean;
import me.zl.mvp.http.imageloader.ImageLoader;

public class HomePageRequirementTypeAdapter extends BaseQuickAdapter<NormalBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public HomePageRequirementTypeAdapter(ImageLoader imageLoader) {
        super(R.layout.item_home_page_service);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, NormalBean item) {
        if (!TextUtils.isEmpty(item.getRequireTypeIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getRequireTypeIcon())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
    }
}