package com.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.SearchResortInstitutionModule;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import com.lex_mung.client_android.mvp.ui.adapter.ResortInstitutionAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;
import me.zl.mvp.utils.DeviceUtils;

import com.lex_mung.client_android.di.component.DaggerSearchResortInstitutionComponent;
import com.lex_mung.client_android.mvp.contract.SearchResortInstitutionContract;
import com.lex_mung.client_android.mvp.presenter.SearchResortInstitutionPresenter;

import com.lex_mung.client_android.R;

import java.util.List;

import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;

public class SearchResortInstitutionActivity extends BaseActivity<SearchResortInstitutionPresenter> implements SearchResortInstitutionContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ResortInstitutionAdapter adapter;
    private String type;
    private int id = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchResortInstitutionComponent
                .builder()
                .appComponent(appComponent)
                .searchResortInstitutionModule(new SearchResortInstitutionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_resort_institution;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            type = bundleIntent.getString(BundleTags.TYPE, "court");
            id = bundleIntent.getInt(BundleTags.ID, -1);
        }
        if ("court".equals(type)) {
            tvTitle.setText(R.string.text_resort_court);
        } else {
            tvTitle.setText(R.string.text_resort_p);
        }
        initAdapter();
        initRecyclerView();
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPresenter.setKeyWord(etSearch.getText().toString());
                if ("court".equals(type)) {
                    mPresenter.getCourt(false);
                } else {
                    mPresenter.getP(false);
                }
                DeviceUtils.hideSoftKeyboard(etSearch);
            }
            return false;
        });
        etSearch.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        adapter = new ResortInstitutionAdapter();
        adapter.setOnLoadMoreListener(() -> {
            if (mPresenter.getPageNum() < mPresenter.getTotalPage()) {
                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                if ("court".equals(type)) {
                    mPresenter.getCourt(true);
                } else {
                    mPresenter.getP(true);
                }
            } else {
                adapter.loadMoreEnd();
            }
        }, recyclerView);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            InstitutionListEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (id == entity.getInstitutionId()) {
                entity.setInstitutionId(0);
                entity.setInstitutionName("");
            }
            AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, entity);
            AppManager.getAppManager().killActivity(SelectResortInstitutionActivity.class);
            killMyself();
        });
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<InstitutionListEntity> listBeans, boolean isAdd) {
        for (InstitutionListEntity bean : listBeans) {
            if (bean.getInstitutionId() == id) {
                bean.setSelect(true);
            }
        }
        if (isAdd) {
            adapter.addData(listBeans);
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(listBeans);
            if (mPresenter.getTotalPage() == mPresenter.getPageNum()) {
                adapter.loadMoreEnd();
            }
        }
    }

    @OnClick({R.id.bt_search})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.bt_search:
                mPresenter.setKeyWord(etSearch.getText().toString());
                if ("court".equals(type)) {
                    mPresenter.getCourt(false);
                } else {
                    mPresenter.getP(false);
                }
                DeviceUtils.hideSoftKeyboard(etSearch);
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
