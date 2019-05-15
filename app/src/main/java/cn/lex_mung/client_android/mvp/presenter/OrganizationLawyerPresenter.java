package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.OrganizationLawyerContract;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class OrganizationLawyerPresenter extends BasePresenter<OrganizationLawyerContract.Model, OrganizationLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawyerListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int pageNum = 1;
    private int totalNum;

    private int orgId;

    @Inject
    public OrganizationLawyerPresenter(OrganizationLawyerContract.Model model, OrganizationLawyerContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 开始执行
     *
     * @param smartRefreshLayout SmartRefreshLayout
     */
    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getConsultList(false, false);
    }

    private void initAdapter() {
        adapter = new LawyerListAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            LawyerEntity.LawyerBean.ListBean bean = adapter.getItem(position);
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getMemberId());
            mRootView.launchActivity(new Intent(mApplication, LawyerHomePageActivity.class), bundle);
        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getConsultList(true, false);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getConsultList(false, false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void getConsultList(boolean isAdd, boolean isShowLoading) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", 0);
        map.put("regionId", 0);
        map.put("businessTypeId", 0);
        if (orgId > 0) {
            map.put("orgId", orgId);
        }
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mModel.getLawyerList1(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> {
                        if (isShowLoading) {
                            mRootView.showLoading("");
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                        @Override
                        public void onNext(LawyerEntity baseResponse) {
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
        } else {
            mModel.getLawyerList(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .doOnSubscribe(disposable -> {
                        if (isShowLoading) {
                            mRootView.showLoading("");
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                        @Override
                        public void onNext(LawyerEntity baseResponse) {
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
