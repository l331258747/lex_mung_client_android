package com.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import com.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import com.lex_mung.client_android.mvp.ui.activity.WebActivity;
import com.lex_mung.client_android.mvp.ui.adapter.OrgTagAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import com.lex_mung.client_android.di.component.DaggerIntegrityComponent;
import com.lex_mung.client_android.di.module.IntegrityModule;
import com.lex_mung.client_android.mvp.contract.IntegrityContract;
import com.lex_mung.client_android.mvp.presenter.IntegrityPresenter;

import com.lex_mung.client_android.R;

import java.util.List;

import javax.inject.Inject;

public class IntegrityFragment extends BaseFragment<IntegrityPresenter> implements IntegrityContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view_lawyer_team)
    RecyclerView recyclerViewLawyerTeam;
    @BindView(R.id.tv_social_function)
    TextView tvSocialFunction;
    @BindView(R.id.tv_personal_honor)
    TextView tvPersonalHonor;
    @BindView(R.id.tv_social_function_text)
    TextView tvSocialFunctionText;
    @BindView(R.id.view_line_1)
    View viewLine1;
    @BindView(R.id.tv_personal_honor_text)
    TextView tvPersonalHonorText;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.tv_join_lawyer_team_text)
    TextView tvJoinLawyerTeamText;
    @BindView(R.id.view_line_3)
    View viewLine3;
    @BindView(R.id.no_layout)
    View noLayout;

    private OrgTagAdapter orgTagAdapter2;//加入的律师团

    public static IntegrityFragment newInstance(LawsHomePagerBaseEntity entity) {
        IntegrityFragment fragment = new IntegrityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIntegrityComponent
                .builder()
                .appComponent(appComponent)
                .integrityModule(new IntegrityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_integrity, container, false);
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
        orgTagAdapter2 = new OrgTagAdapter(mImageLoader);

        orgTagAdapter2.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            OrgTagsEntity entity = orgTagAdapter2.getItem(position);
            if (entity == null) return;
            UserInfoDetailsEntity loginUserInfoEntity = new Gson().fromJson(DataHelper.getStringSF(mActivity, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
            bundle.clear();
            bundle.putString(BundleTags.URL, entity.getLink() + "&memberId=" + loginUserInfoEntity.getMemberId());
            bundle.putString(BundleTags.TITLE, entity.getTagName());
            bundle.putBoolean(BundleTags.IS_SHARE, false);
            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerViewLawyerTeam, new LinearLayoutManager(mActivity));

        recyclerViewLawyerTeam.setAdapter(orgTagAdapter2);
    }

    @Override
    public void setOrgAdapter2(List<OrgTagsEntity> orgTags) {
        orgTagAdapter2.setNewData(orgTags);
    }

    @Override
    public void setSocialFunction(String s) {
        tvSocialFunction.setText(s);
    }

    @Override
    public void setPersonalHonor(String s) {
        tvPersonalHonor.setText(s);
    }



    @Override
    public void hideOrgLayout2() {
        tvJoinLawyerTeamText.setVisibility(View.GONE);
        viewLine3.setVisibility(View.GONE);
        recyclerViewLawyerTeam.setVisibility(View.GONE);
    }

    @Override
    public void hideSocialFunctionLayout() {
        tvSocialFunctionText.setVisibility(View.GONE);
        tvSocialFunction.setVisibility(View.GONE);
        viewLine1.setVisibility(View.GONE);
    }

    @Override
    public void hidePersonalHonorLayout() {
        viewLine1.setVisibility(View.GONE);
        tvPersonalHonorText.setVisibility(View.GONE);
        tvPersonalHonor.setVisibility(View.GONE);
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
