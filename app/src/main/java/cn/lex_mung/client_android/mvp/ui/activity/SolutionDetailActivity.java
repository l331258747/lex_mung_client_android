package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.SolutionDetailModule;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeFreeAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.SolutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerSolutionDetailComponent;
import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import cn.lex_mung.client_android.mvp.presenter.SolutionDetailPresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.StatusBarUtil;

public class SolutionDetailActivity extends BaseActivity<SolutionDetailPresenter> implements SolutionDetailContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;

    @BindView(R.id.group_table)
    Group groupTable;
    @BindView(R.id.sfl_table)
    SimpleFlowLayout sflTable;

    @BindView(R.id.group_lawyer)
    Group groupLawyer;
    @BindView(R.id.tv_lawyer_title)
    TextView tvLawyerTitle;
    @BindView(R.id.recycler_view_lawyer)
    RecyclerView recyclerViewLawyer;
    @BindView(R.id.tv_lawyer_all)
    TextView tvLawyerAll;


    @BindView(R.id.tv_contract_title)
    TextView tvContractTitle;
    @BindView(R.id.group_contract)
    Group groupContract;
    @BindView(R.id.view_contract_write)
    View viewContractWrite;
    @BindView(R.id.tv_contract_write)
    TextView tvContractWrite;
    @BindView(R.id.tv_contract_write_price)
    TextView tvContractWritePrice;
    @BindView(R.id.view_contract_check)
    View viewContractCheck;
    @BindView(R.id.tv_contract_check)
    TextView tvContractCheck;
    @BindView(R.id.tv_contract_check_price)
    TextView tvContractCheckPrice;
    @BindView(R.id.view_contract_call)
    View viewContractCall;
    @BindView(R.id.tv_contract_call_price)
    TextView tvContractCallPrice;


    @BindView(R.id.tv_free_title)
    TextView tvFreeTitle;
    @BindView(R.id.group_free)
    Group groupFree;
    @BindView(R.id.recycler_view_free)
    RecyclerView recyclerViewFree;


    @BindView(R.id.tv_solution_title)
    TextView tvSolutionTitle;
    @BindView(R.id.group_solution)
    Group groupSolution;
    @BindView(R.id.recycler_view_solution)
    RecyclerView recyclerViewSolution;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    int solutionId;
    String solutionName;
    boolean isCriminal;//是否为刑事类

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSolutionDetailComponent
                .builder()
                .appComponent(appComponent)
                .solutionDetailModule(new SolutionDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_solution_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        solutionId = bundleIntent.getInt(BundleTags.SOLUTION_TYPE_ID, -1);
        solutionName = bundleIntent.getString(BundleTags.SOLUTION_TYPE_NAME);
        isCriminal = bundleIntent.getBoolean(BundleTags.IS_CRIMINAL,false);

        titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
        titleView.setTitle(solutionName);

        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_1EC88B), 0);

        mPresenter.onCreate(solutionId);

        tvLawyerTitle.setText(solutionName + "优选律师");
        tvContractTitle.setText(solutionName + "起草审查合同");
        tvFreeTitle.setText(solutionName + "热门咨询");
        tvSolutionTitle.setText(solutionName + "解决方案");

    }

    SolutionAdapter solutionAdapter;

    public void initSolutionAdapter() {
        solutionAdapter = new SolutionAdapter(mImageLoader, 1);
        solutionAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "solution_click");
            SolutionListEntity bean = solutionAdapter.getItem(position);
            if (bean == null) return;
            bean.setHelpNumber(bean.getHelpNumber() + 1);
            solutionAdapter.setData(position, bean);
            bundle.clear();
            bundle.putString(BundleTags.URL,bean.getSolutionUrl());
            bundle.putString(BundleTags.TITLE,bean.getTitle());
            bundle.putString(BundleTags.SHARE_URL, bean.getSolutionUrl());
            bundle.putString(BundleTags.SHARE_TITLE, bean.getTitle());
            bundle.putString(BundleTags.SHARE_DES, "");
            bundle.putString(BundleTags.SHARE_IMAGE, "");
            bundle.putBoolean(BundleTags.IS_SHARE, true);
            launchActivity(new Intent(mActivity, WebSolutionActivity.class), bundle);
        });

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableOverScrollBounce(true);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                        mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                        mPresenter.getSolutions(true);
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        AppUtils.configRecyclerView(recyclerViewSolution, new LinearLayoutManager(mActivity));
        recyclerViewSolution.setNestedScrollingEnabled(false);
        recyclerViewSolution.setAdapter(solutionAdapter);
    }

    List<SolutionListEntity> list2;

    public void setSolutionData(List<SolutionListEntity> list, boolean isAdd) {
        if (isAdd) {
            list2.addAll(list);
//            solutionAdapter.addData(list);
            solutionAdapter.setNewData(list2);
            smartRefreshLayout.finishLoadMore();
        } else {
            list2 = list;
            solutionAdapter.setNewData(list2);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    public void hideSolutionLayout() {
        groupSolution.setVisibility(View.GONE);
    }


    public void initFreeAdapter(List<CommonFreeTextEntity> datas) {
        HomeFreeAdapter homeFreeAdapter = new HomeFreeAdapter(mImageLoader);
        homeFreeAdapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            if (!DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                launchActivity(new Intent(mActivity, LoginActivity.class));
                return;
            }

            CommonFreeTextEntity bean = homeFreeAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getConsultationId());
            launchActivity(new Intent(mActivity, FreeConsultDetail1Activity.class), bundle);
        });

        AppUtils.configRecyclerView(recyclerViewFree, new LinearLayoutManager(mActivity));
        recyclerViewFree.setNestedScrollingEnabled(false);
        recyclerViewFree.setAdapter(homeFreeAdapter);
        homeFreeAdapter.setNewData(datas);
    }

    public void hideFreeLayout() {
        groupFree.setVisibility(View.GONE);
    }

    public void setContractLayout(List<CommonPageContractsEntity> datas) {
        for (int i = 0; i < datas.size(); i++) {
            CommonPageContractsEntity entity = datas.get(i);
            if (entity.getId() == 9999) {//电话咨询
                tvContractCallPrice.setText(entity.getContent());
                viewContractCall.setOnClickListener(v -> {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getUrl());
                    bundle.putString(BundleTags.TITLE, "快速电话咨询");
                    bundle.putBoolean(BundleTags.IS_SHARE, false);
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                });
                continue;
            }

            if (entity.getContent().indexOf("审查") != -1) {
                tvContractCheckPrice.setText(entity.getPriceStr());
                tvContractCheck.setText(entity.getContent() + "(" + solutionName + ")");
                viewContractCheck.setOnClickListener(v -> {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getUrl());
                    bundle.putString(BundleTags.TITLE, entity.getContent());
                    bundle.putBoolean(BundleTags.IS_SHARE, false);
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                });
                continue;
            }

            if (entity.getContent().indexOf("起草") != -1) {
                tvContractWritePrice.setText(entity.getPriceStr());
                tvContractWrite.setText(entity.getContent() + "(" + solutionName + ")");
                viewContractWrite.setOnClickListener(v -> {
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getUrl());
                    bundle.putString(BundleTags.TITLE, entity.getContent());
                    bundle.putBoolean(BundleTags.IS_SHARE, false);
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                });
                continue;
            }
        }

        if(isCriminal){
            hideContractLayout();
        }
    }

    public void hideContractLayout(){
        groupContract.setVisibility(View.GONE);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initTableLayout(List<CommonMarkEntity> marks) {
        for (int i = 0; i < marks.size(); i++) {
            int pos = i;
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_solution_detail_table, null, false);
            TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvTitle.setText(marks.get(i).getName());
            itemView.setOnClickListener(v -> {
                if (isFastClick()) return;
                CommonMarkEntity bean = marks.get(pos);
                bundle.clear();
                bundle.putInt(BundleTags.SOLUTION_TYPE_ID,solutionId);
                bundle.putInt(BundleTags.SOLUTION_TYPE_CHILD_ID,bean.getId());
                bundle.putString(BundleTags.SOLUTION_TYPE_NAME,solutionName);
                launchActivity(new Intent(mActivity, LawyerListActivity.class),bundle);
            });
            sflTable.addView(itemView, i);
        }
    }

    public void hideTableLayout() {
        groupTable.setVisibility(View.GONE);
    }

    public void initLawyerAdapter(List<LawyerEntity2> datas) {
        HomeLawyerAdapter homeLawyerAdapter = new HomeLawyerAdapter(mImageLoader);
        homeLawyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            LawyerEntity2 entity2 = homeLawyerAdapter.getItem(position);
            if (entity2 == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, entity2.getMemberId());
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        });

        AppUtils.configRecyclerView(recyclerViewLawyer, new LinearLayoutManager(mActivity));
        recyclerViewLawyer.setNestedScrollingEnabled(false);
        recyclerViewLawyer.setAdapter(homeLawyerAdapter);
        homeLawyerAdapter.setNewData(datas);
    }

    public void hideLawyerLayout() {
        groupLawyer.setVisibility(View.GONE);
        tvLawyerAll.setVisibility(View.GONE);
    }

    public void hideLawyerAllView() {
        tvLawyerAll.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_lawyer_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lawyer_all:
                if (isFastClick()) return;
                bundle.clear();
                bundle.putInt(BundleTags.SOLUTION_TYPE_ID,solutionId);
                bundle.putString(BundleTags.SOLUTION_TYPE_NAME,solutionName);
                launchActivity(new Intent(mActivity, LawyerListActivity.class),bundle);
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
