package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import me.zl.mvp.http.imageloader.ImageLoader;

public class ActivityDialog extends Dialog {
    Context context;
    ImageLoader mImageLoader;

    View view_close;
    ImageView iv_img;

    public ActivityDialog(@NonNull Context context,ImageLoader mImageLoader) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.mImageLoader = mImageLoader;
    }

    String imgUrl;
    public ActivityDialog setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
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

        if(!TextUtils.isEmpty(imgUrl)){
            mImageLoader.loadImage(getContext()
                    , ImageConfigImpl
                            .builder()
                            .isCenterCrop(false)
                            .url(imgUrl)
                            .errorPic(R.drawable.activity_img)
                            .imageView(iv_img)
                            .build());
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}

