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

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandHistoryContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;
import cn.lex_mung.client_android.mvp.ui.activity.RecommendLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandHistoryAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class ReleaseDemandHistoryPresenter extends BasePresenter<ReleaseDemandHistoryContract.Model, ReleaseDemandHistoryContract.View> {
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

    private ReleaseDemandHistoryAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    @Inject
    public ReleaseDemandHistoryPresenter(ReleaseDemandHistoryContract.Model model, ReleaseDemandHistoryContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new ReleaseDemandHistoryAdapter();
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            HirstoryDemandEntity entity = adapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.tv_btn:
                    sendData(entity.getId(),entity.getRequireTypeId(),entity.getTypeName(),entity.getRequirementExtendValue(),entity.getContent());
                    break;
            }
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

    private void getList(boolean isAdd) {
        mModel.clientRequirementOne(pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<HirstoryDemandEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<HirstoryDemandEntity>> baseResponse) {
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
                                if(adapter.getData() == null || adapter.getData().size() == 0){
                                    mRootView.setTipHide(false);
                                }
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }

    private void sendData(int requirementId, int requireTypeId, String requireTypeName, String maxMoney, String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", requirementId);
        map.put("isFirst", 0);
        map.put("targetLawyerId", lawyerId);//律师id
        map.put("lawyerRegionId", regionId);//区域id

        map.put("requirementTypeId", requireTypeId);//固定价格，为子服务id，协商价格为父服务id
        map.put("requirementTypeName", requireTypeName);

        if (TextUtils.isEmpty(maxMoney)) {
            mRootView.showMessage("请输入您愿意支付的律师费用");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            mRootView.showMessage("请输入您遇到的问题");
            return;
        }
        if (content.length() < 10) {
            mRootView.showMessage("请简单描述您遇到的问题(限10-300个字)");
            return;
        }
        map.put("maxCost", Integer.valueOf(maxMoney));
        map.put("requirementContent", content);

        mModel.releaseRequirement2(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GeneralEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<GeneralEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("需求发布成功，我们会尽快为您审核！");
                            mRootView.killMyself();
                            Bundle bundle = new Bundle();
                            bundle.putInt(BundleTags.REQUIRE_TYPE_ID, requireTypeId);
                            bundle.putString(BundleTags.REQUIRE_TYPE_NAME, requireTypeName);
                            bundle.putInt(BundleTags.REGION_ID, regionId);
                            bundle.putInt(BundleTags.REQUIREMENT_ID,baseResponse.getData().getRequirementId());
                            bundle.putInt(BundleTags.L_MEMBER_ID,lawyerId);
                            bundle.putString(BundleTags.MONEY,maxMoney);
                            bundle.putString(BundleTags.CONTENT,content);
                            mRootView.launchActivity(new Intent(mRootView.getActivity(), RecommendLawyerActivity.class), bundle);
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
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

    int lawyerId, regionId;

    public void setInfo(int lawyerId, int regionId) {
        this.lawyerId = lawyerId;
        this.regionId = regionId;
    }
}
