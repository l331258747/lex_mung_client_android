package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
import cn.lex_mung.client_android.utils.GsonUtil;
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
import com.google.gson.reflect.TypeToken;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.HomePagerContract;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<BannerEntity.ListBean> bannerList = new ArrayList<>();
    private boolean isFlag = true;

    private UnreadMessageCountEntity unreadMessageCountEntity;

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    public List<BannerEntity.ListBean> getBannerList() {
        return bannerList;
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
                onResume();
                break;
        }
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
        if (isLogin) {
            getUnreadCount();
        }
    }

    public void getBanner() {
        if (isFlag) {
            try {
                String bannerJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_BANNER);
                if (!TextUtils.isEmpty(bannerJson)) {
                    List<BannerEntity.ListBean> listBeans = new Gson().fromJson(bannerJson, new TypeToken<List<BannerEntity.ListBean>>() {
                    }.getType());
                    if (listBeans != null) {
                        bannerList.clear();
                        bannerList.addAll(listBeans);
                        mRootView.setBannerAdapter(bannerList);
                    }
                }
                String solutionTypeJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE);
                if (!TextUtils.isEmpty(solutionTypeJson)) {
                    List<SolutionTypeEntity> listBeans = new Gson().fromJson(solutionTypeJson, new TypeToken<List<SolutionTypeEntity>>() {
                    }.getType());
                    if (listBeans != null) {
                        mRootView.setSolutionType(listBeans);
                    }
                }
                String requirementTypeJson = DataHelper.getStringSF(mApplication, DataHelperTags.HOME_PAGE_REQUIREMENT_TYPE);
                if (!TextUtils.isEmpty(requirementTypeJson)) {
                    RequirementTypeV3Entity listBeans = GsonUtil.convertString2Object(requirementTypeJson,RequirementTypeV3Entity.class);
                    if (listBeans != null) {
                        mRootView.setRequirementTypeAdapter(listBeans.getNormal());
                    }
                }
            } catch (Exception ignored) {
            }
            isFlag = false;
        }
        mModel.getBanner()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BannerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BannerEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            bannerList.clear();
                            bannerList.addAll(baseResponse.getData().getList());
                            mRootView.setBannerAdapter(bannerList);
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_BANNER, new Gson().toJson(bannerList));
                            getSolutionType();
                        }
                    }
                });
        getHomepageRequirementType();
    }

    private void getSolutionType() {
        Map<String, Object> map = new HashMap<>();
        mModel.getSolutionType(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
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
                            mRootView.setSolutionType(baseResponse.getData());
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_SOLUTION_TYPE, new Gson().toJson(baseResponse.getData()));
                        }
                    }
                });
    }

    private void getHomepageRequirementType() {
        mModel.getHomepageRequirementType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RequirementTypeV3Entity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RequirementTypeV3Entity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setRequirementTypeAdapter(baseResponse.getData().getNormal());
                            DataHelper.setStringSF(mApplication, DataHelperTags.HOME_PAGE_REQUIREMENT_TYPE, new Gson().toJson(baseResponse.getData()));
                            mRootView.setHotContract(baseResponse.getData().getHot());
                            mRootView.setMoreContract(baseResponse.getData().getMore());
                        }
                    }
                });
    }

    private void getUnreadCount() {
        mModel.getUnreadCount()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
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