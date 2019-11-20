package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class MyCardAdapter extends BaseQuickAdapter<CouponsEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    public MyCardAdapter(ImageLoader imageLoader) {
        super(R.layout.item_my_card);
        this.mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsEntity item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }else{
            helper.setImageDrawable(R.id.item_iv_icon,ContextCompat.getDrawable(mContext,R.drawable.round_10_1ec88b_all));
        }

        helper.setGone(R.id.tv_title,false);//标题-在线法律顾问
        helper.setGone(R.id.tv_content,false);//说明-在线法律顾问

        helper.setGone(R.id.tv_card_club_balance,false);//集团卡余额
        helper.setGone(R.id.tv_card_member_balance,false);//会员卡
        helper.setGone(R.id.ll__balance,false);//余额显示

        helper.setGone(R.id.fl_card_club_btn,false);//按钮
        helper.setText(R.id.tv_card_club_btn,"去使用");//按钮初始化

        if(item.isBuyEquity()) {//购买 开通的权益
//            helper.setGone(R.id.tv_title,true);
//            helper.setGone(R.id.tv_content,true);
//
//            helper.setText(R.id.tv_title,item.getEquityName());
//            helper.setText(R.id.tv_content,item.getEquityDesc());

            helper.setGone(R.id.fl_card_club_btn,true);
            if(item.isOwn()){//开通
                helper.setText(R.id.tv_card_club_btn,"已开通");
            }else{//未开通
                helper.setText(R.id.tv_card_club_btn,"未开通");
            }
        }else{
            if(item.getCouponBalanceList()!= null && item.getCouponBalanceList().size() > 0){
                helper.setGone(R.id.ll__balance,true);
                for (CouponsEntity.CouponsChildEntity child:item.getCouponBalanceList()){
                    if(child.getCouponType() == 5){
                        helper.setGone(R.id.tv_card_club_balance,true);
                        helper.setText(R.id.tv_card_club_balance,child.getBalanceStr());
                    }
                    if(child.getCouponType() == 1){
                        helper.setGone(R.id.tv_card_member_balance,true);
                        helper.setText(R.id.tv_card_member_balance,child.getBalanceStr());
                    }
                }
            }

            helper.setGone(R.id.fl_card_club_btn,true);
            helper.setGone(R.id.tv_coupon_card,false);
        }
    }
}
