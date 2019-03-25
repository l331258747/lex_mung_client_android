package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.MapPickerModule;
import cn.lex_mung.client_android.mvp.ui.adapter.MapPickerAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerMapPickerComponent;
import cn.lex_mung.client_android.mvp.contract.MapPickerContract;
import cn.lex_mung.client_android.mvp.presenter.MapPickerPresenter;

import cn.lex_mung.client_android.R;

import org.simple.eventbus.Subscriber;

import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.MAP_INFO.MAP_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.MAP_INFO.SELECT_PLACE;

public class MapPickerActivity extends BaseActivity<MapPickerPresenter> implements MapPickerContract.View
        , LocationSource
        , AMapLocationListener
        , AMap.OnCameraChangeListener
        , PoiSearch.OnPoiSearchListener {
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recycler_view_map)
    RecyclerView recyclerViewMap;

    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;
    private LatLng latlng;
    private String city;
    private AMap aMap;
    private PoiSearch.Query query;// Poi查询条件类
    private List<PoiItem> poiItems;// poi数据
    private MapPickerAdapter mapPickerAdapter;
    private boolean isRefreshPoiList = true;//是否刷新下面列表数据
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapPickerComponent
                .builder()
                .appComponent(appComponent)
                .mapPickerModule(new MapPickerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map_picker;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.text_send));
        mapView.onCreate(savedInstanceState);
        AppUtils.configRecyclerView(recyclerViewMap, new LinearLayoutManager(mActivity));
        initLocation();
    }

    @OnClick({R.id.tv_right, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                bundle.clear();
                bundle.putDouble(BundleTags.KEY_LAT, poiItems.get(mapPickerAdapter.getPos()).getLatLonPoint().getLatitude());
                bundle.putDouble(BundleTags.KEY_LNG, poiItems.get(mapPickerAdapter.getPos()).getLatLonPoint().getLongitude());
                bundle.putString(BundleTags.KEY_TITLE, poiItems.get(mapPickerAdapter.getPos()).getTitle() + "&" + poiItems.get(mapPickerAdapter.getPos()).getSnippet());
                bundle.putInt(BundleTags.KEY_LEVEL, (int) aMap.getCameraPosition().zoom);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                killMyself();
                break;
            case R.id.iv_search:
                if (TextUtils.isEmpty(city)) return;
                launchActivity(new Intent(mActivity, MapSearchActivity.class));
                break;
        }

    }

    /**
     * 选择地点
     *
     * @param message message
     */
    @Subscriber(tag = MAP_INFO)
    private void selectPlace(Message message) {
        switch (message.what) {
            case SELECT_PLACE:
                PoiItem poiItem = (PoiItem) message.obj;
                LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                isRefreshPoiList = true;
                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 17, 30, 0)), 300, null);
                break;
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);
            setUpMap();
        }
    }

    private void setUpMap() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        int currentPage = 0;
        query = new PoiSearch.Query("", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(latlng.latitude, latlng.longitude);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 显示我的位置
                mListener.onLocationChanged(aMapLocation);
                //设置第一次焦点中心
                latlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                city = aMapLocation.getCity();
                doSearchQuery();
            } else {
                showMessage("定位失败");
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latlng = cameraPosition.target;
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point)));
        if (isRefreshPoiList) {
            doSearchQuery();
        }
        isRefreshPoiList = true;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiItems = result.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        mapPickerAdapter = new MapPickerAdapter(poiItems);
                        recyclerViewMap.setAdapter(mapPickerAdapter);
                        mapPickerAdapter.setOnItemClickListener((adapter, view, position) -> {
                            isRefreshPoiList = false;
                            LatLonPoint latLonPoint = poiItems.get(position).getLatLonPoint();
                            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 17, 30, 0)), 300, null);
                            mapPickerAdapter.setPos(position);
                            mapPickerAdapter.notifyDataSetChanged();
                        });
                    }
                } else {
                    showMessage("无结果");
                }
            }
        } else {
            showMessage("无结果");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationClient.startLocation();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.onDestroy();
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
