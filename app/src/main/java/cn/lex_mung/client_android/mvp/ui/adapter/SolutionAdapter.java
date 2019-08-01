package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class SolutionAdapter extends BaseQuickAdapter<SolutionListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int type;

    public SolutionAdapter(ImageLoader imageLoader,int type) {
        super(R.layout.item_solution);
        this.mImageLoader = imageLoader;
        this.type = type;
    }

    public SolutionAdapter(ImageLoader imageLoader) {
        this(imageLoader,0);
    }

    @Override
    protected void convert(BaseViewHolder helper, SolutionListEntity item) {
        helper.setText(R.id.item_tv_title, item.getTitle());
        if(type == 0){
            String string = mContext.getString(R.string.text_has_helped)
                    + "<font color=\"#1EC88C\">"
                    + item.getHelpNumber()
                    + "</font>"
                    + mContext.getString(R.string.text_people);
            helper.setText(R.id.item_tv_count, Html.fromHtml(string));
        }else{
            String string = item.getHelpNumber() + "  阅读";
            helper.setText(R.id.item_tv_count, string);
        }

        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageRadius(AppUtils.dip2px(mContext,10))
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
    }
}
