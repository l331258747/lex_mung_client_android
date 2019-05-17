package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerHomePagerComponent;
import cn.lex_mung.client_android.di.module.HomePagerModule;
import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.NormalBean;
import cn.lex_mung.client_android.mvp.presenter.HomePagerPresenter;
import cn.lex_mung.client_android.mvp.ui.activity.FastConsultActivity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultActivity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultMainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MessageActivity;
import cn.lex_mung.client_android.mvp.ui.activity.OrganizationLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomePageRequirementTypeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.AdapterViewPager;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.StringUtils;

public class HomePagerFragment extends BaseFragment<HomePagerPresenter> implements HomePagerContract.View {
    @Inject
    ImageLoader mImageLoader;

//    @BindView(R.id.tv_message_count)
//    TextView tvMessageCount;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.rl_hot_1)
    RelativeLayout rlHot1;
    @BindView(R.id.rl_hot_2)
    RelativeLayout rlHot2;
    @BindView(R.id.rl_hot_3)
    RelativeLayout rlHot3;
    @BindView(R.id.tv_hot_1)
    TextView tvHot1;
    @BindView(R.id.tv_hot_2)
    TextView tvHot2;
    @BindView(R.id.tv_hot_3)
    TextView tvHot3;

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

    private boolean isCreated = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            MobclickAgent.onPageStart("w_y_shouye_index");
        } else {
            MobclickAgent.onPageEnd("w_y_shouye_index");
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isCreated = true;

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
            NormalBean entity = adapter.getItem(position);
            contractClick(entity);
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
//                                .imageRadius(AppUtils.dip2px(mActivity,10))//图片存在bug
                                .imageView(imageView)
                                .build());
            }
        });
        banner.setOnBannerListener(position -> {
            try {
                if (isFastClick()) return;
                BannerEntity.ListBean bean = mPresenter.getBannerList().get(position);
                String linkValue = bean.getLinkValue();
                if(TextUtils.isEmpty(linkValue))
                    return;
                if(linkValue.indexOf("orgId=") != -1){
                    //((MainActivity) mActivity).switchPage(2);
                    //用来跳转
                    String orgId = StringUtils.getValueByName(linkValue,"orgId");
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, Integer.valueOf(orgId));
                    launchActivity(new Intent(mActivity,OrganizationLawyerActivity.class),bundle);
                    return;
                }
                if(linkValue.indexOf("needLogin=1") != -1 && !DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)){
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                    return;
                }
                bundle.clear();
                bundle.putString(BundleTags.URL, bean.getLinkValue());
                bundle.putString(BundleTags.TITLE, bean.getTitle());
                bundle.putString(BundleTags.DES, "");
                bundle.putString(BundleTags.IMAGE, bean.getImage());
                bundle.putBoolean(BundleTags.IS_SHARE, false);
                if(linkValue.indexOf("couponId=") != -1){
                    bundle.putBoolean(BundleTags.STATE, false);
                }
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
                ((MainActivity) mActivity).switchPage(2);
                break;
            case R.id.iv_message:
                if (mPresenter.isLogin()) {
                    bundle.clear();
                    bundle.putSerializable(BundleTags.ENTITY, mPresenter.getUnreadMessageCountEntity());
                    launchActivity(new Intent(mActivity, MessageActivity.class), bundle);
                } else {
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.view_free_consult:
                launchActivity(new Intent(mActivity, FreeConsultMainActivity.class));
                break;
            case R.id.view_fast_consult:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_index_kszx");
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, FastConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_experts_consult:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_index_zjzx");
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
    public void setUnreadMessageCount(String count) {
//        tvMessageCount.setText(count);
//        tvMessageCount.setVisibility(View.VISIBLE);
        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_message));
    }

    @Override
    public void hideUnreadMessageCount() {
//        tvMessageCount.setVisibility(View.GONE);
        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_message_un));
    }

    @Override
    public void setHotContract(List<NormalBean> datas) {

        if(datas == null || datas.size() == 0)
            return;
        tvHot1.setText(datas.get(0).getRequireTypeName());
        rlHot1.setOnClickListener(v -> contractClick(datas.get(0)));
        if(datas.size() >= 2){
            tvHot2.setText(datas.get(1).getRequireTypeName());
            rlHot2.setOnClickListener(v -> contractClick(datas.get(1)));
        }
    }

    @Override
    public void setMoreContract(List<NormalBean> datas) {
        tvHot3.setText(datas.get(0).getRequireTypeName());
        rlHot3.setOnClickListener(v -> contractClick(datas.get(0)));
    }

    public void contractClick(NormalBean entity){
        if (entity == null) return;
        if (entity.getJumptype() == 1) {
            ((MainActivity) mActivity).switchPage(2);
        } else {
            bundle.clear();
            bundle.putString(BundleTags.URL, entity.getJumpUrl());
            bundle.putString(BundleTags.TITLE, entity.getRequireTypeName());
            bundle.putBoolean(BundleTags.IS_SHARE, false);
            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
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
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new AdapterViewPager(getChildFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setRequirementTypeAdapter(List<NormalBean> data) {
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
