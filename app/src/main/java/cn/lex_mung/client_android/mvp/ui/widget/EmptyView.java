package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import cn.lex_mung.client_android.R;

public class EmptyView extends LinearLayout {

    Context context;
    LinearLayout llEmpty;

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

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        if (attributes != null) {
            int bgColor = attributes.getResourceId(R.styleable.EmptyView_EmptyView_bg, -1);
            if (bgColor != -1) {
                llEmpty.setBackgroundColor(ContextCompat.getColor(context, bgColor));
            }
            attributes.recycle();
        }
    }
}
