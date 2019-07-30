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
////                            mRootView.setSolutionType(baseResponse.getData());
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
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
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
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
        if (isLogin) {
            getUnreadCount();
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<HomeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<HomeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setHomeAdapter(baseResponse.getData().getList());
                            saveQuickUrl(baseResponse.getData().getList());
                        }
                    }
                });
    }

    //获取优选律师
    public void getLawyerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("regionId", DataHelper.getIntergerSF(mApplication,DataHelperTags.LAUNCH_LOCATION));
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<LawyerEntity2>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<LawyerEntity2>> baseResponse) {
                        if (baseResponse.isSuccess()) {

                        }
                    }
                });
    }

    //获取快速咨询url
    private void saveQuickUrl(List<HomeEntity> homeEntities) {
        if (homeEntities == null) return;

        for (int i = 0; i < homeEntities.size(); i++) {
            HomeEntity homeEntity = homeEntities.get(i);
            if (homeEntity.getType().equals("button")) {

                if (homeEntities.get(i).getBtns() == null) return;

                for (int j = 0; j < homeEntities.get(i).getBtns().size(); j++) {
                    HomeChildEntity homeChildEntity = homeEntities.get(i).getBtns().get(j);
                    if (homeChildEntity.getJumptype().equals("h5") && homeChildEntity.getJumpurl().endsWith("quick.html")) {
                        DataHelper.setStringSF(mApplication, DataHelperTags.QUICK_URL, homeChildEntity.getJumpurl());
                    }
                }
            }
        }
    }

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
                                mRootView.setUnreadMessageCount(count + "");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
