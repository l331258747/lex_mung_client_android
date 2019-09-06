package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerHelpStepComponent;
import cn.lex_mung.client_android.di.module.HelpStepModule;
import cn.lex_mung.client_android.mvp.contract.HelpStepContract;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepEntity;
import cn.lex_mung.client_android.mvp.presenter.HelpStepPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep1Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep2Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep3Fragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep4Fragment;
import cn.lex_mung.client_android.mvp.ui.widget.HelpStepView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class HelpStepActivity extends BaseActivity<HelpStepPresenter> implements HelpStepContract.View {

    @BindView(R.id.view_help_step)
    HelpStepView helpStepView;

    private int pageIndex = 0;//下标
    private List<Fragment> fragments = new ArrayList<>();

    HelpStep1Fragment helpStep1Fragment;
    HelpStep2Fragment helpStep2Fragment;
    HelpStep3Fragment helpStep3Fragment;
    HelpStep4Fragment helpStep4Fragment;

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

    public void goPreferredLawyer(){
        bundle.clear();
        bundle.putInt(BundleTags.REGION_ID,helpStep1Fragment.getRegionId());
        bundle.putInt(BundleTags.SOLUTION_TYPE_ID,helpStep2Fragment.getTypeId());
        bundle.putInt(BundleTags.PAY_LAWYER_MONEY_ID,helpStep3Fragment.getAmountId());
        bundle.putInt(BundleTags.AMOUNT_ID,helpStep3Fragment.getAmountId());
        bundle.putInt(BundleTags.INDUSTRY_ID,-1);
        bundle.putInt(BundleTags.REQUIRE_TYPE_ID,helpStep4Fragment.getTypeId());
        launchActivity(new Intent(mActivity,HelpStepLawyerActivity.class),bundle);

        finish();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_step;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] strs = {"服务地域", "事项分类", "法律服务","标的额"};
        helpStepView.initView(strs);

        mPresenter.getData();
    }

    int requireType;
    public void setType(int requireType){
        this.requireType = requireType;
        if(requireType == 6){
            String[] strs = {"服务地域", "事项分类", "法律服务","愿意支付费用"};
            helpStepView.initView(strs);
        }else{
            String[] strs = {"服务地域", "事项分类", "法律服务","标的额"};
            helpStepView.initView(strs);
        }

        helpStep3Fragment.setType(requireType);
    }

    @Override
    public void setFragment(HelpStepEntity entity){
        fragments.add(helpStep1Fragment = HelpStep1Fragment.newInstance());
        fragments.add(helpStep2Fragment = HelpStep2Fragment.newInstance(entity.getSolutionType()));
        fragments.add(helpStep4Fragment = HelpStep4Fragment.newInstance(entity.getRequireType()));
        fragments.add(helpStep3Fragment = HelpStep3Fragment.newInstance(entity.getRequirementInvolveAmount(),entity.getPayMoneyEntities()));

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
