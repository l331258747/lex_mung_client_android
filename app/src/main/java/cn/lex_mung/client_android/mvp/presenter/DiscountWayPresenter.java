package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

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
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.contract.DiscountWayContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.DiscountWayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY;

@ActivityScope
public class DiscountWayPresenter extends BasePresenter<DiscountWayContract.Model, DiscountWayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private DiscountWayAdapter adapter;

    private int organizationLevId;
    private int memberId;
    private int lMemberId;

    @Inject
    public DiscountWayPresenter(DiscountWayContract.Model model, DiscountWayContract.View rootView) {
        super(model, rootView);
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setLMemberId(int lMemberId) {
        this.lMemberId = lMemberId;
    }

    public void setOrganizationLevId(int organizationLevId) {
        this.organizationLevId = organizationLevId;
    }

    public void onCreate() {
        initAdapter();
        getOrgList();
    }

    private void initAdapter() {
        adapter = new DiscountWayAdapter(mImageLoader, organizationLevId);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            ReleaseDemandOrgMoneyEntity entity = adapter.getItem(position);
            if (entity == null) return;
            adapter.setOrganizationLevId(entity.getOrganizationLevId());
            adapter.notifyDataSetChanged();
            mRootView.setIvSelect(R.drawable.ic_hide_select);
            AppUtils.postInt(REFRESH, REFRESH_DISCOUNT_WAY, entity.getOrganizationLevId());
            mRootView.killMyself();
        });
        mRootView.initRecyclerView(adapter);
        if (organizationLevId == -1) {
            mRootView.setIvSelect(R.drawable.ic_show_select);
        }
    }

    public void setAdapterOrganizationLevId(int organizationLevId) {
        adapter.setOrganizationLevId(organizationLevId);
        adapter.notifyDataSetChanged();
    }

    private void getOrgList() {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("lmemberId", lMemberId);
        mModel.getOrgList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ReleaseDemandOrgMoneyEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ReleaseDemandOrgMoneyEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            adapter.setNewData(baseResponse.getData());
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
