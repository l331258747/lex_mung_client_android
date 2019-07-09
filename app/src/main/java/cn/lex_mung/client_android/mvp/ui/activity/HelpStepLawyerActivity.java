package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerHelpStepLawyerComponent;
import cn.lex_mung.client_android.di.module.HelpStepLawyerModule;
import cn.lex_mung.client_android.mvp.contract.HelpStepLawyerContract;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.BusinessInfoBean;
import cn.lex_mung.client_android.mvp.model.entity.help.FilterBean;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.RecommendLawyerBean;
import cn.lex_mung.client_android.mvp.model.entity.help.RequireInfoBean;
import cn.lex_mung.client_android.mvp.model.entity.help.RequireInfoChildBean;
import cn.lex_mung.client_android.mvp.presenter.HelpStepLawyerPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.HelpStepLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog5;
import cn.lex_mung.client_android.mvp.ui.dialog.CurrencyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.CurrencyDialog2;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.OnlyTextDialog;
import cn.lex_mung.client_android.mvp.ui.widget.SimpleFlowLayout;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class HelpStepLawyerActivity extends BaseActivity<HelpStepLawyerPresenter> implements HelpStepLawyerContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.iv_big_img)
    ImageView ivBigImg;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_2)
    TextView tvName2;
    @BindView(R.id.tv_social_position)
    TextView tvSocialPosition;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_field)
    TextView tvField;
    @BindView(R.id.sfl_field)
    SimpleFlowLayout sflField;
    @BindView(R.id.iv_social_position)
    ImageView ivSocialPosition;
    @BindView(R.id.cl_info)
    ConstraintLayout clInfo;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tv_content_city)
    TextView tvContentCity;
    @BindView(R.id.tv_content_type)
    TextView tvContentType;
    @BindView(R.id.tv_content_money)
    TextView tvContentMoney;
    @BindView(R.id.tv_content_help)
    TextView tvContentHelp;

    HelpStepLawyerAdapter adapter;
    RecommendLawyerBean bean;

    int requireTypeId;
    int buryingPointId = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStepLawyerComponent
                .builder()
                .appComponent(appComponent)
                .helpStepLawyerModule(new HelpStepLawyerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_step_lawyer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        buryingPointId = bundleIntent.getInt(BundleTags.BURYING_POINT, -1);
        mPresenter.getData(bundleIntent.getInt(BundleTags.REGION_ID),
                bundleIntent.getInt(BundleTags.SOLUTION_TYPE_ID),
                bundleIntent.getInt(BundleTags.AMOUNT_ID),
                requireTypeId = bundleIntent.getInt(BundleTags.REQUIRE_TYPE_ID),
                0);
    }

    @Override
    public void onBackPressed() {
        new DefaultDialog(mActivity, dialog -> {
            AppManager.getAppManager().killAllNotClass(MainActivity.class);
        }, "现在返回您将回到首页，您可以在首页右上角的消息中查看需求的进展情况！", "确认返回", "取消").show();
    }

    private void setTitleView() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            if (scrollY <= 0) {
                titleView.getTitleLlayout().setBackgroundResource(R.color.c_00);
                titleView.setTitle("");
            } else if (scrollY > 0 && scrollY <= 560) {
                titleView.getTitleLlayout().setBackgroundResource(R.color.c_00);
                titleView.setTitle("");
            } else {
                titleView.getTitleLlayout().setBackgroundResource(R.color.c_ff);
                titleView.setTitle("优选律师");
            }
        });

    }

    @OnClick({R.id.view_title, R.id.view_bottom_txt, R.id.view_bottom_call, R.id.tv_bottom_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_title:
                if (buryingPointId == 1) {
                    switch (requireTypeId) {
                        case 2:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail", "litigation_arbitration_assistant_lawyer_detail_click");
                            break;
                        case 9:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail", "meeting_assistant_lawyer_detail_click");
                            break;
                        case 6:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail", "enterprise_detail_assistant_lawyer_detail_click");
                            break;
                    }
                } else {
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_lawyer_detail_click");
                }
                bundle.clear();
                bundle.putInt(BundleTags.ID, bean.getMemberId());
                launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
                break;
            case R.id.view_bottom_txt:
                BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_free_text_click");
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, FreeConsultActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_bottom_call:
                if (buryingPointId == 1) {
                    switch (requireTypeId) {
                        case 2:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail", "litigation_arbitration_assistant_quick_consultation_click");
                            break;
                        case 9:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail", "meeting_assistant_quick_consultation_click");
                            break;
                        case 6:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail", "enterprise_detail_assistant_quick_consultation_click");
                            break;
                    }
                } else {
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_quick_consultation_click");
                }

                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
//                    launchActivity(new Intent(mActivity, FastConsultActivity.class));
                    bundle.clear();
                    bundle.putString(BundleTags.URL, DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL));
                    bundle.putString(BundleTags.TITLE, "快速电话咨询");
                    bundle.putBoolean(BundleTags.IS_SHARE, false);
                    launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 2);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.tv_bottom_custom:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
        }
    }

    @Override
    public void setData(HelpStepLawyerEntity entity) {
        setContentLayout(entity.getFilter());
        setTitleLayout(entity.getRecommendLawyer());
    }

    public void initAdapter(List<RequireInfoChildBean> beans) {
        adapter = new HelpStepLawyerAdapter(beans);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                RequireInfoChildBean entity = adapter.getItem(position);
                if (entity == null) return;

                if (entity.getRequirementType() == 1) {//发需求
                    if (buryingPointId == 1) {
                        switch (requireTypeId) {
                            case 2:
                                if (entity.getParentRequireTypeId() == 2) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail", "litigation_arbitration_assistant_arbitration_click");
                                }
                                break;
                            case 9:
                                if (entity.getParentRequireTypeId() == 9) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail", "meeting_assistant_meeting_click");
                                }
                                break;
                            case 6:
                                if (entity.getParentRequireTypeId() == 6) {
                                    BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail", "enterprise_detail_assistant_service_click");
                                }
                                break;
                        }
                    } else {
                        if (entity.getParentRequireTypeId() == 3) {//起草合同
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_draw_contract_click");
                        } else if (entity.getParentRequireTypeId() == 4) {//律师函
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_lawyer_letter_click");
                        } else if (entity.getParentRequireTypeId() == 2) {//诉讼仲裁
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_arbitration_click");
                        } else if (entity.getParentRequireTypeId() == 9) {//线下见面
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_meeting_offline_click");
                        } else if (entity.getParentRequireTypeId() == 6) {//企业顾问
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_legal_adviser_click");
                        } else if (entity.getParentRequireTypeId() == 30) {//审查合同
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_review_contract_click");
                        } else if (entity.getParentRequireTypeId() == 5) {//意见书
                            BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_legal_opinion_click");
                        }
                    }
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getParentRequireTypeId());
                    bundle.putInt(BundleTags.TYPE, entity.getParentType());
                    bundle.putString(BundleTags.TITLE, entity.getParentRequireTypeName());
                    bundle.putInt(BundleTags.MEMBER_ID, bean.getMemberId());
                    bundle.putString(BundleTags.REGION, bean.getRegion());
                    bundle.putInt(BundleTags.REGION_ID, bean.getRegionId());
                    launchActivity(new Intent(mActivity, ReleaseDemandActivity.class), bundle);
                } else {//电话咨询
                    if (buryingPointId == 1) {
                        switch (requireTypeId) {
                            case 2:
                                BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail", "litigation_arbitration_assistant_phone_click");
                                break;
                            case 9:
                                BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail", "meeting_assistant_phone_click");
                                break;
                            case 6:
                                BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail", "enterprise_detail_assistant_phone_click");
                                break;
                        }
                    } else {
                        BuryingPointHelp.getInstance().onEvent(mActivity, "first_page", "assistant_success_phone_click");
                    }
                    mPresenter.expertPrice(bean.getMemberName(),bean.getMemberId());
                }
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }
        });
    }

    @Override
    public void showToErrorDialog(String s) {
        new CallFieldDialog5(mActivity, dialog -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
            startActivity(dialIntent);
            dialog.dismiss();

        }, s, "联系客服").show();
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    public void setContentLayout(FilterBean bean) {
        tvContentCity.setText(bean.getRegion());
        tvContentMoney.setText(bean.getAmount());
        tvContentType.setText(bean.getSolutionType());
        tvContentHelp.setText(bean.getRequireType());
    }

    public void setTitleLayout(List<RecommendLawyerBean> beans) {
        if (beans == null || beans.size() == 0) {
            clInfo.setVisibility(View.GONE);

            titleView.getTitleLlayout().setBackgroundResource(R.color.c_ff);
            titleView.setTitle("优选律师");
            return;
        }
        setTitleView();

        bean = beans.get(0);
        imageView(bean.getIconImage(), bean.getBackgroundImage());

        tvScore.setText(bean.getMatchDegreeStr());
        tvName.setText(bean.getName());
        tvName2.setText(bean.getName2());

        setBusinessLayout(bean.getBusinessInfo());
        setSocialFunctionLayout(bean.getSocialFunction());

        List<RequireInfoChildBean> requireInfoChildBeans = new ArrayList<>();
        for (int i = 0; i < bean.getRequireInfo().size(); i++) {
            RequireInfoBean item = bean.getRequireInfo().get(i);
            if (item.getRequires() != null && item.getRequires().size() > 0) {
                for (int j = 0; j < item.getRequires().size(); j++) {
                    item.getRequires().get(j).setRequirementType(item.getRequirementType());
                    item.getRequires().get(j).setParentRequireTypeId(item.getRequireTypeId());
                    item.getRequires().get(j).setParentRequireTypeName(item.getRequireTypeName());
                    item.getRequires().get(j).setParentType(item.getType());
                    requireInfoChildBeans.add(item.getRequires().get(j));
                }
            }
        }

        if (requireInfoChildBeans.size() > 0) {
            initAdapter(requireInfoChildBeans);
            initRecyclerView();
        }
    }

    public void imageView(String imgUrl, String bigUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(imgUrl)
                            .isCircle(true)
                            .imageView(ivHead)
                            .build());

        } else {
            ivHead.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_lawyer_avatar));
        }
        if (!TextUtils.isEmpty(bigUrl)) {
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(imgUrl)
                            .imageView(ivBigImg)
                            .build());
        } else {
            ivBigImg.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_p_bg_1));
        }
    }

    public void setSocialFunctionLayout(List<String> beans) {
        //社会职务
        if (beans != null && beans.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < beans.size(); i++) {
                stringBuilder.append(beans.get(i)).append("\n");
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            setSocialPosition(stringBuilder.toString());
        } else {
            hideSocialPosition();
        }
    }

    public void setSocialPosition(String socialPosition) {
        tvSocialPosition.setText(socialPosition);
    }

    public void hideSocialPosition() {
        tvSocialPosition.setVisibility(View.GONE);
        ivSocialPosition.setVisibility(View.GONE);
    }

    public void setBusinessLayout(List<BusinessInfoBean> beans) {
        //擅长领域
        if (beans != null && beans.size() > 0) {
            removeViews();
            for (int i = 0; i < beans.size(); i++) {
                View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_personal_home_page_field, null, false);
                TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
                tvTitle.setText(beans.get(i).getSolutionTypeName());
                addSimpleFlowLayout(itemView, i);
            }
        } else {
            if (beans == null || beans.size() == 0) {
                hideFieldLayout_1();
            } else {
                hideFieldLayout();
            }
        }
    }

    public void hideFieldLayout() {
        tvField.setVisibility(View.GONE);
        sflField.setVisibility(View.GONE);
    }

    public void hideFieldLayout_1() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) sflField.getLayoutParams();
        layoutParams.height = AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_300));
        sflField.setLayoutParams(layoutParams);
        tvField.setVisibility(View.INVISIBLE);
        sflField.setVisibility(View.INVISIBLE);
    }

    public void addSimpleFlowLayout(View itemView, int i) {
        sflField.addView(itemView, i);
    }

    public void removeViews() {
        if (sflField.getChildCount() > 0) {
            sflField.removeViews(0, sflField.getChildCount());
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

    @Override
    public void onResume() {
        super.onResume();
        if (buryingPointId == 1) {
            switch (requireTypeId) {
                case 2:
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "litigation_arbitration_assistant_success");
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "meeting_assistant_success");
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "enterprise_assistant_success");
                    break;
            }
        } else {
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "assistant_success");
        }
        if(isGoCall){
            showTestDialog2();
            isGoCall = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (buryingPointId == 1) {
            switch (requireTypeId) {
                case 2:
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "litigation_arbitration_assistant_success");
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "meeting_assistant_success");
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "enterprise_assistant_success");
                    break;
            }
        } else {
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "assistant_success");
        }

    }

    //-----电话

    boolean isGoCall = false;
    @Override
    public void GoCall(String str) {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
        startActivity(dialIntent);
        isGoCall = true;
    }

    //查询余额不足
    public void showBalanceNoDialog(ExpertPriceEntity entity){
        showOnlyDialog(entity);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                onlyTextDialog.dismiss();
                bundle.clear();
                bundle.putSerializable(BundleTags.ENTITY,entity);
                launchActivity(new Intent(mActivity, MyAccountActivity.class),bundle);
            });
        }).start();
    }

    OnlyTextDialog onlyTextDialog;
    public void showOnlyDialog(ExpertPriceEntity entity){
        String string = "当前余额剩余通话时长不足%1$s分钟，请充值余额。";
        onlyTextDialog = new OnlyTextDialog(mActivity).setContent(String.format(string,entity.getMinimumDuration()));
        onlyTextDialog.show();
    }

    //查看余额充足
    public void showBalanceYesDialog(ExpertPriceEntity entity){
        new CurrencyDialog2(mActivity,entity)
                .setClickYes(dialog -> {
                    mPresenter.sendCall(bean.getMemberId(),entity.getCallCenterNo());
                })
                .setClickNo(dialog -> {
                    bundle.clear();
                    bundle.putSerializable(BundleTags.ENTITY,entity);
                    launchActivity(new Intent(mActivity, MyAccountActivity.class),bundle);
                }).show();
    }

    public void showTestDialog2(){
        new CurrencyDialog(mActivity)
                .showTitleBg(false)
                .setContent("如问题仍然未解决，您可再次拨打。")
                .setContentLineSpacing(R.dimen.qb_px_20)
                .setContentSize(14)
                .setSubmitStr("已解决")
                .setCannelStr("再次致电")
                .setClickNo(dialog -> {
                    mPresenter.expertPrice(bean.getMemberName(), bean.getMemberId());
                }).show();
    }
}
