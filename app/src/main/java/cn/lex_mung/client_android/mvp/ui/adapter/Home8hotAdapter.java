package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class Home8hotAdapter extends BaseQuickAdapter<HomeChildEntity, BaseViewHolder> {
    ImageLoader mImageLoader;
    int size;

    public Home8hotAdapter(ImageLoader mImageLoader, int size) {
        super(R.layout.item_home8_hot);
        this.mImageLoader = mImageLoader;
        this.size = size;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeChildEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_content, item.getDesc1());
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(item.getIcon())
                            .imageView(helper.getView(R.id.iv_img))
                            .build());
        }

        helper.setVisible(R.id.view_line, true);
        if (helper.getLayoutPosition() == size - 1) {
            helper.setVisible(R.id.view_line, false);
        }

    }
}
