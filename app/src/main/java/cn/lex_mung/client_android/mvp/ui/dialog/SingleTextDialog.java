package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.R;


public class SingleTextDialog extends Dialog {
    Context context;

    TextView tv_content, tv_submit;

    public SingleTextDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    String submitStr;

    public SingleTextDialog setSubmitStr(String submitStr) {
        this.submitStr = submitStr;
        return this;
    }

    String contentStr;

    public SingleTextDialog setContent(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_single_text);
        initView();
        setCancelable(false);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_submit = findViewById(R.id.tv_submit);


        if (!TextUtils.isEmpty(submitStr)) {
            tv_submit.setText(submitStr);
        }

        if (!TextUtils.isEmpty(contentStr)) {
            tv_content.setText(contentStr);
        }

        tv_submit.setOnClickListener(v -> {
            dismiss();
        });
    }
}
