package cn.lex_mung.client_android.mvp.presenter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MyCardContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.CouponsMainEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesBuyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MainActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
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
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH_1;

@ActivityScope
public class MyCardPresenter extends BasePresenter<MyCardContract.Model, MyCardContract.View> {
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

    private MyCardAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    @Inject
    public MyCardPresenter(MyCardContract.Model model, MyCardContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getCouponsList(false);
    }

    private void initAdapter() {
        adapter = new MyCardAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, iew, position) -> {
            if (isFastClick()) return;
            CouponsEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.isBuyEquity()) {
                if (TextUtils.isEmpty(entity.getLegalAdviserUrl())) return;
                Bundle bundle = new Bundle();
                bundle.putString(BundleTags.URL, entity.getLegalAdviserUrl());
                bundle.putString(BundleTags.TITLE, entity.getEquityName());
                mRootView.launchActivity(new Intent(mRootView.getActivity(), WebActivity.class), bundle);
            } else {
                DataHelper.setIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_ID, entity.getOrganizationId());
                DataHelper.setIntergerSF(mApplication, DataHelperTags.EQUITIES_ORG_LEVEL_ID, entity.getOrganizationLevelNameId());
                AppUtils.post(EQUITIES_REFRESH, EQUITIES_REFRESH_1);
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(1);
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

    List<CouponsEntity> lists = new ArrayList<>();

    private void getCouponsList(boolean isAdd) {
        mModel.getCouponsList(pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CouponsMainEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CouponsMainEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getList().getPages();
                            pageNum = baseResponse.getData().getList().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                lists.clear();
                                if (baseResponse.getData().getEquity() != null) {
                                    for (int i = 0; i < baseResponse.getData().getEquity().size(); i++) {
                                        EquitiesBuyListEntity equitiesBuyListEntity = baseResponse.getData().getEquity().get(i);
                                        CouponsEntity item = new CouponsEntity();
                                        item.setEquityDesc(equitiesBuyListEntity.getEquityDesc());
                                        item.setEquityName(equitiesBuyListEntity.getEquityName());
                                        item.setBuyEquity(true);
                                        item.setIconImage(equitiesBuyListEntity.getIconImage());
                                        item.setImage(equitiesBuyListEntity.getImage());
                                        item.setOwn(equitiesBuyListEntity.isOwn());
                                        item.setRequireTypeNo(equitiesBuyListEntity.getRequireTypeNo());
                                        item.setRoleId(equitiesBuyListEntity.getRoleId());
                                        item.setRequireTypeId(equitiesBuyListEntity.getRequireTypeId());
                                        item.setLegalAdviserUrl(equitiesBuyListEntity.getLegalAdviserUrl());
                                        lists.add(item);
                                    }
                                }
                                lists.addAll(baseResponse.getData().getList().getList());

                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();

                                adapter.setNewData(lists);
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
