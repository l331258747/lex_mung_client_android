package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.OrderContractModule;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerOrderContractComponent;
import cn.lex_mung.client_android.mvp.contract.OrderContractContract;
import cn.lex_mung.client_android.mvp.presenter.OrderContractPresenter;

import cn.lex_mung.client_android.R;

public class OrderContractActivity extends BaseActivity<OrderContractPresenter> implements OrderContractContract.View {

    @BindView(R.id.rl_list)
    RelativeLayout rlList;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_call)
    TextView ivCall;
    @BindView(R.id.iv_send_contract)
    TextView ivSendContract;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.cl_help)
    ConstraintLayout cl_help;
    @BindView(R.id.rl_rush_error)
    RelativeLayout rlRushError;

    private int orderStatus;
    private String lmobile;

    int type;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderContractComponent
                .builder()
                .appComponent(appComponent)
                .orderContractModule(new OrderContractModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_contract;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null){
            mPresenter.setType(type = bundleIntent.getInt(BundleTags.TYPE,0));
            orderStatus = bundleIntent.getInt(BundleTags.STATE,1);
            if(orderStatus == 0){//显示空界面
                rlRushError.setVisibility(View.VISIBLE);
                rlList.setVisibility(View.GONE);
            }else{//显示文件列表
                rlRushError.setVisibility(View.GONE);
                rlList.setVisibility(View.VISIBLE);

                setLmobile(bundleIntent.getString(BundleTags.MOBILE));

                mPresenter.onCreate(smartRefreshLayout,bundleIntent.getString(BundleTags.ORDER_NO),bundleIntent.getInt(BundleTags.ID));
                mPresenter.setHelpView(cl_help);
            }

            if(orderStatus == 3){//订单关闭->不显示底部按钮
                ll_bottom.setVisibility(View.GONE);
            }else{
                ll_bottom.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initRecyclerView(TabOrderContractAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.iv_call, R.id.iv_send_contract,R.id.iv_help_close,R.id.cl_help,R.id.tv_custom_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call:
                if(type == 2){
                    mPresenter.corporateUserphone();
                }else if(type == 1){
                    mPresenter.legalAdviserOrderUserPhone();
                }else{
                    if(TextUtils.isEmpty(lmobile)) return;
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lmobile));
                    startActivity(dialIntent);
                }
                break;
            case R.id.iv_send_contract:
                mPresenter.showFileChooser();
                break;
            case R.id.iv_help_close:
                cl_help.setVisibility(View.GONE);
                break;
            case R.id.cl_help:
                if(TextUtils.isEmpty(mPresenter.getHelpLink())) return;
                bundle.clear();
                bundle.putString(BundleTags.URL, mPresenter.getHelpLink());
                bundle.putString(BundleTags.TITLE, "帮助文档");
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                break;
            case R.id.tv_custom_call:
                Intent dialIntent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent2);
                break;
        }
    }

    public void setLmobile(String lmobile){
        this.lmobile = lmobile;
    }

    @Override
    public void call(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            launchActivity(intent);
        } catch (Exception ignored) {
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading(@NonNull String message) {
        loading = LoadingDialog.getInstance().init(mActivity, message, false);
        loading.show();
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
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode,resultCode,data);
    }
}
