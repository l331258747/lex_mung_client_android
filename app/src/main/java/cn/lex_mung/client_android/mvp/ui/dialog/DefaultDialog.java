package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class DefaultDialog extends Dialog {
    private String title;
    private String confirm;
    private String cancel;
    private OnClickListener clickListener;

    public interface OnClickListener {
        void onClick(DefaultDialog dialog);
    }

    public DefaultDialog(@NonNull Context context, OnClickListener onClickListener, String title, String confirm, String cancel) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
        this.title = title;
        this.confirm = confirm;
        this.cancel = cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delete_dialog2);
        initView();
    }

    private void initView() {
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.setText(confirm);
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.setText(cancel);
        }
        tvCancel.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
