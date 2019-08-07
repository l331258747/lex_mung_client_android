package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerMessageComponent;
import cn.lex_mung.client_android.di.module.MessageModule;
import cn.lex_mung.client_android.mvp.contract.MessageContract;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;
import cn.lex_mung.client_android.mvp.presenter.MessagePresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.fragment.DemandMessageFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.OrderMessageFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.SystemMessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.tv_demand_message)
    TextView tvDemandMessage;
    @BindView(R.id.iv_demand_message)
    ImageView ivDemandMessage;
    @BindView(R.id.tv_order_message)
    TextView tvOrderMessage;
    @BindView(R.id.iv_order_message)
    ImageView ivOrderMessage;
    @BindView(R.id.tv_system_message)
    TextView tvSystemMessage;
    @BindView(R.id.iv_system_message)
    ImageView ivSystemMessage;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_demand_message_count)
    TextView tvDemandMessageCount;
    @BindView(R.id.tv_order_message_count)
    TextView tvOrderMessageCount;
    @BindView(R.id.tv_system_message_count)
    TextView tvSystemMessageCount;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent
                .builder()
                .appComponent(appComponent)
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setEntity((UnreadMessageCountEntity) bundleIntent.getSerializable(BundleTags.ENTITY));
        }
        initViewPager();
    }

    private void initViewPager() {
        fragments.add(DemandMessageFragment.newInstance());
        fragments.add(OrderMessageFragment.newInstance());
        fragments.add(SystemMessageFragment.newInstance());
        viewPager.setOffscreenPageLimit(2);
        titles.add("需求消息");
        titles.add("订单消息");
        titles.add("系统消息");
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments, titles));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        DemandMessageFragment demandMessageFragment = (DemandMessageFragment) fragments.get(position);
                        if (demandMessageFragment != null) {
                            if (isFastClicks()) {
                                demandMessageFragment.refreshData();
                            }
                        }
                        selectedMessage(R.color.c_06a66a, R.color.c_323232, R.color.c_323232, View.VISIBLE, View.GONE, View.GONE);
                        break;
                    case 1:
                        OrderMessageFragment orderMessageFragment = (OrderMessageFragment) fragments.get(position);
                        if (orderMessageFragment != null) {
                            if (isFastClicks()) {
                                orderMessageFragment.refreshData();
                            }
                        }
                        selectedMessage(R.color.c_323232, R.color.c_06a66a, R.color.c_323232, View.GONE, View.VISIBLE, View.GONE);
                        break;
                    case 2:
                        SystemMessageFragment systemMessageFragment = (SystemMessageFragment) fragments.get(position);
                        if (systemMessageFragment != null) {
                            if (isFastClicks()) {
                                systemMessageFragment.refreshData();
                            }
                        }
                        selectedMessage(R.color.c_323232, R.color.c_323232, R.color.c_06a66a, View.GONE, View.GONE, View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.view_demand_message, R.id.view_order_message, R.id.view_system_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_demand_message:
                selectedMessage(R.color.c_06a66a, R.color.c_323232, R.color.c_323232, View.VISIBLE, View.GONE, View.GONE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.view_order_message:
                selectedMessage(R.color.c_323232, R.color.c_06a66a, R.color.c_323232, View.GONE, View.VISIBLE, View.GONE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.view_system_message:
                selectedMessage(R.color.c_323232, R.color.c_323232, R.color.c_06a66a, View.GONE, View.GONE, View.VISIBLE);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private void selectedMessage(int color1, int color2, int color3, int vis1, int vis2, int vis3) {
        tvDemandMessage.setTextColor(AppUtils.getColor(mActivity, color1));
        tvOrderMessage.setTextColor(AppUtils.getColor(mActivity, color2));
        tvSystemMessage.setTextColor(AppUtils.getColor(mActivity, color3));
        ivDemandMessage.setVisibility(vis1);
        ivOrderMessage.setVisibility(vis2);
        ivSystemMessage.setVisibility(vis3);
    }

    @Override
    public void setDemandMessageCount(String s) {
        tvDemandMessageCount.setText(s);
        tvDemandMessageCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOrderMessageCount(String s) {
        tvOrderMessageCount.setText(s);
        tvOrderMessageCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSystemMessageCount(String s) {
        tvSystemMessageCount.setText(s);
        tvSystemMessageCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDemandMessageCount() {
        tvDemandMessageCount.setVisibility(View.GONE);
    }

    @Override
    public void hideOrderMessageCount() {
        tvOrderMessageCount.setVisibility(View.GONE);
    }

    @Override
    public void hideSystemMessageCount() {
        tvSystemMessageCount.setVisibility(View.GONE);
    }

    private long lastClickTime;

    private boolean isFastClicks() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= 10000) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return !flag;
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
