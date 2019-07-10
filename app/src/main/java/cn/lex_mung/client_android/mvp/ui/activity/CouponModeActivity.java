package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.CouponModeModule;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCardFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCouponFragment;
import cn.lex_mung.client_android.mvp.ui.widget.myTabLayout.TabLayout;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerCouponModeComponent;
import cn.lex_mung.client_android.mvp.contract.CouponModeContract;
import cn.lex_mung.client_android.mvp.presenter.CouponModePresenter;

import cn.lex_mung.client_android.R;

public class CouponModeActivity extends BaseActivity<CouponModePresenter> implements CouponModeContract.View {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_no_coupon)
    TextView tvNoCoupon;

    int selectedId;
    int selectedType;//用来区分 优惠卡 优惠券

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCouponModeComponent
                .builder()
                .appComponent(appComponent)
                .couponModeModule(new CouponModeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_coupon_mode;
    }

    int organizationLevId;
    int memberId;
    int lMemberId;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            organizationLevId = bundleIntent.getInt(BundleTags.ID);
            memberId = bundleIntent.getInt(BundleTags.MEMBER_ID);
            lMemberId = bundleIntent.getInt(BundleTags.L_MEMBER_ID);

            selectedId = bundleIntent.getInt(BundleTags.COUPON_ID);
        }

        fragments.add(CouponModeCouponFragment.newInstance());
        fragments.add(CouponModeCardFragment.newInstance());
        titles.add("优惠卷");
        titles.add("优惠卡");
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setMyStyle(viewPager,titles);
    }

    public int getSelectedType() {
        return selectedType;
    }

    public int getSelectedId() {
        return selectedId;
    }

    @OnClick({R.id.tv_no_coupon})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_no_coupon:
                //TODO 不使用优惠券
                //AppUtils.postInt(REFRESH, REFRESH_DISCOUNT_WAY, -1);
                //killMyself();
                break;
        }
    }

    public int getOrganizationLevId() {
        return organizationLevId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getlMemberId() {
        return lMemberId;
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
