package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultComponent;
import cn.lex_mung.client_android.di.module.FreeConsultModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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

    private EasyDialog easyDialog;

    private WheelPicker wpProvince;
    private WheelPicker wpCity;

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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("w_y_shouye_mfzx_wz_list");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("w_y_shouye_mfzx_wz_list");
        MobclickAgent.onPause(mActivity);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setText(R.string.text_submit);
        tvRight.setVisibility(View.VISIBLE);
        mPresenter.onCreate();
    }

    @OnClick({R.id.tv_right, R.id.view_consult_type, R.id.view_user_region, R.id.iv_anonymous})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                MobclickAgent.onEvent(mActivity, "w_y_shouye_mfzx_wz_list_wztj");
                mPresenter.releaseFreeConsult(etInput.getText().toString());
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
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> mPresenter.setConsultType(data.toString()));
        wpConsultType.setData(mPresenter.getSolutionTypeStringList());
        wpConsultType.setSelectedItemPosition(0);
        mPresenter.setConsultType(mPresenter.getSolutionTypeStringList().get(0));
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvConsultType.setText(mPresenter.getConsultType());
            dismiss();
        });
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
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvUserRegion.setText(mPresenter.getRegion());
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
            } else {
                mPresenter.setCity("");
                wpCity.setData(new ArrayList<>());
            }
            wpCity.setSelectedItemPosition(0);
        } else if (picker == wpCity) {
            mPresenter.setCity(data.toString());
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
}
