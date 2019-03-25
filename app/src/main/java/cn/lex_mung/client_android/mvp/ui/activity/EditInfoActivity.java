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
import cn.lex_mung.client_android.di.component.DaggerEditInfoComponent;
import cn.lex_mung.client_android.di.module.EditInfoModule;
import cn.lex_mung.client_android.mvp.contract.EditInfoContract;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.presenter.EditInfoPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.CustomDatePicker;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class EditInfoActivity extends BaseActivity<EditInfoPresenter> implements EditInfoContract.View
        , WheelPicker.OnItemSelectedListener {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.tv_user_sex)
    TextView tvUserSex;
    @BindView(R.id.tv_user_date)
    TextView tvUserDate;
    @BindView(R.id.tv_user_region)
    TextView tvUserRegion;
    @BindView(R.id.tv_user_industry)
    TextView tvUserIndustry;
    @BindView(R.id.et_user_enterprise)
    EditText etUserEnterprise;
    @BindView(R.id.et_user_position)
    EditText etUserPosition;
    @BindView(R.id.et_user_email)
    EditText etUserEmail;
    @BindView(R.id.view_bottom)
    View viewBottom;

    private EasyDialog easyDialog;
    private DefaultDialog defaultDialog;
    private WheelPicker wpProvince;
    private WheelPicker wpCity;
    private WheelPicker wpArea;
    private WheelPicker wpIndustry;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEditInfoComponent
                .builder()
                .appComponent(appComponent)
                .editInfoModule(new EditInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edit_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setText(R.string.text_save);
        tvRight.setVisibility(View.VISIBLE);
        mPresenter.onCreate();
    }

    @Override
    public void setAvatar(String path) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(path)
                        .imageView(ivAvatar)
                        .isCircle(true)
                        .build());
    }

    @Override
    public void setUserName(String memberName) {
        etUserName.setText(memberName);
    }

    @Override
    public void setUserSex(String sex) {
        tvUserSex.setText(sex);
    }

    @Override
    public void setBirthday(String birthday) {
        tvUserDate.setText(birthday);
    }

    @Override
    public void setRegion(String region) {
        tvUserRegion.setText(region);
    }

    @Override
    public void setIndustryName(String industry) {
        tvUserIndustry.setText(industry);
    }

    @Override
    public void setInstitutionName(String institutionName) {
        etUserEnterprise.setText(institutionName);
    }

    @Override
    public void setPositionName(String memberPositionName) {
        etUserPosition.setText(memberPositionName);
    }

    @Override
    public void setEmail(String email) {
        etUserEmail.setText(email);
    }

    @OnClick({
            R.id.tv_right
            , R.id.view_avatar
            , R.id.view_user_sex
            , R.id.view_user_date
            , R.id.view_user_region
            , R.id.view_user_industry
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                mPresenter.updateUserInfo(etUserName.getText().toString()
                        , tvUserSex.getText().toString()
                        , tvUserDate.getText().toString()
                        , etUserEnterprise.getText().toString()
                        , etUserPosition.getText().toString()
                        , etUserEmail.getText().toString()
                );
                break;
            case R.id.view_avatar:
                mPresenter.getLaunchCameraPermission();
                break;
            case R.id.view_user_sex:
                showSelectSexDialog();
                break;
            case R.id.view_user_date:
                showSelectDateDialog();
                break;
            case R.id.view_user_region:
                showSelectRegionDialog();
                break;
            case R.id.view_user_industry:
                showSelectIndustryDialog();
                break;
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

    /**
     * 拍照或者从相册中选择
     */
    @Override
    @SuppressLint("InflateParams")
    public void showSelectPhotoDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_dialog, null);
        initDialog(layout);
        layout.findViewById(R.id.tv_one).setOnClickListener(v -> {
            mPresenter.getImageFromCamera();
            dismiss();
        });
        layout.findViewById(R.id.tv_two).setOnClickListener(v -> {
            mPresenter.getImageFromAlbum();
            dismiss();
        });
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
    }

    /**
     * 选择性别dialog
     */
    @SuppressLint("InflateParams")
    private void showSelectSexDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_dialog, null);
        initDialog(layout);
        TextView tvMan = layout.findViewById(R.id.tv_one);
        TextView tvWoman = layout.findViewById(R.id.tv_two);
        tvMan.setText("男");
        tvWoman.setText("女");
        tvMan.setOnClickListener(v -> {
            tvUserSex.setText("男");
            dismiss();
        });
        tvWoman.setOnClickListener(v -> {
            tvUserSex.setText("女");
            dismiss();
        });
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
    }

    /**
     * 选择日期
     */
    private void showSelectDateDialog() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String date = sdf.format(new Date());
        CustomDatePicker customDatePicker = new CustomDatePicker(this
                , "1900-01-01 00:00"
                , date
                , viewBottom
                , true);
        customDatePicker.setHandler((date1, time, easyDialog) -> {
            tvUserDate.setText(time.split(" ")[0]);
            easyDialog.dismiss();
        });
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(true); // 不允许循环滚动
        String str = tvUserDate.getText().toString();
        if (TextUtils.isEmpty(str)) {
            customDatePicker.show(date.split(" ")[0]);
        } else {
            customDatePicker.show(str);
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
        wpArea = layout.findViewById(R.id.wheel_3);
        wpProvince.setCurved(false);
        wpCity.setCurved(false);
        wpArea.setCurved(false);


        wpCity.setVisibility(View.VISIBLE);
        wpArea.setVisibility(View.VISIBLE);

        wpProvince.setVisibleItemCount(6);
        wpCity.setVisibleItemCount(6);
        wpArea.setVisibleItemCount(6);

        wpProvince.setOnItemSelectedListener(this);
        wpCity.setOnItemSelectedListener(this);
        wpArea.setOnItemSelectedListener(this);

        wpProvince.setData(mPresenter.getAllProv());
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
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
            List<String> string3 = mPresenter.getAreaMap().get(mPresenter.getCity());
            if (string3 != null && string3.size() > 0) {
                wpArea.setData(string3);
                if (TextUtils.isEmpty(mPresenter.getArea())) {
                    mPresenter.setArea(string3.get(p3));
                } else {
                    p3 = string3.indexOf(mPresenter.getArea());
                }
            } else {
                wpArea.setData(new ArrayList<>());
            }
        } else {
            wpCity.setData(new ArrayList<>());
            wpArea.setData(new ArrayList<>());
        }
        wpProvince.setSelectedItemPosition(p1);
        wpCity.setSelectedItemPosition(p2);
        wpArea.setSelectedItemPosition(p3);
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
                List<String> string3 = mPresenter.getAreaMap().get(mPresenter.getCity());
                if (string3 != null && string3.size() > 0) {
                    mPresenter.setArea(string3.get(0));
                    wpArea.setData(string3);
                } else {
                    wpArea.setData(new ArrayList<>());
                }
            } else {
                mPresenter.setCity("");
                mPresenter.setArea("");
                wpCity.setData(new ArrayList<>());
                wpArea.setData(new ArrayList<>());
            }
            wpCity.setSelectedItemPosition(0);
            wpArea.setSelectedItemPosition(0);
        } else if (picker == wpCity) {
            mPresenter.setCity(data.toString());
            List<String> string3 = mPresenter.getAreaMap().get(data.toString());
            if (string3 != null && string3.size() > 0) {
                mPresenter.setArea(string3.get(0));
                wpArea.setData(string3);
            } else {
                wpArea.setData(new ArrayList<>());
            }
            wpArea.setSelectedItemPosition(0);
        } else if (picker == wpArea) {
            mPresenter.setArea(data.toString());
        } else if (picker == wpIndustry) {
            mPresenter.setIndustry(data.toString());
        }
    }

    /**
     * 选择行业
     */
    @SuppressLint("InflateParams")
    private void showSelectIndustryDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        wpIndustry = layout.findViewById(R.id.wheel_1);
        wpIndustry.setCurved(false);
        wpIndustry.setVisibleItemCount(6);
        wpIndustry.setOnItemSelectedListener(this);
        wpIndustry.setData(mPresenter.getIndustryStringList());
        wpIndustry.setSelectedItemPosition(0);
        mPresenter.setIndustry(mPresenter.getIndustryStringList().get(0));
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            tvUserIndustry.setText(mPresenter.getIndustry());
            dismiss();
        });
    }

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                dialog.dismiss();
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
    public void launchActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        finish();
    }
}
