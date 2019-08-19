package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep1Contract;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MapPickerActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.PermissionUtil;


@FragmentScope
public class HelpStep1Presenter extends BasePresenter<HelpStep1Contract.Model, HelpStep1Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    private List<RegionEntity> list;
    private List<String> allProv = new ArrayList<>();//所有的省
    private Map<String, List<String>> cityMap = new HashMap<>();//key:省p---value:市n  value是一个集合
    private String province = "";
    private String city = "";
    private int regionId;


    @Inject
    public HelpStep1Presenter(HelpStep1Contract.Model model, HelpStep1Contract.View rootView) {
        super(model, rootView);
    }

    public void onCreate() {
        new Thread(this::initJsonData).start();
    }

    public List<RegionEntity> getList() {
        return list;
    }

    public List<String> getAllProv() {
        return allProv;
    }

    public Map<String, List<String>> getCityMap() {
        return cityMap;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getRegion() {
        if ("钓鱼岛".equals(province)) {
            return province;
        } else {
            return province + "-" + city;
        }
    }

    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = mApplication.getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, StandardCharsets.UTF_8));//根据字节长度设置编码
            }
            is.close();//关闭流
            list = new Gson().fromJson(sb.toString(), new TypeToken<List<RegionEntity>>() {
            }.getType());
            for (RegionEntity entity : list == null ? new ArrayList<RegionEntity>() : list) {
                allProv.add(entity.getName());//封装所有的省
                //所有的市
                List<String> allCity = new ArrayList<>();//所有市的长度
                for (RegionEntity entity1 : entity.getChild() == null ? new ArrayList<RegionEntity>() : entity.getChild()) {
                    allCity.add(entity1.getName());//封装市集合
                }
                cityMap.put(entity.getName(), allCity);//某个省取出所有的市,
            }
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage());
        }
    }

    public boolean getCityStrToData(String city) {
        for (int i = 0; i < list.size(); i++) {
            List<RegionEntity> list1 = list.get(i).getChild();
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(j).getName().startsWith(city)) {
                    setProvince(list.get(i).getName());
                    setCity(list1.get(j).getName());
                    setRegionId(list1.get(j).getRegionId());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取定位权限
     */
    public void getLocationPermission() {
        PermissionUtil.location(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.getLocation();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法发送位置信息");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
