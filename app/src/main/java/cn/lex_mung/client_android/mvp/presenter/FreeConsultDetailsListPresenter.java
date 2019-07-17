package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
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
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailsListContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetailsListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_CONSULT_DETAILS_REPLY_COUNT;
import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_REPLY_COUNT;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_CONSULT_DETAILS;

//删除
@ActivityScope
public class FreeConsultDetailsListPresenter extends BasePresenter<FreeConsultDetailsListContract.Model, FreeConsultDetailsListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum = 1;
    private int totalNum;
    private int total;

    private int consultationId;
    private int lawyerId;
    private String region;

    private SmartRefreshLayout smartRefreshLayout;

    private FreeConsultDetailsListAdapter adapter;

    @Inject
    public FreeConsultDetailsListPresenter(FreeConsultDetailsListContract.Model model, FreeConsultDetailsListContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(int consultationId, int lawyerId, String region, SmartRefreshLayout smartRefreshLayout) {
        this.consultationId = consultationId;
        this.lawyerId = lawyerId;
        this.region = region;
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getFreeConsultReplyDetailList(false);
    }

    private void initAdapter() {
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        adapter = new FreeConsultDetailsListAdapter(mImageLoader
                , userInfoDetailsEntity.getMemberId()
                , region
                , userInfoDetailsEntity.getIconImage()
                , userInfoDetailsEntity.getMemberName());
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = adapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.item_tv_delete:
                    mRootView.showDeleteDialog(entity.getConsultationReplyId(), position);
                    break;
                case R.id.item_tv_consult:
                    Bundle bundle = new Bundle();
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getLawyerId());
                    mRootView.launchActivity(new Intent(mApplication, LawyerHomePageActivity.class), bundle);
                    break;
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (pageNum < totalNum) {
                pageNum = pageNum + 1;
                getFreeConsultReplyDetailList(true);
            } else {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    private void getFreeConsultReplyDetailList(boolean isAdd) {
        mModel.getFreeConsultReplyDetailList(consultationId, lawyerId, pageNum)
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
                            if (baseResponse.getData().getTotal() > 0) {
                                total = baseResponse.getData().getTotal() - 1;
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

    public void lawyerReply(String content) {
        if (TextUtils.isEmpty(content)) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_input_content));
            return;
        }
        if (content.length() < 5) {
            mRootView.showMessage(mApplication.getString(R.string.text_no_less_5));
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("content", content);
        map.put("consultationId", consultationId);
        map.put("lawyerId", lawyerId);
        mModel.lawyerReply(consultationId, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultReplyListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultReplyListEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (total == 0) {
                                AppUtils.post(REFRESH, REFRESH_CONSULT_DETAILS);
                            }
                            total = total + 1;
                            AppUtils.postInt(EDIT_REPLY_COUNT, EDIT_CONSULT_DETAILS_REPLY_COUNT, total);
                            mRootView.clearInput();
                            mRootView.setTitle(String.format(mApplication.getString(R.string.text_reply_count_1), total));
                            adapter.addData(baseResponse.getData());
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
                            if (position == 0) {
                                mRootView.killMyself();
                                AppUtils.postInt(EDIT_REPLY_COUNT, EDIT_CONSULT_DETAILS_REPLY_COUNT, 0);
                                return;
                            }
                            total = total - 1;
                            AppUtils.postInt(EDIT_REPLY_COUNT, EDIT_CONSULT_DETAILS_REPLY_COUNT, total);
                            mRootView.setTitle(String.format(mApplication.getString(R.string.text_reply_count_1), total));
                            adapter.remove(position);
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
