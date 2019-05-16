package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultListContract;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultListAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


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



    @Inject
    public FreeConsultListPresenter(FreeConsultListContract.Model model, FreeConsultListContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getCouponsList(false);
    }

    private void initAdapter() {
        adapter = new FreeConsultListAdapter(mApplication);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            mRootView.showMessage("详情");

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
        List<String> data = new ArrayList<>();
        String item = "";
        data.add(item);
        data.add(item);
        data.add(item);
        data.add(item);

        adapter.addData(data);
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
