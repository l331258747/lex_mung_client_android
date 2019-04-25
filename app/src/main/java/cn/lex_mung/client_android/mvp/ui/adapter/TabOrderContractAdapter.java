package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class TabOrderContractAdapter extends BaseQuickAdapter<DocGetEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public TabOrderContractAdapter(ImageLoader imageLoader) {
        super(R.layout.item_tab_order_contract);
        mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, DocGetEntity item) {

        helper.setText(R.id.tv_name, item.getCreate_member_name());
        helper.setText(R.id.tv_content, item.getName());
        helper.setText(R.id.tv_file_type, item.getFileType());
        helper.setText(R.id.tv_file_size, item.getSize_text());
        helper.setText(R.id.tv_time, item.getCreate_time());
        helper.setText(R.id.tv_read, item.getRead());

        helper.setImageDrawable(R.id.iv_file_type,ContextCompat.getDrawable(mContext,R.drawable.ic_ppt));

        if (!TextUtils.isEmpty(item.getCreate_member_icon_image())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getCreate_member_icon_image())
                            .imageView(helper.getView(R.id.iv_head))
                            .isCircle(true)
                            .build());
        }
    }
}
