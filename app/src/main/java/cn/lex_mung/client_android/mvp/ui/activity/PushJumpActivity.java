package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.module.PushJumpModule;
import cn.lex_mung.client_android.mvp.model.entity.JMessageEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import cn.lex_mung.client_android.di.component.DaggerPushJumpComponent;
import cn.lex_mung.client_android.mvp.contract.PushJumpContract;
import cn.lex_mung.client_android.mvp.presenter.PushJumpPresenter;

import cn.lex_mung.client_android.R;

import static cn.lex_mung.client_android.app.DataHelperTags.IS_LOGIN_SUCCESS;

public class PushJumpActivity extends BaseActivity<PushJumpPresenter> implements PushJumpContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPushJumpComponent
                .builder()
                .appComponent(appComponent)
                .pushJumpModule(new PushJumpModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_push_jump;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            String json;
            json = bundleIntent.getString(BundleTags.JSON);
            JMessageEntity entity = new Gson().fromJson(json, JMessageEntity.class);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            switch (entity.getSubType()) {
                case 100://欢迎消息
                    intent.setClass(mActivity, MainActivity.class);
                    break;
                case 107:
                    bundle.clear();
                    bundle.putString(BundleTags.URL, entity.getUrl());
                    intent.putExtras(bundle);
                    intent.setClass(mActivity, WebActivity.class);
                    break;
                case 109:
                case 241:
                case 252:
                case 261://261--律师接受专家咨询订单，发到用户端
                case 262://262--律师取消了专家咨询订单，发到用户端
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyOrderActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 240:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getBusiId());
                        bundle.putBoolean(BundleTags.IS_SHOW, true);
                        intent.putExtras(bundle);
                        intent.setClass(mActivity, FreeConsultDetail1Activity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 244:
                case 245:
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        intent.setClass(mActivity, MyAccountActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
                case 280://法律顾问 - 权益分享用户提醒
                    String str = DataHelper.getStringSF(mActivity, DataHelperTags.ONLINE_LAWYER_URL);
                    HomeChildEntity mEntity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
                    if (!TextUtils.isEmpty(str) && mEntity != null) {
                        bundle.clear();
                        bundle.putString(BundleTags.URL, mEntity.getJumpurl());
                        bundle.putString(BundleTags.TITLE, mEntity.getTitle());
                        intent.putExtras(bundle);
                        intent.setClass(mActivity, WebActivity.class);
                    }
                    break;
                case 281://法律顾问 - 订单详情
                    if (DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getBusiId());
                        intent.putExtras(bundle);
                        intent.setClass(mActivity, OrderDetailsBuyEquityActivity.class);
                    } else {
                        intent.setClass(mActivity, LoginActivity.class);
                    }
                    break;
//                case 282://法律顾问 - 抢单
//                    break;
                case 290://私人律师团 - 权益分享用户提醒
                    String str2 = DataHelper.getStringSF(mActivity, DataHelperTags.PRIVATE_LAWYER_URL);
                    HomeChildEntity mEntity2 = GsonUtil.convertString2Object(str2, HomeChildEntity.class);
                    if (!TextUtils.isEmpty(str2) && mEntity2 != null) {
                        bundle.clear();
                        bundle.putString(BundleTags.URL, mEntity2.getJumpurl());
                        bundle.putString(BundleTags.TITLE, mEntity2.getTitle());
                        intent.putExtras(bundle);
                        intent.setClass(mActivity, WebActivity.class);
                    }
                    break;
//                case 291://私人律师团 - 新订单发布提醒
//                    break;
                case 292://私人律师团 - 订单详情
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getBusiId());
                    intent.putExtras(bundle);
                    intent.setClass(mActivity, OrderDetailsPrivateLawyerActivity.class);
                    launchActivity(intent);
                    break;
//                default:
//                    showMessage("当前消息可能需要新版本才能打开，建议检测是否存在最新版本。");
//                    break;
            }
            launchActivity(intent);
        } catch (Exception ignored) {
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
}
