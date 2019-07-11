package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.CouponModeEntity;
import cn.lex_mung.client_android.mvp.ui.activity.RecommendLawyerActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.utils.DecimalUtil;
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
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayResultEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandServiceTypeAdapter;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_DISCOUNT_WAY2;

@ActivityScope
public class ReleaseDemandPresenter extends BasePresenter<ReleaseDemandContract.Model, ReleaseDemandContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    //    private LawsHomePagerBaseEntity lawsHomePagerBaseEntity;
    int lawsHomePagerBaseEntityId;
    String lawsHomePagerBaseEntityRegion;
    int lawsHomePagerBaseEntityRegionId;
    private UserInfoDetailsEntity userInfoDetailsEntity;
    private ReleaseDemandOrgMoneyEntity entity;

    private int requireTypeId = -1;//当前选择的需求ID
    private String requireTypeName;//当前选择的需求Name
    private int type;//1固定服务价格 2可协商服务价格
    private int organizationLevId = 0;//组织等级id
    private int organizationId = 0;
    //    private String lawyerField;//选择的擅长领域name
//    private int lawyerFieldPosition = -1;//选择的擅长领域
    private double balance;//当前余额
    private double amountNew;//会员卡余额
    private int payType = 1;//支付方式
    private int payTypeGroup;//支付方式为6 带的集团id（因为集团卡有多个）
    private int couponId;
    private int couponType = 1;//1优惠卡，2优惠券

    private float payMoney;//实付金额
    private float deduction;//优惠抵扣金额


    private boolean flag = false;

//    private List<String> fieldList = new ArrayList<>();

    private ReleaseDemandServiceTypeAdapter adapter;

    @Inject
    public ReleaseDemandPresenter(ReleaseDemandContract.Model model, ReleaseDemandContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 刷新优惠方式
     *
     * @param message message
     */
    @Subscriber(tag = REFRESH)
    private void refresh(Message message) {
        switch (message.what) {
//            case REFRESH_DISCOUNT_WAY:
//                organizationLevId = Integer.valueOf(message.obj.toString());
//                getReleaseDemandOrgMoney(1);
//                break;
            case REFRESH_DISCOUNT_WAY2:
                CouponModeEntity entity = (CouponModeEntity) message.obj;
                organizationLevId = entity.getOrgLevId();
                organizationId = entity.getOrgId();
                couponId = entity.getCouponId();
                getReleaseDemandOrgMoney(entity.getType());
                break;
        }
    }

//    public void setLawyerFieldPosition(int position) {
//        lawyerFieldPosition = position;
//    }

//    public void setLawyerField(String lawyerField) {
//        this.lawyerField = lawyerField;
//    }


    public float getPayMoney() {
        return payMoney;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setPayType(int payType, int payTypeGroup) {
        this.payType = payType;
        this.payTypeGroup = payTypeGroup;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public int getOrganizationLevId() {
        return organizationLevId;
    }

    public UserInfoDetailsEntity getUserInfoDetailsEntity() {
        return userInfoDetailsEntity;
    }

    //    public LawsHomePagerBaseEntity getLawsHomePagerBaseEntity() {
//        return lawsHomePagerBaseEntity;
//    }
    public int getLawsHomePagerBaseEntityId() {
        return lawsHomePagerBaseEntityId;
    }

    public int getCouponType() {
        return couponType;
    }

    public String getLawsHomePagerBaseEntityRegion() {
        return lawsHomePagerBaseEntityRegion;
    }

//    public String getLawyerField() {
//        return lawyerField;
//    }

//    public List<String> getFieldList() {
//        return fieldList;
//    }

    //    public void onCreate(LawsHomePagerBaseEntity lawsHomePagerBaseEntity, int requireTypeId,String requireTypeName) {
    public void onCreate(int lawsHomePagerBaseEntityId, String lawsHomePagerBaseEntityRegion, int lawsHomePagerBaseEntityRegionId, int requireTypeId, String requireTypeName) {
        initAdapter();
        this.requireTypeId = requireTypeId;
        this.requireTypeName = requireTypeName;

//        this.lawsHomePagerBaseEntity = lawsHomePagerBaseEntity;
        this.lawsHomePagerBaseEntityRegion = lawsHomePagerBaseEntityRegion;
        this.lawsHomePagerBaseEntityId = lawsHomePagerBaseEntityId;
        this.lawsHomePagerBaseEntityRegionId = lawsHomePagerBaseEntityRegionId;

        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);

        if (type == 1) {
            mRootView.showPayLayout();
        } else {
            mRootView.showProblemDescriptionLayout();
        }
        mRootView.setRegion(lawsHomePagerBaseEntityRegion);
//        for (LawsHomePagerBaseEntity.ChildBean businessRadarBean : lawsHomePagerBaseEntity.getBusinessInfo()) {
//            if(!TextUtils.isEmpty(businessRadarBean.getSolutionMarkName()))
//                fieldList.add(businessRadarBean.getSolutionMarkName());
//        }

//        if (fieldList.size() == 0) {
//            mRootView.hideFieldLayout();
//        }
        String string = mApplication.getString(R.string.text_release_demand_tip_2)
                + "<font color=\"#1EC88C\">"
                + mApplication.getString(R.string.text_lex_transaction_process)
                + "</font>";
        mRootView.setTip(string);
        mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, 0)));
        releaseDemandList(requireTypeId);//获取子服务类型
    }

    private void initAdapter() {
        adapter = new ReleaseDemandServiceTypeAdapter(type);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            BusinessEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.getMinAmount() == 0) return;
            if (type != 1) return;
            adapter.setPos(position);
            adapter.notifyDataSetChanged();
            requireTypeId = entity.getRequireTypeId();//选择子服务类型后，用子id
            requireTypeName = entity.getRequireTypeName();
            getReleaseDemandOrgMoney(couponType);
        });
        mRootView.initRecyclerView(adapter);
    }

    private void releaseDemandList(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", lawsHomePagerBaseEntityId);
        map.put("parentId", id);
        mModel.releaseDemandList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BusinessEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BusinessEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (type == 1) {
                                adapter.setNewData(baseResponse.getData());
                                if (baseResponse.getData().size() == 1) {
                                    requireTypeId = baseResponse.getData().get(0).getRequireTypeId();
                                    requireTypeName = baseResponse.getData().get(0).getRequireTypeName();
                                }
                            } else {
                                List<BusinessEntity> entities = new ArrayList<>();
                                for (BusinessEntity entity : baseResponse.getData()) {
                                    if (entity.getMinAmount() > 0) {
                                        entities.add(entity);
                                    }
                                }
                                adapter.setNewData(entities);
                            }

                            mRootView.hideLoadingLayout();

                        }
                    }
                });
    }

    public void getUserBalance() {
        UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        mModel.getUserBalance(userInfoDetailsEntity.getMemberId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            balance = baseResponse.getData().getBalanceAmount();
                            mRootView.setBalance(balance);
                        }
                    }
                });
    }

    //优惠方式：会员卡必须选择了某个权益组织才能显示
    private void getReleaseDemandOrgMoney(int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", userInfoDetailsEntity.getMemberId());
        map.put("lmemberId", lawsHomePagerBaseEntityId);
        map.put("type", type);
        map.put("requireTypeId", requireTypeId);

        if (organizationLevId != 0) {
            map.put("organizationLevId", organizationLevId);
        }

        if (couponId > 0) {
            map.put("couponId", couponId);
        }

        mModel.getReleaseDemandOrgMoney(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ReleaseDemandOrgMoneyEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ReleaseDemandOrgMoneyEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            entity = baseResponse.getData();
                            if (type == 1) {
                                couponType = 1;
                                couponId = -1;
                                organizationId = baseResponse.getData().getOrganizationId();
                                if (organizationLevId != -1) {
                                    organizationLevId = entity.getOrganizationLevId();
                                }
                                payMoney = entity.getAmount();
                                mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, entity.getAmount())));
                                mRootView.setDiscountWay(entity.getOrganizationLevelName());
                                if (entity.getAmountDis() > 0) {
                                    deduction = entity.getAmountDis();
                                    mRootView.setDiscountMoney(String.format(mApplication.getString(R.string.text_discount_money), AppUtils.formatAmount(mApplication, entity.getAmountDis())));
                                } else {
                                    mRootView.hideDiscountMoney();
                                    deduction = 0;
                                }
                                amountNew = entity.getAmountNew();
                                if (amountNew > 0) {
                                    mRootView.setClubCardBalance(amountNew);
                                }
                            } else if (type == 2) {
                                couponType = 2;
                                couponId = entity.getCouponId();
                                organizationId = -1;
                                organizationLevId = -1;
                                payMoney = entity.getPayment();
                                mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, entity.getPayment())));
                                mRootView.setDiscountWay(entity.getCouponName());
                                if (entity.getDeductionAmount() > 0) {
                                    deduction = entity.getDeductionAmount();
                                    mRootView.setDiscountMoney(String.format(mApplication.getString(R.string.text_discount_money), AppUtils.formatAmount(mApplication, entity.getDeductionAmount())));
                                } else {
                                    mRootView.hideDiscountMoney();
                                    deduction = 0;
                                }
                            }
                        } else {
                            if(type == 1){
                                getReleaseDemandOrgMoney(2);
                            }
                        }
                    }
                });
    }

    public void releaseRequirement(String ua, String maxMoney, String content) {
//        if (fieldList.size() > 0
//                && lawyerFieldPosition == -1) {
//            mRootView.showMessage(mApplication.getString(R.string.text_please_select_lawyer_field));
//            return;
//        }
        if (type == 1 && requireTypeId == -1) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_select_service_type));
            return;
        }

        if (type == 1) {
            switch (payType) {
                case 1:
                    if (!AppUtils.isWeixinAvilible(mRootView.getActivity())) {
                        AppUtils.makeText(mRootView.getActivity(), "微信未安装");
                        return;
                    }
                    break;
                case 2:
                    if (!flag) {
                        getPermission(ua, maxMoney, content);
                        return;
                    }
                    break;
                case 3://余额支付
                    if (payMoney > balance) {
                        mRootView.showLackOfBalanceDialog();
                        return;
                    }
                    break;
                case 4://会员卡支付
                    if (payMoney > amountNew) {
                        mRootView.showMessage(mApplication.getString(R.string.text_members_card_lack_of_balance));
                        return;
                    }
                    break;
            }
            if (payMoney == 0) {
                mRootView.showMessage(mApplication.getString(R.string.text_amount_of_error));
                return;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", 0);
        map.put("isFirst", 1);
//        if (lawyerFieldPosition > -1) {
//            map.put("skillId", lawsHomePagerBaseEntity.getBusinessInfo().get(lawyerFieldPosition).getSolutionMarkId());
//        }
        map.put("targetLawyerId", lawsHomePagerBaseEntityId);
        map.put("lawyerRegionId", lawsHomePagerBaseEntityRegionId);

        map.put("requirementTypeId", requireTypeId);//固定价格，为子服务id，协商价格为父服务id
        map.put("requirementTypeName", requireTypeName);
        if (type == 1) {//固定价格
            map.put("maxCost", 0);
            map.put("requirementContent", "");
        } else {//可协商价格
            if (TextUtils.isEmpty(maxMoney)) {
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
            map.put("maxCost", Integer.valueOf(maxMoney));
            map.put("requirementContent", content);
        }
        mModel.releaseRequirement(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
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
                            if (type == 1) {
                                pay(ua, baseResponse.getData().getRequirementId());
                            } else {
                                mRootView.showMessage("需求发布成功，我们会尽快为您审核！");
                                mRootView.killMyself();
                                Bundle bundle = new Bundle();
                                bundle.putInt(BundleTags.REQUIRE_TYPE_ID, requireTypeId);
                                bundle.putString(BundleTags.REQUIRE_TYPE_NAME, requireTypeName);
                                bundle.putInt(BundleTags.REGION_ID, lawsHomePagerBaseEntityRegionId);
                                bundle.putInt(BundleTags.REQUIREMENT_ID, baseResponse.getData().getRequirementId());
                                bundle.putInt(BundleTags.L_MEMBER_ID, lawsHomePagerBaseEntityId);
                                bundle.putString(BundleTags.MONEY, maxMoney);
                                bundle.putString(BundleTags.CONTENT, content);
                                mRootView.launchActivity(new Intent(mRootView.getActivity(), RecommendLawyerActivity.class), bundle);
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    private void getPermission(String ua, String maxMoney, String content) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                flag = true;
                releaseRequirement(ua, maxMoney, content);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法前往支付宝支付");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    private void pay(String ua, int id) {
//        long money = new BigDecimal(payMoney).multiply(new BigDecimal(100)).intValue();
        long money = (long) DecimalUtil.multiply(payMoney, 100);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);//金额
        map.put("type", payType);//支付类型 1微信 2支付宝 3余额支付 4会员卡支付 5百度 6集团卡
        if (deduction > 0) {
            long moneyCoupon = (long) DecimalUtil.multiply(deduction, 100);
            map.put("deduction", moneyCoupon);//优惠金额
        }
        map.put("source", 2);//来源 2app
        map.put("product", 5);//订单类型 5发需求
        map.put("ua", ua);//ua
//        if (payType == 4 && couponId > 0) {//会员卡支付并且有会员卡
        if (couponId > 0) {//会员卡支付并且有会员卡
            map.put("useCoupon", 1);//使用优惠券
//            map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + couponId +  ",\"orgId\":" + organizationLevId +"}");

            if (payType == 6) {
                map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + couponId + ",\"orgId\":" + organizationLevId + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"requirementId\":" + id + ",\"couponId\":" + couponId + ",\"orgId\":" + organizationLevId + "}");
            }

        } else {
            map.put("useCoupon", 0);//不使用优惠券
            if (payType == 6) {
                map.put("other", "{\"requirementId\":" + id + ",\"orgId\":" + organizationLevId + ",\"groupCardId\":" + payTypeGroup + "}");
            } else {
                map.put("other", "{\"requirementId\":" + id + ",\"orgId\":" + organizationLevId + "}");
            }

        }
        String sign = "money=" + money + "&type=" + payType + "&source=" + 2 + "&ua=" + ua;
        map.put("sign", AppUtils.encodeToMD5(sign));
        mModel.pay(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading("正在获取支付信息..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PayEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PayEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_TYPE, requireTypeName);
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), AppUtils.formatAmount(mApplication, entity.getAmount())));
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_NO, baseResponse.getData().getOrderNo());
                            if (payType == 1) {//微信
                                DataHelper.setStringSF(mApplication, DataHelperTags.APP_ID, baseResponse.getData().getAppid());
                                IWXAPI api = WXAPIFactory.createWXAPI(mApplication, baseResponse.getData().getAppid(), true);
                                api.registerApp(baseResponse.getData().getAppid());
                                PayReq request = new PayReq();
                                request.appId = baseResponse.getData().getAppid();
                                request.partnerId = baseResponse.getData().getPartnerId();
                                request.prepayId = baseResponse.getData().getPrepayId();
                                request.packageValue = baseResponse.getData().getPkg();
                                request.nonceStr = baseResponse.getData().getNonceStr();
                                request.timeStamp = baseResponse.getData().getTimestamp();
                                request.sign = baseResponse.getData().getSign();
                                api.sendReq(request);
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                            } else if (payType == 2) {//支付宝
                                Runnable payRunnable = () -> {
                                    PayTask payTask = new PayTask(mRootView.getActivity());
                                    Map<String, String> result = payTask.payV2(baseResponse.getData().getOrderInfo(), true);

                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {//余额or会员卡支付
                                DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                                Bundle bundle = new Bundle();
                                bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                                Intent intent = new Intent(mApplication, PayStatusActivity.class);
                                intent.putExtras(bundle);
                                mRootView.launchActivity(intent);
                                mRootView.killMyself();
                            }
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    DataHelper.setIntergerSF(mApplication, DataHelperTags.PAY_TYPE, PayStatusTags.RELEASE_DEMAND);
                    PayResultEntity payResult = new PayResultEntity((Map<String, String>) msg.obj);
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(mApplication, DataHelperTags.ORDER_NO));
                    bundle.putString(BundleTags.ZFB, payResult.getResultStatus());
                    Intent intent = new Intent(mApplication, PayStatusActivity.class);
                    intent.putExtras(bundle);
                    mRootView.launchActivity(intent);
                    break;
                }
            }
        }
    };

    public void tariffExplanationUrl() {
        mModel.tariffExplanationUrl()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AgreementEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AgreementEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            Bundle bundle = new Bundle();
                            bundle.clear();
                            bundle.putString(BundleTags.URL, baseResponse.getData().getTariffExplanationUrl());
                            bundle.putString(BundleTags.TITLE, "绿豆圈交易流程及服务规范");
                            bundle.putBoolean(BundleTags.IS_SHARE, false);
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);

                        }
                    }
                });
    }

    //集团余额
    public void getGroupBalance() {
        mModel.clientOrgAmount()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<OrgAmountEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<OrgAmountEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setGroupBalance(baseResponse.getData());
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
