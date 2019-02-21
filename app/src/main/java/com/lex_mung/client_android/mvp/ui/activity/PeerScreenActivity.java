package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.PeerScreenModule;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;
import com.lex_mung.client_android.mvp.ui.adapter.PeerScreenAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerPeerScreenComponent;
import com.lex_mung.client_android.mvp.contract.PeerScreenContract;
import com.lex_mung.client_android.mvp.presenter.PeerScreenPresenter;

import com.lex_mung.client_android.R;

import java.io.Serializable;
import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO_LIST;
import static com.lex_mung.client_android.app.EventBusTags.PEER_SCREEN_INFO.PEER_SCREEN_INFO_LIST_1;

public class PeerScreenActivity extends BaseActivity<PeerScreenPresenter> implements PeerScreenContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PeerScreenAdapter peerScreenAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPeerScreenComponent
                .builder()
                .appComponent(appComponent)
                .peerScreenModule(new PeerScreenModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_peer_screen;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
        if (bundleIntent != null) {
            mPresenter.setRegionId1(bundleIntent.getInt(BundleTags.REGION_ID_1));
            mPresenter.setRegionId2(bundleIntent.getInt(BundleTags.REGION_ID_2));
            mPresenter.setPeerScreenEntityList((List<PeerScreenEntity>) bundleIntent.getSerializable(BundleTags.LIST));
        }
    }

    private void initAdapter() {
        peerScreenAdapter = new PeerScreenAdapter();
        peerScreenAdapter.setActivity(this);
        peerScreenAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            PeerScreenEntity entity = peerScreenAdapter.getItem(position);
            if (entity == null) {
                return;
            }
            if ("courtId".equals(entity.getPropKey())
                    || "procuratorateId".equals(entity.getPropKey())) {
                mPresenter.setPos(position);
                bundle.clear();
                bundle.putString(BundleTags.TYPE, "courtId".equals(entity.getPropKey()) ? "court" : "procuratorate");
                bundle.putInt(BundleTags.REGION_ID_1, mPresenter.getRegionId1());
                bundle.putInt(BundleTags.REGION_ID_2, mPresenter.getRegionId2());
                bundle.putInt(BundleTags.ID, entity.getId());
                launchActivity(new Intent(mActivity, SelectResortInstitutionActivity.class), bundle);
                return;
            }
            if (entity.getIsTile() == 0) {
                mPresenter.setPos(position);
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) entity.getItems());
                bundle.putInt(BundleTags.TYPE, 5);
                bundle.putString(BundleTags.TITLE, entity.getText());
                bundle.putInt(BundleTags.ID, entity.getId());
                launchActivity(new Intent(mActivity, SelectListItemActivity.class), bundle);
            }
        });
    }

    public void setPos(int pos) {
        mPresenter.setPos(pos);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(peerScreenAdapter);
    }

    @OnClick({R.id.bt_reset, R.id.bt_confirm})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.bt_reset:
                for (PeerScreenEntity entity : mPresenter.getPeerSearchEntityList()) {
                    entity.setContent("");
                    entity.setId(0);
                    entity.setPos(0);
                }
                peerScreenAdapter.setNewData(mPresenter.getPeerSearchEntityList());
                AppUtils.post(PEER_SCREEN_INFO, PEER_SCREEN_INFO_LIST_1, mPresenter.getPeerSearchEntityList());
                break;
            case R.id.bt_confirm:
                AppUtils.post(PEER_SCREEN_INFO, PEER_SCREEN_INFO_LIST, mPresenter.getPeerSearchEntityList());
                killMyself();
                break;
        }
    }

    @Override
    public void setAdapter(List<PeerScreenEntity> data) {
        peerScreenAdapter.setNewData(data);
    }

    @Override
    public void setAdapterItem(int pos, PeerScreenEntity entity) {
        peerScreenAdapter.setData(pos, entity);
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
