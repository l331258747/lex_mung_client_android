package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.security.Policy;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.DataHelper;


public class FreeConsultDetail1ListAdapter extends BaseQuickAdapter<FreeConsultReplyListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    UserInfoDetailsEntity userInfoDetailsEntity;

    public FreeConsultDetail1ListAdapter(ImageLoader imageLoader) {
        super(R.layout.item_free_detail);
        this.mImageLoader = imageLoader;
        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mContext, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeConsultReplyListEntity item) {

        if (!TextUtils.isEmpty(item.getTypeImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getTypeImage())
                            .imageView(helper.getView(R.id.iv_head))
                            .isCircle(true)
                            .build());
        } else {
            if(item.getType() == 1){
                helper.setImageResource(R.id.iv_head, R.drawable.ic_lawyer_avatar);
            }else{
                helper.setImageResource(R.id.iv_head, R.drawable.ic_avatar);
            }
        }

        helper.setText(R.id.tv_name,item.getTypeName());
        helper.setText(R.id.tv_title2,item.getTitle2());
        helper.setText(R.id.tv_area,item.getLawyerFirm());
        helper.setText(R.id.tv_area,item.getArea());
        helper.setText(R.id.tv_call,item.getMinAmountStr());

        helper.setText(R.id.tv_content,item.getContent());

        helper.setText(R.id.tv_comment,item.getReplyCountStr());
        helper.setText(R.id.tv_time,item.getDateAddedStr());

        helper.setGone(R.id.ll_delete,false);
        if(item.getType() == 1){
            helper.setGone(R.id.ll_call,true);
            helper.setGone(R.id.tv_title2,true);
            helper.setGone(R.id.iv_verify,true);
        }else{
            helper.setGone(R.id.ll_call,false);
            helper.setGone(R.id.tv_title2,false);
            helper.setGone(R.id.iv_verify,false);

            if(userInfoDetailsEntity != null && userInfoDetailsEntity.getMemberId() == item.getMemberId()){
                helper.setGone(R.id.ll_delete,true);
            }
        }

        if(helper.getAdapterPosition() == 0){
            helper.setGone(R.id.ll_comment,true);
        }else{
            helper.setGone(R.id.ll_comment,false);
        }

        helper.addOnClickListener(R.id.ll_call);
        helper.addOnClickListener(R.id.ll_delete);
    }
}
