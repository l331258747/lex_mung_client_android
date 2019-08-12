package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StringUtils;


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

    String contentHtmlStr;
    public SingleTextDialog setContentHtmlStr(String contentHtmlStr){
        this.contentHtmlStr = contentHtmlStr;
        return this;
    }

    OnClickListener onClickListener;
    public SingleTextDialog setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        return this;
    }

    OnClickListener textOnClickListener;
    public SingleTextDialog setTextOnClickListener(OnClickListener textOnClickListener){
        this.textOnClickListener = textOnClickListener;
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
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());


        if (!TextUtils.isEmpty(submitStr)) {
            tv_submit.setText(submitStr);
        }

        if (!TextUtils.isEmpty(contentStr)) {
            tv_content.setText(contentStr);
        }

        if (!TextUtils.isEmpty(contentHtmlStr)) {
            StringUtils.setHtml(tv_content,contentHtmlStr);
        }

        tv_submit.setOnClickListener(v -> {
            dismiss();
            if(onClickListener != null){
                onClickListener.onClick();
            }
        });

        tv_content.setOnClickListener(v -> {
            if(textOnClickListener != null){
                dismiss();
                textOnClickListener.onClick();
            }
        });
    }

    public interface OnClickListener {
        void onClick();
    }

}
