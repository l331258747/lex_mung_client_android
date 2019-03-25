package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

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
import cn.lex_mung.client_android.mvp.contract.SystemMessageContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MyOrderActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.SystemMessageAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_SYSTEM_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UPDATE_SYSTEM_UN_READ_MESSAGE_NUM;

@FragmentScope
public class SystemMessagePresenter extends BasePresenter<SystemMessageContract.Model, SystemMessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private SystemMessageAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int pageNum = 1;
    private int totalNum;

    @Inject
    public SystemMessagePresenter(SystemMessageContract.Model model, SystemMessageContract.View rootView) {
        super(model, rootView);
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getSystemMessageList(false);
    }

    private void initAdapter() {
        adapter = new SystemMessageAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            try {
                if (isFastClick()) return;
                MessageEntity.ListBean bean = adapter.getItem(position);
                if (bean == null) return;
                bean.setIsRead(1);
                adapter.setData(position, bean);
                setRead(bean.getPushMessageId());
                AppUtils.postInt(UN_READ_MESSAGE_NUM, UPDATE_SYSTEM_UN_READ_MESSAGE_NUM, -1);
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                switch (bean.getSubType()) {
                    case 100://欢迎消息
                        intent.setClass(mApplication, MainActivity.class);
                        break;
                    case 107:
                        bundle.clear();
                        bundle.putString(BundleTags.URL, bean.getUrl());
                        intent.putExtras(bundle);
                        intent.setClass(mApplication, WebActivity.class);
                        break;
                    case 109:
                        intent.setClass(mApplication, MyOrderActivity.class);
                        break;
                }
                mRootView.launchActivity(intent);
            } catch (Exception ignored) {
            }
        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getSystemMessageList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getSystemMessageList(false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void getSystemMessageList(boolean isAdd) {
        mModel.getSystemMessageList(pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MessageEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MessageEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }

    public void allSetRead(Dialog dialog) {
        mModel.allSetRead(1)
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
                            AppUtils.postInt(UN_READ_MESSAGE_NUM, SET_SYSTEM_UN_READ_MESSAGE_NUM, 0);
                            List<MessageEntity.ListBean> list = new ArrayList<>();
                            for (MessageEntity.ListBean bean : adapter.getData()) {
                                bean.setIsRead(1);
                                list.add(bean);
                            }
                            adapter.setNewData(list);
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void setRead(int pushMessageId) {
        mModel.setRead(pushMessageId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
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
