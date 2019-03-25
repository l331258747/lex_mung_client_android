package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.PushJumpModule;
import cn.lex_mung.client_android.mvp.model.entity.JMessageEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import cn.lex_mung.client_android.di.component.DaggerPushJumpComponent;
import cn.lex_mung.client_android.mvp.contract.PushJumpContract;
import cn.lex_mung.client_android.mvp.presenter.PushJumpPresenter;

import cn.lex_mung.client_android.R;

import static cn.lex_mung.client_android.app.DataHelperTags.IS_LOGIN_SUCCESS;

public class PushJumpActivity extends BaseActivity<PushJumpPresenter> implements PushJumpContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPushJumpComponent
                .builder()
                .appComponent(appComponent)
                .pushJumpModule(new PushJumpModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_push_jump;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            String json;
            json = bundleIntent.getString(BundleTags.JSON);
            JMessageEntity entity = new Gson().fromJson(json, JMessageEntity.class);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            switch (entity.getSubType()) {
                case 100://欢迎消息
                    intent.setClass(mActivity, MainActivity.class);
                    break;
                case 107:
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getUrl());
                    intent.putExtras(bundle);
                    intent.setClass(mActivity, WebActivity.class);
                    break;
                case 109:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyOrderActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 240:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getBusiId());
                        intent.putExtras(bundle);
                        intent.setClass(mActivity, FreeConsultDetailActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 241:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyOrderActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 244:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyAccountActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 245:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyAccountActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
            }
            launchActivity(intent);
        } catch (Exception ignored) {
        }
        killMyself();
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
