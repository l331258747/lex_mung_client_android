package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerHelpStep2Component;
import cn.lex_mung.client_android.di.module.HelpStep2Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep2Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.presenter.HelpStep2Presenter;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepChildActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class HelpStep2Fragment extends BaseFragment<HelpStep2Presenter> implements HelpStep2Contract.View,WheelPicker.OnItemSelectedListener  {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.view_bottom)
    View viewBottom;

    EasyDialog easyDialog;

    private WheelPicker wpProvince;
    private WheelPicker wpCity;

    int typeId = -1;
    boolean isShow;

    public int getTypeId() {
        return typeId;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step2, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isShow = getArguments().getBoolean(BundleTags.IS_SHOW,false);
        mPresenter.setList((List<SolutionTypeBean>) getArguments().getSerializable(BundleTags.LIST));
    }

    public static HelpStep2Fragment newInstance(List<SolutionTypeBean> entitys) {
        return newInstance(false, entitys);
    }

    public static HelpStep2Fragment newInstance(boolean isShow, List<SolutionTypeBean> entitys) {
        HelpStep2Fragment fragment = new HelpStep2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.LIST, (Serializable) entitys);
        bundle.putBoolean(BundleTags.IS_SHOW, isShow);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep2Component
                .builder()
                .appComponent(appComponent)
                .helpStep2Module(new HelpStep2Module(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.view_select, R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_select:
                showSelectRegionDialog();
                break;
            case R.id.tv_btn:

                if(isShow){
                    switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                        case 2:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "litigation_arbitration_detail_assistant_goodat_page","litigation_arbitration_detail_assistant_goodat_page_next_click");
                            break;
                        case 6:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "enterprise_detail_assistant_goodat_page","enterprise_detail_assistant_goodat_page_next_click");
                            break;
                        case 9:
                            BuryingPointHelp.getInstance().onEvent(mActivity, "meeting_detail_assistant_goodat_page","meeting_detail_assistant_goodat_page_next_click");
                            break;
                    }
                }else{
                    BuryingPointHelp.getInstance().onEvent(mActivity, "first_assistant_goodat_page","first_assistant_goodat_page_next_click");
                }

                if(typeId == -1){
                    showMessage("请选择服务事项");
                    return;
                }
                if(isShow){
                    ((HelpStepChildActivity) this.getActivity()).setIndex(2);
                }else{
                    ((HelpStepActivity) this.getActivity()).setIndex(2);
                }
                break;
        }
    }

    /**
     * 选择地区
     */
    @SuppressLint("InflateParams")
    private void showSelectRegionDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_wheel_picker_dialog, null);
        initDialog(layout);
        wpProvince = layout.findViewById(R.id.wheel_1);
        wpCity = layout.findViewById(R.id.wheel_2);
        layout.findViewById(R.id.wheel_3).setVisibility(View.GONE);
        wpProvince.setCurved(false);
        wpCity.setCurved(false);

        wpCity.setVisibility(View.VISIBLE);

        wpProvince.setVisibleItemCount(6);
        wpCity.setVisibleItemCount(6);

        wpProvince.setOnItemSelectedListener(this);
        wpCity.setOnItemSelectedListener(this);

        wpProvince.setData(mPresenter.getAllProv());
        int p1 = 0;
        int p2 = 0;
        if (TextUtils.isEmpty(mPresenter.getProvince())) {
            mPresenter.setProvince(mPresenter.getAllProv().get(p1));
        } else {
            p1 = mPresenter.getAllProv().indexOf(mPresenter.getProvince());
        }
        List<String> string2 = mPresenter.getCityMap().get(mPresenter.getProvince());
        if (string2 != null && string2.size() > 0) {
            wpCity.setData(string2);
            if (TextUtils.isEmpty(mPresenter.getCity())) {
                mPresenter.setCity(string2.get(p2));
            } else {
                p2 = string2.indexOf(mPresenter.getCity());
            }
        } else {
            wpCity.setData(new ArrayList<>());
        }
        wpProvince.setSelectedItemPosition(p1);
        wpCity.setSelectedItemPosition(p2);

        mPresenter.setTypeId(mPresenter.getList().get(p1).getChild().get(p2).getSolutionTypeId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvContent.setText(mPresenter.getRegion());
            typeId = mPresenter.getTypeId();
            LogUtil.e("reid:" + typeId);
            dismiss();
        });
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
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (picker == wpProvince) {
            mPresenter.setProvince(data.toString());
            List<String> string2 = mPresenter.getCityMap().get(data.toString());
            if (string2 != null && string2.size() > 0) {
                wpCity.setData(string2);
                mPresenter.setCity(string2.get(0));
                mPresenter.setTypeId(mPresenter.getList().get(position).getChild().get(0).getSolutionTypeId());//选择省级后，市级为下标0
            } else {
                mPresenter.setCity("");
                mPresenter.setTypeId(mPresenter.getList().get(position).getSolutionTypeId());//因为没有市级，选择省级下标
                wpCity.setData(new ArrayList<>());
            }
            wpCity.setSelectedItemPosition(0);
        } else if (picker == wpCity) {
            mPresenter.setCity(data.toString());
            mPresenter.setTypeId(mPresenter.getList().get(wpProvince.getCurrentItemPosition()).getChild().get(position).getSolutionTypeId());//选择市级为position
        }
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
        if(isShow){
            switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                case 2:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "litigation_arbitration_detail_assistant_goodat_page",getPair());
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "meeting_detail_assistant_goodat_page",getPair());
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "enterprise_detail_assistant_goodat_page",getPair());
                    break;
            }
        }else{
            BuryingPointHelp.getInstance().onFragmentResumed(mActivity, "first_assistant_goodat_page",getPair());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isShow){
            switch (((HelpStepChildActivity) this.getActivity()).getRequireTypeId()){
                case 2:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "litigation_arbitration_detail_assistant_goodat_page",getPair());
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "meeting_detail_assistant_goodat_page",getPair());
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "enterprise_detail_assistant_goodat_page",getPair());
                    break;
            }
        }else{
            BuryingPointHelp.getInstance().onFragmentPaused(mActivity, "first_assistant_goodat_page",getPair());
        }
    }
}
