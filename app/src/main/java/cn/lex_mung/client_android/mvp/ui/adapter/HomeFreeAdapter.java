package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.ui.widget.FolderTextView2;
import cn.lex_mung.client_android.mvp.ui.widget.Head3View;
import me.zl.mvp.http.imageloader.ImageLoader;

public class HomeFreeAdapter extends BaseQuickAdapter<CommonFreeTextEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public HomeFreeAdapter(ImageLoader imageLoader) {
        super(R.layout.item_home_free);
        mImageLoader = imageLoader;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, CommonFreeTextEntity item) {
        FolderTextView2 tvContent = helper.getView(R.id.tv_content);
        tvContent.setText(item.getContent());

        Head3View head3View = helper.getView(R.id.head3View);
        head3View.setHeads(mImageLoader,item.getLawyerMemberImagesStr());
        helper.setText(R.id.tv_comment,item.getReplyCountStr());
        helper.setText(R.id.tv_time,item.getDateAddedStr());
    }
}