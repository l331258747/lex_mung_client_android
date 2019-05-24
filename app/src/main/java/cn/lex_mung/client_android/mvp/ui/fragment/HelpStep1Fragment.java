package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHelpStep1Component;
import cn.lex_mung.client_android.di.module.HelpStep1Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep1Contract;
import cn.lex_mung.client_android.mvp.presenter.HelpStep1Presenter;

import cn.lex_mung.client_android.R;

public class HelpStep1Fragment extends BaseFragment<HelpStep1Presenter> implements HelpStep1Contract.View, WheelPicker.OnItemSelectedListener {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.view_bottom)
    View viewBottom;

    EasyDialog easyDialog;

    private WheelPicker wpProvince;
    private WheelPicker wpCity;

    private int regionId;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step1, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate();
    }


    @OnClick({R.id.view_select, R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.view_select:
                showSelectRegionDialog();
                break;
            case R.id.tv_btn:
                ((HelpStepActivity)this.getActivity()).setIndex(1);
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

        mPresenter.setRegionId(mPresenter.getList().get(p1).getChild().get(p2).getRegionId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvContent.setText(mPresenter.getRegion());
            regionId = mPresenter.getRegionId();
            LogUtil.e("reid:" + regionId);
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

    public static HelpStep1Fragment newInstance() {
        HelpStep1Fragment fragment = new HelpStep1Fragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep1Component
                .builder()
                .appComponent(appComponent)
                .helpStep1Module(new HelpStep1Module(this))
                .build()
                .inject(this);
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
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (picker == wpProvince) {
            mPresenter.setProvince(data.toString());
            List<String> string2 = mPresenter.getCityMap().get(data.toString());
            if (string2 != null && string2.size() > 0) {
                wpCity.setData(string2);
                mPresenter.setCity(string2.get(0));
                mPresenter.setRegionId(mPresenter.getList().get(position).getChild().get(0).getRegionId());//选择省级后，市级为下标0
            } else {
                mPresenter.setCity("");
                mPresenter.setRegionId(mPresenter.getList().get(position).getRegionId());//因为没有市级，选择省级下标
                wpCity.setData(new ArrayList<>());
            }
            wpCity.setSelectedItemPosition(0);
        } else if (picker == wpCity) {
            mPresenter.setCity(data.toString());
            mPresenter.setRegionId(mPresenter.getList().get(wpProvince.getCurrentItemPosition()).getChild().get(position).getRegionId());//选择市级为position
        }
    }
}
