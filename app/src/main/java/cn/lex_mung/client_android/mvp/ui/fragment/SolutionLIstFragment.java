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
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.SolutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerSolutionLIstComponent;
import cn.lex_mung.client_android.di.module.SolutionLIstModule;
import cn.lex_mung.client_android.mvp.contract.SolutionLIstContract;
import cn.lex_mung.client_android.mvp.presenter.SolutionLIstPresenter;

import cn.lex_mung.client_android.R;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

public class SolutionLIstFragment extends BaseFragment<SolutionLIstPresenter> implements SolutionLIstContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    public static SolutionLIstFragment newInstance(int id) {
        SolutionLIstFragment fragment = new SolutionLIstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleTags.ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SolutionAdapter solutionAdapter;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSolutionLIstComponent
                .builder()
                .appComponent(appComponent)
                .solutionLIstModule(new SolutionLIstModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_solution_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) return;
        initAdapter();
        initRecyclerView();
        mPresenter.setId(getArguments().getInt(BundleTags.ID));
        mPresenter.getSolutionList(false);
    }

    private void initAdapter() {
        solutionAdapter = new SolutionAdapter(mImageLoader);
        solutionAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            SolutionListEntity.ListBean bean = solutionAdapter.getItem(position);
            if (bean == null) return;
            bean.setHelpNumber(bean.getHelpNumber() + 1);
            solutionAdapter.setData(position, bean);
            bundle.clear();
            bundle.putString(BundleTags.URL, bean.getSolutionUrl());
            bundle.putString(BundleTags.TITLE, bean.getTitle());
            bundle.putString(BundleTags.DES, "");
            bundle.putString(BundleTags.IMAGE, "");
            bundle.putBoolean(BundleTags.IS_SHARE, true);
            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                mPresenter.getSolutionList(true);
            } else {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(solutionAdapter);
    }

    @Override
    public void setAdapter(List<SolutionListEntity.ListBean> list, boolean isAdd) {
        if (isAdd) {
            solutionAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            solutionAdapter.setNewData(list);
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
