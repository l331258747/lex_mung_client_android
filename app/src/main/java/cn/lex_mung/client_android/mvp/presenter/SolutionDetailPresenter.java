package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeLawyerAdapter;
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
public class SolutionDetailPresenter extends BasePresenter<SolutionDetailContract.Model, SolutionDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    int solutionId;
    List<CommonMarkEntity> marks = new ArrayList<>();//只展示8条数据

    @Inject
    public SolutionDetailPresenter(SolutionDetailContract.Model model, SolutionDetailContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(int solutionId) {
        this.solutionId = solutionId;
        getMarks();
        getLawyers();
        getContracts();
        getFrees();
        mRootView.initSolutionAdapter();
        getSolutions(false);
    }


    public void getMarks() {
        mModel.commonMarks(solutionId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CommonMarkEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CommonMarkEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {

                            if (baseResponse.getData().size() > 8) {
                                for (int i = 0; i < 8; i++) {
                                    marks.add(baseResponse.getData().get(i));
                                }
                            } else {
                                marks = baseResponse.getData();
                            }

                            if (marks != null && marks.size() > 0) {
                                for (int i = 0; i < marks.size(); i++) {
                                    int pos = i;
                                    View itemView = LayoutInflater.from(mRootView.getActivity()).inflate(R.layout.item_solution_detail_table, null, false);
                                    TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
                                    tvTitle.setText(marks.get(i).getName());
                                    itemView.setOnClickListener(v -> {
                                        if (isFastClick()) return;
                                        CommonMarkEntity bean = marks.get(pos);
                                        //TODO 进入律师搜索页面按3级标签进行搜索。
                                        mRootView.showMessage(bean.getName());
                                    });
                                    mRootView.addTableLayout(itemView, i);
                                }
                            } else {
                                mRootView.hideTableLayout();
                            }
                        }
                    }
                });
    }

    public void getLawyers() {
        Map<String, Object> map = new HashMap<>();
        map.put("regionId", DataHelper.getIntergerSF(mApplication, DataHelperTags.LAUNCH_LOCATION));
        map.put("businessTypeId", solutionId);
        mModel.getLawyerHomeList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading("");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<LawyerEntity2>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<LawyerEntity2>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getList() != null && baseResponse.getData().getList().size() > 0) {
                                if (baseResponse.getData().getList().size() < 5) {
                                    mRootView.hideLawyerAllView();
                                }
                                mRootView.initLawyerAdapter(baseResponse.getData().getList());
                            } else {
                                mRootView.hideLawyerLayout();
                            }
                        }
                    }
                });
    }

    public void getFrees() {
        boolean isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId", solutionId);
        map.put("consultationStatus", 0);
        map.put("sort", 0);
        map.put("pageNum", 1);
        map.put("pageSize", 5);
        mModel.commonFreeText(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)), isLogin)
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
                            if (baseResponse.getData().getList() != null && baseResponse.getData().getList().size() > 0) {
                                mRootView.initFreeAdapter(baseResponse.getData().getList());
                            } else {
                                mRootView.hideFreeLayout();
                            }
                        }
                    }
                });
    }

    private int pageNum = 1;
    private int totalNum;

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void getSolutions(boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", solutionId);
        map.put("pageNum", pageNum);
        map.put("pageSize", 5);
        mModel.getSolutionList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<SolutionListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<SolutionListEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {

                            if (baseResponse.getData().getList() != null && baseResponse.getData().getList().size() > 0) {
                                totalNum = baseResponse.getData().getPages();
                                pageNum = baseResponse.getData().getPageNum();
                                mRootView.setSolutionData(baseResponse.getData().getList(), isAdd);
                            } else {
                                if(!isAdd)
                                    mRootView.hideSolutionLayout();
                            }
                        }
                    }
                });
    }

    public void getContracts() {
        mModel.commonPageContracts()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CommonPageContractsEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CommonPageContractsEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setContractLayout(baseResponse.getData());
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
