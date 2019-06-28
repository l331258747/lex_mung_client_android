package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerLawyerListScreenComponent;
import cn.lex_mung.client_android.di.module.LawyerListScreenModule;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListScreenAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.mvp.contract.LawyerListScreenContract;
import cn.lex_mung.client_android.mvp.presenter.LawyerListScreenPresenter;

import cn.lex_mung.client_android.R;

import java.util.List;

public class LawyerListScreenActivity extends BaseActivity<LawyerListScreenPresenter> implements LawyerListScreenContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLawyerListScreenComponent
                .builder()
                .appComponent(appComponent)
                .lawyerListScreenModule(new LawyerListScreenModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lawyer_list_screen;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            mPresenter.setFlag(bundleIntent.getBoolean(BundleTags.FLAG));
            mPresenter.setRegionId1(bundleIntent.getInt(BundleTags.REGION_ID_1));
            mPresenter.setRegionId2(bundleIntent.getInt(BundleTags.REGION_ID_2));
            mPresenter.setRequireTypeId(bundleIntent.getInt(BundleTags.REQUIRE_TYPE_ID));
            mPresenter.setLawyerListScreenEntityList((List<LawyerListScreenEntity>) bundleIntent.getSerializable(BundleTags.LIST));
        }
    }

    public void setPos(int pos) {
        mPresenter.setPos(pos);
    }

    @Override
    public void initRecyclerView(LawyerListScreenAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.bt_confirm,R.id.bt_reset})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.bt_confirm:
                mPresenter.confirm();
                break;
            case R.id.bt_reset:
                mPresenter.reset();
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
    public LawyerListScreenActivity getActivity() {
        return this;
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
