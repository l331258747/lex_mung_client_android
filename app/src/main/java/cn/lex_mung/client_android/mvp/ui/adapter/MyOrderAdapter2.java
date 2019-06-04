package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;
import me.zl.mvp.http.imageloader.ImageLoader;

public class MyOrderAdapter2 extends BaseQuickAdapter<OrderEntity.ListBean, BaseViewHolder> {

    private ImageLoader mImageLoader;

    public MyOrderAdapter2(ImageLoader mImageLoader) {
        super(R.layout.item_my_order2);
        this.mImageLoader = mImageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity.ListBean item) {
        helper.setText(R.id.item_tv_time, item.getCreateDate());
        if(item.getTypeId() == 5){
            helper.setText(R.id.item_tv_title, item.getTypeName());
        }else{
            helper.setText(R.id.item_tv_title, item.getOrderType());
        }

        if(item.getBuyerPayAmount() > 0){
            helper.setGone(R.id.ll_price, true);
            helper.setText(R.id.tv_price, item.getBuyerPayAmountStr());
        }else{
            helper.setGone(R.id.ll_price, false);
        }


        helper.setText(R.id.item_tv_status, item.getStatusValue());
        if(item.getOrderStatus() == 3 || item.getOrderStatus() == 7){//3已关闭，7已完成
            helper.setBackgroundRes(R.id.item_tv_status,R.drawable.round_100_f8f8f8_all_d8d8d8);
            helper.setTextColor(R.id.item_tv_status,ContextCompat.getColor(mContext,R.color.c_d8d8d8));
        }else{
            helper.setBackgroundRes(R.id.item_tv_status,R.drawable.round_100_fae5db_all_eb5514);
            helper.setTextColor(R.id.item_tv_status,ContextCompat.getColor(mContext,R.color.c_EB5514));
        }


        if(!TextUtils.isEmpty(item.getContent()) || item.getTypeId() == 4){
            if(item.getTypeId() == 4){
                helper.setText(R.id.item_tv_content, "咨询分类："+item.getTypeName());
            }else{
                helper.setText(R.id.item_tv_content, item.getContent());
            }
            helper.setGone(R.id.item_tv_content, true);

        }else{
            helper.setGone(R.id.item_tv_content, false);
        }

        if(item.getLawyerMemberId() > 0 || item.getTypeId() == 1){
            helper.setGone(R.id.iv_head, true);
            helper.setGone(R.id.tv_name, true);

            if(item.getTypeId() == 1){
                helper.setText(R.id.tv_name, item.getReplyCountStr());
            }else{
                helper.setText(R.id.tv_name, item.getLmemberNameStr());
            }

            if(!TextUtils.isEmpty(item.getIconImage())){
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(item.getIconImage())
                                .imageView(helper.getView(R.id.iv_head))
                                .isCircle(true)
                                .build());
            }else{
                helper.setImageResource(R.id.iv_head, R.drawable.ic_lawyer_avatar);
            }

        }else{
            helper.setGone(R.id.iv_head, false);
            helper.setGone(R.id.tv_name, false);
        }

    }
}