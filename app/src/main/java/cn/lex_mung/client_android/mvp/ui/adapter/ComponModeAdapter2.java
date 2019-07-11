package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntityOptimal;
import me.zl.mvp.http.imageloader.ImageLoader;

public class ComponModeAdapter2 extends BaseQuickAdapter<ReleaseDemandOrgMoneyEntityOptimal, BaseViewHolder> {
    private ImageLoader mImageLoader;
    int type = 0;

    private int cardId = -1;

    public ComponModeAdapter2(ImageLoader imageLoader) {
        this(imageLoader,0);
    }

    public ComponModeAdapter2(ImageLoader imageLoader, int type) {
        super(R.layout.item_my_coupons);
        this.mImageLoader = imageLoader;
        this.type = type;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReleaseDemandOrgMoneyEntityOptimal item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }else{
            helper.setImageResource(R.id.item_iv_icon,R.drawable.ic_update_top_round);
        }

        if(type == 0){
            helper.setGone(R.id.tv_card_club_balance,true);
            helper.setGone(R.id.fl_card_club_btn,true);
            helper.setGone(R.id.tv_coupon_card,false);

        }else{
            helper.setGone(R.id.tv_card_club_balance,false);
            helper.setGone(R.id.fl_card_club_btn,false);
            helper.setGone(R.id.tv_coupon_card,true);

            if (cardId == item.getOrganizationId()) {
                helper.setGone(R.id.view_bg,true);
            }else{
                helper.setGone(R.id.view_bg,false);
            }
        }

        helper.addOnClickListener(R.id.tv_coupon_card);//查看详情，弹出对话框显示优惠信息
    }
}
