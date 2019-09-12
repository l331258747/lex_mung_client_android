package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.PublicLawyerContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
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
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class PublicLawyerPresenter extends BasePresenter<PublicLawyerContract.Model, PublicLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PublicLawyerPresenter(PublicLawyerContract.Model model, PublicLawyerContract.View rootView) {
        super(model, rootView);
    }

    //公益律师拨打电话
    public void callOrderInsert(String lawyerMobile, int lawyerMemberId) {
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        if (userInfoDetailsEntity == null) return;

        String mobile = userInfoDetailsEntity.getMobile();
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("lawyerMobile", lawyerMobile);
        map.put("source", 2);
        map.put("lawyerMemberId", lawyerMemberId);
        mModel.callOrderInsert(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.callPublickPhone(lawyerMobile);
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取权益组织详情
     */
    public void getEquitiesDetails(int orgId, int levelId) {
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mModel.getEquitiesDetails1(orgId, levelId)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> mRootView.showLoading(""))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<EquitiesDetailsEntity>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<EquitiesDetailsEntity> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                mRootView.setEquitiesDetail(baseResponse.getData());
                            }
                        }
                    });
        } else {
            mModel.getEquitiesDetails(orgId, levelId)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> mRootView.showLoading(""))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<EquitiesDetailsEntity>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<EquitiesDetailsEntity> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                mRootView.setEquitiesDetail(baseResponse.getData());
                            }
                        }
                    });
        }
    }

    private int pageNum = 1;
    private int totalNum;

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public void getConsultList(int orgId, int orgLevelId, boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", 0);
        map.put("businessTypeId", 0);
//        map.put("regionId", DataHelper.getIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION));
        map.put("orgLevId", orgLevelId);
        map.put("orgId", orgId);
        mModel.getLawyerList(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            if (baseResponse.getData().getList() != null && baseResponse.getData().getList().size() > 0) {
                                totalNum = baseResponse.getData().getPages();
                                pageNum = baseResponse.getData().getPageNum();
                                mRootView.setLawyerAdapter(baseResponse.getData().getList(), isAdd);

                                if (!isAdd && baseResponse.getData().getTotal() > 0) {
                                    mRootView.setLawyerTitle("公益服务律师(" + baseResponse.getData().getTotal() + "名)");
                                }
                            } else {
                                if (!isAdd)
                                    mRootView.hideLawyerLayout();
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
