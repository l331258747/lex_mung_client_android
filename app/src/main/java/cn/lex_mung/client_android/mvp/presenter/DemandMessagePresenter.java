package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
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
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.contract.DemandMessageContract;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.DemandMessageEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MessageChatActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.DemandMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_CUSTOMER_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_UNREAD_MESSAGES_NUMBER;

@FragmentScope
public class DemandMessagePresenter extends BasePresenter<DemandMessageContract.Model, DemandMessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private DemandMessageAdapter adapter;

    @Inject
    public DemandMessagePresenter(DemandMessageContract.Model model, DemandMessageContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate() {
        JMessageClient.registerEventReceiver(this);
        initAdapter();
        getDemandMessageList();
    }

    private void initAdapter() {
        adapter = new DemandMessageAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            DemandMessageEntity.RequirementBean bean = adapter.getItem(position);
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getLawyerMemberId());
            bundle.putString(BundleTags.TITLE, bean.getMemberName());
            mRootView.launchActivity(new Intent(mApplication, MessageChatActivity.class), bundle);
        });
        mRootView.initRecyclerView(adapter);
    }

    public void getDemandMessageList() {
        mModel.getDemandMessageList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DemandMessageEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DemandMessageEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            List<DemandMessageEntity.RequirementBean> list1 = new ArrayList<>();
                            List<DemandMessageEntity.RequirementBean> list2 = new ArrayList<>();
                            List<DemandMessageEntity.RequirementBean> list3 = new ArrayList<>();
                            for (DemandMessageEntity.RequirementBean bean : baseResponse.getData().getRequirement()) {
                                if (bean.getIsReceipt() == 0) {
                                    list1.add(bean);
                                } else if (bean.getIsReceipt() == 2 || bean.getIsReceipt() == 3) {
                                    list3.add(bean);
                                } else if (bean.getIsReceipt() == 1) {
                                    list2.add(bean);
                                }
                            }
                            adapter.setCount1(list1.size());
                            list1.addAll(list2);
                            list1.addAll(list3);
                            if (list1.size() == 0) {
                                AppUtils.postInt(UN_READ_MESSAGE_NUM, SET_CUSTOMER_UN_READ_MESSAGE_NUM, 0);
                            }
                            adapter.setNewData(list1);
                            mRootView.setAdapter(adapter);
                        }
                    }
                });
    }

    /**
     * 收到消息
     */
    public void onEventMainThread(MessageEvent event) {
        AppUtils.post(REFRESH, REFRESH_UNREAD_MESSAGES_NUMBER);
        Message msg = event.getMessage();
        final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
        String targetId = userInfo.getUserName();
        Conversation conversation = JMessageClient.getSingleConversation(targetId,  Api.J_PUSH);
        if (conversation != null) {
            adapter.setToTop(conversation);
        }
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEventMainThread(OfflineMessageEvent event) {
        Conversation conversation = event.getConversation();
        if (!conversation.getTargetId().equals("feedback_Android")) {
            adapter.setToTop(conversation);
        }
    }

    /**
     * 消息漫游完成事件
     *
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEventMainThread(ConversationRefreshEvent event) {
        Conversation conversation = event.getConversation();
        if (!conversation.getTargetId().equals("feedback_Android")) {
            adapter.setToTop(conversation);
        }
    }

    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
