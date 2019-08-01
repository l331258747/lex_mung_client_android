package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
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
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;
import cn.lex_mung.client_android.mvp.presenter.RushLoanPayPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.PayTypeView2;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
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
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @BindView(R.id.tv_discount_way)
    TextView tvDiscountWay;
    @BindView(R.id.rl_coupon_type)
    RelativeLayout rlCouponType;

    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_order_money_text)
    TextView tvOrderMoneyText;

    private DefaultDialog defaultDialog;
    int type;
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
            tvCommodity.setText(bundleIntent.getString(BundleTags.TITLE));

            orderPrice = bundleIntent.getFloat(BundleTags.MONEY);
            type = bundleIntent.getInt(BundleTags.TYPE);
            id = bundleIntent.getInt(BundleTags.ID);

            mPresenter.setRequireTypeId(id);
            mPresenter.setRequireTypeName(bundleIntent.getString(BundleTags.REQUIRE_TYPE_NAME));
            mPresenter.setMobile(bundleIntent.getString(BundleTags.MOBILE));
            mPresenter.setPayMoney(orderPrice);
            mPresenter.setType(type);
        }

        payTypeView.setItemOnClick((type, type6Id) -> {
            mPresenter.setPayType(type, type6Id);
        });

        initViewData();

        mPresenter.getAllBalance();

        mPresenter.getCoupon(orderPrice,id);
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
                if (type == 1) {//快速咨询
                    mPresenter.pay("name", webView.getSettings().getUserAgentString());
                } else {//热门需求
                    mPresenter.releaseRequirement(webView.getSettings().getUserAgentString());
                }
                break;
            case R.id.rl_coupon_type:
                bundle.clear();
                bundle.putInt(BundleTags.ID, couponId);//优惠券id
                bundle.putInt(BundleTags.TYPE, type == 1?0:2);
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
        payTypeView.setPayTYpeData(list);
    }

    public double getTypeBalance(int payType, int payTypeGroup) {
        return payTypeView.getTypeBalance(payType, payTypeGroup);
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
                , getString(R.string.text_lack_of_balance)
                , getString(R.string.text_leave_for_top_up)
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
}
