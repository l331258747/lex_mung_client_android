package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultMainComponent;
import cn.lex_mung.client_android.di.module.FreeConsultMainModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultMainPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.FabFreeDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class FreeConsultMainActivity extends BaseActivity<FreeConsultMainPresenter> implements FreeConsultMainContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.fab)
    ImageView fab;

    private FabFreeDialog fabDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultMainComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultMainModule(new FreeConsultMainModule(this))
                .build()
                .inject(this);
    }

    @OnClick({
//            R.id.tv_btn,
            R.id.fab
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_btn:
//                BuryingPointHelp.getInstance().onEvent(mActivity, "free_text_list_page","free_text_list_page_free_text_post_click");
//                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
//                    launchActivity(new Intent(mActivity, FreeConsultActivity.class));
//                } else {
//                    bundle.clear();
//                    bundle.putInt(BundleTags.TYPE, 1);
//                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
//                }
//                break;
            case R.id.fab:
                showFabDialog();
                break;
        }
    }

    public void showFabDialog() {
        fab.setVisibility(View.GONE);
        if (fabDialog == null) {
            fabDialog = new FabFreeDialog(mActivity, new FabFreeDialog.OnClickListener() {
                @Override
                public void onCloseClick() {
                    fab.setVisibility(View.VISIBLE);
                }

                @Override
                public void onHelpClick() {//发布免费咨询
                    fab.setVisibility(View.VISIBLE);
                    if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                        launchActivity(new Intent(mActivity, FreeConsultActivity.class));
                    } else {
                        bundle.clear();
                        bundle.putInt(BundleTags.TYPE, 1);
                        launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                    }
                }

                @Override
                public void onCustomClick() {//委托找律师 h5
                    fab.setVisibility(View.VISIBLE);
                    String str = DataHelper.getStringSF(mActivity,DataHelperTags.WTAJ_URL);
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
                }
            });
        }
        fabDialog.show();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_main1;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);

        titleView.getRightTv().setOnClickListener(v -> {
            BuryingPointHelp.getInstance().onEvent(mActivity, "free_text_list_page","free_text_list_page_my_consultation_click");
            if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                launchActivity(new Intent(mActivity,FreeConsultListActivity.class));
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }
        });
    }

    @Override
    public void initRecyclerView(FreeConsultMainAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(FreeConsultMainAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
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
        BuryingPointHelp.getInstance().onActivityResumed(mActivity, "free_text_list_page",getPair());
    }

    @Override
    public void onPause() {
        super.onPause();
        BuryingPointHelp.getInstance().onActivityPaused(mActivity, "free_text_list_page",getPair());
    }
}
