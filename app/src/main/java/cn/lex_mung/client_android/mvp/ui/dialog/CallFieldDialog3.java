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
public class CallFieldDialog3 extends Dialog {

    private String content;
    private CallFieldDialog3.MyCountDownTimer myCountDownTimer;
    private Context context;
    private TextView btn;
    private CallFieldDialog3.OnClickListener clickListener;

    public interface OnClickListener {
        void onClick(CallFieldDialog3 dialog);
    }

    public CallFieldDialog3(@NonNull Context context,String content,CallFieldDialog3.OnClickListener clickListener) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.content = content;
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_field_dialog3);
        initView();
        setCancelable(false);
    }

    private void initView() {
        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(content);
        findViewById(R.id.iv_close).setOnClickListener(v -> {
            clickListener.onClick(this);
        });

        btn = findViewById(R.id.tv_btn);
        myCountDownTimer = new CallFieldDialog3.MyCountDownTimer(60000, 1000);
    }

    @Override
    public void show() {
        super.show();
        myCountDownTimer.start();
    }

    private class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            String string = "联系倒计时:<font color=\"#3dd790\">%d</font>";
            StringUtils.setHtml(btn,String.format(string,l / 1000));
        }

        @Override
        public void onFinish() {
            dismiss();
        }
    }
}
