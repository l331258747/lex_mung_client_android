package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.TabOrderContractEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class TabOrderContractAdapter extends BaseQuickAdapter<TabOrderContractEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public TabOrderContractAdapter(ImageLoader imageLoader) {
        super(R.layout.item_tab_order_contract);
        mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, TabOrderContractEntity item) {

        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_content, item.getFileName());
        helper.setText(R.id.tv_file_type, item.getFileType());
        helper.setText(R.id.tv_file_size, item.getFileSize());
        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.tv_read, item.getRead());

        helper.setImageDrawable(R.id.iv_file_type,ContextCompat.getDrawable(mContext,R.drawable.ic_ppt));

        if (!TextUtils.isEmpty(item.getImgHead())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImgHead())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
    }
}
