package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class EquitiesAdapter extends BaseQuickAdapter<EquitiesListEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private boolean isLogin;

    public EquitiesAdapter(ImageLoader imageLoader, boolean isLogin) {
        super(R.layout.item_equities);
        this.mImageLoader = imageLoader;
        this.isLogin = isLogin;
    }

    @Override
    protected void convert(BaseViewHolder helper, EquitiesListEntity item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .isCenterCrop(false)
                            .build());
        }else{
            helper.setImageDrawable(R.id.item_iv_icon,ContextCompat.getDrawable(mContext,R.drawable.round_10_edfaf5_all));
        }

        helper.setGone(R.id.tv_title,false);
        helper.setGone(R.id.tv_content,false);
        helper.getView(R.id.item_tv_certified).setVisibility(View.GONE);
        if(item.getIsBuyEquity()){//购买 开通的权益
            helper.setGone(R.id.tv_title,true);
            helper.setGone(R.id.tv_content,true);

            helper.setText(R.id.tv_title,item.getEquityName());
            helper.setText(R.id.tv_content,item.getEquityDesc());

            helper.getView(R.id.item_tv_certified).setVisibility(View.VISIBLE);
            if(item.isOwn()){//开通
                helper.setText(R.id.item_tv_certified,"已开通");
                helper.setBackgroundRes(R.id.item_tv_certified,R.drawable.round_100_cea769_all);
            }else{//未开通
                helper.setText(R.id.item_tv_certified,"未开通");
                helper.setBackgroundRes(R.id.item_tv_certified,R.drawable.round_100_737373_all);
            }
        }else{
            if (isLogin) {
                helper.getView(R.id.item_tv_certified).setVisibility(View.VISIBLE);
                helper.setBackgroundRes(R.id.item_tv_certified,R.drawable.round_100_cea769_all);
            }
        }
    }
}