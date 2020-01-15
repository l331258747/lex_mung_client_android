package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.HomeTableSszcActivityModule;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeTableSszcActivityComponent;
import cn.lex_mung.client_android.mvp.contract.HomeTableSszcActivityContract;
import cn.lex_mung.client_android.mvp.presenter.HomeTableSszcActivityPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;

public class HomeTableSszcActivityActivity extends BaseActivity<HomeTableSszcActivityPresenter> implements HomeTableSszcActivityContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;

    String title;
    int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeTableSszcActivityComponent
                .builder()
                .appComponent(appComponent)
                .homeTableSszcActivityModule(new HomeTableSszcActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_table_sszc;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title = bundleIntent.getString(BundleTags.TITLE);
        id = bundleIntent.getInt(BundleTags.ID);
        titleView.setTitle(title);
    }


    @OnClick({R.id.view_find_lawyer_help
            , R.id.view_find_lawyer_mine
            ,R.id.view_find_lawyer_send
            ,R.id.view_server_zsss
            ,R.id.view_server_ssdz
            ,R.id.view_server_ajfxpg
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        String str;
        HomeChildEntity entity;
        switch (view.getId()) {
            case R.id.view_find_lawyer_help:
                BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_page", "litigation_arbitration_detail_page_assistant_click");
                bundle.clear();
                bundle.putInt(BundleTags.ID, 2);
                bundle.putString(BundleTags.TITLE, title);
                launchActivity(new Intent(mActivity, HelpStepChildActivity.class), bundle);
                break;
            case R.id.view_find_lawyer_mine:
                BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_page", "litigation_arbitration_detail_page_search_lawyer_click");
                bundle.clear();
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
                break;
            case R.id.view_find_lawyer_send:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.WTAJ_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_server_zsss:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.ZSSS_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_server_ssdz:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.SSDZ_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
                if(!TextUtils.isEmpty(str) && entity != null){
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_server_ajfxpg:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.AJFXPG_URL);
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
