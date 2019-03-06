package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;
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
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.app.TimeFormat;
import com.lex_mung.client_android.mvp.contract.FreeConsultDetailContract;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import org.simple.eventbus.Subscriber;

import static com.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_CONSULT_DETAILS_REPLY_COUNT;
import static com.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_REPLY_COUNT;
import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_CONSULT_DETAILS;

@ActivityScope
public class FreeConsultDetailPresenter extends BasePresenter<FreeConsultDetailContract.Model, FreeConsultDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int consultationId;
    private FreeConsultEntity entity;
    private UserInfoDetailsEntity userInfoDetailsEntity;
    private boolean isLoading = true;

    private int pageNum = 1;
    private int totalNum;
    private int position = 0;

    @Inject
    public FreeConsultDetailPresenter(FreeConsultDetailContract.Model model, FreeConsultDetailContract.View rootView) {
        super(model, rootView);
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public FreeConsultEntity getEntity() {
        return entity;
    }

    public void setData(FreeConsultEntity entity) {
        this.entity = entity;
        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        if (entity.getMemberId() == userInfoDetailsEntity.getMemberId()) {
            mRootView.setName(entity.getMemberName());
            if (!TextUtils.isEmpty(entity.getMemberIconImage())) {
                mRootView.setAvatar(entity.getMemberIconImage());
            } else {
                mRootView.setAvatar(R.drawable.ic_avatar);
            }
        } else {
            if (entity.getIsHide() == 1) {//匿名
                mRootView.setName("匿名用户");
                mRootView.setAvatar(R.drawable.ic_avatar);
            } else {
                mRootView.setName(entity.getMemberName());
                if (!TextUtils.isEmpty(entity.getMemberIconImage())) {
                    mRootView.setAvatar(entity.getMemberIconImage());
                } else {
                    mRootView.setAvatar(R.drawable.ic_avatar);
                }
            }
        }
        mRootView.setRegion(entity.getRegion());
        mRootView.setCategoryName(entity.getCategoryName());
        mRootView.setContent(entity.getContent());
        mRootView.setTime(TimeFormat.getTime(entity.getDateAdded()));
        mRootView.setReplyCount(entity.getReplyCount() + "");
    }

    public void getFreeConsultDetail() {
        if (consultationId == 0) return;
        mModel.getFreeConsultDetail(consultationId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            entity = baseResponse.getData();
                            setData(entity);
                        }
                        if (isLoading) {
                            getFreeConsultReplyList(false);
                            isLoading = false;
                        }
                    }
                });
    }

    public void getFreeConsultReplyList(boolean isAdd) {
        mModel.getFreeConsultReplyList(consultationId, pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (!isAdd) {
                        mRootView.showLoading("");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultReplyEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultReplyEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (!isAdd) {
                                mRootView.setTime(TimeFormat.getTime(entity.getDateAdded()));
                            }
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getList(), isAdd);
                        }
                    }
                });
    }

    /**
     * 刷新回复数量
     *
     * @param message message
     */
    @Subscriber(tag = EDIT_REPLY_COUNT)
    private void editReplyCount(Message message) {
        switch (message.what) {
            case EDIT_CONSULT_DETAILS_REPLY_COUNT:
                mRootView.refreshReplyCount((Integer) message.obj);
                break;
        }
    }

    /**
     * 刷新列表
     *
     * @param message message
     */
    @Subscriber(tag = REFRESH)
    private void refresh(Message message) {
        switch (message.what) {
            case REFRESH_CONSULT_DETAILS:
                pageNum = 1;
                getFreeConsultReplyList(false);
                break;
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
