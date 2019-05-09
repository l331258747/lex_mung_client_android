package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsReaderView;

import cn.lex_mung.client_android.di.module.X5Web2Module;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerX5Web2Component;
import cn.lex_mung.client_android.mvp.contract.X5Web2Contract;
import cn.lex_mung.client_android.mvp.presenter.X5Web2Presenter;

import cn.lex_mung.client_android.R;

public class X5Web2Activity extends BaseActivity<X5Web2Presenter> implements X5Web2Contract.View,TbsReaderView.ReaderCallback {

    LinearLayout ll_parent;
    TbsReaderView mTbsReaderView;
    String filePath;
    String fileName;
    ImageView iv_img;
    String fileType;
    TitleView titleView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerX5Web2Component
                .builder()
                .appComponent(appComponent)
                .x5Web2Module(new X5Web2Module(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_x5_web2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ll_parent = findViewById(R.id.ll_parent);
        iv_img = findViewById(R.id.iv_img);
        findViewById(R.id.titleView).setOnClickListener(v -> finish());

        filePath = getIntent().getStringExtra("x5web_file_path");
        fileName = getIntent().getStringExtra("x5web_file_name");

        String[] sli = fileName.split("\\.");
        fileType = sli[sli.length - 1];

        if (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg")) {
            iv_img.setVisibility(View.VISIBLE);
            Glide.with(this).load(filePath)
                    .into(iv_img);
        } else {
            iv_img.setVisibility(View.GONE);

            mTbsReaderView = new TbsReaderView(this, this);
            ll_parent.addView(mTbsReaderView, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            displayFile();
        }
    }

    private void displayFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(fileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
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
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTbsReaderView !=null)
            mTbsReaderView.onStop();
    }
}
