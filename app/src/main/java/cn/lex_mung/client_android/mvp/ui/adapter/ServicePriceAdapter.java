package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import me.zl.mvp.utils.AppUtils;

public class ServicePriceAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {

    public ServicePriceAdapter(List<BusinessEntity> list) {
        super(R.layout.item_service_price, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {
        helper.setText(R.id.item_tv_title, item.getRequireTypeName());
        if (item.getRequirementType() == 1) {
            helper.setText(R.id.item_tv_release, mContext.getString(R.string.text_release_demand_1));
        } else {
            helper.setText(R.id.item_tv_release, mContext.getString(R.string.text_telephone_counseling));
        }
        ServicePriceChildAdapter adapter = new ServicePriceChildAdapter(item.getRequires());
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.view_line_1).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_line_1).setVisibility(View.VISIBLE);
        }

        helper.addOnClickListener(R.id.item_tv_release);
    }
}
