package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1Contract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1ListActivity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultReplyActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1Adapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
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
public class FreeConsultDetail1Presenter extends BasePresenter<FreeConsultDetail1Contract.Model, FreeConsultDetail1Contract.View> {
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
    private FreeConsultDetail1Adapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int consultationId;

    private View layout;

    private int ConsultationTypeId;
    private int memberId;

    private LawyerListAdapter lawyerListAdapter;
    private UserInfoDetailsEntity userInfoDetailsEntity;
    private boolean isMe;

    @Inject
    public FreeConsultDetail1Presenter(FreeConsultDetail1Contract.Model model, FreeConsultDetail1Contract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        initAdapter();
        initLawyerAdapter();
        getList(false);
        getInfo();
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public void setTitleLayout(View layout) {
        this.layout = layout;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    private void initAdapter() {
        adapter = new FreeConsultDetail1Adapter(mImageLoader,userInfoDetailsEntity);
        adapter.addHeaderView(layout);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = adapter.getItem(position);
            if (entity == null) return;

            Bundle bundle = new Bundle();
            bundle.putInt(BundleTags.ID, entity.getConsultationId());
            bundle.putInt(BundleTags.LAWYER_ID, entity.getLawyerId());
            bundle.putInt(BundleTags.NUM, entity.getReplyCount());
            bundle.putBoolean(BundleTags.IS_SHOW, (userInfoDetailsEntity != null && userInfoDetailsEntity.getMemberId() == memberId) ? true : false);
            mRootView.launchActivity(new Intent(mRootView.getActivity(), FreeConsultDetail1ListActivity.class), bundle);
        });
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = adapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.view_title:
                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "free_consulation_detail","free_consulation_lawyer_detail_click");
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleTags.ID, entity.getLawyerId());
                    bundle.putInt(BundleTags.REQUIRE_TYPE_ID,100);
                    mRootView.launchActivity(new Intent(mRootView.getActivity(), LawyerHomePageActivity.class), bundle);
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

    private void initLawyerAdapter() {
        lawyerListAdapter = new LawyerListAdapter(mImageLoader);
        lawyerListAdapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;

            LawyerEntity2 entity = lawyerListAdapter.getItem(position);
            if (entity == null) return;

            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt(BundleTags.ID, entity.getMemberId());
            mRootView.launchActivity(new Intent(mRootView.getActivity(), LawyerHomePageActivity.class), bundle);

        });
        mRootView.setLawyerList(lawyerListAdapter);
    }


    public void getInfo() {
        mModel.commonFreeText(consultationId,isMe)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setData(baseResponse.getData());
                            ConsultationTypeId = baseResponse.getData().getConsultationTypeId();
                            memberId = baseResponse.getData().getMemberId();

                            if (baseResponse.getData().getReplyCount() == 0) {
                                mRootView.setEmptyView(true);
                                getLawyerList();
                            } else {
                                mRootView.setEmptyView(false);
                            }
                        }
                    }
                });
    }

    public void getList(boolean isAdd) {
        mModel.lawyerFreeText(consultationId, pageNum, 10)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<FreeConsultReplyListEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
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

    public void getLawyerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", 0);
        map.put("regionId", 0);
        map.put("businessTypeId", ConsultationTypeId);
        mModel.getLawyerList(1, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<LawyerEntity2>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<LawyerEntity2>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getList().size() > 3) {
                                List<LawyerEntity2> datas = new ArrayList<>();
                                for (int i = 0; i < 3; i++) {
                                    datas.add(baseResponse.getData().getList().get(i));
                                    lawyerListAdapter.setNewData(datas);
                                }
                            } else {
                                lawyerListAdapter.setNewData(baseResponse.getData().getList());
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
