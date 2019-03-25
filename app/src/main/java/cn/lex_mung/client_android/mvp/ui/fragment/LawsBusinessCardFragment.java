package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.EducationBackgroundEntity;
import cn.lex_mung.client_android.mvp.model.entity.JobSkillsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import cn.lex_mung.client_android.mvp.model.entity.WorkexpEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrgTagAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.PHPSkillsAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.EducationBackgroundAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.WorkExperienceAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerLawsBusinessCardComponent;
import cn.lex_mung.client_android.di.module.LawsBusinessCardModule;
import cn.lex_mung.client_android.mvp.contract.LawsBusinessCardContract;
import cn.lex_mung.client_android.mvp.presenter.LawsBusinessCardPresenter;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.widget.RadarView;

import java.util.List;

import javax.inject.Inject;

public class LawsBusinessCardFragment extends BaseFragment<LawsBusinessCardPresenter> implements LawsBusinessCardContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_skills_text)
    TextView tvSkillsText;
    @BindView(R.id.recycler_view_skills)
    RecyclerView recyclerViewSkills;
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
    @BindView(R.id.tv_qualification)
    TextView tvQualification;
    @BindView(R.id.tv_personal_resume)
    TextView tvPersonalResume;
    @BindView(R.id.radar_view)
    RadarView radarView;
    @BindView(R.id.group_field)
    Group groupField;
    @BindView(R.id.recycler_view_work)
    RecyclerView recyclerViewWork;
    @BindView(R.id.recycler_view_education)
    RecyclerView recyclerViewEducation;
    @BindView(R.id.view_line_1)
    View viewLine1;
    @BindView(R.id.tv_industry_text)
    TextView tvIndustryText;
    @BindView(R.id.view_line_2)
    View viewLine2;
    @BindView(R.id.view_line_3)
    View viewLine3;
    @BindView(R.id.view_line_4)
    View viewLine4;
    @BindView(R.id.view_line_6)
    View viewLine6;
    @BindView(R.id.tv_qualification_text)
    TextView tvQualificationText;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.tv_work_text)
    TextView tvWorkText;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.tv_education_text)
    TextView tvEducationText;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.tv_personal_resume_text)
    TextView tvPersonalResumeText;
    @BindView(R.id.view_personal_resume_bottom)
    View viewPersonalResumeBottom;
    @BindView(R.id.no_layout)
    View noLayout;

    @BindView(R.id.view_credit_service)
    View viewCreditService;
    @BindView(R.id.tv_credit_service_text)
    TextView tvCreditServiceText;
    @BindView(R.id.view_line_7)
    View viewLine7;
    @BindView(R.id.recycler_view_credit)
    RecyclerView recyclerViewCredit;

    private OrgTagAdapter orgTagAdapter;//增信服务
    private PHPSkillsAdapter skillsAdapter;
    private EducationBackgroundAdapter educationBackgroundAdapter;//教育背景Adapter
    private WorkExperienceAdapter workExperienceAdapter;//工作经验Adapter


    public static LawsBusinessCardFragment newInstance(LawsHomePagerBaseEntity entity) {
        LawsBusinessCardFragment fragment = new LawsBusinessCardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawsBusinessCardComponent
                .builder()
                .appComponent(appComponent)
                .lawsBusinessCardModule(new LawsBusinessCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laws_business_card, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            initAdapter();
            initRecyclerView();
            mPresenter.setEntity((LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY));
        }
    }

    private void initAdapter() {
        orgTagAdapter = new OrgTagAdapter(mImageLoader);
        skillsAdapter = new PHPSkillsAdapter();
        educationBackgroundAdapter = new EducationBackgroundAdapter(3);
        workExperienceAdapter = new WorkExperienceAdapter(3);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerViewSkills, new GridLayoutManager(mActivity, 3));
        AppUtils.configRecyclerView(recyclerViewWork, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerViewEducation, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerViewCredit, new LinearLayoutManager(mActivity));

        recyclerViewSkills.setAdapter(skillsAdapter);
        recyclerViewWork.setAdapter(workExperienceAdapter);
        recyclerViewEducation.setAdapter(educationBackgroundAdapter);
        recyclerViewCredit.setAdapter(orgTagAdapter);
    }

    @Override
    public void setOrgAdapter(List<OrgTagsEntity> depositTags) {
        orgTagAdapter.setNewData(depositTags);
    }

    @Override
    public void hideOrgLayout() {
        tvCreditServiceText.setVisibility(View.GONE);
        viewLine7.setVisibility(View.GONE);
        recyclerViewCredit.setVisibility(View.GONE);
        viewCreditService.setVisibility(View.GONE);
    }

    @Override
    public void setSkillAdapter(String skillName, List<JobSkillsEntity> children) {
        tvSkillsText.setText(skillName);
        skillsAdapter.setNewData(children);
    }

    @Override
    public void setFieldData(List<RadarView.RadarData> list, List<RadarView.RadarData> meanList) {
        radarView.setDataList(list, meanList);
        groupField.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDescription(String memberDescription) {
        tvPersonalResume.setText(memberDescription);
    }

    @Override
    public void setLanguage(String skillName, StringBuilder language) {
        tvLanguageText.setText(skillName);
        tvLanguage.setText(language);
    }

    @Override
    public void setOther(String skillName, StringBuilder other) {
        tvOtherText.setText(skillName);
        tvOther.setText(other);
    }

    @Override
    public void setIndustryAdapter(String industry) {
        if (!TextUtils.isEmpty(industry)) {
            tvIndustry.setText(industry);
        }
    }

    @Override
    public void setQualification(StringBuilder qualification) {
        tvQualification.setText(qualification);
    }

    @Override
    public void setWorkAdapter(List<WorkexpEntity> workExp) {
        workExperienceAdapter.setNewData(workExp);
    }


    @Override
    public void setEducationAdapter(List<EducationBackgroundEntity> education) {
        educationBackgroundAdapter.setNewData(education);
    }

    @Override
    public void hideFieldLayout() {
        groupField.setVisibility(View.GONE);
    }

    @Override
    public void hideSkillLayout() {
        tvSkillsText.setVisibility(View.GONE);
        recyclerViewSkills.setVisibility(View.GONE);
        viewLine1.setVisibility(View.GONE);
    }

    @Override
    public void hideOtherLayout() {
        tvOtherText.setVisibility(View.GONE);
        tvOther.setVisibility(View.GONE);
        viewLine4.setVisibility(View.GONE);

    }

    @Override
    public void hideLanguageLayout() {
        tvLanguageText.setVisibility(View.GONE);
        tvLanguage.setVisibility(View.GONE);
        viewLine3.setVisibility(View.GONE);
    }

    @Override
    public void hideIndustryLayout() {
        tvIndustryText.setVisibility(View.GONE);
        tvIndustry.setVisibility(View.GONE);
        viewLine2.setVisibility(View.GONE);
    }

    @Override
    public void hideQualificationLayout() {
        tvQualificationText.setVisibility(View.GONE);
        tvQualification.setVisibility(View.GONE);
    }

    @Override
    public void hideWorkLayout() {
        tvWorkText.setVisibility(View.GONE);
        recyclerViewWork.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    @Override
    public void hideEducationLayout() {
        tvEducationText.setVisibility(View.GONE);
        recyclerViewEducation.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    @Override
    public void hideDescriptionLayout() {
        tvPersonalResumeText.setVisibility(View.GONE);
        tvPersonalResume.setVisibility(View.GONE);
        viewLine6.setVisibility(View.GONE);
        viewPersonalResumeBottom.setVisibility(View.GONE);
    }

    @Override
    public void showNoDataLayout() {
        noLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoDataLayout1() {
        view2.setVisibility(View.GONE);
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
