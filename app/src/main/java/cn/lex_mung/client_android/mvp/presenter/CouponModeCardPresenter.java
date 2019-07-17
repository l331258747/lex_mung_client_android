package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntityOptimal;
import cn.lex_mung.client_android.mvp.model.entity.other.CouponModeEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.CouponModeCardAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.CouponModeCardContract;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY2;


@FragmentScope
public class CouponModeCardPresenter extends BasePresenter<CouponModeCardContract.Model, CouponModeCardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    SmartRefreshLayout smartRefreshLayout;

    private int pageNum;
    private int totalNum;
    private CouponModeCardAdapter adapter;

    private int memberId;
    private int lMemberId;
    private int couponType;
    private int orgId;
    private int requireTypeId;

    @Inject
    public CouponModeCardPresenter(CouponModeCardContract.Model model, CouponModeCardContract.View rootView) {
        super(model, rootView);
    }

    public void setRequireTypeId(int requireTypeId){
        this.requireTypeId = requireTypeId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setLMemberId(int lMemberId) {
        this.lMemberId = lMemberId;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new CouponModeCardAdapter(mImageLoader,1);
        adapter.setCardId(couponType == 1?orgId:-1);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            ReleaseDemandOrgMoneyEntityOptimal entity = adapter.getItem(position);
            if (entity == null) return;
            if(entity.getOrgStatus() == 0){
                mRootView.showMessage("您选择的律师不是"+ entity.getOrganizationLevelName()+"专属服务律师");
                return;
            }
            CouponModeEntity couponModeEntity = new CouponModeEntity();
            couponModeEntity.setOrgId(entity.getOrganizationId());
            couponModeEntity.setOrgLevId(entity.getOrganizationLevId());
            couponModeEntity.setCouponId(-1);
            couponModeEntity.setType(1);
            AppUtils.post(REFRESH, REFRESH_DISCOUNT_WAY2, couponModeEntity);
            mRootView.getCouponModeActivity().killMyself();
        });

        adapter.setOnItemChildClickListener((adapter2, view, position)->{
            if (isFastClick()) return;
            ReleaseDemandOrgMoneyEntityOptimal entity = adapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.tv_coupon_card:
                    mRootView.showDetailDialog(entity.getExclusiveRights());
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

    public void getList(boolean isAdd){
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("lmemberId", lMemberId);
        map.put("requireTypeId",requireTypeId);
        mModel.getOrgList2(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ReleaseDemandOrgMoneyEntity2>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ReleaseDemandOrgMoneyEntity2> baseResponse) {
                        if (baseResponse.isSuccess()) {

                            totalNum = baseResponse.getData().getOptimal().getPages();
                            pageNum = baseResponse.getData().getOptimal().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getOptimal().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                mRootView.setEmptyView(adapter);
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getOptimal().getList());
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
