package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.EventBusTags;
import cn.lex_mung.client_android.app.ShareUtils;
import cn.lex_mung.client_android.di.component.DaggerWebComponent;
import cn.lex_mung.client_android.di.module.WebModule;
import cn.lex_mung.client_android.mvp.contract.WebContract;
import cn.lex_mung.client_android.mvp.model.entity.DeviceEntity2;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoEquityPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoOintmentEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoOrderDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebShareEntity;
import cn.lex_mung.client_android.mvp.presenter.WebPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.mvp.ui.widget.webview.LWebView;
import cn.lex_mung.client_android.mvp.ui.widget.webview.MyWebViewClient;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;

import static cn.lex_mung.client_android.app.EventBusTags.BUY_EQUITY_500_INFO.BUY_EQUITY_500;
import static cn.lex_mung.client_android.app.EventBusTags.BUY_EQUITY_500_INFO.BUY_EQUITY_500_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_ID;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGIN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LOGIN_INFO.LOGOUT;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_WX_PAY;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {
    @BindView(R.id.web_view)
    LWebView webView;
    @BindView(R.id.view_dialog)
    View viewDialog;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.titleView)
    TitleView titleView;


    private String url;
    private String title;
    private boolean isJump;//页面内跳转

    private boolean isShare;
    private String shareUrl;
    private String ShareTitle;
    private String shareImage;
    private String shareDes;


    private int buryingPointId;//快速电话咨询，用来判断从哪里进入的(正常，解决方案)
    AndroidToJs androidToJs;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebComponent
                .builder()
                .appComponent(appComponent)
                .webModule(new WebModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //webview中有一个输入框是在webview的底部，当我点击输入框的时候会弹出安卓的软键盘，这个时候我发现软键盘遮挡住了输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.activity_web;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(url)) {
            if (url.indexOf("contractList.html") > -1) {
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "more_contract_page", getPair());
            } else if (url.indexOf("solution.html") > -1) {
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "solution_detail_page", getPair());
            } else if (url.indexOf("member") > -1) {
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "enterprise_legal_card_detail_page", getPair());
            } else if (url.indexOf("quick.html") > -1) {
                if (buryingPointId == 1) {
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "solution_detail_quick_consulation_from_solution_page", getPair());
                } else {
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consultation_page", getPair());
                }
            }
        }
        mPresenter.onResume();
        webView.onWebResume();

        if (androidToJs.isGoLogin && webView != null && !TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
        androidToJs.isGoLogin = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(url)) {
            if (url.indexOf("contractList.html") > -1) {
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "more_contract_page", getPair());
            } else if (url.indexOf("solution.html") > -1) {
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "solution_detail_page", getPair());
            } else if (url.indexOf("member") > -1) {
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "enterprise_legal_card_detail_page", getPair());
            } else if (url.indexOf("quick.html") > -1) {
                if (buryingPointId == 1) {
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "solution_detail_quick_consulation_from_solution_page", getPair());
                } else {
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consultation_page", getPair());
                }
            }
        }
        webView.onWebPause();
    }

    @Override
    protected void onDestroy() {
        webView.onWebDestroy();
        webView = null;
        super.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            url = bundleIntent.getString(BundleTags.URL);
            title = bundleIntent.getString(BundleTags.TITLE);
            shareDes = bundleIntent.getString(BundleTags.SHARE_DES);
            shareImage = bundleIntent.getString(BundleTags.SHARE_IMAGE);
            shareUrl = bundleIntent.getString(BundleTags.SHARE_URL);
            ShareTitle = bundleIntent.getString(BundleTags.SHARE_TITLE);
            isShare = bundleIntent.getBoolean(BundleTags.IS_SHARE, false);
            isJump = bundleIntent.getBoolean(BundleTags.STATE, true);
            buryingPointId = bundleIntent.getInt(BundleTags.BURYING_POINT, -1);
        }

        if (TextUtils.isEmpty(title))
            title = "绿豆圈";
        if (TextUtils.isEmpty(ShareTitle))
            ShareTitle = "绿豆圈";

        LogUtil.e("url:" + url);

        titleView.setTitle(title);

        titleView.setRightTv("分享");
        titleView.getRightTv().setOnClickListener(v -> {
            if (!TextUtils.isEmpty(shareUrl)) {
                ShareUtils.shareUrl(mActivity
                        , viewDialog
                        , shareUrl
                        , ShareTitle
                        , shareDes
                        , shareImage);
            }
        });
        if (isShare) {
            titleView.getRightTv().setVisibility(View.VISIBLE);
        } else {
            titleView.getRightTv().setVisibility(View.GONE);
        }

        if (url.indexOf("caseEntrustment.html") > -1) {
            titleView.setRightTv("我的委托");
            titleView.getRightTv().setVisibility(View.VISIBLE);
            titleView.getRightTv().setOnClickListener(v -> {
                if (!DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                    return;
                }
                launchActivity(new Intent(mActivity, MyEntrustListActivity.class));
            });
        }

        initWebView();
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new MyWebViewClient(webView, isJump, string -> {
            Intent dialIntent2 = new Intent(Intent.ACTION_DIAL, Uri.parse(string));
            startActivity(dialIntent2);
        }));

        webView.addJavascriptInterface(androidToJs = new AndroidToJs(), "JsBridgeApp");//h5 js调用 app.pay();
        webView.synCookies(url);
        webView.loadUrl(url);
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
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 快速咨询 付款后 创建订单
     */
    @Subscriber(tag = BUY_EQUITY_500_INFO)
    private void buyEquity500(Message message) {
        switch (message.what) {
            case BUY_EQUITY_500:
                String payOrderNo = (String) message.obj;
                if(TextUtils.isEmpty(payOrderNo)) return;
                webView.loadUrl("javascript:checkPayStatus('" + payOrderNo + "')");
                break;
        }
    }


    @Override
    public Activity getActivity() {
        return this;
    }


    public class AndroidToJs extends Object {

        //诉讼无忧保服务 支付
        @JavascriptInterface
        public void goAppointmentPay(String string){
            if (TextUtils.isEmpty(string))
                return;
            WebGoOintmentEntity webGoOintmentEntity = GsonUtil.convertString2Object(string, WebGoOintmentEntity.class);
            if (isFastClick()) return;
            if (mPresenter.isLogin()) {
                bundle.clear();
                bundle.putInt(BundleTags.ID, webGoOintmentEntity.getRequireTypeId());
                bundle.putString(BundleTags.REQUIRE_TYPE_NAME, webGoOintmentEntity.getTitle());
                bundle.putString(BundleTags.TITLE, webGoOintmentEntity.getTitle());
                bundle.putFloat(BundleTags.MONEY, webGoOintmentEntity.getAmount());
                bundle.putString(BundleTags.LAWSUI_ID,webGoOintmentEntity.getLawsuiId());
                bundle.putInt(BundleTags.TYPE,3);
                launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }

        }


        //组织律师列表
        @JavascriptInterface
        public void getList(String organizationId) {
            if (TextUtils.isEmpty(organizationId))
                return;
            int orid;
            try {
                orid = Integer.valueOf(organizationId);
            } catch (Exception e) {
                return;
            }

            bundle.clear();
            bundle.putInt(BundleTags.ID, orid);
            launchActivity(new Intent(mActivity, OrganizationLawyerActivity.class), bundle);

        }


        //支付（抢单）
        @JavascriptInterface
        public void goPay(String string) {
            if (TextUtils.isEmpty(string))
                return;

            WebGoPayEntity businessEntity = GsonUtil.convertString2Object(string, WebGoPayEntity.class);

            if (isFastClick()) return;
            if (mPresenter.isLogin()) {
                bundle.clear();
                bundle.putInt(BundleTags.ID, businessEntity.getRequireTypeId());
                bundle.putString(BundleTags.REQUIRE_TYPE_NAME, businessEntity.getRequireTypeName());
                bundle.putString(BundleTags.TITLE, businessEntity.getRequireTypeName());
                bundle.putFloat(BundleTags.MONEY, businessEntity.getMoney());
                launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }
        }

        boolean isSetToken;

        //设置token
        @JavascriptInterface
        public void setToken(String token1) {
            LogUtil.e("token:" + token1);
            if (token1.equals("undefined")) return;

            DataHelper.setBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS, true);
            DataHelper.setStringSF(mActivity, DataHelperTags.TOKEN, token1);
//            mPresenter.getUserInfoDetail();
            isSetToken = true;

            mPresenter.getUserInfoDetail(null);
        }

        //快速电话咨询支付页面
        @JavascriptInterface
        public void goQuickPay(String string) {
            if (TextUtils.isEmpty(string))
                return;

            if (isSetToken) {
                mPresenter.getUserInfoDetail(() -> {
                    goRushLoanPayActivity(string);
                });
                isSetToken = false;
            } else {
                goRushLoanPayActivity(string);
            }
        }

        public void goRushLoanPayActivity(String string) {
            WebGoPayEntity businessEntity = GsonUtil.convertString2Object(string, WebGoPayEntity.class);

            if (isFastClick()) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, businessEntity.getRequireTypeId());
            bundle.putString(BundleTags.REQUIRE_TYPE_NAME, businessEntity.getRequireTypeName());
            bundle.putString(BundleTags.TITLE, "快速电话咨询");
            bundle.putFloat(BundleTags.MONEY, businessEntity.getMoney());
            bundle.putString(BundleTags.MOBILE, businessEntity.getMobile());
            bundle.putInt(BundleTags.TYPE, 1);
            launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
        }

        //支付权益支付页面
        @JavascriptInterface
        public void goOnlineLegalPay(String string){
            if (string.equals("undefined")) return;

            WebGoEquityPayEntity entity = GsonUtil.convertString2Object(string, WebGoEquityPayEntity.class);

            if (isFastClick()) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, entity.getRequireTypeId());

            List<String> mList = new ArrayList<>();
            for (int i=0;i<entity.getLegalAdviserIds().size();i++){
                if(!TextUtils.isEmpty(entity.getLegalAdviserIds().get(i)))
                    mList.add(entity.getLegalAdviserIds().get(i));
            }
            bundle.putStringArrayList(BundleTags.ENTITY, (ArrayList<String>) mList);
            bundle.putString(BundleTags.TITLE, "在线法律顾问");
            bundle.putString(BundleTags.REQUIRE_TYPE_NAME, "在线法律顾问");
            bundle.putFloat(BundleTags.MONEY, entity.getPriceTotal());
            bundle.putInt(BundleTags.TYPE, 2);
            launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
        }


        //电话
        @JavascriptInterface
        public void toCall(String phone) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(dialIntent);
        }

        //找律师
        @JavascriptInterface
        public void goLawyerList(String requireTypeId) {

            if (TextUtils.isEmpty(requireTypeId))
                return;
            int reid;
            try {
                reid = Integer.valueOf(requireTypeId);
            } catch (Exception e) {
                return;
            }

            runOnUiThread(() -> {
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(2);
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST_ID, reid);
            });
        }

        //获取token
        @JavascriptInterface
        public String getToken() {
            String token = DataHelper.getStringSF(mActivity, DataHelperTags.TOKEN);
            return token;
        }

        //获取用户信息
        @JavascriptInterface
        public String getInfo() {
            UserInfoDetailsEntity userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mActivity, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);

            String token = DataHelper.getStringSF(mActivity, DataHelperTags.TOKEN);
            String uuid;
            if (DataHelper.contains(mActivity, DataHelperTags.UUID)) {//存在
                uuid = DataHelper.getStringSF(mActivity, DataHelperTags.UUID);
            } else {//不存在
                uuid = UUID.randomUUID().toString();
                DataHelper.setStringSF(mActivity, DataHelperTags.UUID, uuid);
            }
            DeviceEntity2 device = new DeviceEntity2(2
                    , DeviceUtils.getVersionName(mActivity)
                    , DeviceUtils.getVersionCode(mActivity)
                    , DataHelper.getStringSF(mActivity, DataHelperTags.CHANNEL)
                    , android.os.Build.BRAND + " " + android.os.Build.MODEL
                    , android.os.Build.VERSION.RELEASE
                    , uuid
                    , DeviceUtils.getAndroidId(mActivity, uuid)
                    , token
                    , mPresenter.isLogin() ? userInfoDetailsEntity.getMobile() : ""
            );
            return GsonUtil.convertVO2String(device);
        }

        //快速咨询
        @JavascriptInterface
        public void toldApp() {
            String str = DataHelper.getStringSF(mActivity, DataHelperTags.QUICK_URL);
            HomeChildEntity entity = GsonUtil.convertString2Object(str, HomeChildEntity.class);
            if (!TextUtils.isEmpty(str) && entity != null) {
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getJumpurl());
                bundle.putString(BundleTags.TITLE, entity.getTitle());
                if (entity.getShowShare() == 1) {
                    bundle.putBoolean(BundleTags.IS_SHARE, true);
                    bundle.putString(BundleTags.SHARE_URL, entity.getShareUrl());
                    bundle.putString(BundleTags.SHARE_TITLE, entity.getShareTitle());
                    bundle.putString(BundleTags.SHARE_DES, entity.getShareDescription());
                    bundle.putString(BundleTags.SHARE_IMAGE, entity.getShareImg());
                }
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            }
        }

        //订单列表
        @JavascriptInterface
        public void goOrderList() {
            launchActivity(new Intent(mActivity, MyOrderActivity.class));
        }

        //订单详情
        @JavascriptInterface
        public void goOrderDetail(String string) {
            if (TextUtils.isEmpty(string))
                return;

            WebGoOrderDetailEntity entity = GsonUtil.convertString2Object(string, WebGoOrderDetailEntity.class);

            bundle.clear();
            bundle.putInt(BundleTags.ID, entity.getOrderId());
            bundle.putString(BundleTags.TITLE, "快速电话咨询");
            bundle.putInt(BundleTags.TYPE, 4);
            bundle.putString(BundleTags.ORDER_NO, entity.getOrderNo());
            launchActivity(new Intent(mActivity, OrderDetailsActivity.class), bundle);
        }

        //回到首页
        @JavascriptInterface
        public void goHome() {
            runOnUiThread(() -> {
                killMyself();
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
            });
        }

        public boolean isGoLogin;

        //登录
        @JavascriptInterface
        public void goLogin() {
            isGoLogin = true;//登录后需要再调用getinfo
            bundle.clear();
            bundle.putInt(BundleTags.TYPE, 1);
            launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
        }

        //登出
        @JavascriptInterface
        public void loginOut() {
            AppUtils.post(LOGIN_INFO, LOGOUT);
            JMessageClient.logout();
            DataHelper.removeSF(mActivity, DataHelperTags.TOKEN);
            DataHelper.removeSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS);
            DataHelper.removeSF(mActivity, DataHelperTags.USER_INFO_DETAIL);
            CookieSyncManager.createInstance(mActivity);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }

        //律师主页 string id
        @JavascriptInterface
        public void goLawyerDetail(String id) {
            if (TextUtils.isEmpty(id))
                return;
            int idInt;
            try {
                idInt = Integer.valueOf(id);
            } catch (Exception e) {
                return;
            }
            bundle.clear();
            bundle.putInt(BundleTags.ID, idInt);
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        }

        //充值页面
        @JavascriptInterface
        public void goAccount() {
            launchActivity(new Intent(mActivity, MyAccountActivity.class));
        }

        //显示隐藏分享按钮 boolean isShow
        @JavascriptInterface
        public void showShareBtn(boolean isShow) {
            titleView.getRightTv().setVisibility(isShow ? View.VISIBLE : View.GONE);
        }

        //分享 json 对象
        @JavascriptInterface
        public void shareUrl(String string) {
            WebShareEntity entity = GsonUtil.convertString2Object(string, WebShareEntity.class);
            String shareUrl = entity.getShareUrl();
            String ShareTitle = entity.getShareTitle();
            String shareImage = entity.getShareImage();
            String shareDes = entity.getShareDes();

            titleView.getRightTv().setOnClickListener(v -> {
                if (!TextUtils.isEmpty(shareUrl)) {
                    ShareUtils.shareUrl(mActivity
                            , viewDialog
                            , shareUrl
                            , ShareTitle
                            , shareDes
                            , shareImage);
                }
            });
        }

        @JavascriptInterface
        public void shareInvitePage(String string){
            if (TextUtils.isEmpty(string))
                return;
            if (isFastClick()) return;

            WebShareEntity entity = GsonUtil.convertString2Object(string, WebShareEntity.class);
            String shareUrl = entity.getShareUrl();
            String ShareTitle = entity.getShareTitle();
            String shareImage = entity.getShareImage();
            String shareDes = entity.getShareDes();

            if (!TextUtils.isEmpty(shareUrl)) {
                ShareUtils.shareUrl(mActivity
                        , viewDialog
                        , shareUrl
                        , ShareTitle
                        , shareDes
                        , shareImage);
            }
        }

        //领取单张优惠券 String 优惠券id
        @JavascriptInterface
        public void getCoupon(String id) {
            if (TextUtils.isEmpty(id))
                return;
            int idInt;
            try {
                idInt = Integer.valueOf(id);
            } catch (Exception e) {
                return;
            }
            mPresenter.clientCouponGain(idInt);
        }

        //领取券包 String 券包id
        @JavascriptInterface
        public void getCoupons(String id) {
            if (TextUtils.isEmpty(id))
                return;
            int idInt;
            try {
                idInt = Integer.valueOf(id);
            } catch (Exception e) {
                return;
            }
            mPresenter.clientReceive(idInt);
        }

    }
}
