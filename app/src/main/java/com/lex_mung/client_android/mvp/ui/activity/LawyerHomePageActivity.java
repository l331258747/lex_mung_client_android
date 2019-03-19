package com.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.app.ShareUtils;
import com.lex_mung.client_android.di.component.DaggerLawyerHomePageComponent;
import com.lex_mung.client_android.di.module.LawyerHomePageModule;
import com.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerTagsEntity;
import com.lex_mung.client_android.mvp.presenter.LawyerHomePagePresenter;
import com.lex_mung.client_android.mvp.ui.adapter.TagAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.lex_mung.client_android.mvp.ui.fragment.ActiveFragment;
import com.lex_mung.client_android.mvp.ui.fragment.IntegrityFragment;
import com.lex_mung.client_android.mvp.ui.fragment.LawsBusinessCardFragment;
import com.lex_mung.client_android.mvp.ui.fragment.LawsCaseFragment;
import com.lex_mung.client_android.mvp.ui.fragment.ServicePriceFragment;
import com.lex_mung.client_android.mvp.ui.fragment.SocialResourcesFragment;
import com.umeng.analytics.MobclickAgent;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class LawyerHomePageActivity extends BaseActivity<LawyerHomePagePresenter> implements LawyerHomePageContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.view_status)
    View viewStatus;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;
    @BindView(R.id.iv_icon)
    ImageView ivLike;
    @BindView(R.id.tv_text)
    TextView tvLikeText;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.ll_age)
    LinearLayout llAge;
    @BindView(R.id.recycler_view_tag)
    RecyclerView recyclerViewTag;
    @BindView(R.id.tv_field)
    TextView tvField;
    @BindView(R.id.tv_law_firms)
    TextView tvLawFirms;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.view)
    View view;

    private TagAdapter tagAdapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLawyerHomePageComponent
                .builder()
                .appComponent(appComponent)
                .lawyerHomePageModule(new LawyerHomePageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lawyer_home_page;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("w_y_shouye_zjzx_list");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("w_y_shouye_zjzx_list");
        MobclickAgent.onPause(mActivity);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//设置状态栏文字颜色为白色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        AppUtils.statsInScreen(this);//全屏
        StatusBarUtil.setTranslucent(mActivity);

        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) viewStatus.getLayoutParams();
        layoutParams1.height = DeviceUtils.getStatusBarHeight(mActivity);
        viewStatus.setLayoutParams(layoutParams1);

        CollapsingToolbarLayout.LayoutParams layoutParams2 = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams2.topMargin = DeviceUtils.getStatusBarHeight(mActivity);
        toolbar.setLayoutParams(layoutParams2);

        if (bundleIntent != null) {
            id = bundleIntent.getInt(BundleTags.ID);
            mPresenter.getLawsHomePagerBase(id);
        }
        initAdapter();
        initRecyclerView();

        appBar.addOnOffsetChangedListener((appBarLayout, i) -> {
            if (toolbar == null) return;
            if (i == 0) {//展开
                toolbar.setVisibility(View.INVISIBLE);
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {//折叠
                toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.INVISIBLE);
            }
        });
        handler.postDelayed(runnable, 200);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (AppUtils.getVirtualBarHeigh(mActivity) != 0) {//存在虚拟按键
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (layoutParams.height == 0) {
                    layoutParams.height = AppUtils.getVirtualBarHeigh(mActivity);
                    view.setLayoutParams(layoutParams);
                }
            } else {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (layoutParams.height != 0) {
                    layoutParams.height = 0;
                    view.setLayoutParams(layoutParams);
                }
            }
            handler.postDelayed(this, 200);
        }
    };

    private void initAdapter() {
        tagAdapter = new TagAdapter(mImageLoader);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerViewTag, new GridLayoutManager(mActivity, 3));
        recyclerViewTag.setAdapter(tagAdapter);
    }

    @Override
    public void initViewPager(LawsHomePagerBaseEntity entity) {
        try {
            fragments.clear();
            titles.clear();
            fragments.add(LawsBusinessCardFragment.newInstance(entity));
            fragments.add(ServicePriceFragment.newInstance(entity));
            titles.add(getString(R.string.text_base_info));
            titles.add(getString(R.string.text_service_price));
            if (entity.getEvaluationList() != null
                    && entity.getEvaluationList().size() > 3) {
                titles.add(entity.getEvaluationList().get(0).getText());
                titles.add(entity.getEvaluationList().get(1).getText());
                titles.add(entity.getEvaluationList().get(2).getText());
                titles.add(entity.getEvaluationList().get(3).getText());
                for (int i = 0; i < entity.getEvaluationList().size(); i++) {
                    switch (entity.getEvaluationList().get(i).getCode()) {
                        case "aspect_vitality"://活跃度
                            fragments.add(ActiveFragment.newInstance(entity));
                            break;
                        case "aspect_reliability"://诚信度
                            fragments.add(IntegrityFragment.newInstance(entity));
                            break;
                        case "aspect_social"://社会资源
                            fragments.add(SocialResourcesFragment.newInstance(entity));
                            break;
                        case "aspect_litigation"://诉讼经验
                            fragments.add(LawsCaseFragment.newInstance(entity.getMemberId(), entity.getInstitutionName()));
                            break;
                    }
                }
            }

            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments, titles));
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(1);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void setField(String field) {
        tvField.setVisibility(View.VISIBLE);
        tvField.setText(field);
    }

    @Override
    public void hideFieldLayout() {
        tvField.setVisibility(View.GONE);
    }

    @Override
    public void setTagsAdapter(List<LawyerTagsEntity> lawyerTags) {
        recyclerViewTag.setVisibility(View.VISIBLE);
        tagAdapter.setNewData(lawyerTags);
    }

    @Override
    public void hideTagsAdapter() {
        recyclerViewTag.setVisibility(View.GONE);
    }

    @Override
    public void setAge(String age) {
        llAge.setVisibility(View.VISIBLE);
        tvAge.setText(age);
    }

    @Override
    public void hideAgeLayout() {
        llAge.setVisibility(View.GONE);
    }

    @Override
    public void setSex(int bg, int color, int icon) {
        llAge.setVisibility(View.VISIBLE);
        llAge.setBackgroundResource(bg);
        tvAge.setTextColor(color);
        ivSex.setImageResource(icon);
    }

    @Override
    public void hideSexIcon() {
        ivSex.setVisibility(View.GONE);
    }

    @Override
    public void showSexIcon() {
        ivSex.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLikeLayout() {
        rlClick.setVisibility(View.GONE);
    }

    @Override
    public void setLikeLayout(int icon, int color, String string) {
        rlClick.setVisibility(View.VISIBLE);
        ivLike.setImageResource(icon);
        tvLikeText.setTextColor(color);
        tvLikeText.setText(string);
    }

    @Override
    public void setName(String memberName) {
        tvName.setText(memberName);
        tvTitle.setText(memberName);
    }

    @Override
    public void setPositionNameAndPractice(String format) {
        tvPost.setText(format);
    }

    @Override
    public void setAvatar(String iconImage) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(iconImage)
                        .imageView(ivAvatar)
                        .isCircle(true)
                        .build());
    }

    @Override
    public void setAvatar(int icon) {
        ivAvatar.setImageResource(icon);
    }

    @Override
    public void setTopBg(String backgroundImage) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(backgroundImage)
                        .imageView(ivBg)
                        .build());
    }

    @Override
    public void setTopBg(int icon) {
        ivBg.setImageResource(icon);
    }

    @Override
    public void setRegionAndInstitutionName(String text) {
        tvLawFirms.setVisibility(View.VISIBLE);
        tvLawFirms.setText(text);
    }

    @OnClick({R.id.rl_click
            , R.id.iv_back_toolbar
            , R.id.iv_share
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.rl_click:
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {//已登录
                    if ("关注TA".contentEquals(tvLikeText.getText())) {
                        mPresenter.follow(id);
                    } else {
                        mPresenter.unFollow(id);
                    }
                } else {//未登录
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.iv_back_toolbar:
                killMyself();
                break;
            case R.id.iv_share:
                ShareUtils.shareUrl(mActivity, viewBottom, "", "", "", "");
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
