package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MyOrderContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1Activity;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsActivity;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsEntrustActivity;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsExpertActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyOrderAdapter2;
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
public class MyOrderPresenter extends BasePresenter<MyOrderContract.Model, MyOrderContract.View> {
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
    private MyOrderAdapter2 adapter;
    private SmartRefreshLayout smartRefreshLayout;

    @Inject
    public MyOrderPresenter(MyOrderContract.Model model, MyOrderContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getCouponsList(false);
    }

    private void initAdapter() {
        adapter = new MyOrderAdapter2(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            OrderEntity entity = adapter.getItem(position);
            if (entity == null) return;
            Bundle bundle = new Bundle();
            switch (entity.getTypeId()) {
                case 1://文字咨询
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getId());
                    bundle.putBoolean(BundleTags.IS_SHOW,true);
                    mRootView.launchActivity(new Intent(mApplication, FreeConsultDetail1Activity.class), bundle);
                    break;
                case 5://客户需求
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getId());
                    bundle.putString(BundleTags.TITLE,entity.getTypeName());
                    bundle.putInt(BundleTags.TYPE, entity.getTypeId());
                    bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                    bundle.putInt(BundleTags.IS_SHOW,entity.getIsHot());
                    mRootView.launchActivity(new Intent(mApplication, OrderDetailsActivity.class), bundle);
                    break;
                case 3://专家咨询
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getId());
                    bundle.putString(BundleTags.TITLE,entity.getTypeName());
                    bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                    mRootView.launchActivity(new Intent(mApplication, OrderDetailsExpertActivity.class), bundle);
                    break;
                case 4://快速电话咨询
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getId());
                    bundle.putString(BundleTags.TITLE,entity.getOrderType());
                    bundle.putInt(BundleTags.TYPE, entity.getTypeId());
                    bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                    mRootView.launchActivity(new Intent(mApplication, OrderDetailsActivity.class), bundle);
                    break;
                case 6://案源信息
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getId());
                    bundle.putString(BundleTags.TITLE,entity.getOrderType());
                    bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
                    mRootView.launchActivity(new Intent(mApplication, OrderDetailsEntrustActivity.class), bundle);
                    break;
            }
        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getCouponsList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getCouponsList(false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    private void getCouponsList(boolean isAdd) {
        UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", entity.getMemberId());
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        mModel.getOrderList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<OrderEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<OrderEntity>> baseResponse) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
