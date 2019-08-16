package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.WebSolutionSelectModule;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.HelpStepDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.GsonUtil;
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
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    bundle.clear();
                    bundle.putInt(BundleTags.BURYING_POINT,1);
                    launchActivity(new Intent(mActivity, FreeConsultActivity.class),bundle);
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.ll_call:
                String str = DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL);
                HomeChildEntity entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    if(entity.getShowShare() == 1){
                        bundle.putBoolean(BundleTags.IS_SHARE, true);
                        bundle.putString(BundleTags.SHARE_URL, entity.getShareUrl());
                        bundle.putString(BundleTags.SHARE_TITLE, entity.getShareTitle());
                        bundle.putString(BundleTags.SHARE_DES, entity.getShareDescription());
                        bundle.putString(BundleTags.SHARE_IMAGE, entity.getShareImg());
                    }
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.ll_lawyer:
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, 8);
                    launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
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
