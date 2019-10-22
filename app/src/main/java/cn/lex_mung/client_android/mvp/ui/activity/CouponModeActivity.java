package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerCouponModeComponent;
import cn.lex_mung.client_android.di.module.CouponModeModule;
import cn.lex_mung.client_android.mvp.contract.CouponModeContract;
import cn.lex_mung.client_android.mvp.model.entity.other.CouponModeEntity;
import cn.lex_mung.client_android.mvp.presenter.CouponModePresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCardFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCouponFragment;
import cn.lex_mung.client_android.mvp.ui.widget.myTabLayout.TabLayout;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY2;

public class CouponModeActivity extends BaseActivity<CouponModePresenter> implements CouponModeContract.View {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_no_coupon)
    TextView tvNoCoupon;

    int orgId;
    int memberId;
    int lMemberId;
    int couponId;
    int selectedType;//用来区分 优惠卡 优惠券
    int requireTypeId;

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


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            memberId = bundleIntent.getInt(BundleTags.MEMBER_ID);
            lMemberId = bundleIntent.getInt(BundleTags.L_MEMBER_ID);

            couponId = bundleIntent.getInt(BundleTags.COUPON_ID);
            orgId = bundleIntent.getInt(BundleTags.ORG_ID);
            selectedType = bundleIntent.getInt(BundleTags.COUPON_TYPE);

            requireTypeId = bundleIntent.getInt(BundleTags.REQUIRE_TYPE_ID);
        }

        fragments.add(CouponModeCardFragment.newInstance());
        fragments.add(CouponModeCouponFragment.newInstance());
        titles.add("优惠卡");
        titles.add("优惠券");
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setMyStyle(viewPager,titles);

        if(selectedType == 2){
            viewPager.setCurrentItem(1);
        }
    }

    public int getRequireTypeId(){
        return requireTypeId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getlMemberId() {
        return lMemberId;
    }

    public int getSelectedType() {
        return selectedType;
    }

    public int getCouponId() {
        return couponId;
    }

    public int getOrgId(){
        return orgId;
    }

    @OnClick({R.id.tv_no_coupon})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_no_coupon:
                CouponModeEntity couponModeEntity = new CouponModeEntity();
                couponModeEntity.setOrgId(-1);
                couponModeEntity.setCouponId(-1);
                couponModeEntity.setType(1);
                couponModeEntity.setOrgLevId(-1);
                AppUtils.post(REFRESH, REFRESH_DISCOUNT_WAY2, couponModeEntity);
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
