package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerRushLoanPayComponent;
import cn.lex_mung.client_android.di.module.RushLoanPayModule;
import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.CommodityContentEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;
import cn.lex_mung.client_android.mvp.presenter.RushLoanPayPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.CommodityContentAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.PayTypeView2;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.ORDER_COUPON;
import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.REFRESH_COUPON;

/**
 * 抢单需求
 */
public class RushLoanPayActivity extends BaseActivity<RushLoanPayPresenter> implements RushLoanPayContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.payTypeView2)
    PayTypeView2 payTypeView;

    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_commodity)
    TextView tvCommodity;
    @BindView(R.id.tv_commodity_price)
    TextView tvCommodityPrice;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @BindView(R.id.tv_discount_way)
    TextView tvDiscountWay;
    @BindView(R.id.rl_coupon_type)
    RelativeLayout rlCouponType;
    @BindView(R.id.tv_coupon_count)
    TextView tvCouponCount;

    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_order_money_text)
    TextView tvOrderMoneyText;

    @BindView(R.id.ll_commodity_content)
    LinearLayout llCommodityContent;
    @BindView(R.id.recycler_view_commodity)
    RecyclerView recyclerViewCommodity;

    private DefaultDialog defaultDialog;
    int type;//0热门需求,1快速咨询,2付费权益,3诉讼无忧保服务
    int id;

    private int couponId;
    private float orderPrice;//订单原价
    private float couponPrice;//优惠价格

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRushLoanPayComponent
                .builder()
                .appComponent(appComponent)
                .rushLoanPayModule(new RushLoanPayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rush_loan_pay;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {

            orderPrice = bundleIntent.getFloat(BundleTags.MONEY);
            type = bundleIntent.getInt(BundleTags.TYPE);
            id = bundleIntent.getInt(BundleTags.ID);

            tvCommodity.setText(bundleIntent.getString(BundleTags.TITLE));
            tvCommodityPrice.setText(StringUtils.getStringNum(orderPrice) + "元");

            mPresenter.setRequireTypeId(id);
            mPresenter.setRequireTypeName(bundleIntent.getString(BundleTags.REQUIRE_TYPE_NAME));//热门需求 名称
            mPresenter.setMobile(bundleIntent.getString(BundleTags.MOBILE));//快速咨询 电话
            mPresenter.setPayMoney(orderPrice);
            mPresenter.setType(type);

            mPresenter.setLawsuiId(bundleIntent.getString(BundleTags.LAWSUI_ID));

            List<String> legalAdviserIds = bundleIntent.getStringArrayList(BundleTags.ENTITY);
            mPresenter.setLegalAdviserIds(legalAdviserIds);
            mPresenter.setMeetNum(bundleIntent.getInt(BundleTags.NUM));

            if(legalAdviserIds != null && legalAdviserIds.size() > 0){
                mPresenter.legalAdviserOrderConfirm(orderPrice);
            }
        }

        payTypeView.setItemOnClick((type, type6Id) -> {
            mPresenter.setPayType(type, type6Id);
        });

        initViewData();

        mPresenter.getAllBalance();

        if(type != 3){
            mPresenter.getCoupon(orderPrice,id);
            mPresenter.getCouponCount(orderPrice,id);
        }else{
            hideCouponLayout();
        }
    }

    public void hideCouponLayout(){
        rlCouponType.setVisibility(View.GONE);
        tvCouponCount.setVisibility(View.GONE);
    }

    public void setCommodityContent(List<CommodityContentEntity> entities){
        if(entities == null || entities.size() == 0) return;
        llCommodityContent.setVisibility(View.VISIBLE);

        AppUtils.configRecyclerView(recyclerViewCommodity, new LinearLayoutManager(mActivity));
        CommodityContentAdapter adapter = new CommodityContentAdapter();
        recyclerViewCommodity.setAdapter(adapter);

        adapter.setNewData(entities);
    }

    //默认布局和数据
    public void initViewData() {
        couponPrice = 0;
        couponId = 0;

        setPriceLayout(couponPrice, orderPrice);//实付价格，优惠价格
        tvDiscountWay.setText("");//优惠券为空
        tvDiscountMoney.setVisibility(View.GONE);//以优惠**元 隐藏
    }

    @OnClick({R.id.bt_pay, R.id.rl_coupon_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_pay:
                if(type == 3){//3诉讼无忧保服务
                    mPresenter.buyEquity500Pay(webView.getSettings().getUserAgentString());
                }else if(type == 2){//付费权益
                    mPresenter.buyEquityCreate(webView.getSettings().getUserAgentString());
                }else if (type == 1) {//快速咨询
                    BuryingPointHelp.getInstance().onEvent(mActivity, "quick_consultation_pay_page", "quick_consultation_pay_page_pay_click");
                    mPresenter.quickPay("name", webView.getSettings().getUserAgentString());
                } else {//热门需求
                    mPresenter.releaseRequirementCreate(webView.getSettings().getUserAgentString());
                }
                break;
            case R.id.rl_coupon_type:
                bundle.clear();
                bundle.putInt(BundleTags.ID, couponId);//优惠券id
                if(type == 3){
                    return;
                }else if(type == 2){
                    bundle.putInt(BundleTags.TYPE, 3);
                }else if(type == 1){
                    bundle.putInt(BundleTags.TYPE, 0);
                }else {
                    bundle.putInt(BundleTags.TYPE, 2);
                }
//                bundle.putInt(BundleTags.TYPE, type == 1?0:2);
                bundle.putInt(BundleTags.REQUIREMENT_ID,id);
                bundle.putDouble(BundleTags.MONEY, orderPrice);
                launchActivity(new Intent(mActivity, OrderCouponActivity.class), bundle);
                break;
        }
    }

    // ------- 优惠券 start

    //设置优惠券
    @Override
    public void setCouponLayout(OrderCouponEntity bean, boolean showToast) {
        if (bean == null) {
            tvDiscountWay.setText("");
            this.couponId = 0;
            setPriceLayout(0, orderPrice);
            return;
        }

        if (orderPrice < bean.getFullNum()) {
            if (showToast) {
                showMessage("无法使用优惠券");
            }
            LogUtil.e("无法使用优惠券");
            return;
        }

        tvDiscountWay.setText(bean.getCouponName());
        this.couponId = bean.getCouponId();

        mPresenter.getPrice(couponId, orderPrice,id);
    }

    //设置价格
    @Override
    public void setPriceLayout(float couponPrice, double payPrice) {
        this.couponPrice = couponPrice;

        tvOrderMoney.setText(String.format(
                AppUtils.getString(mActivity, R.string.text_yuan_money)
                , StringUtils.getStringNum(payPrice)));
        tvDiscountMoney.setText(String.format(
                AppUtils.getString(mActivity, R.string.text_discount_money)
                , StringUtils.getStringNum(couponPrice)));

        if (couponPrice > 0) {
            tvDiscountMoney.setVisibility(View.VISIBLE);
        } else {
            tvDiscountMoney.setVisibility(View.GONE);
        }
    }

    //获取优惠价格
    @Override
    public float getCouponPrice() {
        return couponPrice;
    }

    //获取优惠id
    @Override
    public int getCouponId() {
        return couponId;
    }

    /**
     * 更新优惠券
     */
    @Subscriber(tag = ORDER_COUPON)
    private void selectPlace(Message message) {
        switch (message.what) {
            case REFRESH_COUPON:
                OrderCouponEntity bean = (OrderCouponEntity) message.obj;
                setCouponLayout(bean, true);
                break;
        }
    }

    //--------优惠券 end

    @Override
    public void setAllBalance(AmountBalanceEntity balanceEntity) {
        List<PayTypeEntity> list = new ArrayList<>();
        if (balanceEntity.getAmount() != null) {
            PayTypeEntity entity = new PayTypeEntity();
            entity.setIcon(R.drawable.ic_pay_balance2);
            entity.setTitle("账户余额");
            entity.setType(3);
            entity.setSelected(false);
            entity.setBalance(balanceEntity.getAmount().getAllBalanceAmount());
            list.add(entity);
        }
        if(type != 3){
            if (balanceEntity.getOrgAmounts() != null && balanceEntity.getOrgAmounts().size() > 0) {
                for (int i = 0; i < balanceEntity.getOrgAmounts().size(); i++) {
                    PayTypeEntity entity = new PayTypeEntity();
                    entity.setIcon(R.drawable.ic_pay_group);
                    entity.setTitle(balanceEntity.getOrgAmounts().get(i).getCouponName());
                    entity.setType(6);
                    entity.setSelected(false);
                    entity.setBalance(balanceEntity.getOrgAmounts().get(i).getAmount());
                    entity.setGroupId(balanceEntity.getOrgAmounts().get(i).getCouponId());
                    list.add(entity);
                }
            }
        }

        payTypeView.setPayTYpeData(list);
    }

    public double getTypeBalance(int payType, int payTypeGroup) {
        return payTypeView.getTypeBalance(payType, payTypeGroup);
    }

    @Override
    public void setCouponCountLayout(int couponCount) {
        tvCouponCount.setVisibility(View.VISIBLE);
        tvCouponCount.setText("您有"+couponCount+"个可用的优惠方式");
    }

    @Override
    public void setPayTypeViewSelect(double money) {
        payTypeView.setSelect(money);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
                    , getString(R.string.text_manual_open_permissions)
                    , getString(R.string.text_to_open)
                    , getString(R.string.text_cancel));
        }
        if (!defaultDialog.isShowing()) {
            defaultDialog.show();
        }
    }

    @Override
    public void showLackOfBalanceDialog() {
        new DefaultDialog(mActivity, dialog -> launchActivity(new Intent(mActivity, MyAccountActivity.class))
                , "您账户余额不足，是否前往充值？"
                , "去充值"
                , getString(R.string.text_cancel))
                .show();
    }


    @Override
    public void showLoading(@NonNull String message) {
        loading = LoadingDialog.getInstance().init(mActivity, message, false);
        loading.show();
    }

    @Override
    public void hideLoading() {
        if (loading != null
                && loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        AppUtils.makeText(mActivity, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        AppUtils.startActivity(intent);
    }

    @Override
    public void launchActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launchActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(type == 1){//快速咨询
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consultation_pay_page", getPair());
        }else{//需求抢单

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(type == 1){//快速咨询
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consultation_pay_page", getPair());
        }else{//需求抢单

        }
    }
}
