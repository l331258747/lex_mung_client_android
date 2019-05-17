package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class EmptyView2 extends LinearLayout {

    Context context;
    LinearLayout llEmpty;
    ImageView ivImg;
    TextView tvContent2;
    TextView tvBtn;

    public EmptyView2(Context context) {
        this(context, null);
    }

    public EmptyView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view2, this, true);

        llEmpty = findViewById(R.id.ll_empty);
        ivImg = findViewById(R.id.iv_img);
        tvBtn = findViewById(R.id.tv_btn);
        tvContent2 = findViewById(R.id.tv_content2);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        if (attributes != null) {
            int bgColor = attributes.getResourceId(R.styleable.EmptyView_EmptyView_bg, -1);
            if (bgColor != -1) {
                llEmpty.setBackgroundColor(ContextCompat.getColor(context, bgColor));
            }

            int img = attributes.getResourceId(R.styleable.EmptyView_EmptyView_img, -1);
            if (img != -1) {
                ivImg.setImageDrawable(ContextCompat.getDrawable(context, img));
            }

            String btnTxt = attributes.getString(R.styleable.EmptyView_EmptyView_btn);
            if (!TextUtils.isEmpty(btnTxt)) {
                tvBtn.setText(btnTxt);
                tvBtn.setVisibility(VISIBLE);
            }

            String content2 = attributes.getString(R.styleable.EmptyView_EmptyView_content2);
            if (!TextUtils.isEmpty(content2)) {
                tvContent2.setText(content2);
                tvContent2.setVisibility(VISIBLE);
            }

            attributes.recycle();
        }
    }

    public TextView getBtn(){
        return tvBtn;
    }
}
