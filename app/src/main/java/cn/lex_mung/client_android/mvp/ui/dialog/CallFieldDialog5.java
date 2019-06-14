package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StringUtils;

/**
 * 专家咨询，返回错误对话框，错误70000 + dialog
 */
public class CallFieldDialog5 extends Dialog {

    private CallFieldDialog5.OnClickListener clickListener;
    private String content;
    private String btnText;

    public interface OnClickListener {
        void onClick(CallFieldDialog5 dialog);
    }

    public CallFieldDialog5(@NonNull Context context, CallFieldDialog5.OnClickListener onClickListener, String content, String btnText) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
        this.content = content;
        this.btnText = btnText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_field_dialog5);
        initView();
        setCancelable(false);
    }

    private void initView() {
        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(content);

        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        TextView btn = findViewById(R.id.tv_btn);
        btn.setText(btnText);
        btn.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
