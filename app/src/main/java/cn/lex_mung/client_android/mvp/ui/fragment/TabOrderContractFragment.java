package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerTabOrderContractComponent;
import cn.lex_mung.client_android.di.module.TabOrderContractModule;
import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.presenter.TabOrderContractPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class TabOrderContractFragment extends BaseFragment<TabOrderContractPresenter> implements TabOrderContractContract.View {
    @Inject
    ImageLoader mImageLoader;

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

    private List<DocGetEntity> datas;
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

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        if (getArguments() == null)
//            return;

        mPresenter.getList("单号");

        rlRushError.setVisibility(View.GONE);
        rlList.setVisibility(View.VISIBLE);
        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        tabOrderContractAdapter = new TabOrderContractAdapter(mImageLoader);
        tabOrderContractAdapter.setOnItemClickListener((adapter, view, position) -> {
            //下载？
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
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

    @Override
    public void setAdapter(List<DocGetEntity> list) {
        tabOrderContractAdapter.setNewData(list);
    }

    @OnClick({R.id.iv_call, R.id.iv_send_contract})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call:
                //TODO 联系律师
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
            case R.id.iv_send_contract:
                //TODO 文件浏览
                showFileChooser();
                break;
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

    //-----文件选择
    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            LogUtil.e("Please install a File Manager.");
        }
    }

    private static final String TAG = "ChooseFile";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode,resultCode,data);
    }

}