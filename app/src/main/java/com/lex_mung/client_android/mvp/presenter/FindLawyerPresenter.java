package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.contract.FindLawyerContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;

import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_1;
import static com.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

@FragmentScope
public class FindLawyerPresenter extends BasePresenter<FindLawyerContract.Model, FindLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum = 1;
    private int totalNum;

    private int sort;
    private int fieldId;
    private int regionId1;
    private int regionId2;
    private String lawyerName;

    private List<BusinessTypeEntity> fieldList = new ArrayList<>();
    private List<RegionEntity> regionList = new ArrayList<>();
    private List<LawyerListScreenEntity> list = new ArrayList<>();
    private Map<String, Object> screenMap = new HashMap<>();

    @Inject
    public FindLawyerPresenter(FindLawyerContract.Model model, FindLawyerContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 开始执行
     */
    public void onCreate() {
        new Thread(this::initJsonData).start();
        getBusinessType();
        getConsultList(false, false);
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<BusinessTypeEntity> getFieldList() {
        return fieldList;
    }

    public List<RegionEntity> getRegionList() {
        return regionList;
    }

    public List<LawyerListScreenEntity> getList() {
        return list;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public void setRegionId(int regionId1, int regionId2) {
        this.regionId1 = regionId1;
        this.regionId2 = regionId2;
    }

    public int getRegionId1() {
        return regionId1;
    }

    public int getRegionId2() {
        return regionId2;
    }

    public void setLawyerName(String name) {
        lawyerName = name;
        pageNum = 1;
        getConsultList(false, true);
    }

    public void getConsultList(boolean isAdd, boolean isShowLoading) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        map.put("regionId", regionId2);
        map.put("businessTypeId", fieldId);
        if (!TextUtils.isEmpty(lawyerName)) {
            map.put("lawyerName", lawyerName);
        }
        if (screenMap != null && screenMap.size() > 0) {
            map.putAll(screenMap);
        }
        mModel.getLawyerList(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (isShowLoading) {
                        mRootView.showLoading("");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                    @Override
                    public void onNext(LawyerEntity baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getList(), isAdd);
                        }
                    }
                });
    }

    private void getBusinessType() {
        mModel.getBusinessType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BusinessTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BusinessTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            fieldList.add(new BusinessTypeEntity(0, "全部领域"));
                            fieldList.addAll(baseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO)
    private void refresh(Message message) {
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_LIST:
                list.clear();
                screenMap.clear();
                list.addAll((Collection<? extends LawyerListScreenEntity>) message.obj);
                for (LawyerListScreenEntity entity : list) {
                    if (entity.getId() > 0) {
                        screenMap.put(entity.getPropKey(), entity.getId());
                    }
                }
                pageNum = 1;
                getConsultList(false, false);
                break;
            case LAWYER_LIST_SCREEN_INFO_LIST_1:
                list.clear();
                list.addAll((Collection<? extends LawyerListScreenEntity>) message.obj);
                screenMap.clear();
                pageNum = 1;
                getConsultList(false, false);
                mRootView.setScreenColor(AppUtils.getColor(mApplication, R.color.c_b5b5b5));
                break;
            case LAWYER_LIST_SCREEN_INFO_TYPE:
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                mRootView.setScreenColor(AppUtils.getColor(mApplication, R.color.c_06a66a));
                break;
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
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "utf-8"));//根据字节长度设置编码
            }
            is.close();//关闭流
            regionList.add(new RegionEntity(0, "全部地区"));
            regionList.addAll(new Gson().fromJson(sb.toString(), new TypeToken<List<RegionEntity>>() {
            }.getType()));
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
