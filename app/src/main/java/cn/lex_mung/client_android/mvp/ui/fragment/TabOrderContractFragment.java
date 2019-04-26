package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerTabOrderContractComponent;
import cn.lex_mung.client_android.di.module.TabOrderContractModule;
import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.presenter.TabOrderContractPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class TabOrderContractFragment extends BaseFragment<TabOrderContractPresenter> implements TabOrderContractContract.View {
    @Inject
    ImageLoader mImageLoader;


    @BindView(R.id.emptyView)
    EmptyView emptyView;
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
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    private List<DocGetEntity> datas;
    private TabOrderContractAdapter tabOrderContractAdapter;
    private int orderStatus;
    private String lmobile;
    private String orderNo;

    public static TabOrderContractFragment newInstance(String orderNo,int orderStatus) {
        TabOrderContractFragment fragment = new TabOrderContractFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BundleTags.ORDER_NO, orderNo);
        bundle.putInt(BundleTags.STATE,orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setLmobile(String lmobile){
        this.lmobile = lmobile;
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
        if (getArguments() != null){
            orderStatus = getArguments().getInt(BundleTags.STATE);

            if(orderStatus == 0){//显示空界面
                emptyView.setVisibility(View.VISIBLE);
                rlList.setVisibility(View.GONE);
            }else{//显示文件列表
                emptyView.setVisibility(View.GONE);
                rlList.setVisibility(View.VISIBLE);
                mPresenter.getList(orderNo = getArguments().getString(BundleTags.ORDER_NO));
                mPresenter.setOrderNo(orderNo);
            }

//            if(orderStatus == 3){//订单关闭->不显示底部按钮
//                ll_bottom.setVisibility(View.GONE);
//            }else{
//                ll_bottom.setVisibility(View.VISIBLE);
//            }
        }

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
                if(TextUtils.isEmpty(lmobile)) return;
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lmobile));
                startActivity(dialIntent);
                break;
            case R.id.iv_send_contract:
                //TODO 文件浏览
                mPresenter.showFileChooser();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode,resultCode,data);
    }

}
