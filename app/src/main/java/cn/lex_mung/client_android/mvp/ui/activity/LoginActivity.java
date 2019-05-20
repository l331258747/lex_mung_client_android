package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.lex_mung.client_android.BuildConfig;
import cn.lex_mung.client_android.di.module.LoginModule;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;

import cn.lex_mung.client_android.di.component.DaggerLoginComponent;
import cn.lex_mung.client_android.mvp.contract.LoginContract;
import cn.lex_mung.client_android.mvp.presenter.LoginPresenter;

import cn.lex_mung.client_android.R;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.bt_test_code)
    Button btTestCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.initTimer();
        etCode.setFilters(new InputFilter[]{CharacterHandler.emojiFilter, new InputFilter.LengthFilter(6)});
        etMobile.setFilters(new InputFilter[]{CharacterHandler.emojiFilter, new InputFilter.LengthFilter(11)});
        if (BuildConfig.IS_PROD) {
            btTestCode.setVisibility(View.GONE);
        } else {
            btTestCode.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_code, R.id.bt_login, R.id.tv_agreement, R.id.bt_test_code})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_code:
                mPresenter.getCode(etMobile.getText().toString());
                break;
            case R.id.bt_login:
                mPresenter.login(etMobile.getText().toString(), etCode.getText().toString(), JPushInterface.getRegistrationID(mActivity));
                break;
            case R.id.tv_agreement:
                mPresenter.getAgreement();
                break;
            case R.id.bt_test_code:
                mPresenter.getCode1(etMobile.getText().toString());
                break;
        }
    }

    @Override
    public void setCodeButtonStatus(boolean b, String s, int color) {
        tvCode.setClickable(b);
        tvCode.setText(s);
        tvCode.setTextColor(color);
    }

    @Override
    public void showCode(String code) {
        etCode.setText(code);
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
