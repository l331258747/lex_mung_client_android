package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.app.ShareUtils;
import cn.lex_mung.client_android.di.component.DaggerWebComponent;
import cn.lex_mung.client_android.di.module.WebModule;
import cn.lex_mung.client_android.mvp.contract.WebContract;
import cn.lex_mung.client_android.mvp.model.entity.DeviceEntity;
import cn.lex_mung.client_android.mvp.model.entity.DeviceEntity2;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoOrderDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.WebGoPayEntity;
import cn.lex_mung.client_android.mvp.presenter.WebPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
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

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_ID;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    LWebView webView;
    @BindView(R.id.view_dialog)
    View viewDialog;

    private boolean isShare;
    private String url;
    private String title;
    private String des;
    private String image;
    private boolean isJump;

    private int buryingPointId;//快速电话咨询，用来判断从哪里进入的(正常，解决方案)
    UserInfoDetailsEntity userInfoDetailsEntity;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.activity_web;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(url)){
            if(url.indexOf("contractList.html") > -1){
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "more_contract",getPair());
            }else if(url.indexOf("solution.html") > -1){
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "solution_detail",getPair());
            }else if(url.indexOf("member") > -1){
                BuryingPointHelp.getInstance().onActivityResumed(mActivity, "enterprise_legal_card",getPair());
            }else if(url.indexOf("quick.html") > -1){
                if(buryingPointId == 1){
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consulation_from_solution",getPair());
                }else{
                    BuryingPointHelp.getInstance().onActivityResumed(mActivity, "quick_consultation",getPair());
                }
            }
        }
        mPresenter.onResume();
        webView.onWebResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!TextUtils.isEmpty(url)){
            if(url.indexOf("contractList.html") > -1){
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "more_contract",getPair());
            }else if(url.indexOf("solution.html") > -1){
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "solution_detail",getPair());
            }else if(url.indexOf("member") > -1){
                BuryingPointHelp.getInstance().onActivityPaused(mActivity, "enterprise_legal_card",getPair());
            }else if(url.indexOf("quick.html") > -1){
                if(buryingPointId == 1){
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consulation_from_solution",getPair());
                }else{
                    BuryingPointHelp.getInstance().onActivityPaused(mActivity, "quick_consultation",getPair());
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
        if (bundleIntent != null
                && bundleIntent.containsKey(BundleTags.URL)) {
            url = bundleIntent.getString(BundleTags.URL);
            title = bundleIntent.getString(BundleTags.TITLE);
            des = bundleIntent.getString(BundleTags.DES);
            image = bundleIntent.getString(BundleTags.IMAGE);
            isShare = bundleIntent.getBoolean(BundleTags.IS_SHARE, true);
            isJump = bundleIntent.getBoolean(BundleTags.STATE,true);
            buryingPointId = bundleIntent.getInt(BundleTags.BURYING_POINT, -1);
        }
        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mActivity, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);

        LogUtil.e("url:" + url);

        if (isShare) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(R.string.text_share);
        }
        if (TextUtils.isEmpty(title)) {
            tvRight.setVisibility(View.GONE);
        }
        tvTitle.setText(title);
        showLoading("");
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
                if (newProgress == 100) {
                    hideLoading();
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new MyWebViewClient(webView,isJump,string -> {
            Intent dialIntent2 = new Intent(Intent.ACTION_DIAL, Uri.parse(string));
            startActivity(dialIntent2);
        }));

        webView.addJavascriptInterface(new AndroidToJs(), "JsBridgeApp");//h5 js调用 app.pay();
        webView.synCookies(url);
        webView.loadUrl(url);
    }

    public void goNext(String url){
        webView.synCookies(url);
        webView.loadUrl(url);
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        MobclickAgent.onEvent(mActivity, "w_y__shouye_jjfa_list_fenxiang");
        if (!TextUtils.isEmpty(url)
                && !TextUtils.isEmpty(title)) {
            ShareUtils.shareUrl(mActivity
                    , viewDialog
                    , url
                    , title
                    , des
                    , image);
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
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    public class AndroidToJs extends Object {

        //支付
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

        @JavascriptInterface
        public void setToken(String token1){
            LogUtil.e("token:"+token1);
            if(token1.equals("undefined")) return;

            DataHelper.setBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS, true);
            DataHelper.setStringSF(mActivity, DataHelperTags.TOKEN, token1);
//            mPresenter.getUserInfoDetail();
            isSetToken = true;
        }

        @JavascriptInterface
        public void goQuickPay(String string) {
            if (TextUtils.isEmpty(string))
                return;

            if(isSetToken){
                mPresenter.getUserInfoDetail(()->{
                    goRushLoanPayActivity(string);
                });
                isSetToken = false;
            }else{
                goRushLoanPayActivity(string);
            }
        }

        public void goRushLoanPayActivity(String string){
            WebGoPayEntity businessEntity = GsonUtil.convertString2Object(string, WebGoPayEntity.class);

            if (isFastClick()) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, businessEntity.getRequireTypeId());
            bundle.putString(BundleTags.REQUIRE_TYPE_NAME, businessEntity.getRequireTypeName());
            bundle.putString(BundleTags.TITLE, "快速电话咨询");
            bundle.putFloat(BundleTags.MONEY, businessEntity.getMoney());
            bundle.putString(BundleTags.MOBILE,businessEntity.getMobile());
            bundle.putInt(BundleTags.TYPE,1);
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
        public void goLawyerList(int requireTypeId) {
            runOnUiThread(() -> {
                AppManager.getAppManager().killAllNotClass(MainActivity.class);
                ((MainActivity) AppManager.getAppManager().findActivity(MainActivity.class)).switchPage(2);
                AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST_ID, requireTypeId);
            });
        }

        @JavascriptInterface
        public String getToken() {
            String token = DataHelper.getStringSF(mActivity, DataHelperTags.TOKEN);
            return token;
        }

        @JavascriptInterface
        public String getInfo() {
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
                    , DeviceUtils.getAndroidId(mActivity,uuid)
                    , token
                    , mPresenter.isLogin()?userInfoDetailsEntity.getMobile():""
            );
            return GsonUtil.convertVO2String(device);
        }

        @JavascriptInterface
        public void toldApp(){
            if(!TextUtils.isEmpty(DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL))){
                bundle.clear();
                bundle.putString(BundleTags.URL, DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL));
                bundle.putString(BundleTags.TITLE, "快速电话咨询");
                bundle.putBoolean(BundleTags.IS_SHARE, false);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            }
        }

        //goOrderDetail()  goOrderList()
        @JavascriptInterface
        public void goOrderList(){
            launchActivity(new Intent(mActivity, MyOrderActivity.class));
        }

        @JavascriptInterface
        public void goOrderDetail(String string){
            if (TextUtils.isEmpty(string))
                return;

            WebGoOrderDetailEntity entity = GsonUtil.convertString2Object(string, WebGoOrderDetailEntity.class);

            bundle.clear();
            bundle.putInt(BundleTags.ID, entity.getOrderId());
            bundle.putString(BundleTags.TITLE,"快速电话咨询");
            bundle.putInt(BundleTags.TYPE, 4);
            bundle.putString(BundleTags.ORDER_NO,entity.getOrderNo());
            launchActivity(new Intent(mActivity, OrderDetailsActivity.class), bundle);
        }
    }
}
