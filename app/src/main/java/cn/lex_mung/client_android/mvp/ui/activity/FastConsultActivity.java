package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerFastConsultComponent;
import cn.lex_mung.client_android.di.module.FastConsultModule;
import cn.lex_mung.client_android.mvp.contract.FastConsultContract;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.presenter.FastConsultPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StringUtils;

import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.ORDER_COUPON;
import static cn.lex_mung.client_android.app.EventBusTags.ORDER_COUPON.REFRESH_COUPON;

//删除
public class FastConsultActivity extends BaseActivity<FastConsultPresenter> implements FastConsultContract.View {

    @BindView(R.id.tv_consult_type)
    TextView tvConsultType;
    @BindView(R.id.et_contact_phone)
    EditText etContactPhone;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.iv_mr)
    ImageView ivMr;
    @BindView(R.id.iv_ms)
    ImageView ivMs;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.iv_select_zfb)
    ImageView ivSelectZfb;
    @BindView(R.id.tv_balance_count)
    TextView tvBalanceCount;
    @BindView(R.id.tv_discount_way)
    TextView tvDiscountWay;
    @BindView(R.id.iv_select_balance)
    ImageView ivSelectBalance;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @BindView(R.id.tv_change_phone_number)
    TextView tvChangePhoneNumber;
    @BindView(R.id.tv_fast_consult_tip)
    TextView tvFastConsultTip;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.web_view)
    WebView webView;

    private DefaultDialog defaultDialog;
    private EasyDialog easyDialog;

    private int couponId;
    private double orderPrice;//订单原价
    private float couponPrice;//优惠价格

    private String consultType = "";
    private int pos = -1 ;

    private int buryingPointId = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFastConsultComponent
                .builder()
                .appComponent(appComponent)
                .fastConsultModule(new FastConsultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fast_consult;
    }

    //设置优惠券
    @Override
    public void setCouponLayout(OrderCouponEntity bean, boolean showToast) {
        if(bean == null){
            tvDiscountWay.setText("");
            this.couponId = 0;
            setPriceLayout(0,0,orderPrice);
            return;
        }

        if(orderPrice < bean.getFullNum()){
            if(showToast){
                showMessage("无法使用优惠券");
            }
            LogUtil.e("无法使用优惠券");
            return;
        }

        tvDiscountWay.setText(bean.getCouponName());
        this.couponId = bean.getCouponId();

        mPresenter.getPrice(couponId,orderPrice);
    }

    //设置价格
    @Override
    public void setPriceLayout(double orderPrice,float couponPrice,double payPrice) {

        this.couponPrice = couponPrice;

        tvOrderMoney.setText(String.format(
                AppUtils.getString(mActivity, R.string.text_yuan_money)
                , StringUtils.getStringNum(payPrice)));
        tvDiscountMoney.setText(String.format(
                AppUtils.getString(mActivity, R.string.text_discount_money)
                , StringUtils.getStringNum(couponPrice)));
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


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            buryingPointId = bundleIntent.getInt(BundleTags.BURYING_POINT, -1);
        }
        mPresenter.onCreate();
        setPriceLayout(0,0,0);
        setCouponLayout(null,false);

        String string = "4.详细流程请查看"
                + "<font color=\"#1EC88C\">"
                + "《绿豆圈交易流程及服务规范》"
                + "</font>";
        tvFastConsultTip.setText(Html.fromHtml(string));
//        groupCount.setVisibility(View.GONE);
    }

    //设置价格
    @Override
    public void setOrderMoney(String money,double orderMoney) {
        tvOrderMoney.setText(money);
        orderPrice = orderMoney;
    }

    /**
     * 选择问题类型
     */
    @SuppressLint("InflateParams")
    private void showSelectTypeDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_select_industry_dialog, null);
        initDialog(layout);
        WheelPicker wpConsultType = layout.findViewById(R.id.wheel_1);
        wpConsultType.setCurved(false);
        wpConsultType.setVisibleItemCount(6);

        consultType = mPresenter.getSolutionTypeStringList().get(0);
        pos = 0;
        wpConsultType.setOnItemSelectedListener((picker, data, position) -> {
            consultType = data.toString();
            pos = position;
        });
        wpConsultType.setData(mPresenter.getSolutionTypeStringList());
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            setPriceLayout(0,0,0);
            setCouponLayout(null,false);

            mPresenter.setConsultType(consultType);
            mPresenter.setConsultTypePos(pos);
            mPresenter.setMoney(pos);
            tvConsultType.setText(consultType);

            mPresenter.getCoupon();
            dismiss();

            BuryingPointHelp.getInstance().onEvent(mActivity, "quick_consultation","quick_consultation_goodat_click");
        });
    }

    /**
     * 更新优惠券
     */
    @Subscriber(tag = ORDER_COUPON)
    private void selectPlace(Message message) {
        switch (message.what) {
            case REFRESH_COUPON:
                OrderCouponEntity bean = (OrderCouponEntity) message.obj;
                setCouponLayout(bean,true);
                break;
        }
    }

    @Override
    public void setPhone(String mobile) {
        etContactPhone.setText(mobile);
    }

    @Override
    public void setBalance(String balance) {
        tvBalanceCount.setText(balance);
    }



//    @Override
//    public void setMoney(String money) {
//        tvMoney.setText(money);
//    }

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
        new DefaultDialog(mActivity, dialog -> {
            launchActivity(new Intent(mActivity, MyAccountActivity.class));
        }
                , "您账户余额不足，是否前往充值？"
                , "去充值"
                , getString(R.string.text_cancel))
                .show();
    }

    @OnClick({
            R.id.view_consult_type
            , R.id.tv_wx
            , R.id.tv_zfb
            , R.id.tv_balance
            , R.id.bt_pay
            , R.id.tv_change_phone_number
            , R.id.tv_fast_consult_tip
            , R.id.tv_fast_consult_tip_1
            , R.id.tv_ms
            , R.id.tv_mr
            , R.id.view_discount_way
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fast_consult_tip:
            case R.id.tv_fast_consult_tip_1:
                mPresenter.tariffExplanationUrl();
                break;
            case R.id.view_consult_type:
                showSelectTypeDialog();
                break;
            case R.id.tv_mr:
                ivMr.setImageResource(R.drawable.ic_show_select);
                ivMs.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setSex(1);
                break;
            case R.id.tv_ms:
                ivMr.setImageResource(R.drawable.ic_hide_select);
                ivMs.setImageResource(R.drawable.ic_show_select);
                mPresenter.setSex(2);
                break;
            case R.id.tv_wx:
                ivSelectWx.setImageResource(R.drawable.ic_show_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(1);
                break;
            case R.id.tv_zfb:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_show_select);
                ivSelectBalance.setImageResource(R.drawable.ic_hide_select);
                mPresenter.setPayType(2);
                break;
            case R.id.tv_balance:
                ivSelectWx.setImageResource(R.drawable.ic_hide_select);
                ivSelectZfb.setImageResource(R.drawable.ic_hide_select);
                ivSelectBalance.setImageResource(R.drawable.ic_show_select);
                mPresenter.setPayType(3);
                break;
            case R.id.bt_pay:
                BuryingPointHelp.getInstance().onEvent(mActivity, "quick_consultation","quick_consultation_post_click");
                mPresenter.pay(etUserName.getText().toString(), webView.getSettings().getUserAgentString());
                break;
            case R.id.tv_change_phone_number:
                if ("更换手机号".contentEquals(tvChangePhoneNumber.getText())) {
                    etContactPhone.setText("");
                    etContactPhone.requestFocus();
                    DeviceUtils.openSoftKeyboard(etContactPhone);
                    tvChangePhoneNumber.setText("使用注册手机号");
                } else {
                    etContactPhone.setText(mPresenter.getMobile());
                    DeviceUtils.hideSoftKeyboard(etContactPhone);
                    tvChangePhoneNumber.setText("更换手机号");
                }
                break;
            case R.id.view_discount_way:
                if(orderPrice <= 0){
                    showMessage("请先选择问题类型");
                    return;
                }
                bundle.clear();
                bundle.putInt(BundleTags.ID, couponId);//优惠券id
                bundle.putInt(BundleTags.TYPE,0);
                bundle.putDouble(BundleTags.MONEY,orderPrice);
                launchActivity(new Intent(mActivity, OrderCouponActivity.class), bundle);
                break;
        }
    }

    /**
     * 初始化dialog
     */
    private void initDialog(View layout) {
        easyDialog = new EasyDialog(this)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(mActivity, R.color.c_00))
                .setLocationByAttachedView(viewBottom)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mActivity, R.color.c_50))
                .show();
    }

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(buryingPointId == 1){
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consulation_from_solution",getPair());
        }else{
            BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consultation",getPair());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(buryingPointId == 1){
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consulation_from_solution",getPair());
        }else{
            BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consultation",getPair());
        }
    }
}
