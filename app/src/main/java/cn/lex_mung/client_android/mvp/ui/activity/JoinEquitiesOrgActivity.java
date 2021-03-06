package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerJoinEquitiesOrgComponent;
import cn.lex_mung.client_android.di.module.JoinEquitiesOrgModule;
import cn.lex_mung.client_android.mvp.contract.JoinEquitiesOrgContract;
import cn.lex_mung.client_android.mvp.presenter.JoinEquitiesOrgPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.RoundImageView;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class JoinEquitiesOrgActivity extends BaseActivity<JoinEquitiesOrgPresenter> implements JoinEquitiesOrgContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_equities_bg)
    RoundImageView ivEquitiesBg;
    @BindView(R.id.tv_member_total)
    TextView tvMemberTotal;
    @BindView(R.id.tv_lawyer_total)
    TextView tvLawyerTotal;
    @BindView(R.id.tv_equities_explain)
    TextView tvEquitiesExplain;
    @BindView(R.id.tv_open_qualification)
    TextView tvOpenQualification;
    @BindView(R.id.tv_exclusive_equities)
    TextView tvExclusiveEquities;
    @BindView(R.id.tv_your_org)
    TextView tvYourOrg;
    @BindView(R.id.et_your_name)
    EditText etYourName;
    @BindView(R.id.et_your_mobile)
    EditText etYourMobile;
    @BindView(R.id.et_your_work_unit)
    EditText etYourWorkUnit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerJoinEquitiesOrgComponent
                .builder()
                .appComponent(appComponent)
                .joinEquitiesOrgModule(new JoinEquitiesOrgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_join_equities_org;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setOrgId(bundleIntent.getInt(BundleTags.ID));
            mPresenter.setLevelId(bundleIntent.getInt(BundleTags.LEVEL));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getEquitiesDetails();
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        if (isFastClick()) return;
        mPresenter.submit(etYourName.getText().toString()
                , etYourMobile.getText().toString()
                , etYourWorkUnit.getText().toString());
    }

    @Override
    public void setEquitiesBg(String image) {
        if(!TextUtils.isEmpty(image)){
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(image)
                            .isCenterCrop(false)
                            .imageView(ivEquitiesBg)
                            .build());
        }
    }

    @Override
    public void setOrgName(String organizationName) {
        tvYourOrg.setText(organizationName);
    }

    @Override
    public void setMemberTotal(String memberCount) {
        tvMemberTotal.setText(memberCount);
    }

    @Override
    public void setLawyerTotal(String lawyerCount) {
        tvLawyerTotal.setText(lawyerCount);
    }

    @Override
    public void setEquitiesExplain(String rightsInterpret) {
        tvEquitiesExplain.setText(rightsInterpret);
    }

    @Override
    public void setOpenQualification(String openingQualification) {
        tvOpenQualification.setText(openingQualification);
    }

    @Override
    public void setExclusiveEquities(String exclusiveRights) {
        tvExclusiveEquities.setText(exclusiveRights);
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
