package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.HomeSolutionModule;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeSolutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeSolutionComponent;
import cn.lex_mung.client_android.mvp.contract.HomeSolutionContract;
import cn.lex_mung.client_android.mvp.presenter.HomeSolutionPresenter;

import cn.lex_mung.client_android.R;

public class HomeSolutionActivity extends BaseActivity<HomeSolutionPresenter> implements HomeSolutionContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view_1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view_2)
    RecyclerView recyclerView2;
    @BindView(R.id.recycler_view_3)
    RecyclerView recyclerView3;

    HomeSolutionAdapter adapter1;
    HomeSolutionAdapter adapter2;
    HomeSolutionAdapter adapter3;
    List<CommonSolutionEntity> person = new ArrayList<>();
    List<CommonSolutionEntity> criminal = new ArrayList<>();
    List<CommonSolutionEntity> commercial = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeSolutionComponent
                .builder()
                .appComponent(appComponent)
                .homeSolutionModule(new HomeSolutionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_solution;
    }

    @Override
    public void initAdapter(List<CommonSolutionEntity> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getParentType().equals("个人类"))
                person.add(datas.get(i));
            if (datas.get(i).getParentType().equals("商事类"))
                commercial.add(datas.get(i));
            if (datas.get(i).getParentType().equals("刑事类"))
                criminal.add(datas.get(i));

        }
        setDataView(adapter1, recyclerView1, person);
        setDataView(adapter2, recyclerView2, commercial);
        setDataView(adapter3, recyclerView3, criminal);
    }

    public void setDataView(HomeSolutionAdapter itemAdapter, RecyclerView recyclerView, List<CommonSolutionEntity> datas) {
        itemAdapter = new HomeSolutionAdapter(mImageLoader);
        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 4));
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        itemAdapter.setNewData(datas);

        itemAdapter.setOnItemClickListener((adapter1, view, position)->{
            if (isFastClick()) return;
            CommonSolutionEntity entity = (CommonSolutionEntity) adapter1.getItem(position);
            if (entity == null) return;

            switch (entity.getId()){
                case 2://2 婚姻及家事
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_marriage_housework_click");
                    break;
                case 3://3 融资借贷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_financing_lending_click");
                    break;
                case 4://4 劳动者权益保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_laborer_is_rights_protection_click");
                    break;
                case 5://5  重大刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_major_criminal_case_click");
                    break;
                case 6://6 房屋及其他财物的买卖租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_laborer_is_sale_and_lease_click");
                    break;
                case 7://7 合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_contractual_dispute_click");
                    break;
                case 8://8 基础项目建设
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_basic_project_construction_click");
                    break;
                case 9://9 不动产销售与租赁
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_real_estate_click");
                    break;
                case 10://10 债权债务
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_creditor_is_rights_and_debts_click");
                    break;
                case 13://13 知识产权及商业保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_intellectual_property_click");
                    break;
                case 14://14 人身伤害赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_personal_injury_compensation_click");
                    break;
                case 16://16 普通刑事案件
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_ordinary_criminal_click");
                    break;
                case 17://17 资本市场及股权融资
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_capital_market_click");
                    break;
                case 18://18 公司治理及股东关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_corporate_governance_click");
                    break;
                case 19:// 19 消费维权
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_consumer_rights_click");
                    break;
                case 20://20 职务犯罪
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_duty_crime_click");
                    break;
                case 22://22 对外投资并购、合伙及联营
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_foreign_investment_click");
                    break;
                case 23://23 交通事故
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_traffic_accident_click");
                    break;
                case 27://27 土地纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_land_development_transfer_click");
                    break;
                case 28://28 人力资源及劳资关系
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_human_resources_click");
                    break;
                case 29://29 财税专项
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_finance_and_taxation_click");
                    break;
                case 30://30 行政许可\/处罚
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_administrative_license_penalty_click");
                    break;
                case 31://31 保险及侵权赔偿
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_insurance_and_infringement_compensation_click");
                    break;
                case 32://32 基金/信托/保理/融资租聘
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_fund_trust_insurance_financing_click");
                    break;
                case 33://33 买卖销售合同纠纷
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_sale_and_sales_contract_dispute_click");
                    break;
                case 34://34 环境保护
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_major_criminal_case_click");
                    break;
            }


            bundle.clear();
            bundle.putInt(BundleTags.SOLUTION_TYPE_ID,entity.getId());
            bundle.putString(BundleTags.SOLUTION_TYPE_NAME,entity.getAlias());
            bundle.putBoolean(BundleTags.IS_CRIMINAL,TextUtils.equals("刑事类",entity.getParentType()));
            launchActivity(new Intent(mActivity, SolutionDetailActivity.class),bundle);
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getDatas();
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
