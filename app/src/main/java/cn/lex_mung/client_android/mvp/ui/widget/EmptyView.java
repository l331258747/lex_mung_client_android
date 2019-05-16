package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lex_mung.client_android.R;

public class EmptyView extends LinearLayout {

    Context context;
    LinearLayout llEmpty;
    ImageView ivImg;
    TextView tvContent;
    TextView tvBtn;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true);

        llEmpty = findViewById(R.id.ll_empty);
        ivImg = findViewById(R.id.iv_img);
        tvContent = findViewById(R.id.tv_content);
        tvBtn = findViewById(R.id.tv_btn);

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

            String content = attributes.getString(R.styleable.EmptyView_EmptyView_content);
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(content);
            }
            int contentTextColor = attributes.getColor(R.styleable.EmptyView_EmptyView_content_text_color,-1);
            if (contentTextColor != -1) {
                tvContent.setTextColor(contentTextColor);
            }

            String btnTxt = attributes.getString(R.styleable.EmptyView_EmptyView_btn);
            if (!TextUtils.isEmpty(btnTxt)) {
                tvBtn.setText(btnTxt);
                tvBtn.setVisibility(VISIBLE);
            }

            attributes.recycle();
        }
    }

    public TextView getBtn(){
        return tvBtn;
    }
}
