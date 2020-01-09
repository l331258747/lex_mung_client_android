package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class Home9hotAdapter extends BaseQuickAdapter<HomeChildEntity, BaseViewHolder> {
    ImageLoader mImageLoader;

    public Home9hotAdapter(ImageLoader mImageLoader) {
        super(R.layout.item_home9_hot);
        this.mImageLoader = mImageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeChildEntity item) {
        if (!TextUtils.isEmpty(item.getIcon())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIcon())
                            .imageRadius(AppUtils.dip2px(mContext,10))
                            .imageView(helper.getView(R.id.view_card))
                            .build());
        }else{
            helper.setImageResource(R.id.view_card,R.drawable.round_20_282a3e_all);
        }

        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_content,item.getDesc1());
        helper.setText(R.id.tv_content2,item.getDesc2());
        helper.addOnClickListener(R.id.tv_btn);
    }
}
