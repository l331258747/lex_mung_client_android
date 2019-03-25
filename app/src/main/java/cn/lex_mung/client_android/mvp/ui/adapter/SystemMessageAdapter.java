package cn.lex_mung.client_android.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class SystemMessageAdapter extends BaseQuickAdapter<MessageEntity.ListBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public SystemMessageAdapter(ImageLoader imageLoader) {
        super(R.layout.item_system_message);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageEntity.ListBean item) {
        mImageLoader.loadImage(mContext
                , ImageConfigImpl
                        .builder()
                        .url(item.getIcon())
                        .imageView(helper.getView(R.id.item_iv_avatar))
                        .isCircle(true)
                        .build());
        if (item.getIsRead() == 1) {
            helper.getView(R.id.item_tv_count).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_tv_count).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.item_tv_time, TimeFormat.getTime(item.getCreateTime()));
        helper.setText(R.id.item_tv_title, item.getTitle());
        helper.setText(R.id.item_tv_content, item.getContent());
    }
}