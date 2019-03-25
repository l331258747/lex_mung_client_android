package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerSocialResourcesComponent;
import cn.lex_mung.client_android.di.module.SocialResourcesModule;
import cn.lex_mung.client_android.mvp.contract.SocialResourcesContract;
import cn.lex_mung.client_android.mvp.presenter.SocialResourcesPresenter;

import cn.lex_mung.client_android.R;

public class SocialResourcesFragment extends BaseFragment<SocialResourcesPresenter> implements SocialResourcesContract.View {
    @BindView(R.id.tv_social_function)
    TextView tvSocialFunction;
    @BindView(R.id.tv_court)
    TextView tvCourt;
    @BindView(R.id.tv_p)
    TextView tvP;
    @BindView(R.id.tv_social_function_text)
    TextView tvSocialFunctionText;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.tv_resort_institutions_text)
    TextView tvResortInstitutionsText;
    @BindView(R.id.view_line_1)
    View viewLine1;
    @BindView(R.id.tv_court_text)
    TextView tvCourtText;
    @BindView(R.id.view_line_2)
    View viewLine2;
    @BindView(R.id.tv_p_text)
    TextView tvPText;
    @BindView(R.id.no_layout)
    View noLayout;

    public static SocialResourcesFragment newInstance(LawsHomePagerBaseEntity entity) {
        SocialResourcesFragment fragment = new SocialResourcesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSocialResourcesComponent
                .builder()
                .appComponent(appComponent)
                .socialResourcesModule(new SocialResourcesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_resources, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPresenter.setEntity((LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY));
        }
    }

    @Override
    public void setSocialFunction(String s) {
        tvSocialFunction.setText(s);
    }

    @Override
    public void setCourt(String s) {
        tvCourt.setText(s);
    }

    @Override
    public void setP(String s) {
        tvP.setText(s);
    }

    @Override
    public void hidePLayout() {
        tvPText.setVisibility(View.GONE);
        tvP.setVisibility(View.GONE);
        viewLine2.setVisibility(View.GONE);
    }

    @Override
    public void hideCourtLayout() {
        tvCourtText.setVisibility(View.GONE);
        tvCourt.setVisibility(View.GONE);
        viewLine2.setVisibility(View.GONE);
    }

    @Override
    public void hideSocialFunctionLayout() {
        tvSocialFunctionText.setVisibility(View.GONE);
        tvSocialFunction.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
    }

    @Override
    public void showNoDataLayout() {
        noLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoDataLayout1() {
        tvResortInstitutionsText.setVisibility(View.GONE);
        viewLine1.setVisibility(View.GONE);
        tvCourtText.setVisibility(View.GONE);
        tvCourt.setVisibility(View.GONE);
        viewLine2.setVisibility(View.GONE);
        tvPText.setVisibility(View.GONE);
        tvP.setVisibility(View.GONE);
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
