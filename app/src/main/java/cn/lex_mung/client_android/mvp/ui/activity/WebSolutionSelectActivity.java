package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.WebSolutionSelectModule;
import cn.lex_mung.client_android.mvp.ui.dialog.HelpStepDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerWebSolutionSelectComponent;
import cn.lex_mung.client_android.mvp.contract.WebSolutionSelectContract;
import cn.lex_mung.client_android.mvp.presenter.WebSolutionSelectPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;

public class WebSolutionSelectActivity extends BaseActivity<WebSolutionSelectPresenter> implements WebSolutionSelectContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebSolutionSelectComponent
                .builder()
                .appComponent(appComponent)
                .webSolutionSelectModule(new WebSolutionSelectModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web_solution_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({
            R.id.ll_txt
            , R.id.ll_call
            , R.id.ll_lawyer
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_txt:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_index_mfzx");
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, FreeConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.ll_call:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_index_kszx");
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, FastConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.ll_lawyer:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_index_zjzx");
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, LawyerListActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
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