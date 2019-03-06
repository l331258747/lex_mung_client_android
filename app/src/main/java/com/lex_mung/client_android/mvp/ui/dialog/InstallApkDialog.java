package com.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lex_mung.client_android.R;

public class InstallApkDialog extends Dialog {
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick();
    }

    public InstallApkDialog(@NonNull Context context,OnClickListener onClickListener) {
        super(context, R.style.alert_dialog);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_install_apk_dialog);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        findViewById(R.id.tv_confirm).setOnClickListener(v -> onClickListener.onClick());
    }
}
