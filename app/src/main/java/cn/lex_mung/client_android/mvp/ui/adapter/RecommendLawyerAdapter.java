package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.help.RecommendLawyerBean;
import me.zl.mvp.http.imageloader.ImageLoader;

public class RecommendLawyerAdapter extends BaseQuickAdapter<RecommendLawyerBean, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public RecommendLawyerAdapter(ImageLoader imageLoader) {
        super(R.layout.item_recommend_lawyer);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendLawyerBean item) {
        if(!TextUtils.isEmpty(item.getIconImage())){
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getIconImage())
                            .imageView(helper.getView(R.id.iv_head))
                            .isCircle(true)
                            .build());
        }else{
            helper.setImageResource(R.id.iv_head,R.drawable.ic_lawyer_avatar);
        }

        if(item.isSend()){
            helper.setBackgroundRes(R.id.tv_btn,R.drawable.round_100_737373_all);
            helper.setText(R.id.tv_btn,"已发布");
        }else{
            helper.setBackgroundRes(R.id.tv_btn,R.drawable.round_100_1ec78a_all);
            helper.setText(R.id.tv_btn,"发布需求");
        }

        helper.addOnClickListener(R.id.tv_btn);
        helper.addOnClickListener(R.id.iv_head);

        helper.setText(R.id.tv_name,item.getMemberName());
        helper.setText(R.id.tv_name_2,item.getName3());
        helper.setText(R.id.tv_area,item.getArea());
        helper.setText(R.id.tv_field_content,item.getDescription());
    }
}
