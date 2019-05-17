package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class FreeConsultDetail1Adapter extends BaseQuickAdapter<FreeConsultReplyListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public FreeConsultDetail1Adapter(ImageLoader imageLoader) {
        super(R.layout.item_free_detail);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeConsultReplyListEntity item) {
//        if (!TextUtils.isEmpty(item.getLawyerIconImage())) {
//            mImageLoader.loadImage(mContext
//                    , ImageConfigImpl
//                            .builder()
//                            .url(item.getLawyerIconImage())
//                            .imageView(helper.getView(R.id.iv_head))
//                            .isCircle(true)
//                            .build());
//        } else {
//            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
//        }
    }
}
