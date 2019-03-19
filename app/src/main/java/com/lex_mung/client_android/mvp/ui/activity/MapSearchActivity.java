package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lex_mung.client_android.di.module.MapSearchModule;
import com.lex_mung.client_android.mvp.ui.adapter.MapPickerAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;
import me.zl.mvp.utils.DeviceUtils;

import com.lex_mung.client_android.di.component.DaggerMapSearchComponent;
import com.lex_mung.client_android.mvp.contract.MapSearchContract;
import com.lex_mung.client_android.mvp.presenter.MapSearchPresenter;

import com.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.MAP_INFO.MAP_INFO;
import static com.lex_mung.client_android.app.EventBusTags.MAP_INFO.SELECT_PLACE;

public class MapSearchActivity extends BaseActivity<MapSearchPresenter> implements MapSearchContract.View
        , PoiSearch.OnPoiSearchListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private MapPickerAdapter mapPickerAdapter;

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapSearchComponent
                .builder()
                .appComponent(appComponent)
                .mapSearchModule(new MapSearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
        etSearch.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearchQuery(etSearch.getText().toString());
                DeviceUtils.hideSoftKeyboard(etSearch);
            }
            return false;
        });
    }

    private void initAdapter() {
        mapPickerAdapter = new MapPickerAdapter();
        mapPickerAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            PoiItem poiItem = mapPickerAdapter.getItem(position);
            if (poiItem == null) return;
            AppUtils.post(MAP_INFO, SELECT_PLACE, poiItem);
            killMyself();
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (query != null && poiSearch != null && poiResult != null) {
                    if (poiResult.getPageCount() - 1 > currentPage) {
                        currentPage++;
                        query.setPageNum(currentPage);// 设置查后一页
                        poiSearch.searchPOIAsyn();
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                doSearchQuery(etSearch.getText().toString());
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(mapPickerAdapter);
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        doSearchQuery(etSearch.getText().toString());
        DeviceUtils.hideSoftKeyboard(etSearch);
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord) {
        showLoading("");
        currentPage = 1;
        query = new PoiSearch.Query(keyWord, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);

        poiSearch = new PoiSearch(mActivity, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        hideLoading();// 隐藏对话框
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    List<PoiItem> poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        if (currentPage == 1) {
                            mapPickerAdapter.setNewData(poiItems);
                            smartRefreshLayout.finishRefresh();
                        } else {
                            mapPickerAdapter.addData(poiItems);
                            smartRefreshLayout.finishLoadMore();
                        }
                    } else {
                        showMessage("无结果");
                    }
                }
            } else {
                showMessage("无结果");
            }
        } else {
            showMessage("无结果");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

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
