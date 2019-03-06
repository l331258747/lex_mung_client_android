package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.SelectResortInstitutionModule;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;
import com.lex_mung.client_android.mvp.ui.adapter.ResortInstitutionAdapter;
import com.lex_mung.client_android.mvp.ui.adapter.ResortInstitutionsResultAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;

import com.lex_mung.client_android.di.component.DaggerSelectResortInstitutionComponent;
import com.lex_mung.client_android.mvp.contract.SelectResortInstitutionContract;
import com.lex_mung.client_android.mvp.presenter.SelectResortInstitutionPresenter;

import com.lex_mung.client_android.R;

import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;

public class SelectResortInstitutionActivity extends BaseActivity<SelectResortInstitutionPresenter> implements SelectResortInstitutionContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_1)
    TextView tvTitle1;
    @BindView(R.id.recycler_view_1)
    RecyclerView recyclerView1;
    @BindView(R.id.tv_title_2)
    TextView tvTitle2;
    @BindView(R.id.recycler_view_2)
    RecyclerView recyclerView2;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;

    private String type;
    private int level = 0;
    private int regionId = 100000;
    private int regionId1 = 0;
    private int regionId2 = 0;
    private int id;
    private boolean flag;

    private ResortInstitutionsResultAdapter resultAdapter;
    private ResortInstitutionAdapter institutionAdapter;
    private ResortInstitutionAdapter institutionAdapter1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectResortInstitutionComponent
                .builder()
                .appComponent(appComponent)
                .selectResortInstitutionModule(new SelectResortInstitutionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_resort_institution;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            type = bundleIntent.getString(BundleTags.TYPE, "court");
            level = bundleIntent.getInt(BundleTags.LEVEL, 0);
            regionId = bundleIntent.getInt(BundleTags.REGION_ID, 100000);
            regionId1 = bundleIntent.getInt(BundleTags.REGION_ID_1, 0);
            regionId2 = bundleIntent.getInt(BundleTags.REGION_ID_2, 0);
            id = bundleIntent.getInt(BundleTags.ID, -1);
            flag = bundleIntent.getBoolean(BundleTags.FLAG, false);
        }
        if (level == 0 && regionId1 > 0) {
            bundle.clear();
            bundle.putString(BundleTags.TYPE, type);
            bundle.putInt(BundleTags.LEVEL, 1);
            bundle.putInt(BundleTags.REGION_ID, regionId1);
            bundle.putInt(BundleTags.REGION_ID_1, regionId1);
            bundle.putInt(BundleTags.REGION_ID_2, regionId2);
            bundle.putInt(BundleTags.ID, id);
            launchActivity(new Intent(mActivity, SelectResortInstitutionActivity.class), bundle);
        }
        if (level == 1 && regionId2 > 0) {
            bundle.clear();
            bundle.putString(BundleTags.TYPE, type);
            bundle.putInt(BundleTags.LEVEL, 2);
            bundle.putInt(BundleTags.REGION_ID, regionId2);
            bundle.putInt(BundleTags.REGION_ID_1, regionId1);
            bundle.putInt(BundleTags.REGION_ID_2, regionId2);
            bundle.putInt(BundleTags.ID, id);
            launchActivity(new Intent(mActivity, SelectResortInstitutionActivity.class), bundle);
        }
        initRecyclerView();
        if ("court".equals(type)) {
            tvTitle.setText(R.string.text_resort_court);
            mPresenter.getCourt("", regionId, level, false);
        } else {
            tvTitle.setText(R.string.text_resort_p);
            mPresenter.getProcuratorate("", regionId, level, false);
        }
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView1, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerView2, new LinearLayoutManager(mActivity));
        resultAdapter = new ResortInstitutionsResultAdapter();
        institutionAdapter = new ResortInstitutionAdapter();
        institutionAdapter1 = new ResortInstitutionAdapter();
        switch (level) {
            case 0:
                recyclerView1.setAdapter(institutionAdapter);
                recyclerView2.setAdapter(resultAdapter);

                tvTitle1.setText("court".equals(type) ? R.string.text_supreme_people_court : R.string.text_supreme_people_p);
                tvTitle2.setText(R.string.text_select_by_province);
                break;
            case 1:
                rlSearch.setVisibility(View.GONE);

                recyclerView1.setAdapter(institutionAdapter);
                recyclerView2.setAdapter(resultAdapter);

                tvTitle1.setText("court".equals(type) ? R.string.text_higher_people_court : R.string.text_higher_people_p);
                tvTitle2.setText("court".equals(type) ? getString(R.string.text_intermediate_people_court) + "&" + getString(R.string.text_grass_roots_court)
                        : getString(R.string.text_intermediate_people_p) + "&" + getString(R.string.text_grass_roots_p));
                break;
            case 2:
                rlSearch.setVisibility(View.GONE);

                recyclerView1.setAdapter(institutionAdapter);
                recyclerView2.setAdapter(institutionAdapter1);

                tvTitle1.setText("court".equals(type) ? R.string.text_intermediate_people_court : R.string.text_intermediate_people_p);
                tvTitle2.setText("court".equals(type) ? R.string.text_grass_roots_court : R.string.text_grass_roots_p);
                break;
        }
        institutionAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            InstitutionListEntity entity = institutionAdapter.getItem(position);
            if (entity == null) return;
            if (id == entity.getInstitutionId()) {
                entity.setInstitutionId(0);
                entity.setInstitutionName("");
            }
            if (flag) {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, entity);
            } else {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, entity);
            }
            AppManager.getAppManager().killActivity(SelectResortInstitutionActivity.class);
        });
        institutionAdapter1.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            InstitutionListEntity entity = institutionAdapter1.getItem(position);
            if (entity == null) return;
            if (id == entity.getInstitutionId()) {
                entity.setInstitutionId(0);
                entity.setInstitutionName("");
            }
            if (flag) {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, entity);
            } else {
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, entity);
            }
            AppManager.getAppManager().killActivity(SelectResortInstitutionActivity.class);
        });
        institutionAdapter1.setOnLoadMoreListener(() -> {
            if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                if ("court".equals(type)) {
                    tvTitle.setText(R.string.text_resort_court);
                    mPresenter.getCourt("", regionId, level, true);
                } else {
                    tvTitle.setText(R.string.text_resort_p);
                    mPresenter.getProcuratorate("", regionId, level, true);
                }
            } else {
                institutionAdapter1.loadMoreEnd();
            }
        }, recyclerView2);
        resultAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            RegionEntity entity = resultAdapter.getItem(position);
            if (entity == null) return;
            regionId = entity.getRegionId();
            bundle.clear();
            bundle.putString(BundleTags.TYPE, type);
            bundle.putInt(BundleTags.LEVEL, level == 0 ? 1 : 2);
            bundle.putInt(BundleTags.REGION_ID, regionId);
            bundle.putInt(BundleTags.ID, id);
            launchActivity(new Intent(mActivity, SelectResortInstitutionActivity.class), bundle);
        });
    }

    @Override
    public void setAdapter(List<RegionEntity> regionEntities, List<InstitutionListEntity> list1, List<InstitutionListEntity> list2, boolean isAdd) {
        if (regionEntities != null
                && regionEntities.size() > 0) {
            resultAdapter.setNewData(regionEntities);
        }
        for (InstitutionListEntity bean : list1) {
            if (bean.getInstitutionId() == id) {
                bean.setSelect(true);
            }
        }
        institutionAdapter.setNewData(list1);
        for (InstitutionListEntity bean : list2) {
            if (bean.getInstitutionId() == id) {
                bean.setSelect(true);
            }
        }
        if (isAdd) {
            institutionAdapter1.addData(list2);
            institutionAdapter1.loadMoreComplete();
        } else {
            institutionAdapter1.setNewData(list2);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                institutionAdapter1.loadMoreEnd();
            }
        }
    }

    @OnClick({R.id.rl_search})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.rl_search:
                bundle.clear();
                bundle.putString(BundleTags.TYPE, type);
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, SearchResortInstitutionActivity.class), bundle);
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
