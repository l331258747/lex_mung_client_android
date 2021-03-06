//package cn.lex_mung.client_android.mvp.presenter;
//
//import android.app.Application;
//import android.os.Message;
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.simple.eventbus.Subscriber;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import cn.lex_mung.client_android.app.DataHelperTags;
//import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
//import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
//import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
//import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
//import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
//import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
//import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
//import cn.lex_mung.client_android.utils.GsonUtil;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import me.jessyan.rxerrorhandler.core.RxErrorHandler;
//import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
//import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
//import me.zl.mvp.di.scope.FragmentScope;
//import me.zl.mvp.http.imageloader.ImageLoader;
//import me.zl.mvp.integration.AppManager;
//import me.zl.mvp.mvp.BasePresenter;
//import me.zl.mvp.utils.DataHelper;
//import me.zl.mvp.utils.RxLifecycleUtils;
//import okhttp3.RequestBody;
//
//import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
//import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;
//
//@FragmentScope
//public class HomePagerPresenter extends BasePresenter<HomePagerContract.Model, HomePagerContract.View> {
//    @Inject
//    RxErrorHandler mErrorHandler;
//    @Inject
//    Application mApplication;
//    @Inject
//    ImageLoader mImageLoader;
//    @Inject
//    AppManager mAppManager;
//
//    private boolean isLogin = false;
//
//    private List<BannerEntity> bannerList = new ArrayList<>();
//    private boolean isFlag = true;
//
//    private UnreadMessageCountEntity unreadMessageCountEntity;
//
//    @Inject
//    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
//        super(model, rootView);
//    }
//
//    public List<BannerEntity> getBannerList() {
//        return bannerList;
//    }
//
//    public boolean isLogin() {
//        return isLogin;
//    }
//
//    public UnreadMessageCountEntity getUnreadMessageCountEntity() {
//        return unreadMessageCountEntity;
//    }
//
//    /**
//     * 更新登录信息
//     *
//     * @param message message
//     */
//    @Subscriber(tag = LOGIN_INFO)
//    private void loginInfo(Message message) {
//        switch (message.what) {
//            case LOGOUT:
////                onResume();
//                mRootView.hideUnreadMessageCount();
//                break;
//        }
//    }
//
//    public void onResume() {
//        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
//        if (isLogin) {
//            getUnreadCount();
//        }
//    }
//
//    public void getHomeData() {
//        mModel.clientHome()
//                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(0, 0))
//                .doOnSubscribe(disposable -> {
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(() -> mRootView.hideLoading())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<HomeEntity>>>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseResponse<BaseListEntity<HomeEntity>> baseResponse) {
//                        if (baseResponse.isSuccess()) {
//                            mRootView.setHomeAdapter(baseResponse.getData().getList());
//                        }
//                    }
//                });
//    }
//
////    public void getBanner() {
////        mModel.getBanner()
////                .subscribeOn(Schedulers.io())
////                .retryWhen(new RetryWithDelay(0, 0))
////                .doOnSubscribe(disposable -> {
////                })
////                .subscribeOn(AndroidSchedulers.mainThread())
////                .observeOn(AndroidSchedulers.mainThread())
////                .doFinally(() -> mRootView.hideLoading())
////                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
////                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<BannerEntity>>>(mErrorHandler) {
////                    @Override
////                    public void onNext(BaseResponse<BaseListEntity<BannerEntity>> baseResponse) {
////                        if (baseResponse.isSuccess()) {
////                            bannerList.clear();
////                            bannerList.addAll(baseResponse.getData().getList());
////                            mRootView.setBannerAdapter(bannerList);
////                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_BANNER, new Gson().toJson(bannerList));
////                            getSolutionType();
////                        }
////                    }
////                });
////        getHomepageRequirementType();
////    }
//
////    private void getSolutionType() {
////        Map<String, Object> map = new HashMap<>();
////        mModel.getSolutionType(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
////                .subscribeOn(Schedulers.io())
////                .retryWhen(new RetryWithDelay(0, 0))
////                .doOnSubscribe(disposable -> {
////                })
////                .subscribeOn(AndroidSchedulers.mainThread())
////                .observeOn(AndroidSchedulers.mainThread())
////                .doFinally(() -> mRootView.hideLoading())
////                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
////                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SolutionTypeEntity>>>(mErrorHandler) {
////                    @Override
////                    public void onNext(BaseResponse<List<SolutionTypeEntity>> baseResponse) {
////                        if (baseResponse.isSuccess()) {
////                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE, new Gson().toJson(baseResponse.getData()));
////                        }
////                    }
////                });
////    }
//
////    private void getHomepageRequirementType() {
////        mModel.getHomepageRequirementType()
////                .subscribeOn(Schedulers.io())
////                .retryWhen(new RetryWithDelay(0, 0))
////                .doOnSubscribe(disposable -> {
////                })
////                .subscribeOn(AndroidSchedulers.mainThread())
////                .observeOn(AndroidSchedulers.mainThread())
////                .doFinally(() -> mRootView.hideLoading())
////                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
////                .subscribe(new ErrorHandleSubscriber<BaseResponse<RequirementTypeV3Entity>>(mErrorHandler) {
////                    @Override
////                    public void onNext(BaseResponse<RequirementTypeV3Entity> baseResponse) {
////                        if (baseResponse.isSuccess()) {
////                            mRootView.setRequirementTypeAdapter(baseResponse.getData().getNormal());
////                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_REQUIREMENT_TYPE, new Gson().toJson(baseResponse.getData()));
////                            mRootView.setHotContract(baseResponse.getData().getHot());
////                            mRootView.setMoreContract(baseResponse.getData().getMore());
////                            DataHelper.setStringSF(mApplication, DataHelperTags.QUICK_URL,baseResponse.getData().getQuickUrl());
////                        }
////                    }
////                });
////    }
//
//    private void getUnreadCount() {
//        mModel.getUnreadCount()
//                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(0, 0))
//                .doOnSubscribe(disposable -> {
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(() -> mRootView.hideLoading())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<UnreadMessageCountEntity>>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseResponse<UnreadMessageCountEntity> baseResponse) {
//                        if (baseResponse.isSuccess()) {
//                            unreadMessageCountEntity = baseResponse.getData();
//                            int count = baseResponse.getData().getUnreadOrderMsgCount()
//                                    + baseResponse.getData().getUnreadReqMsgCount()
//                                    + baseResponse.getData().getUnreadSysMsgCount();
//                            if (count > 0) {
//                                mRootView.setUnreadMessageCount(count + "");
//                            } else {
//                                mRootView.hideUnreadMessageCount();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {//不添加这个导致菊花一直转，查看菊花一直转就找401返回值
//                    }
//                });
//    }
//
////    public void getOnlineUrl() {
////        mModel.clientOnlineUrl()
////                .subscribeOn(Schedulers.io())
////                .retryWhen(new RetryWithDelay(0, 0))
////                .doOnSubscribe(disposable -> {
////                })
////                .subscribeOn(AndroidSchedulers.mainThread())
////                .observeOn(AndroidSchedulers.mainThread())
////                .doFinally(() -> mRootView.hideLoading())
////                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
////                .subscribe(new ErrorHandleSubscriber<BaseResponse<OnlineUrlEntity>>(mErrorHandler) {
////                    @Override
////                    public void onNext(BaseResponse<OnlineUrlEntity> baseResponse) {
////                        if (baseResponse.isSuccess()) {
////                            DataHelper.setStringSF(mApplication, DataHelperTags.ONLINE_URL,baseResponse.getData().getUrl());
////                        }
////                    }
////                });
////    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        this.mErrorHandler = null;
//        this.mAppManager = null;
//        this.mImageLoader = null;
//        this.mApplication = null;
//    }
//
//}

package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.PagesSecondEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RightsVipEntity;
import cn.lex_mung.client_android.utils.GsonUtil;
import cn.lex_mung.client_android.utils.http.OnSuccessAndFaultSub2;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;

@FragmentScope
public class HomePagerPresenter extends BasePresenter<HomePagerContract.Model, HomePagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isLogin = false;

    private UnreadMessageCountEntity unreadMessageCountEntity;

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public UnreadMessageCountEntity getUnreadMessageCountEntity() {
        return unreadMessageCountEntity;
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
                mRootView.hideUnreadMessageCount();
                break;
        }
    }

    public void onResume() {
        rightsVip();
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
        if (isLogin) {
            getUnreadCount();
        }
    }

    //二级页面h5地址
    public void pagesSecond() {
        mModel.pagesSecond()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PagesSecondEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PagesSecondEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            savePagesSecondUrl(baseResponse.getData());
                        }
                    }
                });
    }

    //获取存储的url
    private void savePagesSecondUrl(PagesSecondEntity entity) {
        if (entity == null) return;

        if(!TextUtils.isEmpty(entity.getBusinessAnnualMembersUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getBusinessAnnualMembersUrl());
            homeChildEntity.setTitle("企业应收款年度会员服务");
            DataHelper.setStringSF(mApplication,DataHelperTags.ANNUAL_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getHotContractUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getHotContractUrl());
            homeChildEntity.setTitle("合同审查起草");
            DataHelper.setStringSF(mApplication, DataHelperTags.HTSCQC_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getLitigationUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getLitigationUrl());
            homeChildEntity.setTitle("诉讼垫资");
            DataHelper.setStringSF(mApplication, DataHelperTags.SSDZ_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getRetrialUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getRetrialUrl());
            homeChildEntity.setTitle("再审申诉");
            DataHelper.setStringSF(mApplication, DataHelperTags.ZSSS_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getExaminUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getExaminUrl());
            homeChildEntity.setTitle("企业法律风险体检");
            DataHelper.setStringSF(mApplication, DataHelperTags.QYFLFXTY_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getCaseRiskUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getCaseRiskUrl());
            homeChildEntity.setTitle("案件风险评估");
            DataHelper.setStringSF(mApplication, DataHelperTags.AJFXPG_URL, GsonUtil.convertVO2String(homeChildEntity));
        }


        if (!TextUtils.isEmpty(entity.getCaseEntrustedUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getCaseEntrustedUrl());
            homeChildEntity.setTitle("案件委托");
            DataHelper.setStringSF(mApplication, DataHelperTags.WTAJ_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getMemberCard())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getMemberCard());
            homeChildEntity.setTitle("企业法务卡");
            DataHelper.setStringSF(mApplication, DataHelperTags.FWK_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getOnlineLegalUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getOnlineLegalUrl());
            homeChildEntity.setTitle("在线法律顾问");
            DataHelper.setStringSF(mApplication, DataHelperTags.ONLINE_LAWYER_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getQuickConsultationUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getQuickConsultationUrl());
            homeChildEntity.setTitle("快速咨询");
            DataHelper.setStringSF(mApplication, DataHelperTags.QUICK_URL, GsonUtil.convertVO2String(homeChildEntity));
        }

        if (!TextUtils.isEmpty(entity.getLawyerGroupUrl())){
            HomeChildEntity homeChildEntity = new HomeChildEntity();
            homeChildEntity.setJumpurl(entity.getLawyerGroupUrl());
            homeChildEntity.setTitle("私人律师团");
            DataHelper.setStringSF(mApplication, DataHelperTags.PRIVATE_LAWYER_URL, GsonUtil.convertVO2String(homeChildEntity));
        }
    }

    //获取首页数据
    public void getHomeData() {
        mModel.clientHome()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new OnSuccessAndFaultSub2<BaseResponse<BaseListEntity<HomeEntity>>>(mErrorHandler,mApplication) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<HomeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setHomeAdapter(baseResponse.getData().getList());
                            getLawyerList();
                        }else{
                            mRootView.showEmptyView();
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //获取优选律师
    public void getLawyerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("regionId", DataHelper.getIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION));
        map.put("businessTypeId", 0);
        mModel.getLawyerHomeList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new OnSuccessAndFaultSub2<BaseResponse<BaseListEntity<LawyerEntity2>>>(mErrorHandler,mApplication) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<LawyerEntity2>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getList() == null) return;
                            for (int i = 0; i < baseResponse.getData().getList().size(); i++) {
                                if (i == 0) {
                                    HomeEntity homeEntity = new HomeEntity();
                                    homeEntity.setType("home_lawyer_title");
                                    mRootView.addHomeLawyer(homeEntity);
                                }
                                HomeEntity homeEntity = new HomeEntity();
                                homeEntity.setLawyerEntity2(baseResponse.getData().getList().get(i));
                                homeEntity.setType("home_lawyer");
                                mRootView.addHomeLawyer(homeEntity);
                            }
                        }else{
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                        commonFreeText();
                    }
                });
    }

    public void commonFreeText(){
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId", 0);
        map.put("consultationStatus", 0);
        map.put("sort", 0);
        map.put("pageNum", 1);
        map.put("pageSize", 5);
        mModel.commonFreeText(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new OnSuccessAndFaultSub2<BaseResponse<BaseListEntity<CommonFreeTextEntity>>>(mErrorHandler,mApplication) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<CommonFreeTextEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getList() == null) return;
                            for (int i = 0; i < baseResponse.getData().getList().size(); i++) {
                                if (i == 0) {
                                    HomeEntity homeEntity = new HomeEntity();
                                    homeEntity.setType("home_free_title");
                                    mRootView.addHomeFree(homeEntity);
                                }
                                HomeEntity homeEntity = new HomeEntity();
                                homeEntity.setFreeTextEntity(baseResponse.getData().getList().get(i));
                                homeEntity.setType("home_free");
                                mRootView.addHomeFree(homeEntity);
                            }
                        }else{
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                        rightsVip();
                    }
                });
    }

//    //获取快速咨询url 还有要存储的url
//    private void saveShapeUrl(List<HomeEntity> homeEntities) {
//        if (homeEntities == null) return;
//
//        for (int i = 0; i < homeEntities.size(); i++) {
//            HomeEntity homeEntity = homeEntities.get(i);
//            if (homeEntity.getType().equals("button")) {
//                if (homeEntities.get(i).getBtns() == null) return;
//                for (int j = 0; j < homeEntities.get(i).getBtns().size(); j++) {
//                    HomeChildEntity homeChildEntity = homeEntities.get(i).getBtns().get(j);
//                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().indexOf("quick.html") > -1) {
//                        DataHelper.setStringSF(mApplication, DataHelperTags.QUICK_URL, GsonUtil.convertVO2String(homeChildEntity));
//                    }
//                }
//            } else if (homeEntity.getType().equals("three_card")) {
//                if (homeEntities.get(i).getBtns() == null) return;
//                for (int j = 0; j < homeEntities.get(i).getBtns().size(); j++) {
//                    HomeChildEntity homeChildEntity = homeEntities.get(i).getBtns().get(j);
//                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().indexOf("member") > -1) {//法务卡
//                        DataHelper.setStringSF(mApplication, DataHelperTags.FWK_URL, GsonUtil.convertVO2String(homeChildEntity));
//                    }
//                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().indexOf("feature.html") > -1) {//诉讼垫资
//                        DataHelper.setStringSF(mApplication, DataHelperTags.SSDZ_URL, GsonUtil.convertVO2String(homeChildEntity));
//                    }
//                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().indexOf("retrial.html") > -1) {//再审申诉
//                        DataHelper.setStringSF(mApplication, DataHelperTags.ZSSS_URL, GsonUtil.convertVO2String(homeChildEntity));
//                    }
//                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().indexOf("caseEntrustment.html") > -1) {//委托案件
//                        DataHelper.setStringSF(mApplication, DataHelperTags.WTAJ_URL, GsonUtil.convertVO2String(homeChildEntity));
//                    }
//                }
//            }
//        }
//    }

    //消息
    private void getUnreadCount() {
        mModel.getUnreadCount()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UnreadMessageCountEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UnreadMessageCountEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            unreadMessageCountEntity = baseResponse.getData();
                            int count = baseResponse.getData().getUnreadOrderMsgCount()
                                    + baseResponse.getData().getUnreadReqMsgCount()
                                    + baseResponse.getData().getUnreadSysMsgCount();
                            if (count > 0) {
                                mRootView.setUnreadMessageCount(count);
                            } else {
                                mRootView.hideUnreadMessageCount();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {//不添加这个导致菊花一直转，查看菊花一直转就找401返回值
                    }
                });
    }

    //搜索栏轮播
    public void random() {
        mModel.random()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData() == null || baseResponse.getData().size() == 0) {
                                mRootView.showFlipView(false);
                            } else {
                                mRootView.showFlipView(true);
                                mRootView.addNotice(baseResponse.getData());
                            }
                        }
                    }
                });

    }

    public void getSolutionType() {
        Map<String, Object> map = new HashMap<>();
        mModel.getSolutionType(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SolutionTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<SolutionTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE, new Gson().toJson(baseResponse.getData()));
                        }
                    }
                });
    }

    public void rightsVip(){
        if (!DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)){
            mRootView.changeVipData(null);
            return;
        }
        mModel.rightsVip()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RightsVipEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RightsVipEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.changeVipData(baseResponse.getData());
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
