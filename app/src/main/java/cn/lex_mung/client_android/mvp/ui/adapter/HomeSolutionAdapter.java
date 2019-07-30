package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class HomeSolutionAdapter extends BaseQuickAdapter<CommonSolutionEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public HomeSolutionAdapter(ImageLoader mImageLoader) {
        super(R.layout.item_home_solution);
        this.mImageLoader = mImageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonSolutionEntity item) {
        helper.setText(R.id.tv_name,item.getAlias());
        if (!TextUtils.isEmpty(item.getAppIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(item.getAppIcon())
                            .imageView(helper.getView(R.id.iv_img))
                            .build());
        }
    }
}
