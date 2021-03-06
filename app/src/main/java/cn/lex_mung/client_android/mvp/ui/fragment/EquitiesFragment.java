package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerEquitiesComponent;
import cn.lex_mung.client_android.di.module.EquitiesModule;
import cn.lex_mung.client_android.mvp.contract.EquitiesContract;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.presenter.EquitiesPresenter;
import cn.lex_mung.client_android.mvp.ui.activity.AddEquitiesOrgActivity;
import cn.lex_mung.client_android.mvp.ui.activity.JoinEquitiesOrgActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;
import cn.lex_mung.client_android.mvp.ui.activity.PublicLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.activity.TradingListActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.EquitiesAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView;
import cn.lex_mung.client_android.mvp.ui.widget.RoundImageView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class EquitiesFragment extends BaseFragment<EquitiesPresenter> implements EquitiesContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current_equities)
    TextView tvCurrentEquities;
    @BindView(R.id.recycler_view_current_equities)
    RecyclerView recyclerViewCurrentEquities;
    @BindView(R.id.tv_more_equities)
    TextView tvMoreEquities;
    @BindView(R.id.recycler_view_more_equities)
    RecyclerView recyclerViewMoreEquities;
    @BindView(R.id.iv_equities_bg)
    RoundImageView ivEquitiesBg;
    @BindView(R.id.tv_member_total)
    TextView tvMemberTotal;
    @BindView(R.id.tv_lawyer_total)
    TextView tvLawyerTotal;
    @BindView(R.id.tv_equities_explain)
    TextView tvEquitiesExplain;
    @BindView(R.id.tv_open_qualification)
    TextView tvOpenQualification;
    @BindView(R.id.tv_exclusive_equities)
    TextView tvExclusiveEquities;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nsv_equities_details)
    NestedScrollView nsvEquitiesDetails;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smart_refresh_layout;

    @BindView(R.id.ll__balance)
    LinearLayout ll__balance;
    @BindView(R.id.tv_card_club_balance)
    TextView tv_card_club_balance;
    @BindView(R.id.tv_card_member_balance)
    TextView tv_card_member_balance;

    @BindView(R.id.emptyView)
    EmptyView emptyView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    private EquitiesAdapter equitiesAdapter1;
    private EquitiesAdapter equitiesAdapter2;
    private LawyerListAdapter lawyerListAdapter;

    public static EquitiesFragment newInstance() {
        return new EquitiesFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEquitiesComponent
                .builder()
                .appComponent(appComponent)
                .equitiesModule(new EquitiesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_equities, container, false);
    }

    private boolean isCreated = false;
    private boolean isLoadOver = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "vip_page", getPair());
            if(!isLoadOver){
                mPresenter.onResume();//bug1 出现后加的
                isLoadOver = true;
            }

        } else {
            BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "vip_page", getPair());
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isCreated = true;
        initAdapter();
        initEmptyView();
    }

    private void initEmptyView() {
        emptyView.getBtn().setOnClickListener(v -> {
            mPresenter.onResume();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.onResume();//bug1 出现菊花转个不停
    }


    @OnClick({R.id.view_add, R.id.view_trading_details, R.id.view_examine_all_equities, R.id.view_all_lawyer})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_add:
                launchActivity(new Intent(mActivity, AddEquitiesOrgActivity.class));
                break;
            case R.id.view_trading_details:
                bundle.clear();
                bundle.putInt(BundleTags.ID, mPresenter.getEntity().getOrganizationLevelNameId());
                launchActivity(new Intent(mActivity, TradingListActivity.class), bundle);
                break;
            case R.id.view_examine_all_equities:
                mPresenter.showAllEquitiesLayout();
                break;
            case R.id.view_all_lawyer:
                bundle.clear();
                bundle.putInt(BundleTags.ORG_ID, DataHelper.getIntergerSF(mActivity, DataHelperTags.EQUITIES_ORG_ID));
                bundle.putInt(BundleTags.LEVEL, DataHelper.getIntergerSF(mActivity, DataHelperTags.EQUITIES_ORG_LEVEL_ID));
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
                break;
        }
    }

    @Override
    public void showAllEquitiesLayout() {
        tvTitle.setText(getString(R.string.text_equities));
        smart_refresh_layout.setVisibility(View.VISIBLE);
        nsvEquitiesDetails.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView(){
        emptyView.setVisibility(View.VISIBLE);
        smart_refresh_layout.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
    }

    private void initAdapter() {

        smart_refresh_layout.setEnableLoadMore(false);
        smart_refresh_layout.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onResume();
            }
        });


        equitiesAdapter1 = new EquitiesAdapter(mImageLoader, true);
        equitiesAdapter1.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            EquitiesListEntity entity = equitiesAdapter1.getItem(position);
            if (entity == null) return;

            if(entity.getIsBuyEquity()){
                if(TextUtils.isEmpty(entity.getLegalAdviserUrl())) return;
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getLegalAdviserUrl());
                bundle.putString(BundleTags.TITLE, entity.getEquityName());
                launchActivity(new Intent(mActivity,WebActivity.class),bundle);
            }else{
                DataHelper.setIntergerSF(mActivity, DataHelperTags.EQUITIES_ORG_ID, entity.getOrganizationId());
                DataHelper.setIntergerSF(mActivity, DataHelperTags.EQUITIES_ORG_LEVEL_ID, entity.getOrganizationLevelNameId());
                mPresenter.getEquitiesDetails();
            }
        });

        equitiesAdapter2 = new EquitiesAdapter(mImageLoader, false);
        equitiesAdapter2.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            EquitiesListEntity entity = equitiesAdapter2.getItem(position);
            if (entity == null) return;

            if(entity.getIsBuyEquity()){
                if(TextUtils.isEmpty(entity.getLegalAdviserUrl())) return;
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getLegalAdviserUrl());
                bundle.putString(BundleTags.TITLE, entity.getEquityName());
                launchActivity(new Intent(mActivity,WebActivity.class),bundle);
            }else{
                bundle.clear();
                bundle.putInt(BundleTags.ID, entity.getOrganizationId());
                bundle.putInt(BundleTags.LEVEL, entity.getOrganizationLevelNameId());

                if(entity.getIsPublic() == 1){
                    launchActivity(new Intent(mActivity, PublicLawyerActivity.class), bundle);
                }else{
                    launchActivity(new Intent(mActivity, JoinEquitiesOrgActivity.class), bundle);
                }
            }
        });

        lawyerListAdapter = new LawyerListAdapter(mImageLoader);
        lawyerListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            LawyerEntity2 bean = lawyerListAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getMemberId());
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        });

        AppUtils.configRecyclerView(recyclerViewCurrentEquities, new LinearLayoutManager(mActivity));
        recyclerViewCurrentEquities.setAdapter(equitiesAdapter1);
        recyclerViewCurrentEquities.setNestedScrollingEnabled(false);

        AppUtils.configRecyclerView(recyclerViewMoreEquities, new LinearLayoutManager(mActivity));
        recyclerViewMoreEquities.setAdapter(equitiesAdapter2);
        recyclerViewMoreEquities.setNestedScrollingEnabled(false);

        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(lawyerListAdapter);
    }

    @Override
    public void showEquitiesDetails() {
        tvTitle.setText("专属权益");
        nsvEquitiesDetails.setVisibility(View.VISIBLE);
        smart_refresh_layout.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void hideCurrentEquitiesLayout() {
        tvCurrentEquities.setVisibility(View.GONE);
        recyclerViewCurrentEquities.setVisibility(View.GONE);
        tvMoreEquities.setText("全部权益");
    }

    @Override
    public void showCurrentEquitiesLayout() {
        tvCurrentEquities.setVisibility(View.VISIBLE);
        recyclerViewCurrentEquities.setVisibility(View.VISIBLE);
        tvMoreEquities.setText("更多权益");
    }

    @Override
    public void setEquitiesAdapter1(List<EquitiesListEntity> data) {
        emptyView.setVisibility(View.GONE);
        smart_refresh_layout.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.GONE);
        smart_refresh_layout.finishRefresh();

        equitiesAdapter1.setNewData(data);
    }

    @Override
    public void setEquitiesAdapter2(List<EquitiesListEntity> data) {
        emptyView.setVisibility(View.GONE);
        smart_refresh_layout.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.GONE);
        smart_refresh_layout.finishRefresh();

        tvMoreEquities.setVisibility(View.VISIBLE);
        equitiesAdapter2.setNewData(data);
    }

    @Override
    public void setLawyerAdapter(List<LawyerEntity2> list) {
        lawyerListAdapter.setNewData(list);
    }

    @Override
    public void setBalance(List<EquitiesDetailsEntity.CouponsChildEntity> list) {
        tv_card_club_balance.setVisibility(View.GONE);
        tv_card_member_balance.setVisibility(View.GONE);
        ll__balance.setVisibility(View.GONE);

        if (list != null && list.size() > 0) {
            ll__balance.setVisibility(View.VISIBLE);
            for (EquitiesDetailsEntity.CouponsChildEntity child : list) {
                if (child.getCouponType() == 5) {
                    tv_card_club_balance.setVisibility(View.VISIBLE);
                    tv_card_club_balance.setText(child.getBalanceStr());
                }
                if (child.getCouponType() == 1) {
                    tv_card_member_balance.setVisibility(View.VISIBLE);
                    tv_card_member_balance.setText(child.getBalanceStr());
                }
            }
        }
    }

    @Override
    public void setEquitiesBg(String image) {
        if(!TextUtils.isEmpty(image)){
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(image)
                            .isCenterCrop(false)
                            .imageView(ivEquitiesBg)
                            .build());
        }
    }

    @Override
    public void setMemberTotal(String memberCount) {
        tvMemberTotal.setText(memberCount);
    }

    @Override
    public void setLawyerTotal(String lawyerCount) {
        tvLawyerTotal.setText(lawyerCount);
    }

    @Override
    public void setEquitiesExplain(String rightsInterpret) {
        tvEquitiesExplain.setText(rightsInterpret);
    }

    @Override
    public void setOpenQualification(String openingQualification) {
        tvOpenQualification.setText(openingQualification);
    }

    @Override
    public void setExclusiveEquities(String exclusiveRights) {
        tvExclusiveEquities.setText(exclusiveRights);
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
