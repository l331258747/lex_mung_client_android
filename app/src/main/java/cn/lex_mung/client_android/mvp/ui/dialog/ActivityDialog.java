package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import cn.lex_mung.client_android.R;

public class ActivityDialog extends Dialog {
    Context context;

    View view_close;
    ImageView iv_img;

    public ActivityDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    Drawable imgDrawable;
    public ActivityDialog setImgDrawable(Drawable imgDrawable){
        this.imgDrawable = imgDrawable;
        return this;
    }

    OnClickListener onClickListener;
    public ActivityDialog setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_activity);
        initView();
        setCancelable(true);
    }

    private void initView() {
        view_close = findViewById(R.id.view_close);
        iv_img = findViewById(R.id.iv_img);

        view_close.setOnClickListener(v -> {
            dismiss();
        });

        iv_img.setOnClickListener(v -> {
            dismiss();
            if(onClickListener != null){
                onClickListener.onClick();
            }
        });

        if(imgDrawable != null){
            iv_img.setImageDrawable(imgDrawable);
        }

    }

    public interface OnClickListener {
        void onClick();
    }

}

