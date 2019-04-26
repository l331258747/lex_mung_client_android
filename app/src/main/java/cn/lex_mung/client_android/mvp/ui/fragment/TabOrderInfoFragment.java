package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerTabOrderInfoComponent;
import cn.lex_mung.client_android.di.module.TabOrderInfoModule;
import cn.lex_mung.client_android.mvp.contract.TabOrderInfoContract;
import cn.lex_mung.client_android.mvp.presenter.TabOrderInfoPresenter;

import cn.lex_mung.client_android.R;

/**
 * tab-订单详情
 */
public class TabOrderInfoFragment extends BaseFragment<TabOrderInfoPresenter> implements TabOrderInfoContract.View {

    @BindView(R.id.cl_order_info)
    ConstraintLayout clOrderInfo;
    @BindView(R.id.rl_rush_error)
    RelativeLayout rlRushError;
    @BindView(R.id.tv_order_number)
    TextView tv_order_number;
    @BindView(R.id.tv_order_date)
    TextView tv_order_date;
    @BindView(R.id.tv_order_name)
    TextView tv_order_name;
    @BindView(R.id.tv_order_price)
    TextView tv_order_price;
    @BindView(R.id.tv_order_customer)
    TextView tv_order_customer;
    @BindView(R.id.tv_order_status)
    TextView tv_order_status;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.group_time)
    Group group_time;

    int orderStatus;

    public static TabOrderInfoFragment newInstance(int id,int orderStatus) {
        TabOrderInfoFragment fragment = new TabOrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleTags.ID, id);
        bundle.putInt(BundleTags.STATE,orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabOrderInfoComponent
                .builder()
                .appComponent(appComponent)
                .tabOrderInfoModule(new TabOrderInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_order_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPresenter.getRequirementDetail(getArguments().getInt(BundleTags.ID));
            orderStatus = getArguments().getInt(BundleTags.STATE);

            if(orderStatus == 0){
                rlRushError.setVisibility(View.VISIBLE);
                clOrderInfo.setVisibility(View.GONE);
            }else{
                rlRushError.setVisibility(View.GONE);
                clOrderInfo.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.tv_custom_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_custom_call:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading(@NonNull String message) {
        loading = LoadingDialog.getInstance().init(mActivity, message, false);
        loading.show();
    }

    @Override
    public void refreshOrderInfo(RequirementDetailEntity entity) {
        tv_order_number.setText(entity.getOrderNo());
        tv_order_date.setText(entity.getCreateDate());
        tv_order_name.setText(entity.getTypeName());
        tv_order_price.setText(entity.getPayAmountStr());
        tv_order_customer.setText(entity.getLmemberName());
        tv_order_status.setText(entity.getIsReceiptStr());
        group_time.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        if (loading != null
                && loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        AppUtils.makeText(mActivity, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        AppUtils.startActivity(intent);
    }

    @Override
    public void launchActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launchActivity(intent);
    }

    @Override
    public void killMyself() {

    }
}
