package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.EquitiesContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;

@FragmentScope
public class EquitiesPresenter extends BasePresenter<EquitiesContract.Model, EquitiesContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<EquitiesListEntity> list_1 = new ArrayList<>();//我加入的组织
    private List<EquitiesListEntity> list_2 = new ArrayList<>();//我未加入的组织

    private EquitiesDetailsEntity entity;

    @Inject
    public EquitiesPresenter(EquitiesContract.Model model, EquitiesContract.View rootView) {
        super(model, rootView);
    }

    public EquitiesDetailsEntity getEntity() {
        return entity;
    }

    /**
     * 更新登录信息
     *
     * @param message message
     */
    @Subscriber(tag = LOGIN_INFO)
    private void loginInfo(Message message) {
        switch (message.what) {
            case LOGOUT:
                onResume();
                break;
        }
    }

    public void onResume() {
        try {
            if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
                getEquitiesList_1();
            } else {
                getEquitiesList();
            }
        } catch (Exception ignored) {
            getEquitiesList();
        }
    }

    /**
     * 未登录
     */
    private void getEquitiesList() {
        mModel.getEquitiesList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EquitiesListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<EquitiesListEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            list_2.clear();
                            list_2.addAll(baseResponse.getData());
                            mRootView.showAllEquitiesLayout();
                            mRootView.hideCurrentEquitiesLayout();
                            mRootView.setEquitiesAdapter2(baseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 登录
     */
    private void getEquitiesList_1() {
        mModel.getEquitiesList_1()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EquitiesListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<EquitiesListEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            list_1.clear();
                            list_2.clear();
                            for (EquitiesListEntity entity : baseResponse.getData()) {
                                if (entity.getJoinStatus() == 2) {//已经加入
                                    list_1.add(entity);
                                } else {
                                    list_2.add(entity);
                                }
                            }
                            if (list_1.size() > 0) {
                                if (!DataHelper.contains(mApplication, DataHelperTags.EQUITIES_ORG_ID)) {
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_ID, list_1.get(0).getOrganizationId());
                                    DataHelper.setIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_LEVEL_ID, list_1.get(0).getOrganizationLevelNameId());
                                }
                                getEquitiesDetails();
                                mRootView.showEquitiesDetails();
                                mRootView.showCurrentEquitiesLayout();
                                mRootView.setEquitiesAdapter1(list_1);
                            } else {
                                mRootView.showAllEquitiesLayout();
                                mRootView.hideCurrentEquitiesLayout();
                                mRootView.setEquitiesAdapter2(list_2);
                            }
                        }
                    }
                });
    }

    public void showAllEquitiesLayout() {
        mRootView.showAllEquitiesLayout();
        if (list_1.size() > 0) {
            mRootView.showCurrentEquitiesLayout();
            mRootView.setEquitiesAdapter1(list_1);
        } else {
            mRootView.hideCurrentEquitiesLayout();
        }
        mRootView.setEquitiesAdapter2(list_2);
    }

    /**
     * 获取权益组织详情
     */
    public void getEquitiesDetails() {
        int orgId = DataHelper.getIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_ID);
        int orgLevelId = DataHelper.getIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_LEVEL_ID);
        mModel.getEquitiesDetails(orgId, orgLevelId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EquitiesDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EquitiesDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            entity = baseResponse.getData();
                            mRootView.setEquitiesBg(entity.getImage());
                            mRootView.setMemberTotal(entity.getMemberCount() + "");
                            mRootView.setLawyerTotal(entity.getLawyerCount() + "");
                            mRootView.setEquitiesExplain(entity.getRightsInterpret());
                            mRootView.setOpenQualification(entity.getOpeningQualification());
                            mRootView.setExclusiveEquities(entity.getExclusiveRights());
                            getConsultList(orgId, orgLevelId);
                        }
                    }
                });
    }

    private void getConsultList(int orgId, int orgLevelId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", 0);
        map.put("regionId", 0);
        map.put("businessTypeId", 0);
        map.put("orgLevId", orgLevelId);
        map.put("orgId", orgId);
        mModel.getLawyerList(1, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                    @Override
                    public void onNext(LawyerEntity baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setLawyerAdapter(baseResponse.getData().getList());
                        }
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