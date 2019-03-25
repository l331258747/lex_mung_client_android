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

public class FreeConsultDetailsAdapter extends BaseQuickAdapter<FreeConsultReplyListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int lawyerId;

    public FreeConsultDetailsAdapter(ImageLoader imageLoader) {
        super(R.layout.item_consult_details);
        this.mImageLoader = imageLoader;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeConsultReplyListEntity item) {
        helper.addOnClickListener(R.id.item_tv_consult);
        helper.addOnClickListener(R.id.item_tv_delete);
        if (lawyerId == item.getLawyerId()) {
            helper.getView(R.id.item_tv_delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_tv_delete).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getLawyerIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getLawyerIconImage())
                            .imageView(helper.getView(R.id.item_iv_avatar))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
        }
        helper.setText(R.id.item_tv_time, TimeFormat.getTime(item.getDateAdded()));
        if (TextUtils.isEmpty(item.getLawyerName())) {
            helper.setText(R.id.item_tv_name, "匿名用户");
        } else {
            helper.setText(R.id.item_tv_name, item.getLawyerName());
        }
        if (!TextUtils.isEmpty(item.getLawyerFirm())) {
            helper.setText(R.id.item_tv_law_firms, item.getLawyerFirm() + "   ");
        } else {
            helper.setText(R.id.item_tv_law_firms, "");
        }
        helper.setText(R.id.item_tv_content, item.getContent());
        if (item.getReplyCount() > 1) {
            helper.getView(R.id.item_tv_reply).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tv_reply, item.getReplyCount() - 1 + "回复");
        } else {
            helper.getView(R.id.item_tv_reply).setVisibility(View.GONE);
        }
    }
}