package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.mvp.model.entity.order.TabOrderContractEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerTabOrderContractComponent;
import cn.lex_mung.client_android.di.module.TabOrderContractModule;
import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import cn.lex_mung.client_android.mvp.presenter.TabOrderContractPresenter;

import cn.lex_mung.client_android.R;

public class TabOrderContractFragment extends BaseFragment<TabOrderContractPresenter> implements TabOrderContractContract.View {

    @BindView(R.id.rl_rush_error)
    RelativeLayout rlRushError;
    @BindView(R.id.rl_list)
    RelativeLayout rlList;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_call)
    TextView ivCall;
    @BindView(R.id.iv_send_contract)
    TextView ivSendContract;

    TabOrderContractEntity tabOrderContractEntity;
    List<TabOrderContractEntity> datas;

    @Inject
    ImageLoader mImageLoader;
    private TabOrderContractAdapter tabOrderContractAdapter;

    public static TabOrderContractFragment newInstance() {
        TabOrderContractFragment fragment = new TabOrderContractFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabOrderContractComponent
                .builder()
                .appComponent(appComponent)
                .tabOrderContractModule(new TabOrderContractModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_order_contract, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;
        rlRushError.setVisibility(View.GONE);
        rlList.setVisibility(View.VISIBLE);
        initAdapter();
        initRecyclerView();
        datas = tabOrderContractEntity.getDatas();
        setAdapter(datas,false);
    }

    private void initAdapter() {
        tabOrderContractAdapter = new TabOrderContractAdapter(mImageLoader);
        tabOrderContractAdapter.setOnItemClickListener((adapter, view, position) -> {
            //下载？
            LogUtil.e("aaaa");
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            LogUtil.e("bbbb");
//            if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
//                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
//                mPresenter.getSolutionList(true);
//            } else {
//                smartRefreshLayout.finishLoadMoreWithNoMoreData();
//            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(tabOrderContractAdapter);
    }

    public void setAdapter(List<TabOrderContractEntity> list, boolean isAdd) {
        if (isAdd) {
            tabOrderContractAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            tabOrderContractAdapter.setNewData(list);
//            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
//                smartRefreshLayout.finishLoadMoreWithNoMoreData();
//            }
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
