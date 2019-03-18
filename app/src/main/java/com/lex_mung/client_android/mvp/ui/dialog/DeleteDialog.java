package com.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.lex_mung.client_android.R;

public class DeleteDialog extends Dialog {
    private OnClickListener clickListener;
    private String title;

    public interface OnClickListener {
        void onClick(DeleteDialog dialog);
    }

    public DeleteDialog(@NonNull Context context, OnClickListener onClickListener) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
    }

    public DeleteDialog(@NonNull Context context, OnClickListener onClickListener, String title) {
        super(context, R.style.alert_dialog);
        this.title = title;
        this.clickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delete_dialog);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(title)) {
            TextView tvTitle = findViewById(R.id.tv_title);
            tvTitle.setText(title);
        }
        findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
