package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import me.zl.mvp.http.imageloader.ImageLoader;




public class FreeConsultDetail1Adapter extends BaseQuickAdapter<FreeConsultReplyListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private UserInfoDetailsEntity userInfoDetailsEntity;

    public FreeConsultDetail1Adapter(ImageLoader imageLoader,UserInfoDetailsEntity userInfoDetailsEntity) {
        super(R.layout.item_free_detail);
        this.mImageLoader = imageLoader;
        this.userInfoDetailsEntity = userInfoDetailsEntity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeConsultReplyListEntity item) {

        if (!TextUtils.isEmpty(item.getLawyerIconImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getLawyerIconImage())
                            .imageView(helper.getView(R.id.iv_head))
                            .isCircle(true)
                            .build());
        } else {
            helper.setImageResource(R.id.iv_head, R.drawable.ic_lawyer_avatar);
        }

        helper.setText(R.id.tv_name,item.getLawyerName());
        helper.setText(R.id.tv_area,item.getLawyerFirm());
        helper.setText(R.id.tv_title2,item.getLawyerPositionName());
        helper.setText(R.id.tv_call,item.getMinAmountStr());

        if(TextUtils.isEmpty(item.getMinAmount())){
            helper.setGone(R.id.ll_call,false);
        }else{
            helper.setGone(R.id.ll_call,true);
        }

        helper.setGone(R.id.iv_verify,true);
        helper.setText(R.id.tv_content,item.getContent());

        if(userInfoDetailsEntity !=null && item.getMemberId() == userInfoDetailsEntity.getMemberId() && item.getReplyCount() == 0){
            helper.setText(R.id.tv_comment,"追问");
        }else{
            helper.setText(R.id.tv_comment,item.getReplyCountStr());
        }

        helper.setText(R.id.tv_time,item.getDateAddedStr());

        helper.addOnClickListener(R.id.view_title);

    }
}
