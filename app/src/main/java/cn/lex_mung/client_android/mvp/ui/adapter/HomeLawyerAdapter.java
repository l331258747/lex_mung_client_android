package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.StringUtils;

public class HomeLawyerAdapter extends BaseQuickAdapter<LawyerEntity2, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public HomeLawyerAdapter(ImageLoader imageLoader) {
        super(R.layout.item_home20_lawyer);
        mImageLoader = imageLoader;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, LawyerEntity2 item) {

        if (!TextUtils.isEmpty(item.getIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIconImage())
                            .imageView(helper.getView(R.id.item_iv_avatar))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_lawyer_avatar);
        }
        helper.setText(R.id.item_tv_name, item.getMemberName());
        helper.setText(R.id.item_tv_job, item.getMemberPositionNameStr());
        helper.setText(R.id.item_tv_area, item.getReginInstitutionName());
        StringUtils.setHtml(helper.getView(R.id.item_tv_field),item.getDescriptionStr());
        helper.setText(R.id.item_tv_practice_num, item.getPractice());
    }
}