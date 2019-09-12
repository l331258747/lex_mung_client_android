package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityDialogEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import cn.lex_mung.client_android.utils.GsonUtil;
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

        popupList();
    }

    public void popupList(){
        Map<String, Object> map = new HashMap<>();
        map.put("solutionTypeId", solutionId);
        map.put("device", 2);
        mModel.popupList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<ActivityEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<ActivityEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showActivityDialog(baseResponse.getData().getList());
                        }
                    }
                });
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
                                mRootView.initTableLayout(marks);
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
        mModel.commonPageContracts(solutionId)
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
                            List<CommonPageContractsEntity> lists = new ArrayList<>();
                            CommonPageContractsEntity item99 = null;

                            for (int i=0;i<baseResponse.getData().size();i++){
                                if(baseResponse.getData().get(i).getId() == 9999){
                                    item99 = baseResponse.getData().get(i);
                                }else{
                                    lists.add(baseResponse.getData().get(i));
                                }
                            }

                            mRootView.setContractLayout(lists,item99);
                        }
                    }
                });
    }


    //用来获取弹窗集合，以及存储缓存
    public List<ActivityEntity> getNeedActivityDialogEntities(List<ActivityEntity> entities) {
        /*
        targetUsers	目标用户（1所有用户，2首次打开，3用户组，4律师组）
        name	弹窗名字
        id	弹窗id
        url	关联跳转
        iconImage	关联图片
         */

        List<ActivityEntity> needEntities = new ArrayList<>();//需要弹窗的集合

        if (entities == null || entities.size() == 0) return needEntities;

        //缓存里面的以弹出活动的id集合
        List<ActivityDialogEntity> cacheIds = GsonUtil.convertString2Collection(
                DataHelper.getStringSF(mApplication, DataHelperTags.ACTIVITY_DIALOG),
                new TypeToken<List<ActivityDialogEntity>>() {
                });

        if (cacheIds == null) {
            cacheIds = new ArrayList<>();
        }

        boolean hasCache = false;
        if (cacheIds.size() != 0) {
            hasCache = true;
        }

        for (ActivityEntity item : entities) {
            if (TextUtils.isEmpty(item.getIconImage()))//不是图片地址的剔除
                continue;
            if(item.getTargetUsers() == 4)
                continue;

            if (item.getTargetUsers() == 2) {//如果为2 首次进入弹窗
                if (!hasCache) {//如果缓存中没有数据，把数据全部加进缓存集合、弹窗集合。
                    ActivityDialogEntity activityDialogEntity = new ActivityDialogEntity();
                    activityDialogEntity.setId(item.getId());
                    cacheIds.add(activityDialogEntity);
                    needEntities.add(item);
                } else {//如果缓存中有数据，并且id不在缓存中，加进缓存集合、弹窗集合

                    boolean isHas = false;
                    for (int i = 0; i < cacheIds.size(); i++) {
                        if (cacheIds.get(i).getId() == item.getId()) {
                            isHas = true;
                            break;
                        }
                    }

                    if (!isHas) {
                        ActivityDialogEntity activityDialogEntity = new ActivityDialogEntity();
                        activityDialogEntity.setId(item.getId());
                        cacheIds.add(activityDialogEntity);
                        needEntities.add(item);
                    }
                }
            } else {
                needEntities.add(item);//加进弹窗集合
            }
        }

        String str = GsonUtil.convertVO2String(cacheIds);
        DataHelper.setStringSF(mApplication, DataHelperTags.ACTIVITY_DIALOG, str);

        return needEntities;
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
