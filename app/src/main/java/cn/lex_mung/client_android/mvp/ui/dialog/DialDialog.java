package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class DialDialog extends Dialog {
    private String title;
    private OnClickListener clickListener;

    public interface OnClickListener {
        void onClick(DialDialog dialog);
    }

    public DialDialog(@NonNull Context context, OnClickListener onClickListener, String title) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dial_dialog);
        initView();
    }

    private void initView() {
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        tvCancel.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
