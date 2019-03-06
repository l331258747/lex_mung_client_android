package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.di.component.DaggerAddEquitiesOrgComponent;
import com.lex_mung.client_android.di.module.AddEquitiesOrgModule;
import com.lex_mung.client_android.mvp.contract.AddEquitiesOrgContract;
import com.lex_mung.client_android.mvp.presenter.AddEquitiesOrgPresenter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class AddEquitiesOrgActivity extends BaseActivity<AddEquitiesOrgPresenter> implements AddEquitiesOrgContract.View {

    @BindView(R.id.et_your_name)
    EditText etYourName;
    @BindView(R.id.et_your_mobile)
    EditText etYourMobile;
    @BindView(R.id.et_your_org)
    EditText etYourOrg;
    @BindView(R.id.tv_exclusive_service)
    TextView tvExclusiveService;
    @BindView(R.id.iv_exclusive_service)
    ImageView ivExclusiveService;
    @BindView(R.id.tv_corporate_counsel)
    TextView tvCorporateCounsel;
    @BindView(R.id.iv_corporate_counsel)
    ImageView ivCorporateCounsel;
    @BindView(R.id.tv_due_diligence)
    TextView tvDueDiligence;
    @BindView(R.id.iv_due_diligence)
    ImageView ivDueDiligence;
    @BindView(R.id.tv_tip_9)
    TextView tvTip9;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddEquitiesOrgComponent
                .builder()
                .appComponent(appComponent)
                .addEquitiesOrgModule(new AddEquitiesOrgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_equities_org;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String string = getString(R.string.text_add_equities_org_tip_9)
                + "<font color=\"#1EC88C\">"
                + DataHelper.getStringSF(mActivity, DataHelperTags.MOBILE)
                + "</font>"
                + getString(R.string.text_add_equities_org_tip_10);
        tvTip9.setText(Html.fromHtml(string));
    }

    @OnClick({R.id.view_service_1, R.id.view_service_2, R.id.view_service_3, R.id.bt_submit, R.id.tv_tip_8, R.id.tv_tip_9})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_service_1:
                if (mPresenter.isService1()) {
                    tvExclusiveService.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
                    ivExclusiveService.setVisibility(View.GONE);
                    mPresenter.setService1(false);
                } else {
                    tvExclusiveService.setTextColor(AppUtils.getColor(mActivity, R.color.c_cea769));
                    ivExclusiveService.setVisibility(View.VISIBLE);
                    mPresenter.setService1(true);
                }
                break;
            case R.id.view_service_2:
                if (mPresenter.isService2()) {
                    tvCorporateCounsel.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
                    ivCorporateCounsel.setVisibility(View.GONE);
                    mPresenter.setService2(false);
                } else {
                    tvCorporateCounsel.setTextColor(AppUtils.getColor(mActivity, R.color.c_cea769));
                    ivCorporateCounsel.setVisibility(View.VISIBLE);
                    mPresenter.setService2(true);
                }
                break;
            case R.id.view_service_3:
                if (mPresenter.isService3()) {
                    tvDueDiligence.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
                    ivDueDiligence.setVisibility(View.GONE);
                    mPresenter.setService3(false);
                } else {
                    tvDueDiligence.setTextColor(AppUtils.getColor(mActivity, R.color.c_cea769));
                    ivDueDiligence.setVisibility(View.VISIBLE);
                    mPresenter.setService3(true);
                }
                break;
            case R.id.bt_submit:
                mPresenter.submit(etYourName.getText().toString()
                        , etYourMobile.getText().toString()
                        , etYourOrg.getText().toString());
                break;
            case R.id.tv_tip_8:
            case R.id.tv_tip_9:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse(String.format(getString(R.string.text_tel), DataHelper.getStringSF(mActivity, DataHelperTags.MOBILE)));
                intent.setData(data);
                launchActivity(intent);
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
