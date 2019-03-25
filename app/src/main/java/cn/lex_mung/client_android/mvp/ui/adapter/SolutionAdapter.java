package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class SolutionAdapter extends BaseQuickAdapter<SolutionListEntity.ListBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public SolutionAdapter(ImageLoader imageLoader) {
        super(R.layout.item_solution);
        mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, SolutionListEntity.ListBean item) {
        helper.setText(R.id.item_tv_title, item.getTitle());
        String string = mContext.getString(R.string.text_has_helped)
                + "<font color=\"#1EC88C\">"
                + item.getHelpNumber()
                + "</font>"
                + mContext.getString(R.string.text_people);
        helper.setText(R.id.item_tv_count, Html.fromHtml(string));
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
    }
}
