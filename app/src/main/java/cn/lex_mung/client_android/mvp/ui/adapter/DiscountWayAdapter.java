package cn.lex_mung.client_android.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;

public class DiscountWayAdapter extends BaseQuickAdapter<ReleaseDemandOrgMoneyEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;
    private int organizationLevId = -1;

    public DiscountWayAdapter(ImageLoader imageLoader, int organizationLevId) {
        super(R.layout.item_discount_way);
        this.mImageLoader = imageLoader;
        this.organizationLevId = organizationLevId;
    }

    public void setOrganizationLevId(int organizationLevId) {
        this.organizationLevId = organizationLevId;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReleaseDemandOrgMoneyEntity item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }
        if (organizationLevId == -1) {
            helper.setImageResource(R.id.item_iv_select, R.drawable.ic_hide_select);
        } else {
            if (organizationLevId == item.getOrganizationLevId()) {
                helper.setImageResource(R.id.item_iv_select, R.drawable.ic_show_select);
            } else {
                helper.setImageResource(R.id.item_iv_select, R.drawable.ic_hide_select);
            }
        }
    }
}