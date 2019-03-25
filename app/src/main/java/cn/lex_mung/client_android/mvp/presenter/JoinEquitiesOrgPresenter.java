package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
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
import me.zl.mvp.utils.RegexUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.JoinEquitiesOrgContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.HashMap;
import java.util.Map;


@ActivityScope
public class JoinEquitiesOrgPresenter extends BasePresenter<JoinEquitiesOrgContract.Model, JoinEquitiesOrgContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private EquitiesDetailsEntity entity;

    private int orgId;
    private int levelId;

    @Inject
    public JoinEquitiesOrgPresenter(JoinEquitiesOrgContract.Model model, JoinEquitiesOrgContract.View rootView) {
        super(model, rootView);
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * 获取权益组织详情
     */
    public void getEquitiesDetails() {
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mModel.getEquitiesDetails1(orgId, levelId)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> mRootView.showLoading(""))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<EquitiesDetailsEntity>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<EquitiesDetailsEntity> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                entity = baseResponse.getData();
                                setData();
                            }
                        }
                    });
        } else {
            mModel.getEquitiesDetails(orgId, levelId)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> mRootView.showLoading(""))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<EquitiesDetailsEntity>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<EquitiesDetailsEntity> baseResponse) {
                            if (baseResponse.isSuccess()) {
                                entity = baseResponse.getData();
                                setData();
                            }
                        }
                    });
        }
    }

    private void setData() {
        mRootView.setOrgName(entity.getOrganizationLevelName());
        mRootView.setEquitiesBg(entity.getImage());
        mRootView.setMemberTotal(entity.getMemberCount() + "");
        mRootView.setLawyerTotal(entity.getLawyerCount() + "");
        mRootView.setEquitiesExplain(entity.getRightsInterpret());
        mRootView.setOpenQualification(entity.getOpeningQualification());
        mRootView.setExclusiveEquities(entity.getExclusiveRights());
    }

    public void submit(String name, String mobile, String workUnit) {
        if (!DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mRootView.launchActivity(new Intent(mApplication, LoginActivity.class));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mRootView.showMessage("请输入您的称呼");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            mRootView.showMessage("请输入您的手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(mobile)) {
            mRootView.showMessage("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(workUnit)) {
            mRootView.showMessage("请输入您的工作单位或企业名称");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        map.put("memberId", userInfoDetailsEntity.getMemberId());
        map.put("organizationId", entity.getOrganizationId());
        map.put("organizationLevId", entity.getOrganizationLevelNameId());
        map.put("memberName", name);
        map.put("mobile", mobile);
        map.put("institutionName", workUnit);
        mModel.joinEquitiesOrg(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("提交成功");
                            mRootView.killMyself();
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
