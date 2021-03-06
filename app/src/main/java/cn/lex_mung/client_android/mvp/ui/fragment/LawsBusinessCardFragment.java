package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.OrganizationLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageEducationAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageWorkAdapter;
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
import me.zl.mvp.utils.StringUtils;

public class LawsBusinessCardFragment extends BaseFragment<LawsBusinessCardPresenter> implements LawsBusinessCardContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_personal_resume)
    ImageView ivPersonalResume;
    @BindView(R.id.tv_personal_resume_text)
    TextView tvPersonalResumeText;
    @BindView(R.id.tv_personal_resume)
    TextView tvPersonalResume;
    @BindView(R.id.iv_personal_honor)
    ImageView ivPersonalHonor;
    @BindView(R.id.tv_personal_honor_text)
    TextView tvPersonalHonorText;
    @BindView(R.id.tv_personal_honor)
    TextView tvPersonalHonor;
    @BindView(R.id.iv_join_lawyer_team)
    ImageView ivJoinLawyerTeam;
    @BindView(R.id.tv_join_lawyer_team_text)
    TextView tvJoinLawyerTeamText;
//    @BindView(R.id.tv_join_lawyer_team)
//    TextView tvJoinLawyerTeam;
    @BindView(R.id.ll_join_lawyer_team)
    LinearLayout llJoinLawyerTeam;
    @BindView(R.id.iv_work)
    ImageView ivWork;
    @BindView(R.id.tv_work_text)
    TextView tvWorkText;
    @BindView(R.id.recycler_view_work)
    RecyclerView recyclerViewWork;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.tv_education_text)
    TextView tvEducationText;
    @BindView(R.id.recycler_view_education)
    RecyclerView recyclerViewEducation;
    @BindView(R.id.no_layout)
    LinearLayout noLayout;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.view_4)
    View view4;

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
            mPresenter.setEntity((LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY));
        }
    }

    @Override
    public void initRecyclerView(PersonalHomePageWorkAdapter workAdapter, PersonalHomePageEducationAdapter educationAdapter) {
        AppUtils.configRecyclerView(recyclerViewWork, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerViewEducation, new LinearLayoutManager(mActivity));
        recyclerViewWork.setAdapter(workAdapter);
        recyclerViewEducation.setAdapter(educationAdapter);
    }

    @Override
    public void hideDescriptionLayout() {
        tvPersonalResumeText.setVisibility(View.GONE);
        tvPersonalResume.setVisibility(View.GONE);
        ivPersonalResume.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
    }

    @Override
    public void hidePersonalHonorLayout() {
        tvPersonalHonorText.setVisibility(View.GONE);
        tvPersonalHonor.setVisibility(View.GONE);
        ivPersonalHonor.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
    }

    @Override
    public void hideJoinLawyerTeamLayout() {
        tvJoinLawyerTeamText.setVisibility(View.GONE);
//        tvJoinLawyerTeam.setVisibility(View.GONE);
        llJoinLawyerTeam.setVisibility(View.GONE);
        ivJoinLawyerTeam.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    @Override
    public void hideWorkLayout() {
        ivWork.setVisibility(View.GONE);
        tvWorkText.setVisibility(View.GONE);
        recyclerViewWork.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    @Override
    public void hideEducationLayout() {
        ivEducation.setVisibility(View.GONE);
        tvEducationText.setVisibility(View.GONE);
        recyclerViewEducation.setVisibility(View.GONE);
    }

    @Override
    public void setDescription(String memberDescription) {
        tvPersonalResume.setText(memberDescription);
    }

    @Override
    public void setPersonalHonor(String toString) {
        tvPersonalHonor.setText(toString);
    }


//    @Override
//    public void setJoinLawyerTeam(String toString) {
//        tvJoinLawyerTeam.setText(toString);
//    }

    @Override
    public void setJoinLawyerTeam(List<OrgTagsEntity> orgTagsEntities) {
        for (int i = 0; i < orgTagsEntities.size(); i++) {
            OrgTagsEntity entity = orgTagsEntities.get(i);

            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_orgtag, null);
            TextView tv = view.findViewById(R.id.tv_name);
            ImageView iv = view.findViewById(R.id.iv_img);
            ConstraintLayout clParent = view.findViewById(R.id.cl_parent);
            tv.setText(entity.getTagName());
            if(!TextUtils.isEmpty(entity.getImage())){
                iv.setVisibility(View.VISIBLE);
                mImageLoader.loadImage(getContext()
                        , ImageConfigImpl
                                .builder()
                                .isCircle(true)
                                .url(entity.getImage())
                                .imageView(iv)
                                .build());
            }else{
                iv.setVisibility(View.INVISIBLE);
            }
            clParent.setOnClickListener(v -> {
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getLink());
                bundle.putString(BundleTags.TITLE, "组织详情");
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            });
            llJoinLawyerTeam.addView(view);
        }
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
