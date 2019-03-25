package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.TimeFormat;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class FreeConsultDetailsListAdapter extends BaseQuickAdapter<FreeConsultReplyListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int userId;
    private String region;
    private String image;
    private String name;

    public FreeConsultDetailsListAdapter(ImageLoader imageLoader,int userId, String region,String image,String name) {
        super(R.layout.item_consult_details_list);
        this.mImageLoader = imageLoader;
        this.userId = userId;
        this.region = region;
        this.image = image;
        this.name = name;
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeConsultReplyListEntity item) {
        helper.addOnClickListener(R.id.item_tv_consult);
        helper.addOnClickListener(R.id.item_tv_delete);


        if (item.getType() == 1) {//律师
            helper.getView(R.id.item_tv_consult).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_tv_delete).setVisibility(View.GONE);

            if (!TextUtils.isEmpty(item.getLawyerIconImage())) {
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(item.getLawyerIconImage())
                                .imageView(helper.getView(R.id.item_iv_avatar))
                                .isCircle(true)
                                .build());
            } else {
                helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_lawyer_avatar);
            }
            if (TextUtils.isEmpty(item.getLawyerName())) {
                helper.setText(R.id.item_tv_name, "匿名律师");
            } else {
                helper.setText(R.id.item_tv_name, item.getLawyerName());
            }
            if (!TextUtils.isEmpty(item.getLawyerFirm())) {
                helper.setText(R.id.item_tv_law_firms, item.getLawyerFirm() + "   ");
            } else {
                helper.setText(R.id.item_tv_law_firms, "");
            }
        } else {
            helper.getView(R.id.item_tv_consult).setVisibility(View.GONE);
            if (userId == item.getMemberId()) {
                helper.getView(R.id.item_tv_delete).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.item_tv_delete).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(image)) {
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(image)
                                .imageView(helper.getView(R.id.item_iv_avatar))
                                .isCircle(true)
                                .build());
            } else {
                helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
            }
            if (TextUtils.isEmpty(name)) {
                helper.setText(R.id.item_tv_name, "匿名用户");
            } else {
                helper.setText(R.id.item_tv_name, name);
            }
            helper.setText(R.id.item_tv_law_firms, region);
        }
        helper.setText(R.id.item_tv_time, TimeFormat.getTime(item.getDateAdded()));
        helper.setText(R.id.item_tv_content, item.getContent());
    }
}