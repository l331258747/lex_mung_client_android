package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.help.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.PayMoneyEntity;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepChildActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHelpStep6Component;
import cn.lex_mung.client_android.di.module.HelpStep6Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep6Contract;
import cn.lex_mung.client_android.mvp.presenter.HelpStep6Presenter;

import cn.lex_mung.client_android.R;

//愿意支付的律师费
public class HelpStep6Fragment extends BaseFragment<HelpStep6Presenter> implements HelpStep6Contract.View {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    EasyDialog easyDialog;

    int payMoneyId = -1;

    public int getPayMoneyIdId() {
        return payMoneyId;
    }

    public static HelpStep6Fragment newInstance(List<PayMoneyEntity> entitys) {
        HelpStep6Fragment fragment = new HelpStep6Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.LIST, (Serializable) entitys);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep6Component
                .builder()
                .appComponent(appComponent)
                .helpStep6Module(new HelpStep6Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step6, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPresenter.onCreate((List<PayMoneyEntity>) getArguments().getSerializable(BundleTags.LIST));
            tvBtn.setText("优选律师");
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
            mPresenter.setPayMoney(data.toString());
            mPresenter.setPayMoneyId(mPresenter.getMoneyList().get(position).getId());
        });
        wpConsultType.setData(mPresenter.getMoneyStrList());
        wpConsultType.setSelectedItemPosition(0);

        mPresenter.setPayMoney(mPresenter.getMoneyList().get(0).getText());
        mPresenter.setPayMoneyId(mPresenter.getMoneyList().get(0).getId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvContent.setText(mPresenter.getPayMoney());
            payMoneyId = mPresenter.getPayMoneyId();
            dismiss();
        });
    }

    @OnClick({R.id.view_select, R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_select:
                showSelectTypeDialog();
                break;
            case R.id.tv_btn:
                if(payMoneyId == -1){
                    showMessage("请选择愿意支付的律师费");
                    return;
                }

                ((HelpStepChildActivity) this.getActivity()).goPreferredLawyer();

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
}
