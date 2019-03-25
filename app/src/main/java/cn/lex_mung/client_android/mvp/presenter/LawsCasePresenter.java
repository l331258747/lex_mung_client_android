package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.mvp.contract.LawsCaseContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;

import java.util.HashMap;
import java.util.Map;

@FragmentScope
public class LawsCasePresenter extends BasePresenter<LawsCaseContract.Model, LawsCaseContract.View> {
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

    private int memberId;
    private String institution;

    @Inject
    public LawsCasePresenter(LawsCaseContract.Model model, LawsCaseContract.View rootView) {
        super(model, rootView);
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void getCaseList(boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("memberId", memberId);
        if (!TextUtils.isEmpty(institution)) {
            institution = institution.replace(" ", "");
        } else {
            institution = "";
        }
        map.put("lawyerFirm", institution);
        map.put("pageSize", 10);
        mModel.getCaseList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CaseListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CaseListEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getList(), isAdd);
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
