package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

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
import cn.lex_mung.client_android.mvp.contract.SearchResortInstitutionContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionEntity;

import java.util.HashMap;
import java.util.Map;

@ActivityScope
public class SearchResortInstitutionPresenter extends BasePresenter<SearchResortInstitutionContract.Model, SearchResortInstitutionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum = 1;
    private int totalPage = 1;
    private static final int pageSize = 50;

    private String keywords;

    @Inject
    public SearchResortInstitutionPresenter(SearchResortInstitutionContract.Model model, SearchResortInstitutionContract.View rootView) {
        super(model, rootView);
    }

    public void setKeyWord(String keyWord) {
        this.keywords = keyWord;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void getCourt(boolean isAdd) {
        if (TextUtils.isEmpty(keywords)) {
            mRootView.showMessage("请输入关键字");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        String json = new Gson().toJson(map);
        mModel.getCourt(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<InstitutionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<InstitutionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalPage = baseResponse.getData().getResult().getPages();
                            pageNum = baseResponse.getData().getResult().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getResult().getList(), isAdd);
                        }
                    }
                });
    }

    public void getP(boolean isAdd) {
        if (TextUtils.isEmpty(keywords)) {
            mRootView.showMessage("请输入关键字");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        String json = new Gson().toJson(map);
        mModel.getProcuratorate(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<InstitutionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<InstitutionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalPage = baseResponse.getData().getResult().getPages();
                            pageNum = baseResponse.getData().getResult().getPageNum();
                            mRootView.setAdapter(baseResponse.getData().getResult().getList(), isAdd);
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
