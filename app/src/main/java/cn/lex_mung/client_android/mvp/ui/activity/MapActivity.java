package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.MapModule;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerMapComponent;
import cn.lex_mung.client_android.mvp.contract.MapContract;
import cn.lex_mung.client_android.mvp.presenter.MapPresenter;

import cn.lex_mung.client_android.R;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity<MapPresenter> implements MapContract.View {

    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_title_1)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.view_bottom)
    View viewBottom;

    private String title;
    private String address;
    private double latitude;
    private double longitude;
    private int scale;

    private EasyDialog easyDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapComponent
                .builder()
                .appComponent(appComponent)
                .mapModule(new MapModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (bundleIntent != null) {
            String string = bundleIntent.getString(BundleTags.KEY_TITLE);
            if (!TextUtils.isEmpty(string) && string.contains("&")) {
                title = string.split("&")[0];
                address = string.split("&")[1];
            } else {
                title = string;
            }
            latitude = bundleIntent.getDouble(BundleTags.KEY_LAT);
            longitude = bundleIntent.getDouble(BundleTags.KEY_LNG);
            scale = bundleIntent.getInt(BundleTags.KEY_LEVEL);
        }
        AMap aMap = mapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(scale));
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), scale, 30, 0)), 300, null);
        LatLng latLng = new LatLng(latitude, longitude);
        aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point)));
        tvTitle.setText(title);
        tvAddress.setText(address);
    }

    @OnClick(R.id.iv_navigation)
    public void onViewClicked() {
        if (isInstall("com.baidu.BaiduMap")
                && isInstall("com.autonavi.minimap")) {
            showSelectNavigationDialog();
        } else if (isInstall("com.baidu.BaiduMap")) {
            goToBaidu();
        } else if (isInstall("com.autonavi.minimap")) {
            goToGaode();
        } else {
            showMessage("没有可用的地图导航！");
        }
    }

    /**
     * 跳转到百度导航
     */
    private void goToBaidu() {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:" + latitude + "," + longitude + "|name:" + title + "&mode=driving&coord_type=gcj02&src=com.lex_mung.client_android#Intent;" + "scheme=bdapp;package=com.baidu.BaiduMap;end");
            startActivity(intent);
        } catch (URISyntaxException ignored) {
        }
    }

    //跳转到高德导航
    private void goToGaode() {
        try {
            String string = "androidamap://route?sourceApplication=" + "amap" +
                    "&dlat=" + latitude +
                    "&dlon=" + longitude +
                    "&dname=" + title +
                    "&dev=" + 0 +
                    "&t=" + 0;
            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(string));
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageName：应用包名
     * @return boolean
     */
    private boolean isInstall(String packageName) {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> infoList = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); i++) {
                String packName = infoList.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    /**
     * 弹出导航dialog
     */
    @SuppressLint("InflateParams")
    public void showSelectNavigationDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_dialog, null);
        initDialog(layout);
        ((TextView) layout.findViewById(R.id.tv_one)).setText("高德地图");
        ((TextView) layout.findViewById(R.id.tv_two)).setText("百度地图");
        layout.findViewById(R.id.tv_one).setOnClickListener(v -> {
            goToGaode();
            dismiss();
        });
        layout.findViewById(R.id.tv_two).setOnClickListener(v -> {
            goToBaidu();
            dismiss();
        });
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
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

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
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
