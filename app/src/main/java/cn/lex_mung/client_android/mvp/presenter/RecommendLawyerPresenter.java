package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.RecommendLawyerBean;
import cn.lex_mung.client_android.mvp.ui.activity.RecommendLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.RecommendLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandHistoryAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.RecommendLawyerContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class RecommendLawyerPresenter extends BasePresenter<RecommendLawyerContract.Model, RecommendLawyerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private RecommendLawyerAdapter adapter;
    TextView tv_tip;

    @Inject
    public RecommendLawyerPresenter(RecommendLawyerContract.Model model, RecommendLawyerContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate() {
        initAdapter();
        getData(regionId, 3, 0, requireTypeId);//TODO solutionTypeId 修改
    }

    private void initAdapter() {
        adapter = new RecommendLawyerAdapter(mImageLoader);

        View layout = mRootView.getActivity().getLayoutInflater().inflate(R.layout.layout_recommend_lawyer_head, null);
        tv_tip = layout.findViewById(R.id.tv_tip);
        tv_tip.setVisibility(View.GONE);
        adapter.addHeaderView(layout);

        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            RecommendLawyerBean entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.isSend()) return;//发送过的不能再发送

            switch (view.getId()) {
                case R.id.tv_btn:
                case R.id.iv_head:
                    sendData(entity.getMemberId(),position);
                    break;
            }
        });
        mRootView.initRecyclerView(adapter);
    }


    public void getData(int regionId,
                        int solutionTypeId,
                        int amountId,
                        int requireTypeId) {
        mModel.assistantRecommendLawyersOther(regionId, solutionTypeId, amountId, requireTypeId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HelpStepLawyerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HelpStepLawyerEntity> baseResponse) {
                        if (baseResponse.getData().getRecommendLawyer() == null || baseResponse.getData().getRecommendLawyer().size() == 0) {
                            tv_tip.setVisibility(View.GONE);
                        } else {
                            tv_tip.setVisibility(View.VISIBLE);
                            adapter.setNewData(baseResponse.getData().getRecommendLawyer());
                        }
                    }
                });
    }

    public void sendData(int lawyerId,int position){
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", requirementId);
        map.put("isFirst", 0);
        map.put("targetLawyerId", lawyerId);//律师id
        map.put("lawyerRegionId", regionId);//区域id

        map.put("requirementTypeId", requireTypeId);//固定价格，为子服务id，协商价格为父服务id
        map.put("requirementTypeName", requireTypeName);

        if (TextUtils.isEmpty(money)) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_enter_max_money));
            return;
        }
        if (TextUtils.isEmpty(content)) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_enter_your_problem));
            return;
        }
        if (content.length() < 10) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_describe_your_problem));
            return;
        }
        map.put("maxCost", Integer.valueOf(money));
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
                            adapter.getItem(position).setSend(true);
                            adapter.notifyDataSetChanged();
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

    int regionId;

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    int requireTypeId;

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    int requirementId;
    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }

    String requireTypeName;
    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    String money;
    public void setMoney(String money) {
        this.money = money;
    }

    String content;
    public void setContent(String content) {
        this.content = content;
    }
}
