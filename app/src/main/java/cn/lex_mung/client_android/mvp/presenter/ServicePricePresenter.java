package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.ReleaseDemandActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ServicePriceAdapter;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

@FragmentScope
public class ServicePricePresenter extends BasePresenter<ServicePriceContract.Model, ServicePriceContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawsHomePagerBaseEntity entity;

    private boolean isLogin;

    @Inject
    public ServicePricePresenter(ServicePriceContract.Model model, ServicePriceContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        this.entity = entity;
        ServicePriceAdapter adapter = new ServicePriceAdapter(entity.getRequireInfo(), entity.getMinimumDuration());
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            Bundle bundle = new Bundle();
            if (isLogin) {
                BusinessEntity businessEntity = adapter.getItem(position);
                if (businessEntity == null) return;
                if (view.getId() == R.id.item_tv_release) {
                    if (businessEntity.getRequirementType() == 1) {//发需求

                        switch (mRootView.getLawyerHomePageActivity().getRequireTypeId()) {
                            case 2://诉讼
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                            case 6://企业顾问
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                            case 9://线下见面
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                            case 8://专家咨询 电话咨询
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                            case 100://免费咨询
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                            default:
                                switch (businessEntity.getRequireTypeId()){
                                    case 3://起草合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_draw_contract_click");
                                        break;
                                    case 4://律师函
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_lawyers_letter_click");
                                        break;
                                    case 9://线下见面
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_meeting_offline_click");
                                        break;
                                    case 30://审查合同
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_review_of_contracts_click");
                                        break;
                                    case 6://法律顾问
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_legal_counsel_click");
                                        break;
                                    case 7://尽职调查
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_responsible_investigation_click");
                                        break;
                                    case 2://诉讼仲裁
                                        BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_litigation_arbitration_click");
                                        break;
                                }
                                break;
                        }


                        bundle.clear();
                        bundle.putInt(BundleTags.ID, businessEntity.getRequireTypeId());
                        bundle.putInt(BundleTags.TYPE, businessEntity.getType());
                        bundle.putString(BundleTags.TITLE, businessEntity.getRequireTypeName());
                        bundle.putInt(BundleTags.MEMBER_ID, entity.getMemberId());
                        bundle.putString(BundleTags.REGION, entity.getRegion());
                        bundle.putInt(BundleTags.REGION_ID, entity.getRegionId());
                        mRootView.launchActivity(new Intent(mApplication, ReleaseDemandActivity.class), bundle);
                    } else {//电话咨询
                        switch (mRootView.getLawyerHomePageActivity().getRequireTypeId()) {
                            case 2://诉讼
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "litigation_arbitration_lawyer_detail","litigation_arbitration_lawyer_detail_phone_click");
                                break;
                            case 6://企业顾问
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "enterprise_offline_lawyer_detail","enterprise_offline_lawyer_detail_phone_click");
                                break;
                            case 9://线下见面
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "meeting_offline_lawyer_detail","meeting_offline_lawyer_detail_phone_click");
                                break;
                            case 8://专家咨询 电话咨询
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "expert_consulation_lawyer_detail","expert_consulation_lawyer_detail_phone_click");
                                break;
                            case 100://免费咨询
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "free_consulation_lawyer_detail","free_consulation_lawyer_detail_phone_click");
                                break;
                            default:
                                BuryingPointHelp.getInstance().onEvent(mRootView.getLawyerHomePageActivity(), "search_lawyer_detail","search_lawyer_detail_phone_click");
                                break;
                        }
                        expertPrice();
                    }
                }
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                mRootView.launchActivity(new Intent(mApplication, LoginActivity.class), bundle);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    public LawsHomePagerBaseEntity getEntity() {
        return entity;
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
                        if (baseResponse.isSuccess()) {
                            if (!TextUtils.isEmpty(baseResponse.getData().getPhone())) {
                                mRootView.GoCall(baseResponse.getData().getPhone());
                            } else {
                                mRootView.showMessage("电话为空");
                            }
                        } else {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
