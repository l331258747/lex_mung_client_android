package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerPayStatusComponent;
import cn.lex_mung.client_android.di.module.PayStatusModule;
import cn.lex_mung.client_android.mvp.contract.PayStatusContract;
import cn.lex_mung.client_android.mvp.presenter.PayStatusPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;

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
                showFailLayout(AppUtils.getString(mActivity, R.string.text_pay_cancel));
                return;
            }
        }
        if (bundleIntent.containsKey(BundleTags.ZFB)) {
            if ("6001".equals(bundleIntent.getString(BundleTags.ZFB))) {//用户取消
                showFailLayout(AppUtils.getString(mActivity, R.string.text_pay_cancel));
                return;
            }
        }
        mPresenter.checkOrderStatus(bundleIntent.getString(BundleTags.ORDER_NO));
    }

    @Override
    public void showSuccessLayout(String s) {
        ivIcon.setImageResource(R.drawable.ic_pay_success);
        tvStatus.setText(s);
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
        isSuccess = true;
    }

    @Override
    public void showSuccessLayout(String s,String btnStr) {
        showSuccessLayout(s);
        btBack.setText(btnStr);
    }

    @Override
    public void showFailLayout(String s) {
        ivIcon.setImageResource(R.drawable.ic_pay_failure);
        tvStatus.setText(s);
        tvStatus.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
        isSuccess = false;
    }

    @Override
    public void showReleaseDemandLayout(String tip, String orderNo, String payTime, String orderType, String money) {
        groupReleaseDemand.setVisibility(View.VISIBLE);
        tvContent.setText(tip);
        tvOrderNumber.setText(orderNo);
        tvOrderDate.setText(payTime);
        tvOrderType.setText(orderType);
        tvOrderMoney.setText(money);
        btBack.setText(getString(R.string.text_confirm));
    }

    //发布需求和快速电话咨询，按钮改为确定
    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        if (isFastClick()) return;
        if (getString(R.string.text_confirm).equals(btBack.getText().toString())) {
            AppManager.getAppManager().killActivity(ReleaseDemandActivity.class);
            AppUtils.post(PAY_INFO,PAY_CONFIRM);//这个是用来通知抢单页面的。
        }
        if(isSuccess){//成功后把充值页面干掉，失败返回充值页面
            AppManager.getAppManager().killActivity(MyAccountActivity.class);
        }
        killMyself();
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
