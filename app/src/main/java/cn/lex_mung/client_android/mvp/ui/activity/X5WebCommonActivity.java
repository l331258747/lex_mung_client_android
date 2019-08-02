package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.ShareUtils;
import cn.lex_mung.client_android.di.component.DaggerX5WebCommonComponent;
import cn.lex_mung.client_android.di.module.X5WebCommonModule;
import cn.lex_mung.client_android.mvp.contract.X5WebCommonContract;
import cn.lex_mung.client_android.mvp.presenter.X5WebCommonPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import cn.lex_mung.client_android.mvp.ui.widget.webview.LWebView2;
import cn.lex_mung.client_android.mvp.ui.widget.webview.MyWebViewClient2;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

//在线咨询
public class X5WebCommonActivity extends BaseActivity<X5WebCommonPresenter> implements X5WebCommonContract.View {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.web_view)
    LWebView2 webView;
    @BindView(R.id.view_dialog)
    View viewDialog;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private boolean isShare;
    private String url;
    private String shareUrl;
    private String title;
    private String des;
    private String image;
    private boolean isJump;

    DefaultDialog defaultDialog;

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    public ValueCallback<Uri> getUploadMessage() {
        return uploadMessage;
    }

    public ValueCallback<Uri[]> getUploadMessageAboveL() {
        return uploadMessageAboveL;
    }

    @Override
    public void setUploadMessage(ValueCallback<Uri> valueCallback) {
        this.uploadMessage = valueCallback;
    }

    @Override
    public void setUploadMessageAboveL(ValueCallback<Uri[]> valueCallback) {
        this.uploadMessageAboveL = valueCallback;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerX5WebCommonComponent
                .builder()
                .appComponent(appComponent)
                .x5WebCommonModule(new X5WebCommonModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //webview中有一个输入框是在webview的底部，当我点击输入框的时候会弹出安卓的软键盘，这个时候我发现软键盘遮挡住了输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.activity_x5_web_common;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        webView.onWebResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            shareUrl = bundleIntent.getString(BundleTags.SHARE_URL);
            title = bundleIntent.getString(BundleTags.TITLE);
            des = bundleIntent.getString(BundleTags.DES);
            image = bundleIntent.getString(BundleTags.IMAGE);
            isShare = bundleIntent.getBoolean(BundleTags.IS_SHARE, true);
            isJump = bundleIntent.getBoolean(BundleTags.STATE, true);
        }

        LogUtil.e("url:" + url);

        if (isShare) {
            titleView.getRightTv().setVisibility(View.VISIBLE);
            titleView.getRightTv().setText(R.string.text_share);
        }
        if (TextUtils.isEmpty(title)) {
            titleView.getRightTv().setVisibility(View.GONE);
        }
        titleView.getTitleTv().setText(title);
//        showLoading("");
        initWebView();

        titleView.getRightTv().setOnClickListener(v -> {
            if (!TextUtils.isEmpty(url)
                    && !TextUtils.isEmpty(title)) {
                ShareUtils.shareUrl(mActivity
                        , viewDialog
                        , shareUrl
                        , title
                        , des
                        , image);
            }
        });

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
//                    hideLoading();
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            //问题原因：
            //原因是 H5 访问本地文件的时候，使用的<input type="file"> ,WebView 出于安全性的考虑，限制了以上操作。
            //解决方法：
            //重写 WebviewChromeClient 中的 openFileChooser() 和 onShowFileChooser()方法响应<input type="file">，然后使用原生代码来实现调用本地相册和拍照的功能，
            //最后在 onActiivtyResult 把选择的图片 URI 回传给 WebviewChromeClient。

            //For Android  >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                //调用系统相机或者相册
                mPresenter.getLaunchCameraPermission();
                return true;
            }
            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                //调用系统相机或者相册
                mPresenter.getLaunchCameraPermission();
            }

        });

        webView.setWebViewClient(new MyWebViewClient2(webView, isJump, string -> {
            Intent dialIntent2 = new Intent(Intent.ACTION_DIAL, Uri.parse(string));
            startActivity(dialIntent2);
        }));

        webView.synCookies(url);
        webView.loadUrl(url);
    }

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void launchActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
    }


    public void uploadPicture() {
        mPresenter.getImageFromAlbum();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
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

    /**
     * 压缩图片
     */
    @Override
    public void compressionImage(File file) {
        Luban.with(mActivity)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        showLoading("");
                    }

                    @Override
                    public void onSuccess(File file) {
                        hideLoading();
                        Uri imageUri = Uri.fromFile(file);

                        //上传文件
                        if (uploadMessage != null) {
                            uploadMessage.onReceiveValue(imageUri);
                            uploadMessage = null;
                        }
                        if (uploadMessageAboveL != null) {
                            uploadMessageAboveL.onReceiveValue(new Uri[]{imageUri});
                            uploadMessageAboveL = null;

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }
                })
                .launch();
    }
}
