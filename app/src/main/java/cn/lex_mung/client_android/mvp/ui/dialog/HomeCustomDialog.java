package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class HomeCustomDialog extends Dialog {
    Context context;

    TextView tv_submit,tv_cancel;
    LinearLayout ll_content2;

    public HomeCustomDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    OnClickListener onClickListener;
    public HomeCustomDialog setSubmitOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_home_custom);
        initView();
        setCancelable(false);
    }

    private void initView() {
        ll_content2 = findViewById(R.id.ll_content2);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);


        tv_submit.setOnClickListener(v -> {
            dismiss();
            if(onClickListener != null){
                onClickListener.onSubmitClick("400-811-3060");
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });

        ll_content2.setOnClickListener(v -> {
            if(onClickListener != null){
                onClickListener.onCloneClick("lex-yangchun");
            }
        });
    }

    public interface OnClickListener {
        void onSubmitClick(String str);
        void onCloneClick(String str);
    }
}
