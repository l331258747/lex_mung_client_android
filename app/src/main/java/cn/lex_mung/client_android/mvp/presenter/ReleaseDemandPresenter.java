package cn.lex_mung.client_android.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
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
import me.zl.mvp.utils.StringUtils;
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
    private int payType = 1;//支付方式
    private int payTypeGroup;//支付方式为6 带的集团id（因为集团卡有多个）
    private int couponId;
    private int couponType = 1;//1优惠卡，2优惠券

    private float payMoney;//实付金额
    private float deduction;//优惠抵扣金额


    private boolean flag = false;

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
            case REFRESH_DISCOUNT_WAY2:
                CouponModeEntity entity = (CouponModeEntity) message.obj;
                organizationLevId = entity.getOrgLevId();
                organizationId = entity.getOrgId();
                couponId = entity.getCouponId();
                getReleaseDemandOrgMoney(entity.getType());
                break;
        }
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

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

    public int getLawsHomePagerBaseEntityId() {
        return lawsHomePagerBaseEntityId;
    }

    public int getCouponType() {
        return couponType;
    }


    public void onCreate(int lawsHomePagerBaseEntityId, String lawsHomePagerBaseEntityRegion, int lawsHomePagerBaseEntityRegionId, int requireTypeId, String requireTypeName) {
        initAdapter();
        this.requireTypeId = requireTypeId;
        this.requireTypeName = requireTypeName;

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

        String string = "3.详细流程请查看"
                + "<font color=\"#1EC88C\">"
                + "《绿豆圈交易流程及服务规范》"
                + "</font>";
        mRootView.setTip(string);
        mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(0)));
        releaseDemandList(requireTypeId);//获取子服务类型
    }

    private void initAdapter() {
        adapter = new ReleaseDemandServiceTypeAdapter(type);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            BusinessEntity entity = adapter.getItem(position);
            if (entity == null) return;
            if (entity.getMinAmount() == 0) return;//价格为0返回
            if (type != 1) return;//固定价格返回
            if (adapter.getPos() == position) return;//重复选择返回
            adapter.setPos(position);
            adapter.notifyDataSetChanged();
            requireTypeId = entity.getRequireTypeId();//选择子服务类型后，用子id
            requireTypeName = entity.getRequireTypeName();

            organizationLevId = 0;
            couponId = 0;
            getReleaseDemandOrgMoney(1);
            getCouponCount(entity.getMinAmount(),requireTypeId,lawsHomePagerBaseEntityId);
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
                                List<BusinessEntity> entities = new ArrayList<>();
                                for (BusinessEntity entity : baseResponse.getData()) {
                                    if (entity.getMinAmount() > 0) {
                                        entities.add(entity);
                                    }
                                }
                                adapter.setNewData(entities);
                                if (entities.size() == 1) {
                                    requireTypeId = entities.get(0).getRequireTypeId();
                                    requireTypeName = entities.get(0).getRequireTypeName();
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

    //优惠方式：会员卡必须选择了某个权益组织才能显示
    private void getReleaseDemandOrgMoney(int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", userInfoDetailsEntity.getMemberId());
        map.put("lmemberId", lawsHomePagerBaseEntityId);
//        map.put("type", type == -1 ? 1 : type);
        map.put("type", type);
        map.put("requireTypeId", requireTypeId);

        if (organizationLevId != 0) {
            map.put("organizationLevId", organizationLevId);
        }

        if (couponId != 0) {
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
//                            if (type == 1 || type == -1) {
                            if (type == 1) {
                                if (baseResponse.getData().getOrgStatus() == 1) {
                                    couponType = 1;
                                    couponId = -1;
                                    organizationId = baseResponse.getData().getOrganizationId();
                                    if (organizationLevId != -1) {
                                        organizationLevId = entity.getOrganizationLevId();
                                    }
                                    payMoney = entity.getAmount();
                                    mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(entity.getAmount())));
                                    mRootView.setDiscountWay(entity.getOrganizationLevelName());
                                    if (entity.getAmountDis() > 0) {
                                        deduction = entity.getAmountDis();
                                        mRootView.setDiscountMoney(String.format(mApplication.getString(R.string.text_discount_money), StringUtils.getStringNum(entity.getAmountDis())));
                                    } else {
                                        mRootView.hideDiscountMoney();
                                        deduction = 0;
                                    }
                                    getAllBalance();
                                } else {
                                    getReleaseDemandOrgMoney(2);
                                }
                            } else if (type == 2) {
                                couponType = 2;
                                couponId = entity.getCouponId();
                                organizationId = -1;
                                organizationLevId = -1;
                                payMoney = entity.getPayment();
                                mRootView.setOrderMoney(String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(entity.getPayment())));
                                mRootView.setDiscountWay(entity.getCouponName());
                                if (entity.getDeductionAmount() > 0) {
                                    deduction = entity.getDeductionAmount();
                                    mRootView.setDiscountMoney(String.format(mApplication.getString(R.string.text_discount_money), StringUtils.getStringNum(entity.getDeductionAmount())));
                                } else {
                                    mRootView.hideDiscountMoney();
                                    deduction = 0;
                                }
                                getAllBalance();
                            }

                        }
                    }
                });
    }

    public void releaseRequirement(String ua, String maxMoney, String content) {
        if (type == 1 && requireTypeId == -1) {
            mRootView.showMessage("请选择服务类型");
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
                    if (payMoney > mRootView.getTypeBalance(3,0)) {
                        mRootView.showLackOfBalanceDialog();
                        return;
                    }
                    break;
                case 4://会员卡支付
                    if (payMoney > mRootView.getTypeBalance(4,0)) {
                        mRootView.showMessage("会员卡余额不足");
                        return;
                    }
                    break;
                case 6://集团卡支付
                    if (payMoney > mRootView.getTypeBalance(6,payTypeGroup)) {
                        mRootView.showMessage("集团卡余额不足");
                        return;
                    }
                    break;
            }
            if (payMoney == 0) {
                mRootView.showMessage("金额错误");
                return;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", 0);
        map.put("isFirst", 1);
        map.put("targetLawyerId", lawsHomePagerBaseEntityId);
        map.put("lawyerRegionId", lawsHomePagerBaseEntityRegionId);

        map.put("requirementTypeId", requireTypeId);//固定价格，为子服务id，协商价格为父服务id
        map.put("requirementTypeName", requireTypeName);
        if (type == 1) {//固定价格
            map.put("maxCost", 0);
            map.put("requirementContent", "");
        } else {//可协商价格
            if (TextUtils.isEmpty(maxMoney)) {
                mRootView.showMessage("请输入您愿意支付的律师费用");
                return;
            }
            if (TextUtils.isEmpty(content)) {
                mRootView.showMessage("请输入您遇到的问题");
                return;
            }
            if (content.length() < 10) {
                mRootView.showMessage("请简单描述您遇到的问题(限10-300个字)");
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
                            DataHelper.setStringSF(mApplication, DataHelperTags.ORDER_MONEY, String.format(mApplication.getString(R.string.text_yuan_money), StringUtils.getStringNum(payMoney)));
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
                            mRootView.launchActivity(new Intent(mApplication, WebActivity.class), bundle);

                        }
                    }
                });
    }

    public void getAllBalance() {
        Map<String, Object> map = new HashMap<>();
        mModel.amountBalance(organizationLevId,RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AmountBalanceEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AmountBalanceEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setAllBalance(baseResponse.getData());
                        }
                    }
                });
    }

    public void getCouponCount(double orderAmount,int productId,int lmemberId){
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("orderAmount", orderAmount);
        map.put("lmemberId", lmemberId);
        mModel.couponCount(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Integer>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Integer> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if(baseResponse.getData() > 0)
                                mRootView.setCouponCountLayout(baseResponse.getData());
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
