package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StringUtils;

/**
 * 充值dialog
 */
public class CallFieldDialog extends Dialog {

    private CallFieldDialog.OnClickListener clickListener;
    private String content;
    private String btnText;

    public interface OnClickListener {
        void onClick(CallFieldDialog dialog);
    }

    public CallFieldDialog(@NonNull Context context, CallFieldDialog.OnClickListener onClickListener, String content,String btnText) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
        this.content = content;
        this.btnText = btnText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_field_dialog);
        initView();
        setCancelable(false);
    }

    private void initView() {
        TextView tvContent = findViewById(R.id.tv_content);

        StringUtils.setHtml(tvContent,content);

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
