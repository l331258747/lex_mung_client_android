package com.lex_mung.client_android.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.presenter.HomePagerPresenter;
import com.lex_mung.client_android.mvp.ui.activity.MainActivity;
import com.lex_mung.client_android.mvp.ui.activity.WebActivity;
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
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();


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
        initBanner();
        mPresenter.getBanner();
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
            , R.id.view_lawyer_service
            , R.id.view_legal_card
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                ((MainActivity)mActivity).switchPage(1);
                break;
            case R.id.iv_message:
                break;
            case R.id.view_free_consult:
                break;
            case R.id.view_fast_consult:
                break;
            case R.id.view_experts_consult:
                break;
            case R.id.view_lawyer_service:
                break;
            case R.id.view_legal_card:
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
