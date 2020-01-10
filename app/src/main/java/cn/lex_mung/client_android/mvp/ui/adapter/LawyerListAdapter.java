package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.StringUtils;

public class LawyerListAdapter extends BaseQuickAdapter<LawyerEntity2, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public LawyerListAdapter(ImageLoader imageLoader) {
        super(R.layout.item_lawyer_list);
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
        helper.setText(R.id.item_tv_practice_num, item.getPractice());

        helper.setGone(R.id.item_tv_field, false);
        if (!TextUtils.isEmpty(item.getDescriptionStr())) {
            helper.setGone(R.id.item_tv_field, true);
            StringUtils.setHtml(helper.getView(R.id.item_tv_field),item.getDescriptionStr());
        }

        //保证金
        helper.setGone(R.id.ll_tag, false);
        if (!TextUtils.isEmpty(item.getTagName())) {
            helper.setGone(R.id.ll_tag, true);
            helper.setText(R.id.tv_credit_certification, item.getTagName());
        }

        //履历
        helper.setGone(R.id.ll_curriculum_vitae,false);
        if(!TextUtils.isEmpty(item.getCurriculumContent())){
            helper.setGone(R.id.ll_curriculum_vitae,true);
            helper.setText(R.id.tv_curriculum_vitae,item.getCurriculumContent());
        }

        helper.setGone(R.id.item_btn,false);
    }
}