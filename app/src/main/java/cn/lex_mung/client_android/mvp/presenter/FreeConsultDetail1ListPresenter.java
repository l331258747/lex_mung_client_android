package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1ListContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1ListAdapter;
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
import me.zl.mvp.utils.RxLifecycleUtils;

import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_CONSULT_DETAILS_REPLY_COUNT;
import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_REPLY_COUNT;


@ActivityScope
public class FreeConsultDetail1ListPresenter extends BasePresenter<FreeConsultDetail1ListContract.Model, FreeConsultDetail1ListContract.View> {
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
    private FreeConsultDetail1ListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    private int consultationId;
    private int lawyerId;
    private int replyNum;

    @Inject
    public FreeConsultDetail1ListPresenter(FreeConsultDetail1ListContract.Model model, FreeConsultDetail1ListContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
    }

    private void initAdapter() {
        adapter = new FreeConsultDetail1ListAdapter(mImageLoader);
        adapter.setOnItemChildClickListener((adapter1, view, position)->{
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = adapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.view_title:
//                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "free_test_detail_page","free_consulation_lawyer_detail_click");
                    Bundle bundle = new Bundle();
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getLawyerId());
                    bundle.putInt(BundleTags.REQUIRE_TYPE_ID,100);
                    mRootView.launchActivity(new Intent(mRootView.getActivity(), LawyerHomePageActivity.class), bundle);
                    break;
                case R.id.ll_delete:
                    mRootView.showDeleteDialog(entity.getConsultationReplyId(), position);
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
        mModel.replyDetail(consultationId,lawyerId,pageNum)
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

                            for (FreeConsultReplyListEntity item : baseResponse.getData().getList()){
                                item.setReplyCount(replyNum);
                            }

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

    public void deleteReply(int consultationReplyId, int position, Dialog dialog) {
        mModel.deleteReply(consultationReplyId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            dialog.dismiss();
                            setReplyNum(replyNum - 1);
                            getList(false);
                        }
                    }
                });
    }

    /**
     * 刷新回复数量 + 1
     *
     * @param message message
     */
    @Subscriber(tag = EDIT_REPLY_COUNT)
    private void editReplyCount(Message message) {
        switch (message.what) {
            case EDIT_CONSULT_DETAILS_REPLY_COUNT:
                setReplyNum(replyNum + 1);
                pageNum = 1;
                getList(false);
                break;
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

    public void setConsultationId(int consultationId) {
        this.consultationId= consultationId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
}
