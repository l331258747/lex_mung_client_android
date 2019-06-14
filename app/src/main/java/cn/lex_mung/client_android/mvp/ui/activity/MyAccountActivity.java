//package cn.lex_mung.client_android.mvp.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.TextView;
//
//import cn.lex_mung.client_android.R;
//import cn.lex_mung.client_android.app.BundleTags;
//import cn.lex_mung.client_android.di.component.DaggerMyAccountComponent;
//import cn.lex_mung.client_android.di.module.MyAccountModule;
//import cn.lex_mung.client_android.mvp.contract.MyAccountContract;
//import cn.lex_mung.client_android.mvp.presenter.MyAccountPresenter;
//import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import me.zl.mvp.base.BaseActivity;
//import me.zl.mvp.di.component.AppComponent;
//import me.zl.mvp.utils.AppUtils;
//
//public class MyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {
//
//    @BindView(R.id.tv_right)
//    TextView tvRight;
//    @BindView(R.id.tv_balance)
//    TextView tvBalance;
//
//    @Override
//    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMyAccountComponent
//                .builder()
//                .appComponent(appComponent)
//                .myAccountModule(new MyAccountModule(this))
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return R.layout.activity_my_account;
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        tvRight.setVisibility(View.VISIBLE);
//        tvRight.setText(getString(R.string.TransactionListActivity));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mPresenter.getUserBalance();
//    }
//
//    @Override
//    public void setBalance(String balance) {
//        tvBalance.setText(balance);
//    }
//
//
//    @OnClick({R.id.tv_right,R.id.bt_pay, R.id.bt_withdrawal})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_right:
//                launchActivity(new Intent(mActivity, TradingListActivity.class));
//                break;
//            case R.id.bt_pay:
//                launchActivity(new Intent(mActivity, AccountPayActivity.class));
//                break;
//            case R.id.bt_withdrawal:
//                if (mPresenter.getBalance() > 0) {
//                    bundle.clear();
//                    bundle.putDouble(BundleTags.BALANCE, mPresenter.getBalance());
//                    launchActivity(new Intent(mActivity, AccountWithdrawalActivity.class), bundle);
//                } else {
//                    showMessage(getString(R.string.text_no_withdrawal_amount));
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void showLoading(@NonNull String message) {
//        loading = LoadingDialog.getInstance().init(mActivity, message, false);
//        loading.show();
//    }
//
//    @Override
//    public void hideLoading() {
//        if (loading != null
//                && loading.isShowing())
//            loading.dismiss();
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        AppUtils.makeText(mActivity, message);
//    }
//
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        AppUtils.startActivity(intent);
//    }
//
//    @Override
//    public void launchActivity(Intent intent, Bundle bundle) {
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        launchActivity(intent);
//    }
//
//    @Override
//    public void killMyself() {
//        finish();
//    }
//}

package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerMyAccountComponent;
import cn.lex_mung.client_android.di.module.MyAccountModule;
import cn.lex_mung.client_android.mvp.contract.MyAccountContract;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.presenter.MyAccountPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.utils.DecimalUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class MyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.bt_pay)
    TextView bt_pay;
    @BindView(R.id.iv_select_zfb)
    ImageView ivSelectZfb;
    @BindView(R.id.iv_select_wx)
    ImageView ivSelectWx;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_zfb)
    TextView tvZfb;
    @BindView(R.id.bt_detail)
    TextView btDetail;
    @BindView(R.id.bt_withdrawal)
    TextView btWithdrawal;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_tip2)
    TextView tvTip2;

    private DefaultDialog defaultDialog;
    private ExpertPriceEntity entity;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAccountComponent
                .builder()
                .appComponent(appComponent)
                .myAccountModule(new MyAccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_account2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(bundleIntent != null){
            if((entity = (ExpertPriceEntity) bundleIntent.getSerializable(BundleTags.ENTITY)) != null){
                setTip(entity);
            }
        }
        mPresenter.onCreate(entity);
    }

    public void setTip(ExpertPriceEntity entity){
//        String string1 = "【律师名称】律师的咨询费用为【通话单价】\n" +
//                "当前余额可与【律师名称】通话【通话时长】，请确保余额大于通话【保底时长】分钟所需的【最低余额】元（通话不足【保底时长】分钟按【保底时长】分钟计算，超过【保底时长】分钟时按分钟计费。）";
//        String string11 = "【律师名称】律师的咨询费用为【通话单价】，您拥有【权益组织】的专属权益，优惠后的价格为【优惠价格】。\n" +
//                "当前余额可与【律师名称】通话【通话时长】，请确保余额大于通话【保底时长】分钟所需的【最低余额】元（通话不足【保底时长】分钟按【保底时长】分钟计算，超过【保底时长】分钟时按分钟计费。）";
//        String string2 = "充值后可增加与【律师名称】【增加时长】通话时长";

        String string1 = "%1$s律师的咨询费用为%2$s\n" +
                "当前余额可与%3$s通话%4$s，请确保余额大于通话%5$s分钟所需的%6$s元（通话不足%7$s分钟按%8$s分钟计算，超过%9$s分钟时按实际通话分钟数计费。）";
        String string11 = "%1$s律师的咨询费用为%2$s，您拥有%3$s的专属权益，优惠后的价格为%4$s。\n" +
                "当前余额可与%5$s通话%6$s，请确保余额大于通话%7$s分钟所需的%8$s元（通话不足%9$s分钟按%10$s分钟计算，超过%11$s分钟时按实际通话分钟数计费。）";

        tvTip.setVisibility(View.VISIBLE);
        tvTip2.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(entity.getOrgnizationName())) {//有权益
            tvTip.setText(String.format(string11,
                    entity.getLawyerName(),
                    entity.getPriceStr(),
                    entity.getOrgnizationName(),
                    entity.getOrgnizationPriceStr(),
                    entity.getLawyerName(),
                    entity.getFavorableTimeLen(),
                    entity.getMinimumDuration(),
                    entity.getMinimumBalance(),
                    entity.getMinimumDuration(),
                    entity.getMinimumDuration(),
                    entity.getMinimumDuration()));
        }else{
            tvTip.setText(String.format(string1,
                    entity.getLawyerName(),
                    entity.getPriceStr(),
                    entity.getLawyerName(),
                    entity.getOriginTimeLen(),
                    entity.getMinimumDuration(),
                    entity.getMinimumBalance(),
                    entity.getMinimumDuration(),
                    entity.getMinimumDuration(),
                    entity.getMinimumDuration()));
        }
    }


    @Override
    public void initRecyclerView(MyAccountPayAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setOrderMoney(String format) {
        tvOrderMoney.setText(format);
    }

    public void setTip2(String str){
        String string2 = "充值后即可与%1$s通话%2$s分钟。";
        tvTip2.setText(String.format(string2,
                entity.getLawyerName(),
                str));
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
    protected void onResume() {
        super.onResume();
        mPresenter.getUserBalance();
    }

    @Override
    public void setBalance(String balance) {
        tvBalance.setText(balance);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.bt_pay, R.id.bt_detail,R.id.bt_withdrawal,R.id.tv_wx,R.id.tv_zfb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_detail://明细
                launchActivity(new Intent(mActivity, TradingListActivity.class));
                break;
            case R.id.bt_withdrawal:
                if (mPresenter.getBalance() > 0) {
                    bundle.clear();
                    bundle.putDouble(BundleTags.BALANCE, mPresenter.getBalance());
                    launchActivity(new Intent(mActivity, AccountWithdrawalActivity.class), bundle);
                } else {
                    showMessage(getString(R.string.text_no_withdrawal_amount));
                }
                break;
            case R.id.tv_wx:
                ivSelectWx.setImageResource(R.drawable.ic_radio_yes);
                ivSelectZfb.setImageResource(R.drawable.ic_radio_un);
                mPresenter.setPayType(1);
                break;
            case R.id.tv_zfb:
                ivSelectWx.setImageResource(R.drawable.ic_radio_un);
                ivSelectZfb.setImageResource(R.drawable.ic_radio_yes);
                mPresenter.setPayType(2);
                break;
            case R.id.bt_pay:
                MobclickAgent.onEvent(mActivity, "w_y_chongzhi_list_tj");
                mPresenter.pay(webView.getSettings().getUserAgentString());
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
}
