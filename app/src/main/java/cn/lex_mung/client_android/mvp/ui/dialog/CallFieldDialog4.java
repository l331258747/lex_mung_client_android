package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.StringUtils;

/**
 * 立即拨打dialog
 */
public class CallFieldDialog4 extends Dialog {

    private String content;
    private Context context;

    public CallFieldDialog4(@NonNull Context context, String content) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_field_dialog4);
        initView();
        setCancelable(false);
    }

    private void initView() {
        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(content);

        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        TextView btn1 = findViewById(R.id.tv_btn1);
        TextView btn2 = findViewById(R.id.tv_btn2);
        btn1.setOnClickListener(v -> dismiss());
        btn2.setOnClickListener(v -> dismiss());

    }

}
