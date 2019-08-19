package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1Activity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActivityScope
public class FreeConsultPresenter extends BasePresenter<FreeConsultContract.Model, FreeConsultContract.View> {
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

    private List<RegionEntity> list;//城市集合
    private List<String> allProv = new ArrayList<>();//所有的省
    private Map<String, List<String>> cityMap = new HashMap<>();//key:省p---value:市n  value是一个集合
    private String province = "";//省会
    private String city = "";//城市
    private int regionId;//城市ID

    private boolean anonymous = true;//是否匿名

    private List<SolutionTypeEntity> solutionTypeEntityList = new ArrayList<>();//集合solution
    private List<String> solutionTypeStringList = new ArrayList<>();//string集合solution
    private int consultTypeId;//solution Id
    private String consultType;//solution 名字


    @Inject
    public FreeConsultPresenter(FreeConsultContract.Model model, FreeConsultContract.View rootView) {
        super(model, rootView);
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    //城市
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

    //solution
    public int getConsultTypeId() {
        return consultTypeId;
    }

    public void setConsultTypeId(int consultTypeId) {
        this.consultTypeId = consultTypeId;
    }

    public String getConsultType() {
        return consultType;
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public List<SolutionTypeEntity> getSolutionTypeEntityList() {
        return solutionTypeEntityList;
    }

    public List<String> getSolutionTypeStringList() {
        return solutionTypeStringList;
    }

    public void onCreate() {
        new Thread(this::initJsonData).start();
        solutionTypeEntityList.addAll(new Gson().fromJson(
                DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE)
                , new TypeToken<List<SolutionTypeEntity>>() {
                }.getType()));
        List<SolutionTypeEntity> solutionTypeEntityList2 = new ArrayList<>();

        for (SolutionTypeEntity entity : solutionTypeEntityList) {
            if (entity.getFreeText() == 1) {
                solutionTypeStringList.add(entity.getTypeName());
                solutionTypeEntityList2.add(entity);
            }
        }
        solutionTypeEntityList = solutionTypeEntityList2;
    }

    public void releaseFreeConsult(int consultationTypeId,int regionId ,String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("consultationTypeId", consultationTypeId);
        map.put("regionId", regionId);
        map.put("content", content);
        map.put("isHide", isAnonymous() ? 1 : 0);
        mModel.releaseFreeConsult(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("提交成功");
                            Bundle bundle = new Bundle();
                            bundle.putInt(BundleTags.ID, baseResponse.getData().getConsultationId());
                            bundle.putBoolean(BundleTags.IS_SHOW,true);
                            mRootView.launchActivity(new Intent(mApplication, FreeConsultDetail1Activity.class), bundle);
                            mRootView.killMyself();
                        }
                    }
                });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
