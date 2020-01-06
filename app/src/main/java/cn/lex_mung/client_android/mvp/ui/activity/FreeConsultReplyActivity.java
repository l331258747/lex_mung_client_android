package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultReplyComponent;
import cn.lex_mung.client_android.di.module.FreeConsultReplyModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultReplyContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultReplyPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;

public class FreeConsultReplyActivity extends BaseActivity<FreeConsultReplyPresenter> implements FreeConsultReplyContract.View {

    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultReplyComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultReplyModule(new FreeConsultReplyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_reply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setConsultationId(bundleIntent.getInt(BundleTags.ID));
        mPresenter.setLawyerId(bundleIntent.getInt(BundleTags.LAWYER_ID));
        etContent.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
    }

    @OnClick({
            R.id.tv_btn
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_btn:
                mPresenter.reply(etContent.getText().toString());
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
