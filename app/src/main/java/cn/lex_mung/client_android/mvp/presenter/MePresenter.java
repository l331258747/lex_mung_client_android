package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.MeContract;
import cn.lex_mung.client_android.mvp.model.entity.AboutEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RightsVipEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;

@FragmentScope
public class MePresenter extends BasePresenter<MeContract.Model, MeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isLogin = false;//是否登录

    private AboutEntity aboutEntity;
    private UserInfoDetailsEntity entity;

    @Inject
    public MePresenter(MeContract.Model model, MeContract.View rootView) {
        super(model, rootView);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public AboutEntity getAboutEntity() {
        return aboutEntity;
    }

    /**
     * 更新登录信息
     *
     * @param message message
     */
    @Subscriber(tag = LOGIN_INFO)
    private void loginInfo(Message message) {
        switch (message.what) {
            case LOGOUT:
                loginStatusDispose();
                break;
        }
    }

    /**
     * 登录状态处理
     */
    public void loginStatusDispose() {
        try {
            isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
            if (isLogin) {
                entity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
                setData();
                mRootView.showLoginLayout();
                getUserInfoDetail();
            } else {
                mRootView.hideLoginLayout();
            }
        } catch (Exception ignored) {
            isLogin = false;
            mRootView.hideLoginLayout();
        }
    }

    private void getUserInfoDetail() {
        mModel.getUserInfoDetail()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            entity = baseResponse.getData();
                            setData();
                        }
                    }
                });
    }

    private void setData() {
        try {
            //头像
            if (!TextUtils.isEmpty(entity.getIconImage())) {
                mRootView.setAvatar(entity.getIconImage());
            }
            //姓名
            if (!TextUtils.isEmpty(entity.getMemberName())) {
                mRootView.setName(entity.getMemberName());
            } else if (!TextUtils.isEmpty(entity.getMobile())
                    && entity.getMobile().length() >= 3) {
                mRootView.setName("用户" + entity.getMobile().substring(0, 3) + "********");
            }
            //地区
            if (entity.getAddressExtend() != null) {
                mRootView.setRegion(entity.getAddressExtend().getProvince() + entity.getAddressExtend().getCity());
            }
            //组织
            if (entity.getOrganizations() != null
                    && entity.getOrganizations().size() > 0) {
                mRootView.setOrg(entity.getOrganizations().get(0).getOrganizationName());
            } else {
                mRootView.hideOrgLayout();
            }
            //性别
            if (entity.getSex() == 0) {//未知
                mRootView.setSex(R.drawable.round_100_00_all_f4f4f4
                        , AppUtils.getColor(mApplication, R.color.c_ff)
                        , 0);
                mRootView.hideSexIcon();
                if (TextUtils.isEmpty(entity.getAge())) {
                    mRootView.hideAgeLayout();
                }
            } else if (entity.getSex() == 1) {//男
                mRootView.setSex(R.drawable.round_100_00_all_76cbff
                        , AppUtils.getColor(mApplication, R.color.c_76cbff)
                        , R.drawable.ic_man);
                mRootView.showSexIcon();
            } else {//女
                mRootView.setSex(R.drawable.round_100_00_all_ff7878
                        , AppUtils.getColor(mApplication, R.color.c_ff7878)
                        , R.drawable.ic_woman);
                mRootView.showSexIcon();
            }
            //年龄
            if (!TextUtils.isEmpty(entity.getAge())) {
                mRootView.setAge(entity.getAge());
            }
        } catch (Exception ignored) {
        }
    }

    public void getAbout() {
        mModel.getAbout()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AboutEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AboutEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            aboutEntity = baseResponse.getData();
                            DataHelper.setStringSF(mApplication, DataHelperTags.MOBILE, aboutEntity.getKefuPhone());
                        }
                    }
                });
    }

    public void rightsVip() {
        if (!DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mRootView.changeVipData(null);
            return;
        }
        mModel.rightsVip()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RightsVipEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RightsVipEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            List<RightsVipEntity> entities = new ArrayList<>();
                            for (RightsVipEntity item : baseResponse.getData())
                                if (item.isIsOwn())
                                    entities.add(item);
                            mRootView.changeVipData(entities);
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
