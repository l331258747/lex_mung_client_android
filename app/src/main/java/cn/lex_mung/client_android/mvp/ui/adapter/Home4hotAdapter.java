package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class Home4hotAdapter extends BaseQuickAdapter<HomeChildEntity, BaseViewHolder> {
    ImageLoader mImageLoader;

    public Home4hotAdapter(ImageLoader mImageLoader) {
        super(R.layout.item_home4_hot);
        this.mImageLoader = mImageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeChildEntity item) {
        helper.setText(R.id.tv_name,item.getTitle());
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIcon())
                            .imageView(helper.getView(R.id.iv_img))
                            .build());
        }

    }
}
