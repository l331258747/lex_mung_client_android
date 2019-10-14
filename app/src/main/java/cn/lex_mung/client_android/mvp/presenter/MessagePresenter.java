package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;

import cn.lex_mung.client_android.utils.BadgeNumUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.MessageContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import org.simple.eventbus.Subscriber;

import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_CUSTOMER_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_ORDER_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_SYSTEM_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UPDATE_ORDER_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UPDATE_SYSTEM_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_UNREAD_MESSAGES_NUMBER;

@ActivityScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private UnreadMessageCountEntity entity;

    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(UnreadMessageCountEntity entity) {
        this.entity = entity;
        if (entity != null) {
            setUnreadMessageCount(entity.getUnreadReqMsgCount(), entity.getUnreadOrderMsgCount(), entity.getUnreadSysMsgCount());
        }
    }

    private void setUnreadMessageCount(int count1, int count2, int count3) {
        if (count1 > 0) {
            mRootView.setDemandMessageCount(count1 + "");
        } else {
            mRootView.hideDemandMessageCount();
        }
        if (count2 > 0) {
            mRootView.setOrderMessageCount(count2 + "");
        } else {
            mRootView.hideOrderMessageCount();
        }
        if (count3 > 0) {
            mRootView.setSystemMessageCount(count3 + "");
        } else {
            mRootView.hideSystemMessageCount();
        }
    }

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
                            entity = baseResponse.getData();
                            setUnreadMessageCount(entity.getUnreadReqMsgCount(), entity.getUnreadOrderMsgCount(), entity.getUnreadSysMsgCount());
                        }
                    }
                });
    }

    /**
     * 刷新
     *
     * @param message Message
     */
    @Subscriber(tag = REFRESH)
    private void refresh(android.os.Message message) {
        try {
            switch (message.what) {
                case REFRESH_UNREAD_MESSAGES_NUMBER:
                    getUnreadCount();
                    break;
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 更新未读消息数量
     *
     * @param message message
     */
    @Subscriber(tag = UN_READ_MESSAGE_NUM)
    private void receiveUnReadMessageNum(Message message) {
        switch (message.what) {
            case SET_CUSTOMER_UN_READ_MESSAGE_NUM:
                entity.setUnreadReqMsgCount((int) message.obj);
                if (entity.getUnreadReqMsgCount() > 0) {
                    mRootView.setDemandMessageCount(entity.getUnreadReqMsgCount() + "");
                } else {
                    mRootView.hideDemandMessageCount();
                }
                break;
            case SET_ORDER_UN_READ_MESSAGE_NUM:
                entity.setUnreadOrderMsgCount((int) message.obj);
                if (entity.getUnreadOrderMsgCount() > 0) {
                    mRootView.setOrderMessageCount(entity.getUnreadOrderMsgCount() + "");
                } else {
                    mRootView.hideOrderMessageCount();
                }
                break;
            case SET_SYSTEM_UN_READ_MESSAGE_NUM:
                entity.setUnreadSysMsgCount((int) message.obj);
                if (entity.getUnreadSysMsgCount() > 0) {
                    mRootView.setSystemMessageCount(entity.getUnreadSysMsgCount() + "");
                } else {
                    mRootView.hideSystemMessageCount();
                }
                break;
            case UPDATE_ORDER_UN_READ_MESSAGE_NUM:
                entity.setUnreadOrderMsgCount(entity.getUnreadOrderMsgCount() + (int) message.obj);
                if (entity.getUnreadOrderMsgCount() < 0) entity.setUnreadOrderMsgCount(0);
                if (entity.getUnreadOrderMsgCount() > 0) {
                    mRootView.setOrderMessageCount(entity.getUnreadOrderMsgCount() + "");
                } else {
                    mRootView.hideOrderMessageCount();
                }
                break;
            case UPDATE_SYSTEM_UN_READ_MESSAGE_NUM:
                entity.setUnreadSysMsgCount(entity.getUnreadSysMsgCount() + (int) message.obj);
                if (entity.getUnreadSysMsgCount() < 0) entity.setUnreadSysMsgCount(0);
                if (entity.getUnreadSysMsgCount() > 0) {
                    mRootView.setSystemMessageCount(entity.getUnreadSysMsgCount() + "");
                } else {
                    mRootView.hideSystemMessageCount();
                }
                break;
        }

        if(entity != null){
            setHuaweiBadgeNum(entity.getUnreadReqMsgCount() + entity.getUnreadOrderMsgCount() + entity.getUnreadSysMsgCount());
        }
    }

    BadgeNumUtil badgeNumUtil;//华为角标
    //设置华为角标
    public void setHuaweiBadgeNum(int num){
        if(badgeNumUtil == null){
            badgeNumUtil = new BadgeNumUtil(mRootView.getActivity());
        }
        badgeNumUtil.setHuaweiBadgeNum(num);
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
