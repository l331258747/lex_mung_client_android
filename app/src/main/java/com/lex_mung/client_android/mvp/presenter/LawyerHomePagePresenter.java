package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
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
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

@ActivityScope
public class LawyerHomePagePresenter extends BasePresenter<LawyerHomePageContract.Model, LawyerHomePageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawsHomePagerBaseEntity entity;

    @Inject
    public LawyerHomePagePresenter(LawyerHomePageContract.Model model, LawyerHomePageContract.View rootView) {
        super(model, rootView);
    }

    public LawsHomePagerBaseEntity getEntity() {
        return entity;
    }

    public void getLawsHomePagerBase(int id) {
        mModel.getLawsHomePagerBase(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LawsHomePagerBaseEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LawsHomePagerBaseEntity> baseResponse) {
                        entity = baseResponse.getData();
                        mRootView.initViewPager(entity);

                        //律所和地区
                        if (!TextUtils.isEmpty(entity.getRegion())) {
                            mRootView.setRegionAndInstitutionName(String.format(AppUtils.getString(mApplication, R.string.text_post_num)
                                    , entity.getRegion()
                                    , entity.getInstitutionName()));
                        } else {
                            mRootView.setRegionAndInstitutionName(entity.getInstitutionName());
                        }
                        //擅长领域
                        if (!TextUtils.isEmpty(entity.getBusinessDescription())) {
                            mRootView.setField(entity.getBusinessDescription());
                        } else {
                            mRootView.hideFieldLayout();
                        }
                        //标签
                        if (entity.getLawyerTags().size() > 0) {
                            mRootView.setTagsAdapter(entity.getLawyerTags());
                        } else {
                            mRootView.hideTagsAdapter();
                        }
                        //姓名
                        if (!TextUtils.isEmpty(entity.getMemberName())) {
                            mRootView.setName(entity.getMemberName());
                        } else if (!TextUtils.isEmpty(entity.getMobile())
                                && entity.getMobile().length() >= 3) {
                            mRootView.setName("用户" + entity.getMobile().substring(0, 3) + "********");
                        }
                        //头像
                        if (!TextUtils.isEmpty(entity.getIconImage())) {
                            mRootView.setAvatar(entity.getIconImage());
                        } else {
                            mRootView.setAvatar(R.drawable.ic_avatar);
                        }
                        //背景
                        if (!TextUtils.isEmpty(entity.getBackgroundImage())) {
                            mRootView.setTopBg(entity.getBackgroundImage());
                        } else {
                            mRootView.setTopBg(R.drawable.ic_p_bg_1);
                        }
                        //职位和执业年限
                        if (!TextUtils.isEmpty(entity.getMemberPositionName())
                                && !TextUtils.isEmpty(entity.getPractice())) {
                            mRootView.setPositionNameAndPractice(String.format(AppUtils.getString(mApplication, R.string.text_post_num)
                                    , entity.getMemberPositionName()
                                    , entity.getPractice()));
                        } else if (!TextUtils.isEmpty(entity.getMemberPositionName())) {
                            mRootView.setPositionNameAndPractice(entity.getMemberPositionName());
                        } else if (!TextUtils.isEmpty(entity.getPractice())) {
                            mRootView.setPositionNameAndPractice(entity.getPractice());
                        } else {
                            mRootView.setPositionNameAndPractice("");
                        }
                        //性别
                        if (entity.getSex() == 0) {//未知
                            mRootView.setSex(R.drawable.round_100_00_all_f4f4f4
                                    , AppUtils.getColor(mApplication, R.color.c_ff)
                                    , 0);
                            mRootView.hideSexIcon();
                            if (TextUtils.isEmpty(entity.getAge())) {
                                mRootView.hideAgeLayout();
                            }
                        } else if (entity.getSex() == 1) {//男
                            mRootView.setSex(R.drawable.round_100_00_all_76cbff
                                    , AppUtils.getColor(mApplication, R.color.c_76cbff)
                                    , R.drawable.ic_man);
                            mRootView.showSexIcon();
                        } else {//女
                            mRootView.setSex(R.drawable.round_100_00_all_ff7878
                                    , AppUtils.getColor(mApplication, R.color.c_ff7878)
                                    , R.drawable.ic_woman);
                            mRootView.showSexIcon();
                        }
                        //年龄
                        if (!TextUtils.isEmpty(entity.getAge())) {
                            mRootView.setAge(entity.getAge());
                        }
                        //是否关注
                        if (entity.getIsCollect() == 1) {//已关注
                            mRootView.setLikeLayout(R.drawable.ic_like
                                    , AppUtils.getColor(mApplication, R.color.c_f8b22d)
                                    , AppUtils.getString(mApplication, R.string.text_like));
                        } else if (entity.getIsCollect() == 0) {//未关注
                            mRootView.setLikeLayout(R.drawable.ic_no_like
                                    , AppUtils.getColor(mApplication, R.color.c_ff)
                                    , AppUtils.getString(mApplication, R.string.text_no_like));
                        } else {//不显示
                            mRootView.hideLikeLayout();
                        }
                    }
                });
    }

    public void follow(int id) {
        mModel.follow(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setLikeLayout(R.drawable.ic_like
                                    , AppUtils.getColor(mApplication, R.color.c_f8b22d)
                                    , AppUtils.getString(mApplication, R.string.text_like));
                        }
                    }
                });
    }

    public void unFollow(int id) {
        mModel.unFollow(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setLikeLayout(R.drawable.ic_no_like
                                    , AppUtils.getColor(mApplication, R.color.c_ff)
                                    , AppUtils.getString(mApplication, R.string.text_no_like));
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
