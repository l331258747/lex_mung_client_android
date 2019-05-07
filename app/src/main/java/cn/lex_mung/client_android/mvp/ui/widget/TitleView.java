package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

/**
 * Created by LGQ
 * Time: 2018/11/13
 * Function:
 */

public class TitleView extends RelativeLayout {

    RelativeLayout titleLlayout;
    ImageView leftIv, rightIv;
    TextView titleTv, rightTv;
    Context context;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.title_layout, this, true);

        titleLlayout = findViewById(R.id.title_layout);
        leftIv = findViewById(R.id.left_iv);
        rightIv = findViewById(R.id.right_iv);
        titleTv = findViewById(R.id.title_tv);
        rightTv = findViewById(R.id.right_tv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.TitleView_TitleView_title);
            if (!TextUtils.isEmpty(titleText)) {
                titleTv.setText(titleText);
            }

            int leftDrawable = attributes.getResourceId(R.styleable.TitleView_TitleView_left_img, -1);
            if (leftDrawable != -1) {
                leftIv.setVisibility(VISIBLE);
                leftIv.setImageDrawable(ContextCompat.getDrawable(context, leftDrawable));
            }

            String rightText = attributes.getString(R.styleable.TitleView_TitleView_right_text);
            if (!TextUtils.isEmpty(rightText)) {
                rightTv.setText(rightText);
                rightTv.setVisibility(VISIBLE);
            }

            int rightDrawable = attributes.getResourceId(R.styleable.TitleView_TitleView_right_img, -1);
            if (rightDrawable != -1) {
                rightIv.setVisibility(VISIBLE);
                rightIv.setImageDrawable(ContextCompat.getDrawable(context, rightDrawable));
            }
            int titleBg = attributes.getResourceId(R.styleable.TitleView_TitleView_bg, -1);
            if (titleBg != -1) {
                titleLlayout.setBackgroundColor(ContextCompat.getColor(context, titleBg));
            }

            attributes.recycle();
        }
    }

    public ImageView getLeftView(){
        return leftIv;
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setRightTv(String rightText) {
        rightTv.setText(rightText);
        rightTv.setVisibility(VISIBLE);
    }

    public void setRightIv(int resourceId) {
        rightIv.setImageDrawable(ContextCompat.getDrawable(context, resourceId));
        rightTv.setVisibility(VISIBLE);
    }

    public ImageView getLeftIv() {
        return leftIv;
    }

    public ImageView getRightIv() {
        return rightIv;
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public TextView getRightTv() {
        return rightTv;
    }

}