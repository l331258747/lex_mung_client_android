//package cn.lex_mung.client_android.mvp.ui.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.youth.banner.Banner;
//import com.youth.banner.BannerConfig;
//import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import cn.lex_mung.client_android.R;
//import cn.lex_mung.client_android.app.BundleTags;
//import cn.lex_mung.client_android.app.DataHelperTags;
//import cn.lex_mung.client_android.di.component.DaggerHomePagerComponent;
//import cn.lex_mung.client_android.di.module.HomePagerModule;
//import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
//import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
//import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.NormalBean;
//import cn.lex_mung.client_android.mvp.presenter.HomePagerPresenter;
//import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultMainActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.HomeTableActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.MessageActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.OrganizationLawyerActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
//import cn.lex_mung.client_android.mvp.ui.activity.X5WebCommonActivity;
//import cn.lex_mung.client_android.mvp.ui.adapter.HomeAdapter;
//import cn.lex_mung.client_android.mvp.ui.adapter.HomePageRequirementTypeAdapter;
//import cn.lex_mung.client_android.mvp.ui.dialog.HelpStepDialog;
//import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
//import cn.lex_mung.client_android.mvp.ui.widget.myTabLayout.TabLayout;
//import cn.lex_mung.client_android.utils.BuryingPointHelp;
//import cn.lex_mung.client_android.utils.LogUtil;
//import me.zl.mvp.base.AdapterViewPager;
//import me.zl.mvp.base.BaseFragment;
//import me.zl.mvp.di.component.AppComponent;
//import me.zl.mvp.http.imageloader.ImageLoader;
//import me.zl.mvp.utils.AppUtils;
//import me.zl.mvp.utils.DataHelper;
//import me.zl.mvp.utils.StringUtils;
//
//public class HomePagerFragment extends BaseFragment<HomePagerPresenter> implements HomePagerContract.View {
//    @Inject
//    ImageLoader mImageLoader;
//
//    //    @BindView(R.id.tv_message_count)
////    TextView tvMessageCount;
//    @BindView(R.id.iv_message)
//    ImageView iv_message;
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
////    @BindView(R.id.banner)
////    Banner banner;
////    @BindView(R.id.tab_layout)
////    TabLayout tabLayout;
////    @BindView(R.id.view_pager)
////    ViewPager viewPager;
//
////    @BindView(R.id.rl_hot_1)
////    RelativeLayout rlHot1;
////    @BindView(R.id.rl_hot_2)
////    RelativeLayout rlHot2;
////    @BindView(R.id.rl_hot_3)
////    RelativeLayout rlHot3;
////    @BindView(R.id.tv_hot_1)
////    TextView tvHot1;
////    @BindView(R.id.tv_hot_2)
////    TextView tvHot2;
////    @BindView(R.id.tv_hot_3)
////    TextView tvHot3;
//
////    private List<Fragment> fragments = new ArrayList<>();
////    private List<String> titles = new ArrayList<>();
//
////    private HomePageRequirementTypeAdapter adapter;
//
//    private HomeAdapter homeAdapter;
//
//    public static HomePagerFragment newInstance() {
//        return new HomePagerFragment();
//    }
//
//    @Override
//    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
//        DaggerHomePagerComponent
//                .builder()
//                .appComponent(appComponent)
//                .homePagerModule(new HomePagerModule(this))
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home_pager2, container, false);
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        isCreated = true;
//
//        initAdapter();
//        initRecyclerView();
////        initBanner();
////        mPresenter.getBanner();
////        mPresenter.getOnlineUrl();
//
//        mPresenter.getHomeData();
//
//    }
//
//    private boolean isCreated = false;
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (!isCreated) {
//            return;
//        }
//        if (isVisibleToUser) {
//            BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "first_page",getPair());
//            mPresenter.onResume();
//        } else {
//            BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "first_page",getPair());
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(!getUserVisibleHint()) return;
//        mPresenter.onResume();
//    }
//
//    private void initAdapter() {
//        homeAdapter = new HomeAdapter(mImageLoader);
////        homeAdapter.setOnItemClickListener((adapter1, view, position) -> {
////            if (isFastClick()) return;
////            HomeEntity entity = homeAdapter.getItem(position);
////            if (entity == null) return;
////            showMessage("" + entity.getTypeId());
////        });
////
////        homeAdapter.setOnItemChildClickListener((adapter1, view, position)->{
////            if (isFastClick()) return;
////            HomeEntity entity = homeAdapter.getItem(position);
////            if (entity == null) return;
////            switch (view.getId()) {
////                case R.id.view_home_3button1:
////                    showMessage("" + entity.getBtns().get(0).getTitle());
////                    break;
////            }
////        });
//
//        homeAdapter.setOnBannerClickListener(entity -> {
//            try {
//                if (isFastClick()) return;
//                if (entity == null) return;
//
//                showMessage("" + entity.getTitle());
//
//                if(entity.getJumptype().equals("inner")){
//                    //1 专家咨询
//                    //2 免费咨询
//                    //3 诉讼仲裁
//                    //4 企业顾问
//                    //5 线下见面
//                    //6 按问题找律师
//
//
//                    return;
//                }
//
//                String linkValue = entity.getJumpurl();
//                if (TextUtils.isEmpty(linkValue))
//                    return;
//                if (linkValue.indexOf("orgId=") != -1) {
//                    //用来跳转
//                    String orgId = StringUtils.getValueByName(linkValue, "orgId");
//                    bundle.clear();
//                    bundle.putInt(BundleTags.ID, Integer.valueOf(orgId));
//                    launchActivity(new Intent(mActivity, OrganizationLawyerActivity.class), bundle);
//                    return;
//                }
//                if (linkValue.indexOf("needLogin=1") != -1 && !DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
//                    bundle.clear();
//                    bundle.putInt(BundleTags.TYPE, 1);
//                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
//                    return;
//                }
//                bundle.clear();
//                bundle.putString(BundleTags.URL, entity.getJumpurl());
//                bundle.putString(BundleTags.TITLE, entity.getTitle());
//                bundle.putBoolean(BundleTags.IS_SHARE, false);
//                if (linkValue.indexOf("couponId=") != -1) {
//                    bundle.putBoolean(BundleTags.STATE, false);
//                }
//                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
//            } catch (Exception ignored) {
//            }
//
//        });
//
//
////        adapter = new HomePageRequirementTypeAdapter(mImageLoader);
////        adapter.setOnItemClickListener((adapter1, view, position) -> {
////            if (isFastClick()) return;
////            NormalBean entity = adapter.getItem(position);
////            contractClick(entity);
////
////            if(entity.getRequireTypeId() == 2){
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","litigation_arbitration_click");
////            }else if(entity.getRequireTypeId() == 9){
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","meeting_offline_click");
////            }else if(entity.getRequireTypeId() == 6){
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","legal_counsel_click");
////            }else{
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","legal_card_click");
////            }
////        });
//    }
//
//    private void initRecyclerView() {
//        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
//        recyclerView.setAdapter(homeAdapter);
//
////        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 4));
////        recyclerView.setAdapter(adapter);
//    }
//
//    private void initBanner() {
////        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
////            @Override
////            public void displayImage(Context context, Object path, ImageView imageView) {
////                mImageLoader.loadImage(context
////                        , ImageConfigImpl
////                                .builder()
////                                .url(path.toString())
////                                .imageRadius(AppUtils.dip2px(mActivity,10))
////                                .imageView(imageView)
////                                .build());
////            }
////        });
////        banner.setOnBannerListener(position -> {
////            try {
////                if (isFastClick()) return;
////                BannerEntity bean = mPresenter.getBannerList().get(position);
////                String linkValue = bean.getLinkValue();
////                if (TextUtils.isEmpty(linkValue))
////                    return;
////                if (linkValue.indexOf("orgId=") != -1) {
////                    //((MainActivity) mActivity).switchPage(2);
////                    //用来跳转
////                    String orgId = StringUtils.getValueByName(linkValue, "orgId");
////                    bundle.clear();
////                    bundle.putInt(BundleTags.ID, Integer.valueOf(orgId));
////                    launchActivity(new Intent(mActivity, OrganizationLawyerActivity.class), bundle);
////                    return;
////                }
////                if (linkValue.indexOf("needLogin=1") != -1 && !DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
////                    bundle.clear();
////                    bundle.putInt(BundleTags.TYPE, 1);
////                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
////                    return;
////                }
////                bundle.clear();
////                bundle.putString(BundleTags.URL, bean.getLinkValue());
////                bundle.putString(BundleTags.TITLE, bean.getTitle());
////                bundle.putString(BundleTags.SHARE_DES, "");
////                bundle.putString(BundleTags.SHARE_IMAGE, bean.getImage());
////                bundle.putBoolean(BundleTags.IS_SHARE, false);
////                if (linkValue.indexOf("couponId=") != -1) {
////                    bundle.putBoolean(BundleTags.STATE, false);
////                }
////                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
////            } catch (Exception ignored) {
////            }
////        });
////        banner.setPageMargin(AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_20)));
////        banner.setOffscreenPageLimit(3);
////        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
////        banner.setPageTransformer(true, new ViewPager.PageTransformer() {
////            private static final float MIN_SCALE = 0.85f;
////
////            @Override
////            public void transformPage(@NonNull View view, float position) {
////                if (position >= -1 || position <= 1) {
////                    final float height = view.getHeight();
////                    final float width = view.getWidth();
////                    final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
////                    final float vertMargin = height * (1 - scaleFactor) / 2;
////                    final float horzMargin = width * (1 - scaleFactor) / 2;
////                    view.setPivotY(0.5f * height);
////                    view.setPivotX(0.5f * width);
////                    if (position < 0) {
////                        view.setTranslationX(horzMargin - vertMargin / 2);
////                    } else {
////                        view.setTranslationX(-horzMargin + vertMargin / 2);
////                    }
////                    view.setScaleX(scaleFactor);
////                    view.setScaleY(scaleFactor);
////                }
////            }
////        });
////        banner.setDelayTime(3000);
//    }
//
////    @OnClick({
////            R.id.tv_search
////            , R.id.iv_message
////            , R.id.view_free_consult
////            , R.id.view_fast_consult
////            , R.id.view_experts_consult
////            , R.id.fab
////            , R.id.fab_custom
////    })
////    public void onViewClicked(View view) {
////        switch (view.getId()) {
////            case R.id.fab_custom:
////                if(!TextUtils.isEmpty(DataHelper.getStringSF(mActivity,DataHelperTags.ONLINE_URL))){
////                    bundle.clear();
////                    bundle.putString(BundleTags.URL, DataHelper.getStringSF(mActivity,DataHelperTags.ONLINE_URL));
////                    bundle.putString(BundleTags.TITLE, "在线咨询");
////                    bundle.putBoolean(BundleTags.IS_SHARE, false);
////                    launchActivity(new Intent(mActivity, X5WebCommonActivity.class), bundle);
////                }
////                break;
////            case R.id.tv_search:
////                ((MainActivity) mActivity).switchPage(2);
////                break;
////            case R.id.fab:
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","assistant_click");
////                showHelpDialog();
////                break;
////            case R.id.iv_message:
////                if (mPresenter.isLogin()) {
////                    bundle.clear();
////                    bundle.putSerializable(BundleTags.ENTITY, mPresenter.getUnreadMessageCountEntity());
////                    launchActivity(new Intent(mActivity, MessageActivity.class), bundle);
////                } else {
////                    launchActivity(new Intent(mActivity, LoginActivity.class));
////                }
////                break;
////            case R.id.view_free_consult:
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","free_text_click");
////                launchActivity(new Intent(mActivity, FreeConsultMainActivity.class));
////                break;
////            case R.id.view_fast_consult:
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","quick_consultation_click");
////                if(!TextUtils.isEmpty(DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL))){
////                    bundle.clear();
////                    bundle.putString(BundleTags.URL, DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL));
////                    bundle.putString(BundleTags.TITLE, "快速电话咨询");
////                    bundle.putBoolean(BundleTags.IS_SHARE, false);
////                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
////                }else{
//////                        launchActivity(new Intent(mActivity, FastConsultActivity.class));
////                    showMessage("快速咨询地址为空");
////                }
////                break;
////            case R.id.view_experts_consult:
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","expert_consultation_click");
////                bundle.clear();
////                bundle.putInt(BundleTags.ID, 8);
////                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
////                break;
////        }
////    }
//
//    public void showHelpDialog() {
//        new HelpStepDialog(mActivity,
//                dialog -> {
//                    launchActivity(new Intent(mActivity, HelpStepActivity.class));
//                }).setContent("服务助手平均每天帮助2561名用户找到合适的法律服务和律师，它能帮助您解决如下问题：")
//                .setContent2("· 不知道当前是否需要法律服务\n· 不知道选择什么样的律师\n· 不知道适合自己的律师费用")
//                .setCannelStr("不需要")
//                .setSubmitStr("试试看").show();
//    }
//
//    @Override
//    public void setUnreadMessageCount(String count) {
////        tvMessageCount.setText(count);
////        tvMessageCount.setVisibility(View.VISIBLE);
//        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_message));
//    }
//
//    @Override
//    public void hideUnreadMessageCount() {
////        tvMessageCount.setVisibility(View.GONE);
//        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_message_un));
//    }
//
//    @Override
//    public void setHotContract(List<NormalBean> datas) {
//
////        if (datas == null || datas.size() == 0)
////            return;
////        tvHot1.setText(datas.get(0).getRequireTypeName());
////        rlHot1.setOnClickListener(v -> {
////            contractClick(datas.get(0));
////            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_1_click");
////        });
////        if (datas.size() >= 2) {
////            tvHot2.setText(datas.get(1).getRequireTypeName());
////            rlHot2.setOnClickListener(v -> {
////                contractClick(datas.get(1));
////                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_2_click");
////            });
////        }
//    }
//
//    @Override
//    public void setMoreContract(List<NormalBean> datas) {
////        tvHot3.setText(datas.get(0).getRequireTypeName());
////        rlHot3.setOnClickListener(v -> {
////            contractClick(datas.get(0));
////            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_more_click");
////        });
//    }
//
//    @Override
//    public void setHomeAdapter(List<HomeEntity> datas) {
//        homeAdapter.setNewData(datas);
//    }
//
//    public void contractClick(NormalBean entity) {
//        if (entity == null) return;
//        if (entity.getJumptype() == 1) {
////            ((MainActivity) mActivity).switchPage(2);
//
//            bundle.clear();
//            bundle.putInt(BundleTags.ID, entity.getRequireTypeId());
//            bundle.putString(BundleTags.TITLE, entity.getRequireTypeName());
//            launchActivity(new Intent(mActivity, HomeTableActivity.class), bundle);
//
//        } else {
//            bundle.clear();
//            bundle.putString(BundleTags.URL, entity.getJumpUrl());
//            bundle.putString(BundleTags.TITLE, entity.getRequireTypeName());
//            bundle.putBoolean(BundleTags.IS_SHARE, false);
//            launchActivity(new Intent(mActivity, WebActivity.class), bundle);
//        }
//    }
//
//    @Override
//    public void setBannerAdapter(List<BannerEntity> bannerList) {
////        List<String> imageList = new ArrayList<>();
////        for (BannerEntity bean : bannerList) {
////            imageList.add(bean.getImage());
////        }
////        banner.setImages(imageList);
////        banner.start();
//    }
//
//    @Override
//    public void setSolutionType(List<SolutionTypeEntity> list) {
//
////        List<SolutionTypeEntity> solutionTypeEntityList2 = new ArrayList<>();
////        for (SolutionTypeEntity entity : list) {
////            if (entity.getFreeSolution() == 1) {
////                solutionTypeEntityList2.add(entity);
////            }
////        }
////        list = solutionTypeEntityList2;
////
////        for (SolutionTypeEntity entity : list) {
////            fragments.add(SolutionLIstFragment.newInstance(entity.getId()));
////            titles.add(entity.getAlias());
////        }
////        viewPager.setOffscreenPageLimit(2);
////        viewPager.setAdapter(new AdapterViewPager(getChildFragmentManager(), fragments));
////        tabLayout.setupWithViewPager(viewPager);
////        tabLayout.setMyStyle(viewPager,titles);
////
////        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int i, float v, int i1) {
////            }
////
////            @Override
////            public void onPageSelected(int i) {
////                BuryingPointHelp.getInstance().onEvent(mActivity, "solution_detail","solution_type_click");
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int i) {
////            }
////        });
//    }
//
//    @Override
//    public void setRequirementTypeAdapter(List<NormalBean> data) {
////        adapter.setNewData(data);
//    }
//
//    @Override
//    public void setData(@Nullable Object data) {
//
//    }
//
//    @Override
//    public void showLoading(@NonNull String message) {
//        loading = LoadingDialog.getInstance().init(mActivity, message, false);
//        loading.show();
//    }
//
//    @Override
//    public void hideLoading() {
//        if (loading != null
//                && loading.isShowing())
//            loading.dismiss();
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        AppUtils.makeText(mActivity, message);
//    }
//
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        AppUtils.startActivity(intent);
//    }
//
//    @Override
//    public void launchActivity(Intent intent, Bundle bundle) {
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        launchActivity(intent);
//    }
//
//    @Override
//    public void killMyself() {
//
//    }
//}

package cn.lex_mung.client_android.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.presenter.HomePagerPresenter;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultMainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.HomeSolutionActivity;
import cn.lex_mung.client_android.mvp.ui.activity.HomeTableActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MessageActivity;
import cn.lex_mung.client_android.mvp.ui.activity.OrganizationLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.activity.PhoneSubActivity;
import cn.lex_mung.client_android.mvp.ui.activity.SolutionDetailActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView;
import cn.lex_mung.client_android.utils.BadgeNumUtil;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.StringUtils;

public class HomePagerFragment extends BaseFragment<HomePagerPresenter> implements HomePagerContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smart_refresh_layout;
    @BindView(R.id.emptyView)
    EmptyView emptyView;
    @BindView(R.id.ll_loading)
    LinearLayout ll_loading;

    BadgeNumUtil badgeNumUtil;//华为角标


    private HomeAdapter homeAdapter;

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
        return inflater.inflate(R.layout.fragment_home_pager2, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isCreated = true;

        initAdapter();
        initTextBanner();
        initEmptyView();

        ll_loading.setVisibility(View.VISIBLE);
        smart_refresh_layout.setVisibility(View.GONE);

        mPresenter.pagesSecond();
        mPresenter.getHomeData();
        mPresenter.random();
        mPresenter.getSolutionType();//有的地方用到了缓存
    }

    private void initEmptyView() {
        emptyView.getBtn().setOnClickListener(v -> {
            mPresenter.pagesSecond();
            mPresenter.getHomeData();
            mPresenter.random();
            mPresenter.getSolutionType();//有的地方用到了缓存
        });
    }

    private boolean isCreated = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "first_page", getPair());
            mPresenter.onResume();
            if (flipisShow) viewFlipper.startFlipping();
        } else {
            BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "first_page", getPair());
            if (flipisShow) viewFlipper.stopFlipping();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) return;
        mPresenter.onResume();
    }

    private void initAdapter() {
        smart_refresh_layout.setEnableLoadMore(false);
        smart_refresh_layout.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.pagesSecond();
                mPresenter.getHomeData();
            }
        });


        homeAdapter = new HomeAdapter(mImageLoader);

        homeAdapter.setOnBannerClickListener(new HomeAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(HomeChildEntity entity) {
                try {
                    if (isFastClick()) return;
                    if (entity == null) return;

                    LogUtil.e(entity.getJumpurl());

                    if (entity.getJumptype().equals("inner")) {
                        Uri uri = Uri.parse(entity.getJumpurl());
                        //lex://pages/free scheme=lex  host=pages  path=/free
                        LogUtil.e("scheme = " + uri.getScheme() + " host = " + uri.getHost() + " path = " + uri.getPath() + " query = " + uri.getQuery());

                        if (TextUtils.isEmpty(entity.getJumpurl()))
                            return;
                        if (!entity.getJumpurl().startsWith("lex"))
                            return;
                        if (TextUtils.isEmpty(uri.getPath()))
                            return;
                        if (!uri.getPath().startsWith("/"))
                            return;

                        //1 专家咨询 expert
                        //2 免费咨询 free
                        //3 诉讼仲裁 lawyer
                        //4 企业顾问 lawyer
                        //5 线下见面 lawyer
                        //6 按问题找律师 subject
                        switch (uri.getPath().substring(1)) {
                            case "expert":
                                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_expert_consultation_click");
                                bundle.clear();
                                bundle.putInt(BundleTags.ID, 8);
                                launchActivity(new Intent(mActivity, LawyerListActivity.class), bundle);
                                break;
                            case "free":
                                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_free_text_click");
                                launchActivity(new Intent(mActivity, FreeConsultMainActivity.class));
                                break;
                            case "lawyer":
                                String requireTypeIdStr = uri.getQueryParameter("id");
                                String requireTypeName = uri.getQueryParameter("name");
                                int requireTypeId;
                                if (TextUtils.isEmpty(requireTypeIdStr) || TextUtils.isEmpty(requireTypeName))
                                    return;

                                try {
                                    requireTypeId = Integer.valueOf(requireTypeIdStr);
                                } catch (Exception e) {
                                    return;
                                }

                                if (requireTypeId == 2) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_litigation_arbitration_click");
                                } else if (requireTypeId == 9) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_meeting_offline_click");
                                } else if (requireTypeId == 6) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_legal_counsel_click");
                                }

                                bundle.clear();
                                bundle.putInt(BundleTags.ID, requireTypeId);
                                bundle.putString(BundleTags.TITLE, requireTypeName);
                                launchActivity(new Intent(mActivity, HomeTableActivity.class), bundle);
                                break;
                            case "subject":
                                String requireTypeIdStr1 = uri.getQueryParameter("id");
                                String requireTypeName1 = uri.getQueryParameter("name");
                                String hasContract = uri.getQueryParameter("hasContract");
                                int requireTypeId1;
                                if (TextUtils.isEmpty(requireTypeIdStr1) || TextUtils.isEmpty(requireTypeName1))
                                    return;
                                try {
                                    requireTypeId1 = Integer.valueOf(requireTypeIdStr1);
                                } catch (Exception e) {
                                    return;
                                }

                                switch (requireTypeId1){
                                    case 0://更多分类
                                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_more_categories_click");
                                        break;
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
                                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_environmental_protection_click");
                                        break;

                                }

                                if(requireTypeId1 == 0){
                                    launchActivity(new Intent(mActivity, HomeSolutionActivity.class));
                                }else{
                                    bundle.clear();
                                    bundle.putInt(BundleTags.SOLUTION_TYPE_ID,requireTypeId1);
                                    bundle.putString(BundleTags.SOLUTION_TYPE_NAME,requireTypeName1);
                                    bundle.putBoolean(BundleTags.IS_CRIMINAL,TextUtils.equals("0",hasContract));
                                    launchActivity(new Intent(mActivity, SolutionDetailActivity.class),bundle);
                                }

                                break;
                        }


                        return;
                    }

                    //BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "legal_card_click"); //法务卡 member
                    //BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","quick_consultation_click"); //快速咨询 quick.html
                    //BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_1_click"); //热门1
                    //BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_2_click"); //热门2
                    //BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","hot_service_more_click"); //热门更多 contractList.html
                    //BuryingPointHelp.getInstance().onEvent(mActivity, "solution_detail","solution_type_click"); //解决方案tablayout

                    String linkValue = entity.getJumpurl();
                    if (TextUtils.isEmpty(linkValue))
                        return;

                    //TODO 埋点
                    if (linkValue.indexOf("member") > -1) {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_legal_card_click"); //法务卡 member
                    } else if (linkValue.indexOf("quick.html") > -1) {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_quick_consultation_click"); //快速咨询 quick.html
                    } else if (linkValue.indexOf("contractList.html") > -1) {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","first_page_hot_service_more_click"); //热门更多 contractList.html
                    } else if (linkValue.indexOf("feature.html") > -1) {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","first_page_litigation_click"); //诉讼垫资 feature.html
                    } else if (linkValue.indexOf("retrial.html") > -1) {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page","first_page_retrial_appeal_click"); //再审申诉 retrial.html
                    }

                    if (linkValue.indexOf("orgId=") != -1) {
                        //用来跳转
                        String orgId = StringUtils.getValueByName(linkValue, "orgId");
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, Integer.valueOf(orgId));
                        launchActivity(new Intent(mActivity, OrganizationLawyerActivity.class), bundle);
                        return;
                    }
                    if (linkValue.indexOf("needLogin=1") != -1 && !DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                        bundle.clear();
                        bundle.putInt(BundleTags.TYPE, 1);
                        launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                        return;
                    }
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getJumpurl());
                    bundle.putString(BundleTags.TITLE, entity.getTitle());
                    if(entity.getShowShare() == 1){
                        bundle.putBoolean(BundleTags.IS_SHARE, true);
                        bundle.putString(BundleTags.SHARE_URL, entity.getShareUrl());
                        bundle.putString(BundleTags.SHARE_TITLE, entity.getShareTitle());
                        bundle.putString(BundleTags.SHARE_DES, entity.getShareDescription());
                        bundle.putString(BundleTags.SHARE_IMAGE, entity.getShareImg());
                    }
                    if (linkValue.indexOf("couponId=") != -1) {//领取优惠券页面，不需要跳转
                        bundle.putBoolean(BundleTags.STATE, false);
                    }
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onLawyerClick(LawyerEntity2 entity2) {
                if (isFastClick()) return;
                if (entity2 == null) return;
                bundle.clear();
                bundle.putInt(BundleTags.ID, entity2.getMemberId());
                launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
            }

            @Override
            public void onLawyerTitleClick() {
                if (isFastClick()) return;
                launchActivity(new Intent(mActivity, LawyerListActivity.class));
            }
        });

        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(homeAdapter);
    }

    @OnClick({
            R.id.view_search_text
            , R.id.iv_message
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_search_text:

                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "first_page_search_click");
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
        }
    }

    @Override
    public void setUnreadMessageCount(int count) {
        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_message));

        setHuaweiBadgeNum(count);
    }

    //设置华为角标
    public void setHuaweiBadgeNum(int num){
        if(badgeNumUtil == null){
            badgeNumUtil = new BadgeNumUtil(mActivity);
        }
        badgeNumUtil.setHuaweiBadgeNum(num);
    }

    @Override
    public void hideUnreadMessageCount() {
        iv_message.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_message_un));
        setHuaweiBadgeNum(0);
    }

    //------------搜索栏轮播 start
    public void stopFlipping() {
        if (viewFlipper != null) {
            viewFlipper.stopFlipping();
        }
    }

    public void initTextBanner() {
        viewFlipper.setAutoStart(false);
        viewFlipper.setFlipInterval(3000); // ms
    }


    boolean flipisShow;

    @Override
    public void showFlipView(boolean isShow) {
        this.flipisShow = isShow;
        if (isShow) {
            viewFlipper.setVisibility(View.VISIBLE);
            tvSearch.setVisibility(View.GONE);
        } else {
            viewFlipper.setVisibility(View.GONE);
            tvSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Activity getHomeActivity() {
        return this.getActivity();
    }

    /**
     * 轮播
     */
    public void addNotice(List<String> datas) {
        int size = datas.size();
        viewFlipper.removeAllViews();
        if (size == 0) {
            stopFlipping();
            return;
        }

        if (size == 1) {
            View view = View.inflate(getHomeActivity(), R.layout.view_flipper_item_layout2, null);
            ((TextView) view.findViewById(R.id.textview1)).setText(datas.get(0));
            viewFlipper.addView(view);
            stopFlipping();
            return;
        }

        viewFlipper.startFlipping();
        for (int i = 0; i < size; i++) {
            View view = View.inflate(getHomeActivity(), R.layout.view_flipper_item_layout2, null);
            ((TextView) view.findViewById(R.id.textview1)).setText(datas.get(i));
            viewFlipper.addView(view);
        }
    }

    //------------搜索栏轮播 end


    @Override
    public void setHomeAdapter(List<HomeEntity> datas) {
        emptyView.setVisibility(View.GONE);
        smart_refresh_layout.setVisibility(View.VISIBLE);
        ll_loading.setVisibility(View.GONE);

        homeAdapter.setNewData(datas);
        smart_refresh_layout.finishRefresh();
    }

    public void showEmptyView(){
        emptyView.setVisibility(View.VISIBLE);
        smart_refresh_layout.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
    }

    @Override
    public void addHomeLawyer(HomeEntity homeEntity) {
        homeAdapter.addData(homeEntity);
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
