package com.lex_mung.client_android.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.lex_mung.client_android.R;

import java.math.BigDecimal;

import me.zl.mvp.utils.AppUtils;

public class ProgressBar extends View {
    private Context context;
    private Paint paint;// 画圆环的画笔
    private Paint paint1;// 画进度的画笔
    private Paint paint2;// 画进度的画笔
    private float mRingRadius;// 圆环半径
    private float mStrokeWidth;//圆环宽度
    private int mProgress = 0;// 当前进度
    private String text = mProgress + "";

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initVariable();
    }

    //初始化画笔
    private void initVariable() {
        // 圆环宽度
        mStrokeWidth = AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_4));
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(AppUtils.getColor(context, R.color.c_717171));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(AppUtils.getColor(context, R.color.c_ff));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(mStrokeWidth);
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(AppUtils.getColor(context, R.color.c_ff));
        paint2.setStrokeWidth(mStrokeWidth);
        Rect rect = new Rect();
        paint2.setColor(AppUtils.getColor(context, R.color.c_ff));
        paint2.setTextSize(40);
        paint2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint2.setStrokeWidth(0);
        paint2.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRingRadius = w / 2 - mStrokeWidth / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        int circlePoint = getWidth() / 2;
        canvas.drawCircle(circlePoint, circlePoint, mRingRadius, paint); //画出圆
        float angle = new BigDecimal(mProgress).divide(new BigDecimal(100)).multiply(new BigDecimal(360)).floatValue();
        canvas.drawArc(mStrokeWidth / 2
                , mStrokeWidth / 2
                , getWidth() - mStrokeWidth / 2
                , getWidth() - mStrokeWidth / 2
                , 0
                , angle
                , false
                , paint1);  //根据进度画圆弧
        canvas.translate(circlePoint, circlePoint);
        float textWidth = paint2.measureText(text);
        float baseLineY = Math.abs(paint2.ascent() + paint2.descent()) / 2;
        canvas.drawText(text, -textWidth / 2, baseLineY, paint2);

    }

    //设置进度
    public void setProgress(int progress) {
        mProgress = progress;
        text = mProgress + "";
        postInvalidate();//重绘
    }
}
