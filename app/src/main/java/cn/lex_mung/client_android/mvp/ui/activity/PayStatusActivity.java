package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.PayStatusTags;
import cn.lex_mung.client_android.di.component.DaggerPayStatusComponent;
import cn.lex_mung.client_android.di.module.PayStatusModule;
import cn.lex_mung.client_android.mvp.contract.PayStatusContract;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.presenter.PayStatusPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.EQUITIES_REFRESH.EQUITIES_REFRESH_1;
import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_CONFIRM;
import static cn.lex_mung.client_android.app.EventBusTags.PAY_INFO.PAY_INFO;

public class PayStatusActivity extends BaseActivity<PayStatusPresenter> implements PayStatusContract.View {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.group_release_demand)
    Group groupReleaseDemand;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.bt_back2)
    Button btBack2;

    boolean isSuccess = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayStatusComponent
                .builder()
                .appComponent(appComponent)
                .payStatusModule(new PayStatusModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_status;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent.containsKey(BundleTags.WX)) {
            if (bundleIntent.getInt(BundleTags.WX) == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
                showFailLayout("用户取消");
                return;
            }
        }
        if (bundleIntent.containsKey(BundleTags.ZFB)) {
            if ("6001".equals(bundleIntent.getString(BundleTags.ZFB))) {//用户取消
                showFailLayout("用户取消");
                return;
            }
        }
        mPresenter.setGoCouponListener(v -> {
            bundle.clear();
            bundle.putInt(BundleTags.TYPE, 1);
            launchActivity(new Intent(mActivity, OrderCouponActivity.class), bundle);
        });
        mPresenter.checkOrderStatus(bundleIntent.getString(BundleTags.ORDER_NO));
    }

    @Override
    public void showSuccessLayout(String s) {
        ivIcon.setImageResource(R.drawable.ic_pay_success);
        tvStatus.setText(s);
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
        btBack2.setVisibility(View.GONE);
        isSuccess = true;
    }


    @Override
    public void showSuccessLayout(String s, String btnStr) {
        showSuccessLayout(s);
        btBack.setText(btnStr);
    }

    @Override
    public void showSuccessLayout(String s, String btnStr, String btnStr2) {
        showSuccessLayout(s);
        btBack.setText(btnStr);
        btBack2.setVisibility(View.VISIBLE);
        btBack2.setText(btnStr2);
    }

    @Override
    public void showFailLayout(String s) {
        ivIcon.setImageResource(R.drawable.ic_pay_failure);
        tvStatus.setText(s);
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
        btBack2.setVisibility(View.GONE);
        isSuccess = false;
    }

    @Override
    public void showFailLayout(String s, String btnStr) {
        showFailLayout(s);
        btBack.setText(btnStr);
    }

    @Override
    public void showFailLayout(String s, String btnStr, String btnStr2) {
        showFailLayout(s);
        btBack.setText(btnStr);
        btBack2.setVisibility(View.VISIBLE);
        btBack2.setText(btnStr2);
    }

    @Override
    public void setContentLayout(String s, View.OnClickListener onClickListener) {
        tvContent.setText(s);
        tvContent.setOnClickListener(onClickListener);
    }

    @Override
    public void setImg(int imageId) {
        ivIcon.setImageResource(imageId);
    }

    @Override
    public void showReleaseDemandLayout(String tip, String orderNo, String payTime, String orderType, String money) {
        groupReleaseDemand.setVisibility(View.VISIBLE);
        tvContent.setText(tip);
        tvOrderNumber.setText(orderNo);
        tvOrderDate.setText(payTime);
        tvOrderType.setText(orderType);
        tvOrderMoney.setText(money);
        btBack.setText("确定");
    }

    //发布需求和快速电话咨询，按钮改为确定
    @OnClick({R.id.bt_back, R.id.bt_back2})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.bt_back:
                if(isSuccess){
                    switch (DataHelper.getIntergerSF(mActivity, DataHelperTags.PAY_TYPE)) {
                        case PayStatusTags.PAY://充值，无优惠券
                        case PayStatusTags.PAY_COUPON://充值,有优惠券
                        case PayStatusTags.PAY_EXPERT://充值(专家咨询-按钮显示专家咨询)
                        case PayStatusTags.FAST_CONSULT://快速咨询
                            killMyself();
                            break;
                        case PayStatusTags.RELEASE_DEMAND://发布需求
                            AppManager.getAppManager().killActivity(ReleaseDemandActivity.class);//发需求页
                            AppUtils.post(PAY_INFO, PAY_CONFIRM);//这个是用来通知抢单页面的。（热门需求）
                            killMyself();
                            break;
                        case PayStatusTags.ONLINE_LAWYER://在线法律顾问
                        case PayStatusTags.PRIVATE_LAWYER://私人律师团
                            killMyself();

                            AppManager.getAppManager().killAllNotClass(MainActivity.class);
                            ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(1);
                            break;
                        default:
                            killMyself();
                            break;
                    }
                }else{
                    killMyself();
                }
                break;
            case R.id.bt_back2:
                if(isSuccess){
                    switch (DataHelper.getIntergerSF(mActivity, DataHelperTags.PAY_TYPE)) {
                        case PayStatusTags.ONLINE_LAWYER://在线法律顾问
                            killMyself();
                            AppManager.getAppManager().killAllNotClass(MainActivity.class);
                            ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(1);

                            String str = DataHelper.getStringSF(mActivity, DataHelperTags.ONLINE_LAWYER_URL);
                            HomeChildEntity entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                            if (!TextUtils.isEmpty(str) && entity != null) {
                                bundle.clear();
                                bundle.putString(BundleTags.URL, entity.getJumpurl());
                                bundle.putString(BundleTags.TITLE, entity.getTitle());
                                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                            }

                            break;
                        case PayStatusTags.PRIVATE_LAWYER://私人律师团
                            killMyself();
                            AppManager.getAppManager().killAllNotClass(MainActivity.class);
                            ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(1);
                            //TODO webActivity 进去有权益详情页  预订  私人律师团

                            break;
                        default:
                            killMyself();
                            break;
                    }
                }else{
                    switch (DataHelper.getIntergerSF(mActivity, DataHelperTags.PAY_TYPE)) {
                        case PayStatusTags.ONLINE_LAWYER://在线法律顾问
                        case PayStatusTags.PRIVATE_LAWYER://私人律师团
                            killMyself();
                            launchActivity(new Intent(mActivity,MyAccountActivity.class));
                            break;
                        default:
                            killMyself();
                            break;
                    }
                }
                break;
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
    public void killMyself() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
