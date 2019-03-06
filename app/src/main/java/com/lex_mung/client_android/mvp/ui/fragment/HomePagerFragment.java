package com.lex_mung.client_android.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.component.DaggerHomePagerComponent;
import com.lex_mung.client_android.di.module.HomePagerModule;
import com.lex_mung.client_android.mvp.contract.HomePagerContract;
import com.lex_mung.client_android.mvp.model.entity.RequirementTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.presenter.HomePagerPresenter;
import com.lex_mung.client_android.mvp.ui.activity.FastConsultActivity;
import com.lex_mung.client_android.mvp.ui.activity.FreeConsultActivity;
import com.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;
import com.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import com.lex_mung.client_android.mvp.ui.activity.MainActivity;
import com.lex_mung.client_android.mvp.ui.activity.WebActivity;
import com.lex_mung.client_android.mvp.ui.adapter.HomePageRequirementTypeAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class HomePagerFragment extends BaseFragment<HomePagerPresenter> implements HomePagerContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_message_count)
    TextView tvMessageCount;
    @BindView(R.id.tv_free_consult_1)
    TextView tvFreeConsult1;
    @BindView(R.id.tv_fast_consult_1)
    TextView tvFastConsult1;
    @BindView(R.id.tv_experts_consult_1)
    TextView tvExpertsConsult1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private HomePageRequirementTypeAdapter adapter;

    public static HomePagerFragment newInstance() {
        return new HomePagerFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomePagerComponent
                .builder()
                .appComponent(appComponent)
                .homePagerModule(new HomePagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_pager, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String freeConsult = "<font color=\"#1EC88C\">"
                + getString(R.string.text_free)
                + "</font>"
                + mContext.getString(R.string.text_free_consult_1);
        String fastConsult = "<font color=\"#1EC88C\">"
                + getString(R.string.text_fast_consult_1)
                + "</font>"
                + mContext.getString(R.string.text_fast_consult_2);
        String expertsConsult = "<font color=\"#1EC88C\">"
                + getString(R.string.text_experts_consult_1)
                + "</font>"
                + mContext.getString(R.string.text_experts_consult_2);
        tvFreeConsult1.setText(Html.fromHtml(freeConsult));
        tvFastConsult1.setText(Html.fromHtml(fastConsult));
        tvExpertsConsult1.setText(Html.fromHtml(expertsConsult));

        initAdapter();
        initRecyclerView();
        initBanner();
        mPresenter.getBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private void initAdapter() {
        adapter = new HomePageRequirementTypeAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            RequirementTypeEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.getJumptype() == 1) {
            } else {
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getJumpUrl());
                bundle.putBoolean(BundleTags.IS_SHARE, false);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            }
        });
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 4));
        recyclerView.setAdapter(adapter);
    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                mImageLoader.loadImage(context
                        , ImageConfigImpl
                                .builder()
                                .url(path.toString())
                                .imageView(imageView)
                                .build());
            }
        });
        banner.setOnBannerListener(position -> {
            try {
                if (isFastClick()) return;
                BannerEntity.ListBean bean = mPresenter.getBannerList().get(position);
                bundle.clear();
                bundle.putString(BundleTags.URL, bean.getLinkValue());
                bundle.putString(BundleTags.TITLE, bean.getTitle());
                bundle.putString(BundleTags.DES, "");
                bundle.putString(BundleTags.IMAGE, bean.getImage());
                bundle.putBoolean(BundleTags.IS_SHARE, true);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            } catch (Exception ignored) {
            }
        });
        banner.setPageMargin(AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_20)));
        banner.setOffscreenPageLimit(3);
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;

            @Override
            public void transformPage(@NonNull View view, float position) {
                if (position >= -1 || position <= 1) {
                    final float height = view.getHeight();
                    final float width = view.getWidth();
                    final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    final float vertMargin = height * (1 - scaleFactor) / 2;
                    final float horzMargin = width * (1 - scaleFactor) / 2;
                    view.setPivotY(0.5f * height);
                    view.setPivotX(0.5f * width);
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                }
            }
        });
        banner.setDelayTime(3000);
    }

    @OnClick({
            R.id.tv_search
            , R.id.iv_message
            , R.id.view_free_consult
            , R.id.view_fast_consult
            , R.id.view_experts_consult
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                ((MainActivity) mActivity).switchPage(1);
                break;
            case R.id.iv_message:
                if (mPresenter.isLogin()) {
                } else {
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.view_free_consult:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, FreeConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_fast_consult:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, FastConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_experts_consult:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, LawyerListActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
        }
    }

    @Override
    public void setBannerAdapter(List<BannerEntity.ListBean> bannerList) {
        List<String> imageList = new ArrayList<>();
        for (BannerEntity.ListBean bean : bannerList) {
            imageList.add(bean.getImage());
        }
        banner.setImages(imageList);
        banner.start();
    }

    @Override
    public void setSolutionType(List<SolutionTypeEntity> list) {
        for (SolutionTypeEntity entity : list) {
            fragments.add(SolutionLIstFragment.newInstance(entity.getId()));
            titles.add(entity.getTypeName());
        }
        viewPager.setOffscreenPageLimit(list.size() - 1);
        viewPager.setAdapter(new AdapterViewPager(getChildFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setRequirementTypeAdapter(List<RequirementTypeEntity> data) {
        adapter.setNewData(data);
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
