package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.TextureView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class DeleteDialog extends Dialog {
    private OnClickListener clickListener;
    private String title;
    private String confirmStr;

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

    public DeleteDialog(@NonNull Context context, OnClickListener onClickListener, String title,String confirmStr) {
        super(context, R.style.alert_dialog);
        this.title = title;
        this.clickListener = onClickListener;
        this.confirmStr = confirmStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delete_dialog2);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(title)) {
            TextView tvTitle = findViewById(R.id.tv_title);
            tvTitle.setText(title);
        }

        TextView confirmTextView = findViewById(R.id.tv_confirm);
        if(!TextUtils.isEmpty(confirmStr)){
            confirmTextView.setText(confirmStr);
        }

        findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        confirmTextView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
