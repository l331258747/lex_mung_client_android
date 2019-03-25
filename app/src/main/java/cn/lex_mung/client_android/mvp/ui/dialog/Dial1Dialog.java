package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class Dial1Dialog extends Dialog {
    private String title;
    private MyCountDownTimer myCountDownTimer;
    private TextView tvTime;
    private Context context;

    public Dial1Dialog(@NonNull Context context, String title) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dial_1_dialog);
        initView();
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        tvConfirm.setOnClickListener(v -> dismiss());
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
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
            tvTime.setText(String.format(context.getString(R.string.text_countdown), l / 1000));

        }

        @Override
        public void onFinish() {
            dismiss();
        }
    }
}
