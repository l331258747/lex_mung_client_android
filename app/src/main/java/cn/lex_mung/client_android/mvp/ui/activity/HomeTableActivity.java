package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.HomeTableModule;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeTableComponent;
import cn.lex_mung.client_android.mvp.contract.HomeTableContract;
import cn.lex_mung.client_android.mvp.presenter.HomeTablePresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;

public class HomeTableActivity extends BaseActivity<HomeTablePresenter> implements HomeTableContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.group_ssdz)
    Group groupSsdz;
    @BindView(R.id.group_zsss)
    Group groupZsss;
    @BindView(R.id.group_qyfwk)
    Group groupQyfwk;
    @BindView(R.id.group_qyflfxty)
    Group groupQyflfxty;
    @BindView(R.id.group_ajfxpg)
    Group groupAjfxpg;

    String title;
    int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeTableComponent
                .builder()
                .appComponent(appComponent)
                .homeTableModule(new HomeTableModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_table;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title = bundleIntent.getString(BundleTags.TITLE);
        id = bundleIntent.getInt(BundleTags.ID);
        titleView.setTitle(title);

        String str;
        HomeChildEntity entity;
        if(id == 2){
            str = DataHelper.getStringSF(mActivity,DataHelperTags.SSDZ_URL);
            entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null)
                groupSsdz.setVisibility(View.VISIBLE);
            str = DataHelper.getStringSF(mActivity,DataHelperTags.ZSSS_URL);
            entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null)
                groupZsss.setVisibility(View.VISIBLE);
        }else if(id == 6){
            str = DataHelper.getStringSF(mActivity,DataHelperTags.FWK_URL);
            entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null)
                groupQyfwk.setVisibility(View.VISIBLE);
            str = DataHelper.getStringSF(mActivity,DataHelperTags.QYFLFXTY_URL);
            entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null)
                groupQyflfxty.setVisibility(View.VISIBLE);
            str = DataHelper.getStringSF(mActivity,DataHelperTags.AJFXPG_URL);
            entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null)
                groupAjfxpg.setVisibility(View.VISIBLE);

        }
    }

    public void goHelp(){
        bundle.clear();
        bundle.putInt(BundleTags.ID, id);
        bundle.putString(BundleTags.TITLE, title);
        launchActivity(new Intent(mActivity, HelpStepChildActivity.class), bundle);
    }

    @OnClick({R.id.view_help, R.id.view_lawyer,R.id.view_ssdz,R.id.view_zsss,R.id.view_qyfwk,R.id.view_qyflfxty,R.id.view_ajfxpg})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        String str;
        HomeChildEntity entity;
        switch (view.getId()) {
            case R.id.view_help:
                switch (id) {
                    case 2:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_page", "litigation_arbitration_detail_page_assistant_click");
                        break;
                    case 9:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail_page", "meeting_detail_pageassistant_click");
                        break;
                    case 6:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail_page", "enterprise_detail_page_assistant_click");
                        break;
                }
                goHelp();
                break;
            case R.id.view_lawyer:
                switch (id) {
                    case 2:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_page", "litigation_arbitration_detail_page_search_lawyer_click");
                        break;
                    case 9:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail_page", "meeting_detail_page_search_lawyer_click");
                        break;
                    case 6:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail_page", "enterprise_detail_page_search_lawyer_click");
                        break;
                }
                bundle.clear();
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
                break;
            case R.id.view_ssdz:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.SSDZ_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
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
            case R.id.view_zsss:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.ZSSS_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
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
            case R.id.view_qyfwk:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.FWK_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
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
            case R.id.view_qyflfxty:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.QYFLFXTY_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
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
            case R.id.view_ajfxpg:
                str = DataHelper.getStringSF(mActivity,DataHelperTags.AJFXPG_URL);
                entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
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

    @Override
    public void onResume() {
        super.onResume();
        switch (id){
            case 2:
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "litigation_arbitration_detail_page",getPair());
                break;
            case 9:
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "meeting_detail_page",getPair());
                break;
            case 6:
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "enterprise_detail_page",getPair());
                break;
        }
//        BuryingPointHelp.getInstance().onActivityResumed(mActivity, "litigation_arbitration_detial");
    }

    @Override
    public void onPause() {
        super.onPause();
//        BuryingPointHelp.getInstance().onActivityPaused(mActivity, "litigation_arbitration_detial");

        switch (id){
            case 2:
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "litigation_arbitration_detail_page",getPair());
                break;
            case 9:
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "meeting_detail_page",getPair());
                break;
            case 6:
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "enterprise_detail_page",getPair());
                break;
        }
    }
}
