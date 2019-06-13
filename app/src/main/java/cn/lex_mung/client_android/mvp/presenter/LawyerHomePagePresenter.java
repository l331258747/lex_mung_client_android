package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.fragment.LawsBusinessCardFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.PracticeExperienceFragment;
import cn.lex_mung.client_android.mvp.ui.fragment.ServicePriceFragment;
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

@ActivityScope
public class LawyerHomePagePresenter extends BasePresenter<LawyerHomePageContract.Model, LawyerHomePageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<Fragment> fragments = new ArrayList<>();

    private int id;

    private LawsHomePagerBaseEntity entity;

    private boolean isShowLoading = true;
    private boolean isLoading = true;

    private boolean isLogin;

    @Inject
    public LawyerHomePagePresenter(LawyerHomePageContract.Model model, LawyerHomePageContract.View rootView) {
        super(model, rootView);
    }

    public void setId(int id) {
        this.id = id;
    }

    public LawsHomePagerBaseEntity getEntity() {
        return entity;
    }

    public void getLawsHomePagerBase() {
        String json;
        if (!TextUtils.isEmpty(json = DataHelper.getString(mApplication, DataHelperTags.LAWS_HOME_PAGER_BASE + "_" + id, "_" + id))) {
            entity = new Gson().fromJson(json, LawsHomePagerBaseEntity.class);
            setData();
            isShowLoading = false;
        }
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mModel.getLawsHomePagerBase1(id)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> {
                        if (isShowLoading) mRootView.showLoading("");
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<LawsHomePagerBaseEntity>>(mErrorHandler) {

                        @Override
                        public void onNext(BaseResponse<LawsHomePagerBaseEntity> baseResponse) {
                            entity = baseResponse.getData();
                            DataHelper.setString(mApplication, DataHelperTags.LAWS_HOME_PAGER_BASE + "_" + id, "_" + id, new Gson().toJson(entity));
                            setData();
                        }
                    });
        } else {
            mModel.getLawsHomePagerBase(id)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> mRootView.showLoading(""))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<LawsHomePagerBaseEntity>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<LawsHomePagerBaseEntity> baseResponse) {
                            entity = baseResponse.getData();
                            setData();
                        }
                    });
        }
    }

    @SuppressLint("InflateParams")
    private void setData() {
        try {
            if (isLoading) {
                fragments.add(LawsBusinessCardFragment.newInstance(entity));
                fragments.add(PracticeExperienceFragment.newInstance(entity));
                fragments.add(ServicePriceFragment.newInstance(entity));
                mRootView.initViewPager(fragments);
                isLoading = false;
            }

            mRootView.showCall(entity.showCall());

            //背景
            if (!TextUtils.isEmpty(entity.getBackgroundImage())) {
                mRootView.setTopBg(entity.getBackgroundImage());
            } else {
                mRootView.setTopBg(R.drawable.ic_p_bg_1);
            }
            //头像
            if (!TextUtils.isEmpty(entity.getIconImage())) {
                mRootView.setAvatar(entity.getIconImage());
            } else {
                mRootView.setAvatar(R.drawable.ic_lawyer_avatar);
            }
            //姓名
            if (!TextUtils.isEmpty(entity.getMemberName())) {
                mRootView.setName(entity.getMemberName());
            } else if (!TextUtils.isEmpty(entity.getMobile())
                    && entity.getMobile().length() >= 3) {
                mRootView.setName("用户" + entity.getMobile().substring(0, 3) + "********");
            }
            //律师职位
            if (!TextUtils.isEmpty(entity.getMemberPositionName())) {
                mRootView.setPositionName(entity.getMemberPositionName());
            }
            //律所和执业年限
            if (!TextUtils.isEmpty(entity.getInstitutionName())
                    && !TextUtils.isEmpty(entity.getPractice())) {
                mRootView.setInstitutionNameAndPractice(String.format(AppUtils.getString(mApplication, R.string.text_post_num)
                        , entity.getInstitutionName()
                        , entity.getPractice()));
            } else if (!TextUtils.isEmpty(entity.getInstitutionName())) {
                mRootView.setInstitutionNameAndPractice(entity.getMemberPositionName());
            } else if (!TextUtils.isEmpty(entity.getPractice())) {
                mRootView.setInstitutionNameAndPractice(entity.getPractice());
            } else {
                mRootView.setInstitutionNameAndPractice("");
            }
            //担保额度
            if (entity.getLawyerTags() != null
                    && entity.getLawyerTags().size() > 0) {
                mRootView.setCreditCertification(entity.getLawyerTags().get(0).getTagName());
            } else {
                mRootView.hideCreditCertificationLayout();
            }
            //是否关注
            if (entity.getIsCollect() == 1) {//已关注
                mRootView.setLikeLayout(R.drawable.round_100_ffffff_all_717171
                        , AppUtils.getColor(mApplication, R.color.c_717171)
                        , AppUtils.getString(mApplication, R.string.text_like));
            } else if (entity.getIsCollect() == 0) {//未关注
                mRootView.setLikeLayout(R.drawable.round_100_ffffff_all_06a66a
                        , AppUtils.getColor(mApplication, R.color.c_06a66a)
                        , AppUtils.getString(mApplication, R.string.text_no_like));
            } else {//不显示
                mRootView.hideLikeLayout();
            }
            //擅长领域
            if (entity.getBusinessInfo() != null
                    && entity.getBusinessInfo().size() > 0) {
                mRootView.removeViews();
                for (int i = 0; i < entity.getBusinessInfo().size(); i++) {
                    int pos = i;
                    View itemView = LayoutInflater.from(mRootView.getActivity()).inflate(R.layout.item_personal_home_page_field, null, false);
                    TextView tvTitle = itemView.findViewById(R.id.item_tv_title);
                    tvTitle.setText(entity.getBusinessInfo().get(i).getSolutionTypeName());
                    itemView.setOnClickListener(v -> {
                        if (isFastClick()) return;
                        LawsHomePagerBaseEntity.ChildBean bean = entity.getBusinessInfo().get(pos);
                        mRootView.showFieldDialog(bean);
                    });
                    mRootView.addSimpleFlowLayout(itemView, i);
                }
            } else {
                if (entity.getSocialFunction() == null
                        || entity.getSocialFunction().size() == 0) {
                    mRootView.hideFieldLayout_1();
                } else {
                    mRootView.hideFieldLayout();
                }
            }
            //社会职务
            if (entity.getSocialFunction() != null
                    && entity.getSocialFunction().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < entity.getSocialFunction().size(); i++) {
                    stringBuilder.append(entity.getSocialFunction().get(i)).append("\n");
                    if (i == 2) break;
                }
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                if (entity.getSocialFunction().size() > 3) {
                    mRootView.showMoreSocialPositionLayout();
                }
                mRootView.setSocialPosition(stringBuilder.toString());
            } else {
                mRootView.hideSocialPosition();
            }
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage() + "~~~~");
        }
    }

    public void follow() {
        mModel.follow(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setLikeLayout(R.drawable.round_100_ffffff_all_717171
                                    , AppUtils.getColor(mApplication, R.color.c_717171)
                                    , AppUtils.getString(mApplication, R.string.text_like));
                        }
                    }
                });
    }

    public void unFollow() {
        mModel.unFollow(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setLikeLayout(R.drawable.round_100_ffffff_all_06a66a
                                    , AppUtils.getColor(mApplication, R.color.c_06a66a)
                                    , AppUtils.getString(mApplication, R.string.text_no_like));
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


    //---------电话
    public void setEntity() {
        if (isFastClick()) return;
        Bundle bundle = new Bundle();
        if (isLogin) {
            MobclickAgent.onEvent(mApplication, "w_y_shouye_zjzx_detail_boda");
            expertPrice();
        } else {
            bundle.clear();
            bundle.putInt(BundleTags.TYPE, 1);
            mRootView.launchActivity(new Intent(mApplication, LoginActivity.class), bundle);
        }
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    private void expertPrice() {
        mModel.expertPrice(entity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ExpertPriceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ExpertPriceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            ExpertPriceEntity expertPriceEntity = baseResponse.getData();
                            expertPriceEntity.setLawyerName(entity.getMemberName());
                            if (expertPriceEntity.getMinimumRecharge() == 0) {
                                mRootView.showBalanceYesDialog(expertPriceEntity);
                            } else {
                                mRootView.showBalanceNoDialog(expertPriceEntity);
                            }
                        }
                    }
                });
    }

    public void sendCall(String phone) {
//        mRootView.showDial1Dialog(String.format(mApplication.getString(R.string.text_call_consult_tip_3), phone));

        mModel.sendCall(entity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ExpertCallEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ExpertCallEntity> baseResponse) {
                        if(baseResponse.isSuccess()){
                            if(!TextUtils.isEmpty(baseResponse.getData().getPhone())){
                                mRootView.GoCall(baseResponse.getData().getPhone());
                            }else{
                                mRootView.showMessage("电话为空");
                            }
                        }else{
                             /*
                            70001：余额不足
                            70002：您好，当前律师可能正在繁忙，建议您改天再联系或者联系平台其他律师进行咨询。
                            70003：您好，该律师暂时无法接听您的电话，建议您联系平台其他律师或拨打客服热线400-811-3060及时处理。
                             */
                            switch (baseResponse.getCode()) {
                                case 70001:
                                    // 充值
                                    break;
                                case 70002:
                                    mRootView.showToErrorDialog(baseResponse.getMessage());
                                    break;
                                case 70003:
                                    mRootView.showToErrorDialog(baseResponse.getMessage());
                                    break;
                            }
                        }
                    }
                });
    }
}
