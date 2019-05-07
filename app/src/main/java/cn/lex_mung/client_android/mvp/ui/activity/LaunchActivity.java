package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.umeng.analytics.AnalyticsConfig;

import java.util.UUID;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerLaunchComponent;
import cn.lex_mung.client_android.di.module.LaunchModule;
import cn.lex_mung.client_android.mvp.contract.LaunchContract;
import cn.lex_mung.client_android.mvp.model.entity.DeviceEntity;
import cn.lex_mung.client_android.mvp.presenter.LaunchPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLaunchComponent
                .builder()
                .appComponent(appComponent)
                .launchModule(new LaunchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_launch;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        AppUtils.statsInScreen(this);
        try {
            String uuid;
            if (DataHelper.contains(mActivity, DataHelperTags.UUID)) {//存在
                uuid = DataHelper.getStringSF(mActivity, DataHelperTags.UUID);
            } else {//不存在
                uuid = UUID.randomUUID().toString();
                DataHelper.setStringSF(mActivity, DataHelperTags.UUID, uuid);
            }
            DeviceEntity device = new DeviceEntity(2
                    , DeviceUtils.getVersionName(mActivity)
                    , DeviceUtils.getVersionCode(mActivity)
                    , DataHelper.getStringSF(mActivity, DataHelperTags.CHANNEL)
                    , android.os.Build.BRAND + " " + android.os.Build.MODEL
                    , android.os.Build.VERSION.RELEASE
                    , uuid
                    , DeviceUtils.getAndroidId(mActivity,uuid)
            );
            DataHelper.setStringSF(mActivity, DataHelperTags.DEVICE, new Gson().toJson(device));
        } catch (Exception ignored) {
        } finally {
            mPresenter.getPermission();
        }

        LogUtil.e("Channel：" + AnalyticsConfig.getChannel(mActivity));
    }

    @Override
    public void launch() {
        new Handler().postDelayed(() -> {
            if (!DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_SHOW_WELCOME)) {
                DataHelper.setBooleanSF(mActivity, DataHelperTags.IS_SHOW_WELCOME, true);
                launchActivity(new Intent(mActivity, MainActivity.class));
            } else {
                launchActivity(new Intent(mActivity, MainActivity.class));
            }
            killMyself();
        }, 1000);
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
