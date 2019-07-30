package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

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

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.LaunchContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.LaunchLocationEntity;
import cn.lex_mung.client_android.utils.LocationUtil;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;


@ActivityScope
public class LaunchPresenter extends BasePresenter<LaunchContract.Model, LaunchContract.View> {
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

    @Inject
    public LaunchPresenter(LaunchContract.Model model, LaunchContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate() {
        new Thread(this::initJsonData).start();
        getAllDepreg();
        getPermission();
    }

    public void getLocationPermission() {
        PermissionUtil.location(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                getLocation(true);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                getLocation(false);
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                getLocation(false);
            }
        }, mRxPermissions, mErrorHandler);
    }

    public void getPermission() {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                getLocationPermission();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                getLocationPermission();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                getLocationPermission();
            }
        }, mRxPermissions, mErrorHandler);
    }

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

    public void getLocation(boolean isPermission) {
        if (!isPermission) {
            cityjson(false);
            return;
        }

        LocationUtil locationUtil = new LocationUtil();
        locationUtil.startLocation(mRootView.getActivity().getApplicationContext(), (code, adress)->{
            if (code == 0) {
                cityjson(true, getCityStrToData(adress.getCity()));
            } else {
                cityjson(false);
            }
        });
    }

    public void cityjson(boolean isLocation, int cityId) {
        LogUtil.e("手机定位cityId： " + cityId);
        if (isLocation) {
            DataHelper.setIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION, cityId);
            mRootView.launch();
            return;
        }

        mModel.cityjson()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LaunchLocationEntity>(mErrorHandler) {
                    @Override
                    public void onNext(LaunchLocationEntity baseResponse) {
                        LogUtil.e("ip定位cityId： " + baseResponse.getAdcodeInt());
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION, baseResponse.getAdcodeInt());
                        mRootView.launch();
                    }

                    @Override
                    public void onError(Throwable t) {//如果不这样设置，会导致进入登录页面。在进入mainactivity
                        LogUtil.e("ip定位失败cityId： " + 0);
                        DataHelper.setIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION, 430100);//默认定位长沙
                        mRootView.launch();
                    }
                });
    }

    public void cityjson(boolean isLocation) {
        this.cityjson(isLocation, 0);
    }

    //str 为长沙   通过本地json数据判断str
    public int getCityStrToData(String str) {
        for (int i = 0; i < list.size(); i++) {
            List<RegionEntity> list1 = list.get(i).getChild();
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(j).getName().startsWith(str)) {
                    return list1.get(j).getRegionId();
                }
            }
        }
        return 0;
    }

    private void getAllDepreg() {
        mModel.appStartUp()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                    }

                    @Override
                    public void onError(Throwable t) {//如果不这样设置，会导致进入登录页面。在进入mainactivity
                    }
                });
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
