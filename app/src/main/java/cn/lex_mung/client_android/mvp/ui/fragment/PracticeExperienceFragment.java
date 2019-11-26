package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerPracticeExperienceComponent;
import cn.lex_mung.client_android.di.module.PracticeExperienceModule;
import cn.lex_mung.client_android.mvp.contract.PracticeExperienceContract;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.presenter.PracticeExperiencePresenter;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageCaseAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView2;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class PracticeExperienceFragment extends BaseFragment<PracticeExperiencePresenter> implements PracticeExperienceContract.View {

    @BindView(R.id.iv_service_firm)
    ImageView ivServiceFirm;
    @BindView(R.id.tv_service_firm_text)
    TextView tvServiceFirmText;
    @BindView(R.id.tv_service_firm)
    TextView tvServiceFirm;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.iv_professional_skill)
    ImageView ivProfessionalSkill;
    @BindView(R.id.tv_professional_skill_text)
    TextView tvProfessionalSkillText;
    @BindView(R.id.tv_professional_skills_text)
    TextView tvProfessionalSkillsText;
    @BindView(R.id.tv_professional_skills)
    TextView tvProfessionalSkills;
    @BindView(R.id.tv_industry_text)
    TextView tvIndustryText;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_language_text)
    TextView tvLanguageText;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_other_text)
    TextView tvOtherText;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.iv_resort_court)
    ImageView ivResortCourt;
    @BindView(R.id.tv_resort_court_text)
    TextView tvResortCourtText;
    @BindView(R.id.tv_resort_court)
    TextView tvResortCourt;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.iv_resort_p)
    ImageView ivResortP;
    @BindView(R.id.tv_resort_p_text)
    TextView tvResortPText;
    @BindView(R.id.tv_resort_p)
    TextView tvResortP;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.iv_laws_case)
    View ivLawsCase;
    @BindView(R.id.tv_laws_case_text)
    View tvLawsCaseText;
    @BindView(R.id.recycler_view_laws_case)
    RecyclerView recyclerViewLawsCase;
    @BindView(R.id.no_layout)
    LinearLayout noLayout;
    @BindView(R.id.emptyView)
    EmptyView2 emptyView;
    @BindView(R.id.cl_parent)
    ConstraintLayout clParent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    PersonalHomePageCaseAdapter caseAdapter;

    public static PracticeExperienceFragment newInstance(LawsHomePagerBaseEntity entity) {
        PracticeExperienceFragment fragment = new PracticeExperienceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPracticeExperienceComponent
                .builder()
                .appComponent(appComponent)
                .practiceExperienceModule(new PracticeExperienceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice_experience, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPresenter.setEntity((LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY));
        }

        initRecyclerView();

        emptyView.getBtn().setOnClickListener(v -> {
            bundle.clear();
            bundle.putInt(BundleTags.TYPE, 1);
            launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
            emptyView.setVisibility(View.VISIBLE);
            clParent.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            clParent.setVisibility(View.VISIBLE);
        }
    }

    public void initRecyclerView() {

        caseAdapter = new PersonalHomePageCaseAdapter();
        caseAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            CaseListEntity bean = caseAdapter.getItem(position);
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putString(BundleTags.URL, bean.getUrl());
            bundle.putString(BundleTags.TITLE, bean.getTitle());
            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
        });

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableOverScrollBounce(true);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                        mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                        mPresenter.getCaseList(true);
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        AppUtils.configRecyclerView(recyclerViewLawsCase, new LinearLayoutManager(mActivity));
        recyclerViewLawsCase.setNestedScrollingEnabled(false);
        recyclerViewLawsCase.setAdapter(caseAdapter);
    }

    public void setAdapter(boolean isAdd, List<CaseListEntity> entities) {
        if (isAdd) {
            caseAdapter.addData(entities);
            smartRefreshLayout.finishLoadMore();
        } else {
            caseAdapter.setNewData(entities);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (caseAdapter.getItemCount() == 0) {
                hideCaseLayout();
            } else {
                showCaseLayout();
            }
        }
    }

    @Override
    public void showCaseLayout() {
        view4.setVisibility(View.VISIBLE);
        ivLawsCase.setVisibility(View.VISIBLE);
        tvLawsCaseText.setVisibility(View.VISIBLE);
        recyclerViewLawsCase.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCaseLayout() {
        view4.setVisibility(View.GONE);
        ivLawsCase.setVisibility(View.GONE);
        tvLawsCaseText.setVisibility(View.GONE);
        recyclerViewLawsCase.setVisibility(View.GONE);
    }

    @Override
    public void setServiceFirm(String toString) {
        tvServiceFirm.setText(toString);
    }

    @Override
    public void hideServiceFirmLayout() {
        ivServiceFirm.setVisibility(View.GONE);
        tvServiceFirmText.setVisibility(View.GONE);
        tvServiceFirm.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
    }

    @Override
    public void setProfessionalSkills(String toString) {
        tvProfessionalSkills.setText(toString);
    }

    @Override
    public void hideProfessionalSkillsLayout() {
        tvProfessionalSkillsText.setVisibility(View.GONE);
        tvProfessionalSkills.setVisibility(View.GONE);
    }

    @Override
    public void setIndustry(String toString) {
        tvIndustry.setText(toString);
    }

    @Override
    public void hideIndustryLayout() {
        tvIndustryText.setVisibility(View.GONE);
        tvIndustry.setVisibility(View.GONE);
    }

    @Override
    public void setLanguage(String toString) {
        tvLanguage.setText(toString);
    }

    @Override
    public void hideLanguageLayout() {
        tvLanguageText.setVisibility(View.GONE);
        tvLanguage.setVisibility(View.GONE);
    }

    @Override
    public void setOther(String toString) {
        tvOther.setText(toString);
    }

    @Override
    public void hideOtherLayout() {
        tvOtherText.setVisibility(View.GONE);
        tvOther.setVisibility(View.GONE);
    }

    @Override
    public void setCourt(String toString) {
        tvResortCourt.setText(toString);
    }

    @Override
    public void hideCourtLayout() {
        ivResortCourt.setVisibility(View.GONE);
        tvResortCourtText.setVisibility(View.GONE);
        tvResortCourt.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    @Override
    public void setP(String toString) {
        tvResortP.setText(toString);
    }

    @Override
    public void hidePLayout() {
        ivResortP.setVisibility(View.GONE);
        tvResortPText.setVisibility(View.GONE);
        tvResortP.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    @Override
    public void showNoDataLayout1() {
        ivProfessionalSkill.setVisibility(View.GONE);
        tvProfessionalSkillText.setVisibility(View.GONE);
        tvProfessionalSkillsText.setVisibility(View.GONE);
        tvProfessionalSkills.setVisibility(View.GONE);
        tvIndustryText.setVisibility(View.GONE);
        tvIndustry.setVisibility(View.GONE);
        tvLanguageText.setVisibility(View.GONE);
        tvLanguage.setVisibility(View.GONE);
        tvOtherText.setVisibility(View.GONE);
        tvOther.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
    }

    @Override
    public void showNoDataLayout() {
        noLayout.setVisibility(View.VISIBLE);
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
