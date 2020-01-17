package cn.lex_mung.client_android.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustListLawyersBean;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.StringUtils;

public class EntrustLawyerAdapter extends BaseQuickAdapter<EntrustListLawyersBean, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int lawyerId;

    public EntrustLawyerAdapter(int lawyerId, ImageLoader imageLoader) {
        super(R.layout.item_entrust_lawyer);
        mImageLoader = imageLoader;
        this.lawyerId = lawyerId;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder helper, EntrustListLawyersBean item) {

        if (!TextUtils.isEmpty(item.getIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIconImage())
                            .imageView(helper.getView(R.id.item_iv_avatar))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
        }
        helper.setText(R.id.item_tv_name, item.getMemberName());
        helper.setText(R.id.item_tv_job, item.getMemberPositionNameStr());
        helper.setText(R.id.item_tv_area, item.getReginInstitutionName());
        StringUtils.setHtml(helper.getView(R.id.item_tv_field), item.getDescriptionStr());

        //保证金
        helper.setGone(R.id.ll_tag, false);
        if (item.getTag() != null && !TextUtils.isEmpty(item.getTag().getTagName())) {
            helper.setGone(R.id.ll_tag, true);
            helper.setText(R.id.tv_credit_certification, item.getTag().getTagName());

            if (!TextUtils.isEmpty(item.getTag().getImage())) {
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(item.getTag().getImage())
                                .imageView(helper.getView(R.id.iv_credit_certification))
                                .isCircle(true)
                                .build());
            } else {
                helper.setImageResource(R.id.iv_credit_certification, R.drawable.ic_personal_home_page_credit_certification);
            }
        }

        //履历
        helper.setGone(R.id.ll_curriculum_vitae,false);
        if(!TextUtils.isEmpty(item.getCurriculumContent())){
            helper.setGone(R.id.ll_curriculum_vitae,true);
            helper.setText(R.id.tv_curriculum_vitae,item.getCurriculumContent());
        }

        //服务优势
        helper.setGone(R.id.ll_advantage, false);
        if (!TextUtils.isEmpty(item.getAdvantage())) {
            helper.setGone(R.id.ll_advantage, true);
            helper.setText(R.id.tv_advantage, item.getAdvantage());
        }

        //确认 联系律师 按钮
        helper.setGone(R.id.ll_btn, false);
        if (lawyerId == 0) {
            helper.setGone(R.id.ll_btn, true);
        }

        //选定为律师 角标
        if (item.getStatus() == 2) {
            helper.setGone(R.id.iv_badge_number, true);
        } else {
            helper.setGone(R.id.iv_badge_number, false);
        }

        if (!TextUtils.isEmpty(item.getPractice())) {
            helper.setText(R.id.item_tv_practice_num, item.getPractice());
            helper.setGone(R.id.item_tv_practice_num, true);
        } else {
            helper.setGone(R.id.item_tv_practice_num, false);
        }

        helper.addOnClickListener(R.id.tv_btn_left);
        helper.addOnClickListener(R.id.tv_btn_right);
    }
}