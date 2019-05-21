package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultListContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1Activity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultListAdapter;
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
public class FreeConsultListPresenter extends BasePresenter<FreeConsultListContract.Model, FreeConsultListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum;
    private int totalNum;
    private FreeConsultListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private UserInfoDetailsEntity userInfoDetailsEntity;

    @Inject
    public FreeConsultListPresenter(FreeConsultListContract.Model model, FreeConsultListContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new FreeConsultListAdapter();

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;

            CommonFreeTextEntity.ListBean bean = adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putInt(BundleTags.ID,bean.getConsultationId());
            bundle.putBoolean(BundleTags.IS_SHOW,true);
            mRootView.launchActivity(new Intent(mApplication,FreeConsultDetail1Activity.class),bundle);

        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getList(false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void getList(boolean isAdd){
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId",0);
        map.put("consultationStatus",0);
        map.put("sort",0);
        map.put("pageNum",pageNum);
        map.put("pageSize",10);
        map.put("memberId",userInfoDetailsEntity.getMemberId());
        mModel.commonFreeText(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CommonFreeTextEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CommonFreeTextEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());

                                if(adapter.getData() == null || adapter.getData().size() == 0){
                                    mRootView.setEmptyView(true);
                                }else{
                                    mRootView.setEmptyView(false);
                                }

                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
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
