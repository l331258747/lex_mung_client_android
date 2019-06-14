package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

/**
 * 类似 toast 的 单条信息 对话框
 */
public class OnlyTextDialog extends Dialog {
    Context context;

    TextView tv_content;

    public OnlyTextDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }


    String contentStr;

    public OnlyTextDialog setContent(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_only_text);
        initView();
        setCancelable(false);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);

        if (!TextUtils.isEmpty(contentStr)) {
            tv_content.setText(contentStr);
        }

    }
}
