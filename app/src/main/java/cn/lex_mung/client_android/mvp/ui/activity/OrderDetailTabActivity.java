package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerOrderDetailTabComponent;
import cn.lex_mung.client_android.di.module.OrderDetailTabModule;
import cn.lex_mung.client_android.mvp.contract.OrderDetailTabContract;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailTabPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.fragment.TabOrderContractFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.TabOrderInfoFragment;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

/**
 * 订单详情 tab
 */
public class OrderDetailTabActivity extends BaseActivity<OrderDetailTabPresenter> implements OrderDetailTabContract.View {

    @BindView(R.id.tv_order_info)
    TextView tvOrderInfo;
    @BindView(R.id.tv_order_contract)
    TextView tvOrderContract;
    @BindView(R.id.iv_order_info)
    ImageView ivOrderInfo;
    @BindView(R.id.iv_order_contract)
    ImageView ivOrderContract;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailTabComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailTabModule(new OrderDetailTabModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_detail_tab;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        fragments.add(TabOrderInfoFragment.newInstance());
        fragments.add(TabOrderContractFragment.newInstance());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        switchPager(16, 14, Typeface.BOLD, Typeface.NORMAL,  View.VISIBLE, View.GONE);
                        break;
                    case 1:
                        switchPager(14, 16, Typeface.NORMAL, Typeface.BOLD, View.GONE, View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.tv_order_info, R.id.tv_order_contract})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_info:
                switchPager(16, 14, Typeface.BOLD, Typeface.NORMAL,  View.VISIBLE, View.GONE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_order_contract:
                switchPager(14, 16, Typeface.NORMAL, Typeface.BOLD, View.GONE, View.VISIBLE);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 切换页面
     */
    private void switchPager(int i1, int i2,  int t1, int t2, int v1, int v2) {
        tvOrderInfo.setTextSize(i1);
        tvOrderContract.setTextSize(i2);
        tvOrderInfo.setTypeface(Typeface.defaultFromStyle(t1));
        tvOrderContract.setTypeface(Typeface.defaultFromStyle(t2));
        ivOrderInfo.setVisibility(v1);
        ivOrderContract.setVisibility(v2);
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
