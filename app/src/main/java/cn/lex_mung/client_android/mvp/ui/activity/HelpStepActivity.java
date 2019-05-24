package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lex_mung.client_android.di.module.HelpStepModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep1Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep2Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep3Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep4Fragment;
import cn.lex_mung.client_android.mvp.ui.widget.HelpStepView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHelpStepComponent;
import cn.lex_mung.client_android.mvp.contract.HelpStepContract;
import cn.lex_mung.client_android.mvp.presenter.HelpStepPresenter;

import cn.lex_mung.client_android.R;

public class HelpStepActivity extends BaseActivity<HelpStepPresenter> implements HelpStepContract.View {

    @BindView(R.id.view_help_step)
    HelpStepView helpStepView;
    @BindView(R.id.fragment_tab)
    FrameLayout fragmentTab;

    private int pageIndex = 0;//下标
    private List<Fragment> fragments = new ArrayList<>();

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

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStepComponent
                .builder()
                .appComponent(appComponent)
                .helpStepModule(new HelpStepModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_step;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragments.add(HelpStep1Fragment.newInstance());
        fragments.add(HelpStep2Fragment.newInstance());
        fragments.add(HelpStep3Fragment.newInstance());
        fragments.add(HelpStep4Fragment.newInstance());

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
