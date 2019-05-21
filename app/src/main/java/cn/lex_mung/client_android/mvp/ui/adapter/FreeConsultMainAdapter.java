package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.ui.widget.FolderTextView;
import me.zl.mvp.http.imageloader.ImageLoader;

public class FreeConsultMainAdapter extends BaseQuickAdapter<CommonFreeTextEntity.ListBean, BaseViewHolder> {

    ImageLoader mImageLoader;
    FolderTextView tv_content;

    public FreeConsultMainAdapter(ImageLoader imageLoader) {
        super(R.layout.item_free_main);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonFreeTextEntity.ListBean item) {
        tv_content = helper.getView(R.id.tv_content);
//        TextViewEllipsize.setEllipsize(tv_content
//                ,item.getContent()
//                ,"查看详情"
//                ,ContextCompat.getColor(mContext,R.color.c_26CD8D));

        helper.setText(R.id.tv_content, item.getContent());

        if (!TextUtils.isEmpty(item.getMemberIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getMemberIconImage())
                            .imageView(helper.getView(R.id.iv_head))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.iv_head, R.drawable.ic_avatar);
        }

        helper.setText(R.id.tv_name,item.getMemberName());
        helper.setText(R.id.tv_area,item.getRegion());
        helper.setText(R.id.tv_type,item.getCategoryName());

        helper.setText(R.id.tv_comment,item.getReplyCountStr());
        helper.setText(R.id.tv_time,item.getDateAddedStr());


    }
}