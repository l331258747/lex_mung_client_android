package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerSolutionDetailComponent;
import cn.lex_mung.client_android.di.module.SolutionDetailModule;
import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import cn.lex_mung.client_android.mvp.presenter.SolutionDetailPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeContractAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeFreeAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.SolutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.ActivityDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
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
    @BindView(R.id.group_contract_call)
    Group groupContractCall;
    @BindView(R.id.recycler_view_contract)
    RecyclerView recyclerViewContract;
    @BindView(R.id.tv_contract_all)
    TextView tvContractAll;
//    @BindView(R.id.view_contract_write)
//    View viewContractWrite;
//    @BindView(R.id.tv_contract_write)
//    TextView tvContractWrite;
//    @BindView(R.id.tv_contract_write_price)
//    TextView tvContractWritePrice;

//    @BindView(R.id.view_contract_check)
//    View viewContractCheck;
//    @BindView(R.id.tv_contract_check)
//    TextView tvContractCheck;
//    @BindView(R.id.tv_contract_check_price)
//    TextView tvContractCheckPrice;

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
//    boolean isCriminal;//是否为刑事类

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
//        isCriminal = bundleIntent.getBoolean(BundleTags.IS_CRIMINAL,false);

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

            switch (solutionId) {//解决方案
                case 2://2 婚姻及家事
                    BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_solution_click");
                    break;
                case 4://4 劳动者权益保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_solution_click");
                    break;
                case 6://6 房屋及其他财物的买卖租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_solution_click");
                    break;
                case 10://10 债权债务
                    BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_solution_click");
                    break;
                case 14://14 人身伤害赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_solution_click");
                    break;
                case 19:// 19 消费维权
                    BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_solution_click");
                    break;
                case 23://23 交通事故
                    BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_solution_click");
                    break;
                case 7://7 合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_solution_click");
                    break;
                case 8://8 基础项目建设
                    BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_solution_click");
                    break;
                case 9://9 不动产销售与租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_solution_click");
                    break;
                case 13://13 知识产权及商业保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_solution_click");
                    break;
                case 17://17 资本市场及股权融资
                    BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_solution_click");
                    break;
                case 18://18 公司治理及股东关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_solution_click");
                    break;
                case 22://22 对外投资并购、合伙及联营
                    BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_solution_click");
                    break;
                case 27://27 土地纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_solution_click");
                    break;
                case 28://28 人力资源及劳资关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_solution_click");
                    break;
                case 29://29 财税专项
                    BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_solution_click");
                    break;
                case 30://30 行政许可\/处罚
                    BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_solution_click");
                    break;
                case 31://31 保险及侵权赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_solution_click");
                    break;
                case 32://32 基金/信托/保理/融资租聘
                    BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_solution_click");
                    break;
                case 33://33 买卖销售合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_solution_click");
                    break;
                case 34://34 环境保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_solution_click");
                    break;
                case 5://5  重大刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_solution_click");
                    break;
                case 20://20 职务犯罪
                    BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_solution_click");
                    break;
                case 16://16 普通刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_solution_click");
                    break;
                case 3://3 融资借贷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_solution_click");
                    break;
            }


            bean.setHelpNumber(bean.getHelpNumber() + 1);
            solutionAdapter.setData(position, bean);
            bundle.clear();
            bundle.putString(BundleTags.URL, bean.getSolutionUrl());
            bundle.putString(BundleTags.TITLE, bean.getTitle());
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

            switch (solutionId) {//免费咨询
                case 2://2 婚姻及家事
                    BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_free_text_details_click");
                    break;
                case 4://4 劳动者权益保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_free_text_details_click");
                    break;
                case 6://6 房屋及其他财物的买卖租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_free_text_details_click");
                    break;
                case 10://10 债权债务
                    BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_free_text_details_click");
                    break;
                case 14://14 人身伤害赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_free_text_details_click");
                    break;
                case 19:// 19 消费维权
                    BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_free_text_details_click");
                    break;
                case 23://23 交通事故
                    BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_free_text_details_click");
                    break;
                case 7://7 合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_free_text_details_click");
                    break;
                case 8://8 基础项目建设
                    BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_free_text_details_click");
                    break;
                case 9://9 不动产销售与租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_free_text_details_click");
                    break;
                case 13://13 知识产权及商业保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_free_text_details_click");
                    break;
                case 17://17 资本市场及股权融资
                    BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_free_text_details_click");
                    break;
                case 18://18 公司治理及股东关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_free_text_details_click");
                    break;
                case 22://22 对外投资并购、合伙及联营
                    BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_free_text_details_click");
                    break;
                case 27://27 土地纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_free_text_details_click");
                    break;
                case 28://28 人力资源及劳资关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_free_text_details_click");
                    break;
                case 29://29 财税专项
                    BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_free_text_details_click");
                    break;
                case 30://30 行政许可\/处罚
                    BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_free_text_details_click");
                    break;
                case 31://31 保险及侵权赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_free_text_details_click");
                    break;
                case 32://32 基金/信托/保理/融资租聘
                    BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_free_text_details_click");
                    break;
                case 33://33 买卖销售合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_free_text_details_click");
                    break;
                case 34://34 环境保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_free_text_details_click");
                    break;
                case 5://5  重大刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_free_text_details_click");
                    break;
                case 20://20 职务犯罪
                    BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_free_text_details_click");
                    break;
                case 16://16 普通刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_free_text_details_click");
                    break;
                case 3://3 融资借贷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_free_text_details_click");
                    break;
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

    public void setContractLayout(List<CommonPageContractsEntity> datas, CommonPageContractsEntity entity99) {
        if (entity99 != null) {
            tvContractCallPrice.setText(entity99.getContent());
            viewContractCall.setOnClickListener(v -> {

                switch (solutionId) {//电话咨询
                    case 2://2 婚姻及家事
                        BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_quick_consultation_click");
                        break;
                    case 4://4 劳动者权益保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_quick_consultation_click");
                        break;
                    case 6://6 房屋及其他财物的买卖租赁
                        BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_quick_consultation_click");
                        break;
                    case 10://10 债权债务
                        BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_quick_consultation_click");
                        break;
                    case 14://14 人身伤害赔偿
                        BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_quick_consultation_click");
                        break;
                    case 19:// 19 消费维权
                        BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_quick_consultation_click");
                        break;
                    case 23://23 交通事故
                        BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_quick_consultation_click");
                        break;
                    case 7://7 合同纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_quick_consultation_click");
                        break;
                    case 8://8 基础项目建设
                        BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_quick_consultation_click");
                        break;
                    case 9://9 不动产销售与租赁
                        BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_quick_consultation_click");
                        break;
                    case 13://13 知识产权及商业保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_quick_consultation_click");
                        break;
                    case 17://17 资本市场及股权融资
                        BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_quick_consultation_click");
                        break;
                    case 18://18 公司治理及股东关系
                        BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_quick_consultation_click");
                        break;
                    case 22://22 对外投资并购、合伙及联营
                        BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_quick_consultation_click");
                        break;
                    case 27://27 土地纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_quick_consultation_click");
                        break;
                    case 28://28 人力资源及劳资关系
                        BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_quick_consultation_click");
                        break;
                    case 29://29 财税专项
                        BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_quick_consultation_click");
                        break;
                    case 30://30 行政许可\/处罚
                        BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_quick_consultation_click");
                        break;
                    case 31://31 保险及侵权赔偿
                        BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_quick_consultation_click");
                        break;
                    case 32://32 基金/信托/保理/融资租聘
                        BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_quick_consultation_click");
                        break;
                    case 33://33 买卖销售合同纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_quick_consultation_click");
                        break;
                    case 34://34 环境保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_quick_consultation_click");
                        break;
                    case 5://5  重大刑事案件
                        BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_quick_consultation_click");
                        break;
                    case 20://20 职务犯罪
                        BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_quick_consultation_click");
                        break;
                    case 16://16 普通刑事案件
                        BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_quick_consultation_click");
                        break;
                    case 3://3 融资借贷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_quick_consultation_click");
                        break;
                }
                bundle.clear();
                bundle.putString(BundleTags.URL, entity99.getUrl());
                bundle.putString(BundleTags.TITLE, "快速电话咨询");
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            });
        } else {
            groupContractCall.setVisibility(View.GONE);
        }

        if (datas != null && datas.size() > 0) {
            tvContractAll.setText("展开全部 >");
            HomeContractAdapter homeContractAdapter = new HomeContractAdapter();
            homeContractAdapter.setOnItemClickListener((adapter1, view, position) -> {
                if (isFastClick()) return;
                CommonPageContractsEntity bean = homeContractAdapter.getItem(position);
                if (bean == null) return;
                bundle.clear();
                bundle.putString(BundleTags.URL, bean.getUrl());
                bundle.putString(BundleTags.TITLE, bean.getContent());
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            });

            AppUtils.configRecyclerView(recyclerViewContract, new LinearLayoutManager(mActivity));
            recyclerViewContract.setNestedScrollingEnabled(false);
            recyclerViewContract.setAdapter(homeContractAdapter);

            int maxSize = 5;
            if (datas.size() < maxSize) {
                hideContractAllView();
                homeContractAdapter.setNewData(datas);
            } else {
                List<CommonPageContractsEntity> datas5 = new ArrayList<>();
                for (int i = 0; i < maxSize; i++) {
                    datas5.add(datas.get(i));
                }
                homeContractAdapter.setNewData(datas5);

                tvContractAll.setOnClickListener(v -> {
                    if (isFastClick()) return;

                    if (!isContractAll) {
                        tvContractAll.setText("折叠 >");
                        homeContractAdapter.setNewData(datas);
                    } else {
                        tvContractAll.setText("展开全部 >");
                        homeContractAdapter.setNewData(datas5);
                    }
                    isContractAll = !isContractAll;
                });
            }
//            if (entity.getContent().indexOf("审查") != -1) {
//                tvContractCheckPrice.setText(entity.getPriceStr());
//                tvContractCheck.setText(entity.getContent() + "(" + solutionName + ")");
//                viewContractCheck.setOnClickListener(v -> {
//
//                    switch (solutionId) {//审查合同
//                        case 2://2 婚姻及家事
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_view_contract_click");
//                            break;
//                        case 4://4 劳动者权益保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_view_contract_click");
//                            break;
//                        case 6://6 房屋及其他财物的买卖租赁
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_view_contract_click");
//                            break;
//                        case 10://10 债权债务
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_view_contract_click");
//                            break;
//                        case 14://14 人身伤害赔偿
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_view_contract_click");
//                            break;
//                        case 19:// 19 消费维权
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_page_view_contract_click");
//                            break;
//                        case 23://23 交通事故
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_view_contract_click");
//                            break;
//                        case 7://7 合同纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_view_contract_click");
//                            break;
//                        case 8://8 基础项目建设
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_view_contract_click");
//                            break;
//                        case 9://9 不动产销售与租赁
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_view_contract_click");
//                            break;
//                        case 13://13 知识产权及商业保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_view_contract_click");
//                            break;
//                        case 17://17 资本市场及股权融资
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_view_contract_click");
//                            break;
//                        case 18://18 公司治理及股东关系
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_view_contract_click");
//                            break;
//                        case 22://22 对外投资并购、合伙及联营
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_view_contract_click");
//                            break;
//                        case 27://27 土地纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_view_contract_click");
//                            break;
//                        case 28://28 人力资源及劳资关系
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_view_contract_click");
//                            break;
//                        case 29://29 财税专项
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_view_contract_click");
//                            break;
//                        case 30://30 行政许可\/处罚
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_view_contract_click");
//                            break;
//                        case 31://31 保险及侵权赔偿
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_view_contract_click");
//                            break;
//                        case 32://32 基金/信托/保理/融资租聘
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_view_contract_click");
//                            break;
//                        case 33://33 买卖销售合同纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_view_contract_click");
//                            break;
//                        case 34://34 环境保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_view_contract_click");
//                            break;
//                        case 5://5  重大刑事案件
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_view_contract_click");
//                            break;
//                        case 20://20 职务犯罪
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_view_contract_click");
//                            break;
//                        case 16://16 普通刑事案件
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_view_contract_click");
//                            break;
//                        case 3://3 融资借贷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_view_contract_click");
//                            break;
//                    }
//
//                    bundle.clear();
//                    bundle.putString(BundleTags.URL, entity.getUrl());
//                    bundle.putString(BundleTags.TITLE, entity.getContent());
//                    bundle.putBoolean(BundleTags.IS_SHARE, false);
//                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
//                });
//                continue;
//            }
//
//            if (entity.getContent().indexOf("起草") != -1) {
//                tvContractWritePrice.setText(entity.getPriceStr());
//                tvContractWrite.setText(entity.getContent() + "(" + solutionName + ")");
//                viewContractWrite.setOnClickListener(v -> {
//
//                    switch (solutionId) {//起草合同
//                        case 2://2 婚姻及家事
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_draw_contract_click");
//                            break;
//                        case 4://4 劳动者权益保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_draw_contract_click");
//                            break;
//                        case 6://6 房屋及其他财物的买卖租赁
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_draw_contract_click");
//                            break;
//                        case 10://10 债权债务
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_draw_contract_click");
//                            break;
//                        case 14://14 人身伤害赔偿
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_draw_contract_click");
//                            break;
//                        case 19:// 19 消费维权
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_draw_contract_click");
//                            break;
//                        case 23://23 交通事故
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_draw_contract_click");
//                            break;
//                        case 7://7 合同纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_draw_contract_click");
//                            break;
//                        case 8://8 基础项目建设
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_draw_contract_click");
//                            break;
//                        case 9://9 不动产销售与租赁
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_draw_contract_click");
//                            break;
//                        case 13://13 知识产权及商业保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_draw_contract_click");
//                            break;
//                        case 17://17 资本市场及股权融资
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_draw_contract_click");
//                            break;
//                        case 18://18 公司治理及股东关系
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_draw_contract_click");
//                            break;
//                        case 22://22 对外投资并购、合伙及联营
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_draw_contract_click");
//                            break;
//                        case 27://27 土地纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_draw_contract_click");
//                            break;
//                        case 28://28 人力资源及劳资关系
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_draw_contract_click");
//                            break;
//                        case 29://29 财税专项
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_draw_contract_click");
//                            break;
//                        case 30://30 行政许可\/处罚
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_draw_contract_click");
//                            break;
//                        case 31://31 保险及侵权赔偿
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_draw_contract_click");
//                            break;
//                        case 32://32 基金/信托/保理/融资租聘
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_draw_contract_click");
//                            break;
//                        case 33://33 买卖销售合同纠纷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_draw_contract_click");
//                            break;
//                        case 34://34 环境保护
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_draw_contract_click");
//                            break;
//                        case 5://5  重大刑事案件
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_draw_contract_click");
//                            break;
//                        case 20://20 职务犯罪
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_draw_contract_click");
//                            break;
//                        case 16://16 普通刑事案件
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_draw_contract_click");
//                            break;
//                        case 3://3 融资借贷
//                            BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_draw_contract_click");
//                            break;
//                    }
//
//
//                    bundle.clear();
//                    bundle.putString(BundleTags.URL, entity.getUrl());
//                    bundle.putString(BundleTags.TITLE, entity.getContent());
//                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
//                });
//                continue;
//            }
//        }
        } else {
            hideContractLayout();
        }
    }

    boolean isContractAll = false;

    public void hideContractLayout() {
        groupContract.setVisibility(View.GONE);
        tvContractAll.setVisibility(View.GONE);
    }

    public void hideContractAllView() {
        tvContractAll.setVisibility(View.GONE);
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
                if (bean == null) return;


                switch (solutionId) {//律师详情
                    case 2://2 婚姻及家事
                        switch (bean.getId()) {
                            case 21://离婚诉讼
                                BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_divorce_lawsuit_click");
                                break;
                            case 22://夫妻财产/债务
                                BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_property_claims_click");
                                break;
                            case 23://家庭暴力
                                BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_domestic_violence_click");
                                break;
                            case 24://抚养/收养
                                BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_raising_adoption_click");
                                break;
                            case 65://财产继承
                                BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_property_inheritance_click");
                                break;
                        }
                        break;
                    case 3://3 融资借贷
                        switch (bean.getId()) {
                            case 26://民间借贷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_private lending_click");
                                break;
                            case 27://融资租赁
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_financial_leasing_click");
                                break;
                            case 30://融资担保
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_financing_guarantee_click");
                                break;
                            case 72://无形资产融资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_intangible_asset_financing_click");
                                break;
                            case 73://股权融资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_equity_financing_click");
                                break;
                            case 74://保理/票据融资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_factoring_bill_financing_click");
                                break;
                            case 75://典当/小额贷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_Pawn_financing_click");
                                break;
                            case 76://基础建设项目融资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_project_financing_click");
                                break;
                            case 137://债券发行
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_bond_issuance_click");
                                break;
                            case 138://资产证券化
                                BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_asset_securitization_click");
                                break;
                        }
                        break;
                    case 4://4 劳动者权益保护
                        switch (bean.getId()) {
                            case 28://劳资纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_labor_dispute_click");
                                break;
                            case 29://薪资追讨
                                BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_salary_recovery_click");
                                break;
                            case 31://工伤保险
                                BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_injury_insurance_click");
                                break;
                            case 79://社保纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_social_security_dispute_click");
                                break;
                        }
                        break;
                    case 5://5  重大刑事案件
                        switch (bean.getId()) {
                            case 32://毒品犯罪
                                BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_drug_crime_click");
                                break;
                            case 33://黑社会组织犯罪
                                BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_underworld_organization_click");
                                break;
                            case 34://重大暴力犯罪
                                BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_major_violence_click");
                                break;
                            case 83://经济类犯罪
                                BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_economic_crime_click");
                                break;
                        }
                        break;
                    case 6://6 房屋及其他财物的买卖租赁
                        switch (bean.getId()) {
                            case 35://房屋销售/买卖
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_house_sale_click");
                                break;
                            case 36://二手房买卖
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_second_hand_housing_sales_click");
                                break;
                            case 37://房屋租赁
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_realtor_click");
                                break;
                            case 108://房屋抵押
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_mortgage_click");
                                break;
                            case 127://房屋拆迁/补偿
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_demolition_compensation_click");
                                break;
                            case 128://房屋赠与/继承
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_house_gift_click");
                                break;
                            case 129://汽车贷款/抵押
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_car_loan_mortgage_click");
                                break;
                            case 130://艺术品投资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_art_investment_click");
                                break;
                            case 131://土地承包
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_land_contracting_click");
                                break;
                        }
                        break;
                    case 7://7 合同纠纷
                        switch (bean.getId()) {
                            case 38://买卖合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_sale_contract_dispute_click");
                                break;
                            case 39://施工合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_construction_contract_dispute_click");
                                break;
                            case 40://借款合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_loan_contract_dispute_click");
                                break;
                            case 103://国际贸易合同
                                BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_international_trade_contract_click");
                                break;
                        }
                        break;
                    case 8://8 基础项目建设
                        switch (bean.getId()) {
                            case 41://房地产开发
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_real_estate_development_click");
                                break;
                            case 42://建设施工合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_construction_contract_dispute_click");
                                break;
                            case 43://征地拆迁补偿
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_demolition_compensation_click");
                                break;
                            case 89://建筑材料欠款
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_material_money_click");
                                break;
                            case 90://建设项目转让
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_project_transfer_click");
                                break;
                            case 91://环境影响评价
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_environmental_impact_assessment_click");
                                break;
                            case 92://PPP项目
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_public_private_partnership_click");
                                break;
                            case 93://基础建设项目融资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_project_financing_click");
                                break;
                            case 94://债券发行
                                BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_bond_issuance_click");
                                break;
                        }
                        break;
                    case 9://9 不动产销售与租赁
                        switch (bean.getId()) {
                            case 44://房屋销售/买卖
                                BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_house_sale_click");
                                break;
                            case 45://房屋租赁
                                BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_realtor_click");
                                break;
                            case 46://房屋抵押
                                BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_mortgage_click");
                                break;
                            case 95://物业管理纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_property_dispute_click");
                                break;
                            case 96://共有物业
                                BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_shared_property_click");
                                break;
                        }
                        break;
                    case 10://10 债权债务
                        switch (bean.getId()) {
                            case 47://P2P借贷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_p2p_lending_click");
                                break;
                            case 48://民间借贷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_private lending_click");
                                break;
                            case 49://抵押担保
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_mortgage_guarantee_click");
                                break;
                            case 86://民间集资
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_private_fundraising_click");
                                break;
                            case 87://金融理财
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_financial_management_click");
                                break;
                            case 88://融资担保
                                BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_financing_guarantee_click");
                                break;
                        }
                        break;
                    case 13://13 知识产权及商业保护
                        switch (bean.getId()) {
                            case 56://著作权申请/保护/交易
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_copyright_application_protection_click");
                                break;
                            case 57://商标权申请/保护/交易
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_trademark_application_protection_click");
                                break;
                            case 58://专利权申请/保护/交易
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_patent_application_protection_click");
                                break;
                            case 100://商业秘密保护
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_trade_secret_protection_click");
                                break;
                            case 101://高新技术企业认定
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_high_technology_click");
                                break;
                            case 102://技术合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_technical_contract_dispute_click");
                                break;
                        }
                        break;
                    case 14://14 人身伤害赔偿
                        switch (bean.getId()) {
                            case 59://人身伤害
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_personal_injury_click");
                                break;
                            case 60://保险理赔
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_Insurance_claims_click");
                                break;
                            case 61://工伤索赔
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_work_injury_claim_click");
                                break;
                            case 124://名誉侵权
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_reputation_infringement_click");
                                break;
                            case 125://产品质量
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_product_quality_click");
                                break;
                            case 126://医疗事故
                                BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_medical_accident_click");
                                break;
                        }
                        break;
                    case 16://16 普通刑事案件
                        switch (bean.getId()) {
                            case 115://贪污受贿案件
                                BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_corruption_bribery_click");
                                break;
                            case 116://渎职案件
                                BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_malfeasance_click");
                                break;
                            case 117://职务类犯罪
                                BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_job_crime_click");
                                break;
                        }
                        break;
                    case 17://17 资本市场及股权融资
                        switch (bean.getId()) {
                            case 110://新三板上市
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_new_Third_Board_Listing_click");
                                break;
                            case 111://境内IPO
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_domestic_ipo_click");
                                break;
                            case 139://债券发行
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_debt_issuance_click");
                                break;
                            case 140://基金管理
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_fund_management_click");
                                break;
                            case 141://信托产品
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_trust_products_click");
                                break;
                            case 142://股权投资VC/PE
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_equity_investment_click");
                                break;
                            case 143://境外融资/上市
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_overseas_financing_click");
                                break;
                            case 144://证券交易
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_securities_trading_click");
                                break;
                            case 145://期货
                                BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_futures_click");
                                break;
                        }
                        break;
                    case 18://18 公司治理及股东关系
                        switch (bean.getId()) {
                            case 105://公司治理
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_corporate_governance_click");
                                break;
                            case 106://股东保护和股权架构
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_shareholder_protection_click");
                                break;
                            case 146://劳动合同管理
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_labor_contract_management_click");
                                break;
                            case 147://财税专项
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_finance_and_taxation_click");
                                break;
                            case 148://员工股权激励
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_equity_employee_incentive_click");
                                break;
                            case 149://公司破产/清算
                                BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_bankruptcy_liquidation_click");
                                break;
                        }
                        break;
                    case 19:// 19 消费维权
                        switch (bean.getId()) {
                            case 107://消费者保护
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_consumer_protection_click");
                                break;
                            case 132://投资理财
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_Investment_click");
                                break;
                            case 133://物业管理纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_property_dispute_click");
                                break;
                            case 134://保险理赔
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_insurance_claims_click");
                                break;
                            case 135://产品质量
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_product_quality_click");
                                break;
                            case 136://医疗事故
                                BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_medical_accident_click");
                                break;
                        }
                        break;
                    case 20://20 职务犯罪
                        switch (bean.getId()) {
                            case 112://死刑复核
                                BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_death_penalty_review_click");
                                break;
                            case 113://缓刑假释减刑
                                BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_probation_parole_commutation_click");
                                break;
                        }
                        break;
                    case 22://22 对外投资并购、合伙及联营
                        switch (bean.getId()) {
                            case 150://有限公司/上市公司并购
                                BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_merger_click");
                                break;
                            case 151://国有资产处置
                                BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_disposal_of_state_assets_click");
                                break;
                            case 152://不良资产处置
                                BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_disposal_of_bad_assets_click");
                                break;
                        }
                        break;
                    case 23://23 交通事故
                        switch (bean.getId()) {
                            case 123://交通事故
                                BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_accident_click");
                                break;
                        }
                        break;
                    case 27://27 土地纠纷
                        switch (bean.getId()) {
                            case 77://土地转让/抵押
                                BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_land_transfer_mortgage_click");
                                break;
                        }
                        break;
                    case 28://28 人力资源及劳资关系
                        switch (bean.getId()) {
                            case 158://社保纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_social_security_dispute_click");
                                break;
                            case 159://劳资纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_labor_dispute_click");
                                break;
                            case 160://薪资追讨
                                BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_salary_recovery_click");
                                break;
                            case 161://工伤保险
                                BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_injury_insurance_click");
                                break;
                        }
                        break;
                    case 29://29 财税专项
                        switch (bean.getId()) {
                            case 147://财税专项
                                BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_finance_taxation_click");
                                break;
                        }
                        break;
                    case 30://30 行政许可\/处罚
                        switch (bean.getId()) {
                            case 153://行政许可/处罚
                                BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_administrative_penalty_click");
                                break;
                        }
                        break;
                    case 31://31 保险及侵权赔偿
                        switch (bean.getId()) {
                            case 154://保险及侵权赔偿
                                BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_insurance_infringement_compensation_click");
                                break;
                        }
                        break;
                    case 32://32 基金/信托/保理/融资租聘
                        switch (bean.getId()) {
                            case 155://基金/信托/保理/融资租赁
                                BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_fund_trust_insurance_financing_click");
                                break;
                        }
                        break;
                    case 33://33 买卖销售合同纠纷
                        switch (bean.getId()) {
                            case 156://买卖销售合同纠纷
                                BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_sale_and_sales_contract_dispute_click");
                                break;
                        }
                        break;
                    case 34://34 环境保护
                        switch (bean.getId()) {
                            case 157://环境保护
                                BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_environmental_protection_click");
                                break;
                        }
                        break;
                }


                bundle.clear();
                bundle.putInt(BundleTags.SOLUTION_TYPE_ID, solutionId);
                bundle.putInt(BundleTags.SOLUTION_TYPE_CHILD_ID, bean.getId());
                bundle.putString(BundleTags.SOLUTION_TYPE_NAME, solutionName);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
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

            switch (solutionId) {//律师详情
                case 2://2 婚姻及家事
                    BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_lawyer_detail_click");
                    break;
                case 3://3 融资借贷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_lawyer_detail_click");
                    break;
                case 4://4 劳动者权益保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_lawyer_detail_click");
                    break;
                case 5://5  重大刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_lawyer_detail_click");
                    break;
                case 6://6 房屋及其他财物的买卖租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_lawyer_detail_click");
                    break;
                case 7://7 合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_lawyer_detail_click");
                    break;
                case 8://8 基础项目建设
                    BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_lawyer_detail_click");
                    break;
                case 9://9 不动产销售与租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_lawyer_detail_click");
                    break;
                case 10://10 债权债务
                    BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_lawyer_detail_click");
                    break;
                case 13://13 知识产权及商业保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_lawyer_detail_click");
                    break;
                case 14://14 人身伤害赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_lawyer_detail_click");
                    break;
                case 16://16 普通刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_lawyer_detail_click");
                    break;
                case 17://17 资本市场及股权融资
                    BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_lawyer_detail_click");
                    break;
                case 18://18 公司治理及股东关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_lawyer_detail_click");
                    break;
                case 19:// 19 消费维权
                    BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_lawyer_detail_click");
                    break;
                case 20://20 职务犯罪
                    BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_lawyer_detail_click");
                    break;
                case 22://22 对外投资并购、合伙及联营
                    BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_lawyer_detail_click");
                    break;
                case 23://23 交通事故
                    BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_lawyer_detail_click");
                    break;
                case 27://27 土地纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_lawyer_detail_click");
                    break;
                case 28://28 人力资源及劳资关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_lawyer_detail_click");
                    break;
                case 29://29 财税专项
                    BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_lawyer_detail_click");
                    break;
                case 30://30 行政许可\/处罚
                    BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_lawyer_detail_click");
                    break;
                case 31://31 保险及侵权赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_lawyer_detail_click");
                    break;
                case 32://32 基金/信托/保理/融资租聘
                    BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_lawyer_detail_click");
                    break;
                case 33://33 买卖销售合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_lawyer_detail_click");
                    break;
                case 34://34 环境保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_lawyer_detail_click");
                    break;
            }

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

                switch (solutionId) {//全部律师
                    case 2://2 婚姻及家事
                        BuryingPointHelp.getInstance().onEvent(mActivity, "marriage_housework_page", "marriage_housework_page_see_more_lawyers_click");
                        break;
                    case 4://4 劳动者权益保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "laborer_is_rights_protection_page", "laborer_is_rights_protection_page_see_more_lawyers_click");
                        break;
                    case 6://6 房屋及其他财物的买卖租赁
                        BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_lease_page", "sale_and_lease_page_see_more_lawyers_click");
                        break;
                    case 10://10 债权债务
                        BuryingPointHelp.getInstance().onEvent(mActivity, "creditor_is_rights_and_debts_page", "creditor_is_rights_and_debts_page_see_more_lawyers_click");
                        break;
                    case 14://14 人身伤害赔偿
                        BuryingPointHelp.getInstance().onEvent(mActivity, "personal_injury_compensation_page", "personal_injury_compensation_page_see_more_lawyers_click");
                        break;
                    case 19:// 19 消费维权
                        BuryingPointHelp.getInstance().onEvent(mActivity, "consumer_rights_page", "consumer_rights_page_see_more_lawyers_click");
                        break;
                    case 23://23 交通事故
                        BuryingPointHelp.getInstance().onEvent(mActivity, "traffic_accident_page", "traffic_accident_page_see_more_lawyers_click");
                        break;
                    case 7://7 合同纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "contractual_dispute_page", "contractual_dispute_page_see_more_lawyers_click");
                        break;
                    case 8://8 基础项目建设
                        BuryingPointHelp.getInstance().onEvent(mActivity, "basic_project_construction_page", "basic_project_construction_page_see_more_lawyers_click");
                        break;
                    case 9://9 不动产销售与租赁
                        BuryingPointHelp.getInstance().onEvent(mActivity, "real_estate_page", "real_estate_page_see_more_lawyers_click");
                        break;
                    case 13://13 知识产权及商业保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "intellectual_property_page", "intellectual_property_page_see_more_lawyers_click");
                        break;
                    case 17://17 资本市场及股权融资
                        BuryingPointHelp.getInstance().onEvent(mActivity, "capital_market_page", "capital_market_page_see_more_lawyers_click");
                        break;
                    case 18://18 公司治理及股东关系
                        BuryingPointHelp.getInstance().onEvent(mActivity, "corporate_governance_page", "corporate_governance_page_see_more_lawyers_click");
                        break;
                    case 22://22 对外投资并购、合伙及联营
                        BuryingPointHelp.getInstance().onEvent(mActivity, "foreign_investment_page", "foreign_investment_page_see_more_lawyers_click");
                        break;
                    case 27://27 土地纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "land_development_transfer_page", "land_development_transfer_page_see_more_lawyers_click");
                        break;
                    case 28://28 人力资源及劳资关系
                        BuryingPointHelp.getInstance().onEvent(mActivity, "human_resources_page", "human_resources_page_see_more_lawyers_click");
                        break;
                    case 29://29 财税专项
                        BuryingPointHelp.getInstance().onEvent(mActivity, "finance_and_taxation_page", "finance_and_taxation_page_see_more_lawyers_click");
                        break;
                    case 30://30 行政许可\/处罚
                        BuryingPointHelp.getInstance().onEvent(mActivity, "administrative_license_penalty_page", "administrative_license_penalty_page_see_more_lawyers_click");
                        break;
                    case 31://31 保险及侵权赔偿
                        BuryingPointHelp.getInstance().onEvent(mActivity, "insurance_and_infringement_compensation_page", "insurance_and_infringement_compensation_page_see_more_lawyers_click");
                        break;
                    case 32://32 基金/信托/保理/融资租聘
                        BuryingPointHelp.getInstance().onEvent(mActivity, "fund_trust_insurance_financing_page", "fund_trust_insurance_financing_page_see_more_lawyers_click");
                        break;
                    case 33://33 买卖销售合同纠纷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "sale_and_sales_contract_dispute_page", "sale_and_sales_contract_dispute_page_see_more_lawyers_click");
                        break;
                    case 34://34 环境保护
                        BuryingPointHelp.getInstance().onEvent(mActivity, "environmental_protection_page", "environmental_protection_page_see_more_lawyers_click");
                        break;
                    case 5://5  重大刑事案件
                        BuryingPointHelp.getInstance().onEvent(mActivity, "major_criminal_case_page", "major_criminal_case_page_see_more_lawyers_click");
                        break;
                    case 20://20 职务犯罪
                        BuryingPointHelp.getInstance().onEvent(mActivity, "duty_crime_page", "duty_crime_page_see_more_lawyers_click");
                        break;
                    case 16://16 普通刑事案件
                        BuryingPointHelp.getInstance().onEvent(mActivity, "ordinary_criminal_page", "ordinary_criminal_page_see_more_lawyers_click");
                        break;
                    case 3://3 融资借贷
                        BuryingPointHelp.getInstance().onEvent(mActivity, "financing_lending_page", "financing_lending_page_see_more_lawyers_click");
                        break;
                }

                bundle.clear();
                bundle.putInt(BundleTags.SOLUTION_TYPE_ID, solutionId);
                bundle.putString(BundleTags.SOLUTION_TYPE_NAME, solutionName);
                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);

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

    @Override
    protected void onResume() {
        super.onResume();

        switch (solutionId) {
            case 2://2 婚姻及家事
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "marriage_housework_page", getPair());
                break;
            case 3://3 融资借贷
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "financing_lending_page", getPair());
                break;
            case 4://4 劳动者权益保护
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "laborer_is_rights_protection_page", getPair());
                break;
            case 5://5  重大刑事案件
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "major_criminal_case_page", getPair());
                break;
            case 6://6 房屋及其他财物的买卖租赁
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "sale_and_lease_page", getPair());
                break;
            case 7://7 合同纠纷
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "contractual_dispute_page", getPair());
                break;
            case 8://8 基础项目建设
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "basic_project_construction_page", getPair());
                break;
            case 9://9 不动产销售与租赁
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "real_estate_page", getPair());
                break;
            case 10://10 债权债务
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "creditor_is_rights_and_debts_page", getPair());
                break;
            case 13://13 知识产权及商业保护
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "intellectual_property_page", getPair());
                break;
            case 14://14 人身伤害赔偿
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "personal_injury_compensation_page", getPair());
                break;
            case 16://16 普通刑事案件
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "ordinary_criminal_page", getPair());
                break;
            case 17://17 资本市场及股权融资
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "capital_market_page", getPair());
                break;
            case 18://18 公司治理及股东关系
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "corporate_governance_page", getPair());
                break;
            case 19:// 19 消费维权
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "consumer_rights_page", getPair());
                break;
            case 20://20 职务犯罪
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "duty_crime_page", getPair());
                break;
            case 22://22 对外投资并购、合伙及联营
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "foreign_investment_page", getPair());
                break;
            case 23://23 交通事故
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "traffic_accident_page", getPair());
                break;
            case 27://27 土地纠纷
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "land_development_transfer_page", getPair());
                break;
            case 28://28 人力资源及劳资关系
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "human_resources_page", getPair());
                break;
            case 29://29 财税专项
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "finance_and_taxation_page", getPair());
                break;
            case 30://30 行政许可\/处罚
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "administrative_license_penalty_page", getPair());
                break;
            case 31://31 保险及侵权赔偿
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "insurance_and_infringement_compensation_page", getPair());
                break;
            case 32://32 基金/信托/保理/融资租聘
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "fund_trust_insurance_financing_page", getPair());
                break;
            case 33://33 买卖销售合同纠纷
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "sale_and_sales_contract_dispute_page", getPair());
                break;
            case 34://34 环境保护
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "environmental_protection_page", getPair());
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        switch (solutionId) {
            case 2://2 婚姻及家事
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "marriage_housework_page", getPair());
                break;
            case 3://3 融资借贷
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "financing_lending_page", getPair());
                break;
            case 4://4 劳动者权益保护
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "laborer_is_rights_protection_page", getPair());
                break;
            case 5://5  重大刑事案件
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "major_criminal_case_page", getPair());
                break;
            case 6://6 房屋及其他财物的买卖租赁
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "sale_and_lease_page", getPair());
                break;
            case 7://7 合同纠纷
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "contractual_dispute_page", getPair());
                break;
            case 8://8 基础项目建设
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "basic_project_construction_page", getPair());
                break;
            case 9://9 不动产销售与租赁
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "real_estate_page", getPair());
                break;
            case 10://10 债权债务
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "creditor_is_rights_and_debts_page", getPair());
                break;
            case 13://13 知识产权及商业保护
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "intellectual_property_page", getPair());
                break;
            case 14://14 人身伤害赔偿
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "personal_injury_compensation_page", getPair());
                break;
            case 16://16 普通刑事案件
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "ordinary_criminal_page", getPair());
                break;
            case 17://17 资本市场及股权融资
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "capital_market_page", getPair());
                break;
            case 18://18 公司治理及股东关系
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "corporate_governance_page", getPair());
                break;
            case 19:// 19 消费维权
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "consumer_rights_page", getPair());
                break;
            case 20://20 职务犯罪
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "duty_crime_page", getPair());
                break;
            case 22://22 对外投资并购、合伙及联营
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "foreign_investment_page", getPair());
                break;
            case 23://23 交通事故
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "traffic_accident_page", getPair());
                break;
            case 27://27 土地纠纷
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "land_development_transfer_page", getPair());
                break;
            case 28://28 人力资源及劳资关系
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "human_resources_page", getPair());
                break;
            case 29://29 财税专项
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "finance_and_taxation_page", getPair());
                break;
            case 30://30 行政许可\/处罚
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "administrative_license_penalty_page", getPair());
                break;
            case 31://31 保险及侵权赔偿
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "insurance_and_infringement_compensation_page", getPair());
                break;
            case 32://32 基金/信托/保理/融资租聘
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "fund_trust_insurance_financing_page", getPair());
                break;
            case 33://33 买卖销售合同纠纷
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "sale_and_sales_contract_dispute_page", getPair());
                break;
            case 34://34 环境保护
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "environmental_protection_page", getPair());
                break;
        }
    }

    @Override
    public void showActivityDialog(List<ActivityEntity> entities) {
        if (entities == null || entities.size() == 0) return;
        /*
        targetUsers	目标用户（1所有用户，2首次打开，3用户组，4律师组）
        name	弹窗名字
        id	弹窗id
        url	关联跳转
        iconImage	关联图片
         */
        for (ActivityEntity item : mPresenter.getNeedActivityDialogEntities(entities)) {
            if (TextUtils.isEmpty(item.getUrl())) continue;
            Glide.with(mActivity)
                    .load(item.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            LogUtil.e("活动弹窗 图片加载失败");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //加载成功，resource为加载到的图片
                            //如果return true，则不会再回调Target的onResourceReady（也就是不再往下传递），imageView也就不会显示加载到的图片了。
                            LogUtil.e("活动弹窗 图片加载成功");
                            new ActivityDialog(mActivity)
                                    .setImgDrawable(resource)
                                    .setOnClickListener(() -> {
                                        //if(item.getTypeId() == 2) //消息通知
                                        if (item.getTypeId() == 1) {//h5
                                            bundle.clear();
                                            bundle.putString(BundleTags.URL, item.getUrl());
                                            bundle.putString(BundleTags.TITLE, item.getName());
                                            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                                        }
                                    }).show();
                            return false;
                        }
                    }).preload();
        }
    }
}
