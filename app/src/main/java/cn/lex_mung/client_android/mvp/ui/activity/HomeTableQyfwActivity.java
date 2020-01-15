package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerHomeTableQyfwComponent;
import cn.lex_mung.client_android.di.module.HomeTableQyfwModule;
import cn.lex_mung.client_android.mvp.contract.HomeTableQyfwContract;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.presenter.HomeTableQyfwPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class HomeTableQyfwActivity extends BaseActivity<HomeTableQyfwPresenter> implements HomeTableQyfwContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;

    String title;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeTableQyfwComponent
                .builder()
                .appComponent(appComponent)
                .homeTableQyfwModule(new HomeTableQyfwModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_table_qyfw;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title = bundleIntent.getString(BundleTags.TITLE);
        titleView.setTitle(title);
    }

    @OnClick({R.id.view_server_online
            , R.id.view_server_private_lawyer
            , R.id.view_wtzls
            , R.id.view_qcscht
            , R.id.view_ajfxpg
            , R.id.view_flfxtj
            , R.id.view_ssdz
            , R.id.view_zsss
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        String str;
        HomeChildEntity entity;
        switch (view.getId()) {
            case R.id.view_server_online:
                str = DataHelper.getStringSF(mActivity, DataHelperTags.ONLINE_LAWYER_URL);
                entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                if (!TextUtils.isEmpty(str) && entity != null) {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_server_private_lawyer:
                str = DataHelper.getStringSF(mActivity, DataHelperTags.PRIVATE_LAWYER_URL);
                entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                if (!TextUtils.isEmpty(str) && entity != null) {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_wtzls:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.WTAJ_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_qcscht:
                str = DataHelper.getStringSF(mActivity, DataHelperTags.HTSCQC_URL);
                entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                if (!TextUtils.isEmpty(str) && entity != null) {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_ajfxpg:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.AJFXPG_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_flfxtj:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.QYFLFXTY_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_ssdz:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.SSDZ_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_zsss:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.ZSSS_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
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
