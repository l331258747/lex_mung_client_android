package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerLawyerHomePageComponent;
import cn.lex_mung.client_android.di.module.LawyerHomePageModule;
import cn.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.presenter.LawyerHomePagePresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.FieldDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import cn.lex_mung.client_android.mvp.ui.widget.NoScrollViewPager;
import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
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
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_law_firms)
    TextView tvLawFirms;
    @BindView(R.id.iv_credit_certification)
    ImageView ivCreditCertification;
    @BindView(R.id.tv_credit_certification)
    TextView tvCreditCertification;
    @BindView(R.id.tv_field)
    TextView tvField;
    @BindView(R.id.sfl_field)
    SimpleFlowLayout sflField;
    @BindView(R.id.tv_social_position)
    TextView tvSocialPosition;
    @BindView(R.id.iv_social_position)
    ImageView ivSocialPosition;
    @BindView(R.id.tv_more_social_position)
    TextView tvMoreSocialPosition;
    @BindView(R.id.tv_basic_info)
    TextView tvBasicInfo;
    @BindView(R.id.iv_basic_info)
    ImageView ivBasicInfo;
    @BindView(R.id.tv_service_price)
    TextView tvServicePrice;
    @BindView(R.id.iv_service_price)
    ImageView ivServicePrice;
    @BindView(R.id.tv_practice_experience)
    TextView tvPracticeExperience;
    @BindView(R.id.iv_practice_experience)
    ImageView ivPracticeExperience;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.iv_release)
    TextView ivRelease;
    @BindView(R.id.iv_call)
    TextView ivCall;

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.rl_head_root)
    ViewGroup rlHeadRoot;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    boolean isCall;//是否显示拨打电话按钮
    private int requireTypeId;//用来埋点用的
//    boolean isGoCall = false;

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
        mPresenter.onResume();

        if(!DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)){
            llBottom.setVisibility(View.GONE);
        }else{
            llBottom.setVisibility(View.VISIBLE);
        }

        switch (requireTypeId) {
            case 2://诉讼
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "litigation_arbitration_lawyer_detail_page", getPair());
                break;
            case 6://企业顾问
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "enterprise_offline_lawyer_detail_page", getPair());
                break;
            case 9://线下见面
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "meeting_offline_lawyer_detail_page", getPair());
                break;
            case 8://专家咨询 电话咨询
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "expert_consulation_lawyer_detail_page", getPair());
                break;
            case 100://免费咨询
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "free_text_lawyer_detail_page", getPair());
                break;
            default:
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "search_lawyer_lawyer_detail_page", getPair());
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        switch (requireTypeId) {
            case 2://诉讼
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "litigation_arbitration_lawyer_detail_page", getPair());
                break;
            case 6://企业顾问
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "enterprise_offline_lawyer_detail_page", getPair());
                break;
            case 9://线下见面
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "meeting_offline_lawyer_detail_page", getPair());
                break;
            case 8://专家咨询 电话咨询
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "expert_consulation_lawyer_detail_page", getPair());
                break;
            case 100://免费咨询
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "free_text_lawyer_detail_page", getPair());
                break;
            default:
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "search_lawyer_lawyer_detail_page", getPair());
                break;
        }


    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            requireTypeId = bundleIntent.getInt(BundleTags.REQUIRE_TYPE_ID);
            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
            mPresenter.getLawsHomePagerBase();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//设置状态栏文字颜色为白色
        }
        initStatus();
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    /**
     * 初始化状态栏位置和滑动监听
     */
    private void initStatus() {
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float percent = (float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange();
            rlHeadRoot.setAlpha(percent);
            StatusBarUtil.setTranslucentForImageView(mActivity, (int) (255f * percent), null);
            if (percent == 0) {
                viewPager.setNoScroll(false);
            } else if (percent == 1) {
                viewPager.setNoScroll(false);
            } else {
                viewPager.setNoScroll(true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        switchPager(16, 14, 14, Typeface.BOLD, Typeface.NORMAL, Typeface.NORMAL, View.VISIBLE, View.GONE, View.GONE);
                        break;
                    case 1:
                        switchPager(14, 16, 14, Typeface.NORMAL, Typeface.BOLD, Typeface.NORMAL, View.GONE, View.VISIBLE, View.GONE);
                        break;
                    case 2:
                        switchPager(14, 14, 16, Typeface.NORMAL, Typeface.NORMAL, Typeface.BOLD, View.GONE, View.GONE, View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        StatusBarUtil.setTransparentForImageView(mActivity, null);
        int statusBarHeight = DeviceUtils.getStatusBarHeight(mActivity);
        CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) rlHeadRoot.getLayoutParams();
        lp1.topMargin = statusBarHeight;
        rlHeadRoot.setLayoutParams(lp1);
        CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) toolBar.getLayoutParams();
        lp2.topMargin = statusBarHeight;
        toolBar.setLayoutParams(lp2);
    }

    @OnClick({R.id.iv_back
            , R.id.iv_head_back
            , R.id.tv_like
            , R.id.tv_basic_info
            , R.id.tv_service_price
            , R.id.tv_practice_experience
            , R.id.tv_social_position
            , R.id.iv_head_share
            , R.id.iv_call
            , R.id.iv_release
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_head_back:
                killMyself();
                break;
            case R.id.tv_like:
                if ("关注TA".contentEquals(tvLike.getText())) {
                    mPresenter.follow();
                } else {
                    mPresenter.unFollow();
                }
                break;
            case R.id.tv_basic_info:
                if (viewPager.getCurrentItem() == 0) return;
                switchPager(16, 14, 14, Typeface.BOLD, Typeface.NORMAL, Typeface.NORMAL, View.VISIBLE, View.GONE, View.GONE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_practice_experience:
                if (viewPager.getCurrentItem() == 1) return;
                switchPager(14, 16, 14, Typeface.NORMAL, Typeface.BOLD, Typeface.NORMAL, View.GONE, View.VISIBLE, View.GONE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_service_price:
                if (viewPager.getCurrentItem() == 2) return;
                switchPager(14, 14, 16, Typeface.NORMAL, Typeface.NORMAL, Typeface.BOLD, View.GONE, View.GONE, View.VISIBLE);
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_social_position:
                if (tvMoreSocialPosition.getVisibility() != View.VISIBLE) return;

                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) mPresenter.getEntity().getSocialFunction());
                launchActivity(new Intent(mActivity, MoreSocialPositionActivity.class), bundle);
                break;
            case R.id.iv_head_share:
                break;
            case R.id.iv_call:
                if (!isCall) return;

                switch (requireTypeId) {
                    case 2://诉讼
                        BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_lawyer_detail_page", "litigation_arbitration_lawyer_detail_page_phone_click");
                        break;
                    case 6://企业顾问
                        BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_offline_lawyer_detail_page", "enterprise_offline_lawyer_detail_page_phone_click");
                        break;
                    case 9://线下见面
                        BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_offline_lawyer_detail_page", "meeting_offline_lawyer_detail_page_phone_click");
                        break;
                    case 8://专家咨询 电话咨询
                        BuryingPointHelp.getInstance().onEvent(mActivity, "expert_consulation_lawyer_detail_page", "expert_consulation_lawyer_detail_page_phone_click");
                        break;
                    case 100://免费咨询
                        BuryingPointHelp.getInstance().onEvent(mActivity, "free_text_lawyer_detail_page", "free_text_lawyer_detail_page_phone_click");
                        break;
                    default:
                        BuryingPointHelp.getInstance().onEvent(mActivity, "search_lawyer_lawyer_detail_page", "search_lawyer_lawyer_detail_page_phone_click");
                        break;
                }

                sendCall();
                break;
            case R.id.iv_release:
                viewPager.setCurrentItem(2);
                mAppBarLayout.setExpanded(false);
                break;
        }
    }

    public void sendCall(){
        mPresenter.setEntity();
    }


    /**
     * 切换页面
     */
    private void switchPager(int i1, int i2, int i3, int t1, int t2, int t3, int v1, int v2, int v3) {
        tvBasicInfo.setTextSize(i1);
        tvPracticeExperience.setTextSize(i2);
        tvServicePrice.setTextSize(i3);
        tvBasicInfo.setTypeface(Typeface.defaultFromStyle(t1));
        tvPracticeExperience.setTypeface(Typeface.defaultFromStyle(t2));
        tvServicePrice.setTypeface(Typeface.defaultFromStyle(t3));
        ivBasicInfo.setVisibility(v1);
        ivPracticeExperience.setVisibility(v2);
        ivServicePrice.setVisibility(v3);
    }

    @Override
    public void initViewPager(List<Fragment> fragments) {
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), fragments));
    }

    @Override
    public void setName(String memberName) {
        tvName.setText(memberName);
        tvHeadTitle.setText(memberName);
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
    public void hideLikeLayout() {
        tvLike.setVisibility(View.GONE);
    }

    @Override
    public void setLikeLayout(int icon, int color, String string) {
        tvLike.setVisibility(View.VISIBLE);
        tvLike.setBackgroundResource(icon);
        tvLike.setTextColor(color);
        tvLike.setText(string);
    }

    @Override
    public void setPositionName(String memberPositionName) {
        tvPost.setText(memberPositionName);
    }

    @Override
    public void setInstitutionNameAndPractice(String format) {
        tvLawFirms.setText(format);
    }

    @Override
    public void setCreditCertification(String tagName) {
        tvCreditCertification.setText(tagName);
    }

    @Override
    public void hideCreditCertificationLayout() {
        tvCreditCertification.setVisibility(View.GONE);
        ivCreditCertification.setVisibility(View.GONE);
    }

    @Override
    public void setSocialPosition(String socialPosition) {
        tvSocialPosition.setText(socialPosition);
    }

    @Override
    public void hideSocialPosition() {
        tvSocialPosition.setVisibility(View.GONE);
        ivSocialPosition.setVisibility(View.GONE);
    }

    @Override
    public void showMoreSocialPositionLayout() {
        tvMoreSocialPosition.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFieldLayout() {
        tvField.setVisibility(View.GONE);
        sflField.setVisibility(View.GONE);
    }

    @Override
    public void hideFieldLayout_1() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) sflField.getLayoutParams();
        layoutParams.height = AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_300));
        sflField.setLayoutParams(layoutParams);
        tvField.setVisibility(View.INVISIBLE);
        sflField.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void addSimpleFlowLayout(View itemView, int i) {
        sflField.addView(itemView, i);
    }

    @Override
    public void removeViews() {
        if (sflField.getChildCount() > 0) {
            sflField.removeViews(0, sflField.getChildCount());
        }
    }

    @Override
    public void showFieldDialog(LawsHomePagerBaseEntity.ChildBean bean) {
        new FieldDialog(mActivity, bean, mImageLoader).show();
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


    //-----电话

    @Override
    public void showCall(boolean isShow) {
        isCall = isShow;
        if (isShow) {
            ivCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_06a66a_all));
        } else {
            ivCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_b5b5b5_all));
        }
    }

    @Override
    public void showExpertPrice(ExpertPriceEntity entity) {
        if (entity.getOrderStatus() == 0) {//是否有预约订单，0.没有预约订单，1.已预约但未接单 2，已预约已接单，未完成
            bundle.clear();
            bundle.putSerializable(BundleTags.ENTITY, entity);
            launchActivity(new Intent(mActivity, PhoneSubActivity.class), bundle);
        } else if (entity.getOrderStatus() == 1) {
            new SingleTextDialog(mActivity)
                    .setContentHtmlStr("您已成功发起咨询邀约，请等待律师确认咨询时间，您可以进入<font color=\"#1EC88B\">我的-我的订单</font>页查看预约状态。")
                    .setTextOnClickListener(()->{
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getOrderId());
                        bundle.putString(BundleTags.TITLE,"专家咨询详情");
                        bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                        launchActivity(new Intent(mActivity,OrderDetailsExpertActivity.class),bundle);
                    })
                    .setSubmitStr("我知道了！").show();

        } else if (entity.getOrderStatus() == 2) {
            new SingleTextDialog(mActivity)
                    .setContentHtmlStr("律师将于 " + entity.getLawyerOrderTime() + " 给您来电，请耐心等候。您可进入<font color=\"#1EC88B\">我的-我的订单</font>页管理订单。")
                    .setTextOnClickListener(()->{
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getOrderId());
                        bundle.putString(BundleTags.TITLE,"专家咨询详情");
                        bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                        launchActivity(new Intent(mActivity,OrderDetailsExpertActivity.class),bundle);
                    })
                    .setSubmitStr("我知道了！").show();
        }
    }

//    @Override
//    public void showToErrorDialog(String s) {
//        new CallFieldDialog5(mActivity, dialog -> {
//            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
//            startActivity(dialIntent);
//            dialog.dismiss();
//
//        }, s, "联系客服").show();
//    }
//    OnlyTextDialog onlyTextDialog;
//    //查看余额充足
//    public void showBalanceYesDialog(ExpertPriceEntity entity) {
//        new CurrencyDialog2(mActivity, entity)
//                .setClickYes(dialog -> {
//                    mPresenter.sendCall(entity.getCallCenterNo());
//                })
//                .setClickNo(dialog -> {
//                    bundle.clear();
////                bundle.putSerializable(BundleTags.ENTITY,entity);
//                    bundle.putBoolean(BundleTags.IS_EXPERT, true);
//                    launchActivity(new Intent(mActivity, MyAccountActivity.class), bundle);
//                }).show();
//    }
//查询余额不足
//public void showBalanceNoDialog(ExpertPriceEntity entity) {
//    showOnlyDialog(entity);
//    new Thread(() -> {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        runOnUiThread(() -> {
//            onlyTextDialog.dismiss();
//            bundle.clear();
////                bundle.putSerializable(BundleTags.ENTITY,entity);
//            bundle.putBoolean(BundleTags.IS_EXPERT, true);
//            launchActivity(new Intent(mActivity, MyAccountActivity.class), bundle);
//        });
//    }).start();
//}
//public void showTestDialog2() {
//    new CurrencyDialog(mActivity)
//            .showTitleBg(false)
//            .setContent("如问题仍然未解决，您可再次拨打。")
//            .setContentLineSpacing(R.dimen.qb_px_20)
//            .setContentSize(14)
//            .setSubmitStr("已解决")
//            .setCannelStr("再次致电")
//            .setClickNo(dialog -> {
//                if (!isCall) return;
//                mPresenter.setEntity();
//            }).show();
//}
//@Override
//public void GoCall(String str) {
//    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
//    startActivity(dialIntent);
//    isGoCall = true;
//}
//public void showOnlyDialog(ExpertPriceEntity entity) {
//    String string = "当前余额剩余通话时长不足%1$s分钟，请充值余额。";
//    onlyTextDialog = new OnlyTextDialog(mActivity).setContent(String.format(string, entity.getMinimumDuration()));
//    onlyTextDialog.show();
//}
}
