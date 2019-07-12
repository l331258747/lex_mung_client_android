package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.FreeTextBizinfoEntity;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1Activity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
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
public class FreeConsultMainPresenter extends BasePresenter<FreeConsultMainContract.Model, FreeConsultMainContract.View> {
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
    private FreeConsultMainAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView tv_send;
    private TextView tv_solve;

    @Inject
    public FreeConsultMainPresenter(FreeConsultMainContract.Model model, FreeConsultMainContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getList(false);
        getNum();
    }

    private void initAdapter() {
        adapter = new FreeConsultMainAdapter(mImageLoader);
        View layout = mRootView.getActivity().getLayoutInflater().inflate(R.layout.layout_free_consult_main_title, null);
        tv_send = layout.findViewById(R.id.tv_send);
        tv_solve = layout.findViewById(R.id.tv_solve);

        adapter.addHeaderView(layout);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            if(!DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)){
                mRootView.launchActivity(new Intent(mApplication, LoginActivity.class));
                return;
            }

            CommonFreeTextEntity bean = adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putInt(BundleTags.ID,bean.getConsultationId());
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

    public void getNum(){
        mModel.freeTextBizinfo()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeTextBizinfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeTextBizinfoEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            setNumLayout(baseResponse.getData().getToday_free_text_count(),baseResponse.getData().getToday_free_text_reply_count());
                        }
                    }
                });
    }

    public void setNumLayout(int today_free_text_count, int today_free_text_reply_count){
        tv_send.setText(today_free_text_count+"");
        tv_solve.setText(today_free_text_reply_count+"");
    }

    public void getList(boolean isAdd){

        boolean isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);

        if(!isLogin && pageNum > 2){
            new DefaultDialog(mRootView.getActivity(),
                    dialog -> {
                        mRootView.launchActivity(new Intent(mRootView.getActivity(), LoginActivity.class));
                        dialog.dismiss();
                    }
            ,"继续查看需要登录","去登陆","取消").show();
            smartRefreshLayout.finishLoadMore();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("categoryId",0);
        map.put("consultationStatus",0);
        map.put("sort",0);
        map.put("pageNum",pageNum);
        map.put("pageSize",10);
        mModel.commonFreeText(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)),isLogin)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<CommonFreeTextEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<CommonFreeTextEntity>> baseResponse) {
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
