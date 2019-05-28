package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.HelpStepChildModule;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep1Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep2Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep3Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep4Fragment;
import cn.lex_mung.client_android.mvp.ui.widget.HelpStepChildView;
import cn.lex_mung.client_android.mvp.ui.widget.HelpStepView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHelpStepChildComponent;
import cn.lex_mung.client_android.mvp.contract.HelpStepChildContract;
import cn.lex_mung.client_android.mvp.presenter.HelpStepChildPresenter;

import cn.lex_mung.client_android.R;

public class HelpStepChildActivity extends BaseActivity<HelpStepChildPresenter> implements HelpStepChildContract.View {

    @BindView(R.id.view_help_step)
    HelpStepChildView helpStepView;
    @BindView(R.id.titleView)
    TitleView titleView;

    private int pageIndex = 0;//下标
    private List<Fragment> fragments = new ArrayList<>();

    HelpStep1Fragment helpStep1Fragment;
    HelpStep2Fragment helpStep2Fragment;
    HelpStep3Fragment helpStep3Fragment;

    int requireTypeId;

    @Override
    public void onBackPressed() {
        if (pageIndex > 0) {
            pageIndex -= 1;
            setIndex(pageIndex);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    public void goPreferredLawyer() {
        bundle.clear();
        bundle.putInt(BundleTags.REGION_ID, helpStep1Fragment.getRegionId());
        bundle.putInt(BundleTags.SOLUTION_TYPE_ID, helpStep2Fragment.getTypeId());
        bundle.putInt(BundleTags.AMOUNT_ID, helpStep3Fragment.getAmountId());
        bundle.putInt(BundleTags.REQUIRE_TYPE_ID, requireTypeId);

        launchActivity(new Intent(mActivity, HelpStepLawyerActivity.class), bundle);

        finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStepChildComponent
                .builder()
                .appComponent(appComponent)
                .helpStepChildModule(new HelpStepChildModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_step_child;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        requireTypeId = bundleIntent.getInt(BundleTags.ID);
        if (!TextUtils.isEmpty(bundleIntent.getString(BundleTags.TITLE))) {
            titleView.setTitle(bundleIntent.getString(BundleTags.TITLE));
        }

        mPresenter.getData();
    }

    @Override
    public void setFragment(HelpStepEntity entity) {
        fragments.add(helpStep1Fragment = HelpStep1Fragment.newInstance(true));
        fragments.add(helpStep2Fragment = HelpStep2Fragment.newInstance(true, entity.getSolutionType()));
        fragments.add(helpStep3Fragment = HelpStep3Fragment.newInstance(true, entity.getRequirementInvolveAmount()));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }

        setIndex(0);
    }

    public void setIndex(int index) {
        pageIndex = index;
        helpStepView.setProgress(index);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }

        try {

            if (fragments.get(index).isAdded()) {
                transaction.show(fragments.get(index)).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.fragment_tab, fragments.get(index)).commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
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
