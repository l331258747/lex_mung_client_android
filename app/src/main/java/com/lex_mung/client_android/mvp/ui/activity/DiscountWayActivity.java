package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.component.DaggerDiscountWayComponent;
import com.lex_mung.client_android.di.module.DiscountWayModule;
import com.lex_mung.client_android.mvp.contract.DiscountWayContract;
import com.lex_mung.client_android.mvp.presenter.DiscountWayPresenter;
import com.lex_mung.client_android.mvp.ui.adapter.DiscountWayAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY;

public class DiscountWayActivity extends BaseActivity<DiscountWayPresenter> implements DiscountWayContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_select)
    ImageView ivSelect;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDiscountWayComponent
                .builder()
                .appComponent(appComponent)
                .discountWayModule(new DiscountWayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_discount_way;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setOrganizationLevId(bundleIntent.getInt(BundleTags.ID));
            mPresenter.setMemberId(bundleIntent.getInt(BundleTags.MEMBER_ID));
            mPresenter.setLMemberId(bundleIntent.getInt(BundleTags.L_MEMBER_ID));
        }
        mPresenter.onCreate();
    }

    @Override
    public void initRecyclerView(DiscountWayAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setIvSelect(int icon) {
        ivSelect.setImageResource(icon);
    }

    @OnClick({R.id.tv_use_rules, R.id.view_no_discounts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use_rules:

                break;
            case R.id.view_no_discounts:
                mPresenter.setAdapterOrganizationLevId(-1);
                ivSelect.setImageResource(R.drawable.ic_show_select);
                AppUtils.postInt(REFRESH, REFRESH_DISCOUNT_WAY, -1);
                killMyself();
                break;
        }
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
}
