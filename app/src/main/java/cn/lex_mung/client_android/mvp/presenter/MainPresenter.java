package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MainContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityDialogEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import cn.lex_mung.client_android.mvp.ui.activity.EditInfoActivity;
import cn.lex_mung.client_android.utils.GsonUtil;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void popupList(){
        Map<String, Object> map = new HashMap<>();
        map.put("solutionTypeId", 11111);
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

    public void checkVersion() {
        mModel.checkVersion()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<VersionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<VersionEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (baseResponse.getData().getHasUpdate() == 1) {//有更新
                                mRootView.startDownload(baseResponse.getData());
                                return;
                            }
                            LogUtil.e("您当前是最新版本!");
                            setHelpDialog();
                            getOnlineUrl();
                            popupList();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        setHelpDialog();
                        getOnlineUrl();
                        popupList();
                    }
                });
    }

    public void setHelpDialog() {
        if (!DataHelper.getBooleanSF(mRootView.getActivity(), DataHelperTags.IS_ONE_IN)) {
            DataHelper.setBooleanSF(mRootView.getActivity(), DataHelperTags.IS_ONE_IN, true);
            mRootView.showHelpDialog();
        }
    }

    public void onResume() {
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
            if (entity == null) {
                DataHelper.setBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS, false);
                return;
            }
            String userId = "lex" + entity.getMemberId();
            String password = AppUtils.encodeToMD5(entity.getMobile());
            JMessageClient.login(userId, password, new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        LogUtils.debugInfo("IM登录成功");
                    } else {
                        LogUtils.debugInfo("IM登录失败" + responseCode + "  " + responseMessage);
                    }
                }
            });
            switch (DataHelper.getIntergerSF(mApplication, DataHelperTags.LOGIN_TYPE)) {
                case 1:
                    mRootView.launchActivity(new Intent(mApplication, EditInfoActivity.class));
                    break;
            }
            DataHelper.setIntergerSF(mApplication, DataHelperTags.LOGIN_TYPE, -1);
        }
    }

    public void getOnlineUrl() {
        mModel.clientOnlineUrl()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<OnlineUrlEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<OnlineUrlEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.ONLINE_URL, baseResponse.getData().getUrl());
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
