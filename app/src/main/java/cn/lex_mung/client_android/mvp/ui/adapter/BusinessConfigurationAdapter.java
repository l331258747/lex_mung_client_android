package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class BusinessConfigurationAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {
    private ImageLoader mImageLoader;

    private boolean isShow = true;

    public BusinessConfigurationAdapter(ImageLoader imageLoader) {
        super(R.layout.item_business_configuration);
        this.mImageLoader = imageLoader;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            mImageLoader.loadImage(mContext
                    , ImageConfigImpl
                            .builder()
                            .url(item.getImage())
                            .imageView(helper.getView(R.id.item_iv_icon))
                            .build());
        }

        if (item.getRequirementType() == 1) {
            helper.setText(R.id.item_tv_release, mContext.getString(R.string.text_release_demand_1));
        } else {
            helper.setText(R.id.item_tv_release, mContext.getString(R.string.text_telephone_counseling));
        }
        if (item.getRcount() == 1) {
            helper.setVisible(R.id.item_tv_release, true);
        } else {
            helper.setVisible(R.id.item_tv_release, false);
        }
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
        BusinessConfigurationChildAdapter adapter = new BusinessConfigurationChildAdapter(item.getRequires(), isShow);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        helper.addOnClickListener(R.id.item_tv_release);
    }
}