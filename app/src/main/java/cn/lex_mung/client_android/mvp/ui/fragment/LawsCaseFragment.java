package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.CaseAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerLawsCaseComponent;
import cn.lex_mung.client_android.di.module.LawsCaseModule;
import cn.lex_mung.client_android.mvp.contract.LawsCaseContract;
import cn.lex_mung.client_android.mvp.presenter.LawsCasePresenter;

import cn.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public class LawsCaseFragment extends BaseFragment<LawsCasePresenter> implements LawsCaseContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private CaseAdapter caseAdapter;

    public static LawsCaseFragment newInstance(int id, String institution) {
        LawsCaseFragment fragment = new LawsCaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", id);
        bundle.putString("INSTITUTION", institution);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawsCaseComponent
                .builder()
                .appComponent(appComponent)
                .lawsCaseModule(new LawsCaseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laws_case, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPresenter.setMemberId(getArguments().getInt("ID"));
            mPresenter.setInstitution(getArguments().getString("INSTITUTION"));
        }
        mPresenter.getCaseList(false);
        initAdapter();
        initRecyclerView();
        caseAdapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    private void initAdapter() {
        caseAdapter = new CaseAdapter();
        caseAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            CaseListEntity.ListBean bean = caseAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putString(BundleTags.URL, bean.getUrl());
            bundle.putString(BundleTags.TITLE, bean.getTitle());
            bundle.putBoolean(BundleTags.IS_SHARE, false);
            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                mPresenter.getCaseList(true);
            } else {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(caseAdapter);
    }

    @Override
    public void setAdapter(List<CaseListEntity.ListBean> list, boolean isAdd) {
        if (isAdd) {
            caseAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            caseAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
            smartRefreshLayout.finishRefresh();
            caseAdapter.setNewData(list);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}
