package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.ui.widget.FolderTextView;
import cn.lex_mung.client_android.mvp.ui.widget.Head3View;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.StringUtils;

public class HomeFreeAdapter extends BaseQuickAdapter<CommonFreeTextEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public HomeFreeAdapter(ImageLoader imageLoader) {
        super(R.layout.item_home_free);
        mImageLoader = imageLoader;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, CommonFreeTextEntity item) {
        FolderTextView tvContent = helper.getView(R.id.tv_content);
        tvContent.setEndColor(R.color.c_4A90E2);
        tvContent.setText(item.getContent());

        Head3View head3View = helper.getView(R.id.head3View);
        head3View.setHeads(mImageLoader,item.getLawyerMemberImagesStr());
        helper.setText(R.id.tv_comment,item.getReplyCountStr());
        helper.setText(R.id.tv_time,item.getDateAddedStr());
    }
}