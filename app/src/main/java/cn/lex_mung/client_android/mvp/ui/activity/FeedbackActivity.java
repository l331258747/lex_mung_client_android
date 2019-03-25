package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.FeedbackModule;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;

import cn.lex_mung.client_android.di.component.DaggerFeedbackComponent;
import cn.lex_mung.client_android.mvp.contract.FeedbackContract;
import cn.lex_mung.client_android.mvp.presenter.FeedbackPresenter;

import cn.lex_mung.client_android.R;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.io.Serializable;

import javax.inject.Inject;

public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_num_1)
    TextView tvNum1;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.view_dialog)
    View viewDialog;

    private EasyDialog easyDialog;
    private DefaultDialog defaultDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent
                .builder()
                .appComponent(appComponent)
                .feedbackModule(new FeedbackModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setText(getString(R.string.text_submit));
        tvRight.setVisibility(View.VISIBLE);
        tvNum1.setText(String.format(getString(R.string.text_0_300), 0));
        mPresenter.getFeedbackType();
        etInput.setFilters(new InputFilter[]{CharacterHandler.emojiFilter, new InputFilter.LengthFilter(300)});
    }

    @Override
    public void setType(String feedbackTypeName) {
        tvType.setText(feedbackTypeName);
    }

    @SuppressLint("SetTextI18n")
    @OnTextChanged(value = R.id.et_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        tvNum1.setText(String.format(getString(R.string.text_0_300), s.length()));
        if (s.length() >= 300) {
            tvNum1.setTextColor(AppUtils.getColor(mActivity, R.color.c_ea5514));
        } else {
            tvNum1.setTextColor(AppUtils.getColor(mActivity, R.color.c_323232));
        }
    }

    @OnClick({R.id.tv_right, R.id.tv_type_text, R.id.ll_image})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_right:
                mPresenter.uploadFeedback(etInput.getText().toString());
                break;
            case R.id.tv_type_text:
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) mPresenter.getFeedbackTypeEntities());
                bundle.putInt(BundleTags.TYPE, 4);
                launchActivity(new Intent(mActivity, SelectListItemActivity.class), bundle);
                break;
            case R.id.ll_image:
                mPresenter.getLaunchCameraPermission();
                break;
        }
    }

    @Override
    public void setIcon(String path) {
        if (TextUtils.isEmpty(path)) {
            ivImage.setVisibility(View.GONE);
            ivIcon.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.VISIBLE);
        } else {
            ivImage.setVisibility(View.VISIBLE);
            ivIcon.setVisibility(View.GONE);
            tvText.setVisibility(View.GONE);
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(path)
                            .imageView(ivImage)
                            .isCrossFade(true)
                            .build());
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
                .setLocationByAttachedView(viewDialog)
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

    /**
     * 拍照或者从相册中选择
     */
    @Override
    @SuppressLint("InflateParams")
    public void showSelectPhotoDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_dialog, null);
        initDialog(layout);
        layout.findViewById(R.id.tv_one).setOnClickListener(v -> {
            mPresenter.getImageFromCamera();
            dismiss();
        });
        layout.findViewById(R.id.tv_two).setOnClickListener(v -> {
            mPresenter.getImageFromAlbum();
            dismiss();
        });
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
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
    public void launchActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
