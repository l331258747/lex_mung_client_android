package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

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
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetailActivity;

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

    private List<SolutionTypeEntity> solutionTypeEntityList = new ArrayList<>();
    private List<String> solutionTypeStringList = new ArrayList<>();
    private String consultType;
    private List<RegionEntity> list;
    private List<String> allProv = new ArrayList<>();//所有的省
    private Map<String, List<String>> cityMap = new HashMap<>();//key:省p---value:市n  value是一个集合
    private String province = "";
    private String city = "";
    private String region = "";
    private boolean anonymous = true;


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

    public String getRegion() {
        if ("钓鱼岛".equals(province)) {
            return region = province;
        } else {
            return region = province + "-" + city;
        }
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public String getConsultType() {
        return consultType;
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
        for (SolutionTypeEntity entity : solutionTypeEntityList) {
            solutionTypeStringList.add(entity.getTypeName());
        }
    }

    public void releaseFreeConsult(String content) {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(consultType)) {
            int consultationTypeId = 0;
            for (SolutionTypeEntity entity : solutionTypeEntityList) {
                if (consultType.equals(entity.getTypeName())) {
                    consultationTypeId = entity.getId();
                    break;
                }
            }
            map.put("consultationTypeId", consultationTypeId);
        } else {
            mRootView.showMessage("请选择问题类型");
            return;
        }
        if (!TextUtils.isEmpty(province)
                && !TextUtils.isEmpty(city)) {
            int regionId = 0;
            for (RegionEntity entity : list) {
                if (entity.getName().equals(province)) {
                    for (RegionEntity entity1 : entity.getChild()) {
                        if (entity1.getName().equals(city)) {
                            regionId = entity1.getRegionId();
                            break;
                        }
                    }
                    break;
                }
            }
            map.put("regionId", regionId);
        } else {
            mRootView.showMessage("请选择所在地区");
            return;
        }
        if (!TextUtils.isEmpty(content)) {
            if (content.length() < 10) {
                mRootView.showMessage("至少输入10个字");
                return;
            } else {
                map.put("content", content);
            }
        } else {
            mRootView.showMessage("请输入您遇到的问题");
            return;
        }
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
                            UserInfoDetailsEntity loginUserInfoEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
                            FreeConsultEntity entity = baseResponse.getData();
                            entity.setCategoryName(consultType);
                            entity.setMemberIconImage(loginUserInfoEntity.getIconImage());
                            entity.setMemberName(loginUserInfoEntity.getMemberName());
                            entity.setReplyCount(0);
                            entity.setRegion(region);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(BundleTags.ENTITY, entity);
                            mRootView.launchActivity(new Intent(mApplication, FreeConsultDetailActivity.class), bundle);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
