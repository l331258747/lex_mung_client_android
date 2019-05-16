package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;


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

    @Inject
    public FreeConsultMainPresenter(FreeConsultMainContract.Model model, FreeConsultMainContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        getCouponsList(false);
    }

    private void initAdapter() {
        adapter = new FreeConsultMainAdapter(mApplication);
        View layout = mRootView.getActivity().getLayoutInflater().inflate(R.layout.layout_free_consult_main_title, null);
        adapter.addHeaderView(layout);

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
