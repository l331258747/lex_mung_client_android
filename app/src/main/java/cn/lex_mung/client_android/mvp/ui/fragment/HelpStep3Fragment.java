package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerHelpStep3Component;
import cn.lex_mung.client_android.di.module.HelpStep3Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep3Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.PayMoneyEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.RequirementInvolveAmountBean;
import cn.lex_mung.client_android.mvp.presenter.HelpStep3Presenter;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepChildActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

//涉及金额 标的额
public class HelpStep3Fragment extends BaseFragment<HelpStep3Presenter> implements HelpStep3Contract.View {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_tip)
    TextView tv_tip;

    EasyDialog easyDialog;

    int amountId = -1;
    boolean isShow;

    int payLawyerMoneyId = -1;

    public int getAmountId() {
        return amountId;
    }

    public int getPayLawyerMoneyId(){
        return payLawyerMoneyId;
    }

    public static HelpStep3Fragment newInstance(List<RequirementInvolveAmountBean> entitys, List<PayMoneyEntity> payMoneyEntities) {
        return newInstance(false,entitys,payMoneyEntities);
    }

    public static HelpStep3Fragment newInstance(boolean isShow, List<RequirementInvolveAmountBean> entitys, List<PayMoneyEntity> payMoneyEntities) {
        HelpStep3Fragment fragment = new HelpStep3Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.LIST, (Serializable) entitys);
        bundle.putSerializable(BundleTags.LIST_2, (Serializable) payMoneyEntities);
        bundle.putBoolean(BundleTags.IS_SHOW, isShow);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep3Component
                .builder()
                .appComponent(appComponent)
                .helpStep3Module(new HelpStep3Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step3, container, false);
    }

    boolean isCreate;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            isShow = getArguments().getBoolean(BundleTags.IS_SHOW);
            tvBtn.setText("优选律师");

            mPresenter.onCreate((List<RequirementInvolveAmountBean>) getArguments().getSerializable(BundleTags.LIST));
            mPresenter.onCreate2((List<PayMoneyEntity>) getArguments().getSerializable(BundleTags.LIST_2));

            isCreate = true;

            if(requireType == 6){
                tv_title.setText("愿意支付的费用");
                tv_tip.setText("法律顾问的律师费为按年收费，愿意支付费用的多少会直接影响推荐律师的执业年限、行业经验等。");
            }else{
                tv_title.setText("涉案金额");
                tv_tip.setText("涉及金额是法律服务中一项重要的判断依据，会直接影响到推荐给您的法律服务。");
            }
        }
    }

    int requireType;
    public void setType(int requireType){
        this.requireType = requireType;

        if(!isCreate) return;

        mPresenter.setMoney("");
        mPresenter.setPayLawyerMoney("");
        mPresenter.setAmountId(-1);
        mPresenter.setPayLawyerMoneyId(-1);

        tvContent.setText(mPresenter.getMoney());

        amountId = mPresenter.getAmountId();
        payLawyerMoneyId = mPresenter.getPayLawyerMoneyId();

        if(requireType == 6){
            tv_title.setText("愿意支付的费用");
        }else{
            tv_title.setText("涉案金额");
        }

    }

    /**
     * 选择问题类型
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            mPresenter.setMoney(data.toString());
            mPresenter.setAmountId(mPresenter.getMoneyList().get(position).getAmountId());
        });
        wpConsultType.setData(mPresenter.getMoneyStrList());
        wpConsultType.setSelectedItemPosition(0);

        mPresenter.setMoney(mPresenter.getMoneyList().get(0).getAmountName());
        mPresenter.setAmountId(mPresenter.getMoneyList().get(0).getAmountId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvContent.setText(mPresenter.getMoney());
            amountId = mPresenter.getAmountId();
            dismiss();
        });
    }

    /**
     * 选择问题类型
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog2() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            mPresenter.setPayLawyerMoney(data.toString());
            mPresenter.setPayLawyerMoneyId(mPresenter.getPayLawyerMoneyList().get(position).getId());
        });
        wpConsultType.setData(mPresenter.getPayLawyerMoneyStrList());
        wpConsultType.setSelectedItemPosition(0);

        mPresenter.setPayLawyerMoney(mPresenter.getPayLawyerMoneyList().get(0).getText());
        mPresenter.setPayLawyerMoneyId(mPresenter.getPayLawyerMoneyList().get(0).getId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvContent.setText(mPresenter.getPayLawyerMoney());
            payLawyerMoneyId = mPresenter.getPayLawyerMoneyId();
            dismiss();
        });
    }


    @OnClick({R.id.view_select, R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_select:
                if(requireType == 6){
                    showSelectTypeDialog2();
                }else{
                    showSelectTypeDialog();
                }
                break;
            case R.id.tv_btn:

                if(isShow){
                    switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                        case 2:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_assistant_target_amount_page","litigation_arbitration_detail_assistant_target_amount_page_next_click");
                            break;
                        case 6:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail_assistant_target_amount_page","enterprise_detail_assistant_target_amount_page_next_click");
                            break;
                        case 9:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail_assistant_target_amount_page","meeting_detail_assistant_target_amount_page_next_click");
                            break;
                    }
                }else{
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_assistant_target_amount_page","first_assistant_target_amount_page_next_click");
                }

                if(requireType == 6 && payLawyerMoneyId == -1){
                    showMessage("请选择愿意支付的律师费用");
                    return;
                }

                if(requireType != 6 && amountId == -1){
                    showMessage("请选择涉及金额");
                    return;
                }

                if(isShow){
                    ((HelpStepChildActivity) this.getActivity()).goPreferredLawyer();
                }else{
//                    ((HelpStepActivity) this.getActivity()).setIndex(3);
                    ((HelpStepActivity)this.getActivity()).goPreferredLawyer();
                }

                break;
        }
    }

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    /**
     * 初始化dialog
     */
    private void initDialog(View layout) {
        easyDialog = new EasyDialog(mActivity)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(mActivity, R.color.c_00))
                .setLocationByAttachedView(viewBottom)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mActivity, R.color.c_50))
                .show();
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

    @Override
    public void onResume() {
        super.onResume();
//        BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "assistant_target_amount");

        if(isShow){
            switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                case 2:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "litigation_arbitration_detail_assistant_target_amount_page",getPair());
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "meeting_detail_assistant_target_amount_page",getPair());
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "enterprise_detail_assistant_target_amount_page",getPair());
                    break;
            }
        }else{
            BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "first_assistant_target_amount_page",getPair());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "assistant_target_amount");


        if(isShow){
            switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                case 2:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "litigation_arbitration_detail_assistant_target_amount_page",getPair());
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "meeting_detail_assistant_target_amount_page",getPair());
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "enterprise_detail_assistant_target_amount_page",getPair());
                    break;
            }
        }else{
            BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "first_assistant_target_amount_page",getPair());
        }
    }
}
