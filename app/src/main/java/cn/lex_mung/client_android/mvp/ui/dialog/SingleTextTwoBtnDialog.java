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


public class SingleTextTwoBtnDialog extends Dialog {
    Context context;

    TextView tv_content, tv_submit,tv_cancel;


    public SingleTextTwoBtnDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    String submitStr;

    public SingleTextTwoBtnDialog setSubmitStr(String submitStr) {
        this.submitStr = submitStr;
        return this;
    }

    String cancelStr;

    public SingleTextTwoBtnDialog setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
        return this;
    }

    String contentStr;

    public SingleTextTwoBtnDialog setContent(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }

    String contentHtmlStr;
    public SingleTextTwoBtnDialog setContentHtmlStr(String contentHtmlStr){
        this.contentHtmlStr = contentHtmlStr;
        return this;
    }

    OnClickListener onSubmitClickListener;
    public SingleTextTwoBtnDialog setSubmitOnClickListener(OnClickListener onSubmitClickListener){
        this.onSubmitClickListener = onSubmitClickListener;
        return this;
    }

    OnClickListener onCancelClickListener;
    public SingleTextTwoBtnDialog setCancelOnClickListener(OnClickListener onCancelClickListener){
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_single_text_two_btn);
        initView();
        setCancelable(false);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());


        if (!TextUtils.isEmpty(submitStr)) {
            tv_submit.setText(submitStr);
        }

        if (!TextUtils.isEmpty(cancelStr)) {
            tv_cancel.setText(cancelStr);
        }

        if (!TextUtils.isEmpty(contentStr)) {
            tv_content.setText(contentStr);
        }

        if (!TextUtils.isEmpty(contentHtmlStr)) {
            StringUtils.setHtml(tv_content,contentHtmlStr);
        }

        tv_submit.setOnClickListener(v -> {
            dismiss();
            if(onSubmitClickListener != null){
                onSubmitClickListener.onClick();
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
            if(onCancelClickListener != null){
                onCancelClickListener.onClick();
            }
        });
    }

    public interface OnClickListener {
        void onClick();
    }

}
