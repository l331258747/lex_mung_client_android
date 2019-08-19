package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

/**
 * 服务助手对话框
 */
public class HelpStepDialog extends Dialog {
    Context context;
    OnClickListener onClickListener;
    ImageView iv_title_name;
    TextView tv_content, tv_cancel, tv_submit,tv_content2,tv_title_name;

    public HelpStepDialog(@NonNull Context context, OnClickListener onClickListener) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    String cancelStr;
    public HelpStepDialog setCannelStr(String cancelStr){
        this.cancelStr = cancelStr;
        return this;
    }

    String submitStr;
    public HelpStepDialog setSubmitStr(String submitStr){
        this.submitStr = submitStr;
        return this;
    }

    String contentStr;
    public HelpStepDialog setContent(String contentStr){
        this.contentStr = contentStr;
        return this;
    }
    String contentStr2;
    public HelpStepDialog setContent2(String contentStr2){
        this.contentStr2 = contentStr2;
        return this;
    }

    int ivTitleResId = -1;
    public HelpStepDialog setIvTitle(int ivTitleResId) {
        this.ivTitleResId = ivTitleResId;
        return this;
    }

    String tvTitle;
    public HelpStepDialog setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_help_step);
        initView();
        setCancelable(false);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_content2 = findViewById(R.id.tv_content2);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_submit = findViewById(R.id.tv_submit);
        tv_title_name = findViewById(R.id.tv_title_name);
        iv_title_name = findViewById(R.id.iv_title_name);

        if(ivTitleResId != -1){
            iv_title_name.setImageResource(ivTitleResId);
        }

        if(!TextUtils.isEmpty(tvTitle)){
            tv_title_name.setText(tvTitle);
        }

        if(!TextUtils.isEmpty(cancelStr)){
            tv_cancel.setText(cancelStr);
        }

        if(!TextUtils.isEmpty(submitStr)){
            tv_submit.setText(submitStr);
        }

        if(!TextUtils.isEmpty(contentStr)){
            tv_content.setText(contentStr);
        }

        if(!TextUtils.isEmpty(contentStr2)){
            tv_content2.setText(contentStr2);
            tv_content2.setVisibility(View.VISIBLE);
        }

        tv_cancel.setOnClickListener(v -> dismiss());

        tv_submit.setOnClickListener(v -> {
            onClickListener.onClick(this);
            dismiss();
        });
    }


    public interface OnClickListener {
        void onClick(HelpStepDialog dialog);
    }
}
