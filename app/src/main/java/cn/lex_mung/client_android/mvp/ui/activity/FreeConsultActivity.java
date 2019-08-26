package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultComponent;
import cn.lex_mung.client_android.di.module.FreeConsultModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultContract;
import cn.lex_mung.client_android.mvp.model.entity.LocationEntity;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LocationUtil;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class FreeConsultActivity extends BaseActivity<FreeConsultPresenter> implements FreeConsultContract.View
        , WheelPicker.OnItemSelectedListener {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_consult_type)
    TextView tvConsultType;
    @BindView(R.id.tv_user_region)
    TextView tvUserRegion;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_anonymous)
    ImageView ivAnonymous;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.view_user_region)
    View viewUserRegion;

    private EasyDialog easyDialog;

    private WheelPicker wpProvince;
    private WheelPicker wpCity;

    private int buryingPointId = -1;

    private int regionId = -1;
    private int consultTypeId = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultModule(new FreeConsultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (buryingPointId == 1) {
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "solution_detail_free_text_from_solution_page", getPair());
        } else {
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "free_text_post_page", getPair());
        }

        if (isGoPermisstions) {
            isGoPermisstions = false;
            getLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (buryingPointId == 1) {
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "solution_detail_free_text_from_solution_page", getPair());
        } else {
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "free_text_post_page", getPair());
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            buryingPointId = bundleIntent.getInt(BundleTags.BURYING_POINT, -1);
        }
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);
        mPresenter.onCreate();

        mPresenter.getLocationPermission();
    }

    @OnClick({R.id.tv_right, R.id.view_consult_type, R.id.view_user_region, R.id.iv_anonymous})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_right:
                if (consultTypeId == -1) {
                    showMessage("请选择问题类型");
                    return;
                }
                if (regionId == -1) {
                    showMessage("请选择服务区域");
                    return;
                }
                String content = etInput.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showMessage("请输入您遇到的问题");
                    return;
                }
                if (!TextUtils.isEmpty(content) && content.length() < 10){
                    showMessage("至少输入10个字");
                    return;
                }
                BuryingPointHelp.getInstance().onEvent(mActivity, "free_text_post_page", "free_text_post_page_post_click");
                mPresenter.releaseFreeConsult(mPresenter.getConsultTypeId(), mPresenter.getRegionId(), etInput.getText().toString());
                break;
            case R.id.view_consult_type:
                showSelectTypeDialog();
                break;
            case R.id.view_user_region:
                showSelectRegionDialog();
                break;
            case R.id.iv_anonymous:
                if (mPresenter.isAnonymous()) {
                    mPresenter.setAnonymous(false);
                    ivAnonymous.setImageResource(R.drawable.ic_hide_select);
                } else {
                    mPresenter.setAnonymous(true);
                    ivAnonymous.setImageResource(R.drawable.ic_show_select);
                }
                break;
        }
    }

    /**
     * 选择问题类型
     * 选择问题对话框，改变 P 里面的值
     * 对话框选择，改变 P 里面的值
     * 确定后 改变 P 里面的值 并赋值 A consultTypeId
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            mPresenter.setConsultType(data.toString());
            mPresenter.setConsultTypeId(mPresenter.getSolutionTypeEntityList().get(position).getId());
        });
        wpConsultType.setData(mPresenter.getSolutionTypeStringList());
        wpConsultType.setSelectedItemPosition(0);

        mPresenter.setConsultType(mPresenter.getSolutionTypeEntityList().get(0).getTypeName());
        mPresenter.setConsultTypeId(mPresenter.getSolutionTypeEntityList().get(0).getId());

        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvConsultType.setText(mPresenter.getConsultType());
            consultTypeId = mPresenter.getConsultTypeId();
            dismiss();
        });
    }

    /**
     * 选择地区
     * 1, 先通过定位给 P regionId赋值,并赋值 A regionId
     * 2，城市对话框，改变 P 里面的值
     * 3，对话框选择，改变 P 里面的值
     * 4，确定后 改变 P 里面的值 并赋值 A regionId
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
            tvUserRegion.setText(mPresenter.getRegion());
            regionId = mPresenter.getRegionId();
            LogUtil.e("reid:" + regionId);
            dismiss();
        });
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

    /**
     * 初始化dialog
     */
    private void initDialog(View layout) {
        easyDialog = new EasyDialog(this)
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

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    private DefaultDialog defaultDialog;
    private boolean isGoPermisstions;//如果拒绝了，不需要再提示

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                startActivity(intent);
                dialog.dismiss();
                isGoPermisstions = true;
            }
                    , getString(R.string.text_manual_open_permissions)
                    , getString(R.string.text_to_open)
                    , getString(R.string.text_cancel));
        }
        if (!defaultDialog.isShowing()) {
            defaultDialog.show();
        }
    }

    @Override
    public void getLocation() {
        LocationUtil locationUtil = new LocationUtil();
        tvUserRegion.setText("定位中...");
        viewUserRegion.setEnabled(false);
        locationUtil.startLocation(getActivity().getApplicationContext(), new LocationUtil.LocationListener() {
            @Override
            public void getAdress(int code, LocationEntity adress) {
                LogUtil.e("code:" + code + " adress:" + adress);
                if (code == 0) {
                    if (mPresenter.getCityStrToData(adress.getCity())) {
                        tvUserRegion.setText(mPresenter.getRegion());
                        regionId = mPresenter.getRegionId();
                        LogUtil.e("reid:" + regionId);
                        viewUserRegion.setEnabled(true);
                    }
                } else {
                    tvUserRegion.setText("");
                    viewUserRegion.setEnabled(true);
                }
            }
        });
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
    public Activity getActivity() {
        return this;
    }
}
