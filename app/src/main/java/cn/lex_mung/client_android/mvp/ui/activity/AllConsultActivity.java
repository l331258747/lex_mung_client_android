package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.AllConsultModule;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerAllConsultComponent;
import cn.lex_mung.client_android.mvp.contract.AllConsultContract;
import cn.lex_mung.client_android.mvp.presenter.AllConsultPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;

public class AllConsultActivity extends BaseActivity<AllConsultPresenter> implements AllConsultContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAllConsultComponent
                .builder()
                .appComponent(appComponent)
                .allConsultModule(new AllConsultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_all_consult;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @OnClick({R.id.view_card_text
            , R.id.view_card_call
            , R.id.view_card_expert
            , R.id.view_card_meet
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        String str;
        HomeChildEntity entity;
        switch (view.getId()) {
            case R.id.view_card_text:
                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_free_text_click");
                launchActivity(new Intent(mActivity, FreeConsultMainActivity.class));
                break;
            case R.id.view_card_call:
                str = DataHelper.getStringSF(mActivity, DataHelperTags.QUICK_URL);
                entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                if (!TextUtils.isEmpty(str) && entity != null) {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                }
                break;
            case R.id.view_card_expert:
                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_expert_consultation_click");
                bundle.clear();
                bundle.putInt(BundleTags.ID, 8);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
                break;
            case R.id.view_card_meet:
                bundle.clear();
                bundle.putInt(BundleTags.ID, 9);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
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
