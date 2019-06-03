package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.DownloadUtils;
import cn.lex_mung.client_android.di.component.DaggerMainComponent;
import cn.lex_mung.client_android.di.module.MainModule;
import cn.lex_mung.client_android.mvp.contract.MainContract;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.presenter.MainPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.HelpStepDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.fragment.EquitiesFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.FindLawyerFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.HomePagerFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.MeFragment;
import cn.lex_mung.client_android.mvp.ui.widget.BottomNavigationViewEx;
import cn.lex_mung.client_android.mvp.ui.widget.CustomScrollViewPager;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.StatusBarUtil;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.view_pager)
    CustomScrollViewPager viewPager;
    @BindView(R.id.bottom_navigation_view_ex)
    BottomNavigationViewEx bottomNavigationViewEx;
    @BindView(R.id.view)
    View view;

    private SparseIntArray items = new SparseIntArray();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useFragment() {
        return true;
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.checkVersion();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        bottomNavigationViewEx.setItemIconTintList(null);//取消图片着色效果
        initViewPager();

        setStatusColor(0);
    }

    @Override
    public void showHelpDialog(){
        new HelpStepDialog(mActivity,
                dialog -> {
                    launchActivity(new Intent(mActivity,HelpStepActivity.class));
                }).setContent("服务助手平均每天帮助2561名用户找到合适的法律服务和律师，它能帮助您解决如下问题：")
                .setContent2("· 不知道当前是否需要法律服务\n· 不知道选择说明样的律师\n· 不知道合适字的律师费用")
                .setCannelStr("不需要")
                .setSubmitStr("试试看").show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private void setStatusColor(int position){
        if(position == 0){
            StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ddf6ed), 0);
        }
//        else if(position == 3){
//            StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_06a66a), 0);
//        }
        else{
            StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ff), 0);
        }
    }

    private void initViewPager() {
        fragments.add(HomePagerFragment.newInstance());
        fragments.add(EquitiesFragment.newInstance());
        fragments.add(FindLawyerFragment.newInstance());
        fragments.add(MeFragment.newInstance());
        items.append(R.id.i_home, 0);
        items.append(R.id.i_equities, 1);
        items.append(R.id.i_lawyer, 2);
        items.append(R.id.i_me, 3);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setScrollable(false);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments));

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = items.get(item.getItemId());
                if (previousPosition != position) {
                    previousPosition = position;
                    viewPager.setCurrentItem(position);
                    setStatusColor(position);
                }
                if (position == 3
                        && !DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                }
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        MobclickAgent.onEvent(mActivity, "w_y_shouye_index_zls");
                        break;
                    case 3:
                        MobclickAgent.onEvent(mActivity, "w_y_shouye_index_wode");
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 切换页面
     *
     * @param pos 下标
     */
    public void switchPage(int pos) {
        try {
            viewPager.setCurrentItem(pos);
            bottomNavigationViewEx.setCurrentItem(pos);
        } catch (Exception ignored) {
        }
    }

    /**
     * 收到消息处理
     *
     * @param notificationClickEvent 通知点击事件
     */
    public void onEvent(NotificationClickEvent notificationClickEvent) {
        try {
            if (null == notificationClickEvent) {
                return;
            }
            Message msg = notificationClickEvent.getMessage();
            if (msg != null) {
                Conversation conversation = JMessageClient.getSingleConversation(msg.getTargetID(), Api.J_PUSH);
                Intent notificationIntent = new Intent(mActivity, MessageChatActivity.class);
                int id = Integer.parseInt(conversation.getTargetId().replace("lex", ""));
                notificationIntent.putExtra(BundleTags.ID, id);
                conversation.resetUnreadCount();
                launchActivity(notificationIntent);
            }
        } catch (Exception ignored) {
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

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showMessage("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppUtils.exitApp();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void startDownload(VersionEntity data) {
        DownloadUtils.getInstance().update(mActivity, data);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
