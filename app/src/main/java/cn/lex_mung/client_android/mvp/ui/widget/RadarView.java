package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import cn.lex_mung.client_android.R;

import java.util.List;

import me.zl.mvp.utils.AppUtils;

public class RadarView extends View {
    private Context context;
    private List<RadarData> dataList;//数据
    private List<RadarData> meanDataList;//平均值

    private int count = 6;//雷达网圈数
    private float angle;//多边形弧度
    private float radius;
    private Paint mainPaint;//雷达区画笔
    private Paint valuePaint;//数据区画笔
    private TextPaint textPaint;//标题画笔
    private TextPaint scorePaint;//分值画笔

    private int mainColor = getResources().getColor(R.color.c_b5b5b5);//雷达区颜色
    private int valueColor1 = getResources().getColor(R.color.c_caffd6);//平均值区颜色
    private int valueColor2 = getResources().getColor(R.color.c_f8b62d);//数据区颜色
    private int textColor = getResources().getColor(R.color.c_323232);//文本颜色
    private int scoreColor = getResources().getColor(R.color.c_f8b62d);//文本颜色

    private int mWidth;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setup();
    }

    private void setup() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(mainColor);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(valueColor1);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);

        scorePaint = new TextPaint();
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(scoreColor);
        scorePaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        scorePaint.setTextSize(AppUtils.sp2px(context, 14f));//字体大小sp
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_200));
        mWidth = w;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mWidth / 2);

        if (isDataListValid()) {
            drawSpiderweb(canvas);

            valuePaint.setColor(valueColor1);
            drawRegion(canvas, meanDataList);

            valuePaint.setColor(valueColor2);
            drawRegion(canvas);

            drawText(canvas);
        }
    }

    /**
     * 绘制蜘蛛网
     *
     * @param canvas Canvas
     */
    private void drawSpiderweb(Canvas canvas) {
        mainPaint.setStrokeWidth(AppUtils.dip2px(context, 0.5f));//雷达网线宽度dp
        Path webPath = new Path();
        Path linePath = new Path();
        float r = radius / (count - 1);//蜘蛛丝之间的间距
        int num = 0;
        for (int i = 0; i < count; i++) {
            float curR = r * i;//当前半径
            webPath.reset();
            for (int j = 0; j < count; j++) {
                float x = (float) (curR * Math.sin(angle / 2 + angle * j));
                float y = (float) (curR * Math.cos(angle / 2 + angle * j));
                if (j == 0) {
                    webPath.moveTo(x, y);
                } else {
                    webPath.lineTo(x, y);
                }
                if (i == count - 1) {//当绘制最后一环时绘制连接线
                    linePath.reset();
                    linePath.moveTo(0, 0);
                    linePath.lineTo(x, y);
                    canvas.drawPath(linePath, mainPaint);
                }
            }
            webPath.close();
            canvas.drawPath(webPath, mainPaint);
            textPaint.setTextSize(AppUtils.sp2px(context, 10f));
            textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            int y = (int) Math.abs(r * 5 * Math.cos(angle / 2 + angle * 5)) / 5;
            canvas.drawText(num + "%", 0, i == 0 ? 0 : -(y * i), textPaint);
            num = num + 20;
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas Canvas
     */
    private void drawText(Canvas canvas) {
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textPaint.setTextSize(AppUtils.sp2px(context, 12f));//字体大小sp
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) ((radius + fontHeight * 2) * Math.sin(angle / 2 + angle * i));
            float y = (float) ((radius + fontHeight * 2) * Math.cos(angle / 2 + angle * i));
            String title = dataList.get(i).title;
            String score = "　" + ((int) dataList.get(i).score) + "%";
            String title1 = "";
            String title2 = "";
            String title3 = "";
            String title4 = "";
            if (title.length() <= 5 || textPaint.measureText(title) <= textPaint.measureText("一一一一一")) {
                title1 = title;

                float dis = textPaint.measureText(title1);//文本长度
                canvas.drawText(title1, x - dis / 2, y, textPaint);
                canvas.drawText(score, x - dis / 2, y + fontHeight + 10, scorePaint);
            } else if (title.length() <= 10) {
                title1 = title.substring(0, 5);
                title2 = title.substring(5, title.length());

                float dis = textPaint.measureText(title1);//文本长度
                canvas.drawText(title2, x - dis / 2, y, textPaint);
                canvas.drawText(title1, x - dis / 2, y - fontHeight, textPaint);
                canvas.drawText(score, x - dis / 2, y + fontHeight + 10, scorePaint);
            } else if (title.length() <= 15) {
                title1 = title.substring(0, 5);
                title2 = title.substring(5, 10);
                title3 = title.substring(10, title.length());

                float dis = textPaint.measureText(title1);//文本长度
                if (i == 0 || i == 5) {
                    y = y + 10;
                    canvas.drawText(title2, x - dis / 2, y, textPaint);
                    canvas.drawText(title1, x - dis / 2, y - fontHeight, textPaint);
                    canvas.drawText(title3, x - dis / 2, y + fontHeight, textPaint);
                    canvas.drawText(score, x - dis / 2, y + fontHeight + fontHeight + 10, scorePaint);
                } else {
                    canvas.drawText(title3, x - dis / 2, y, textPaint);
                    canvas.drawText(title2, x - dis / 2, y - fontHeight, textPaint);
                    canvas.drawText(title1, x - dis / 2, y - fontHeight - fontHeight, textPaint);
                    canvas.drawText(score, x - dis / 2, y + fontHeight + 10, scorePaint);
                }
            } else if (title.length() <= 20) {
                title1 = title.substring(0, 5);
                title2 = title.substring(5, 10);
                title3 = title.substring(10, 15);
                title4 = title.substring(15, title.length());

                float dis = textPaint.measureText(title1);//文本长度
                if (i == 0 || i == 5) {
                    y = y + 10;
                    canvas.drawText(title2, x - dis / 2, y, textPaint);
                    canvas.drawText(title1, x - dis / 2, y - fontHeight, textPaint);
                    canvas.drawText(title3, x - dis / 2, y + fontHeight, textPaint);
                    canvas.drawText(title4, x - dis / 2, y + fontHeight + fontHeight, textPaint);
                    canvas.drawText(score, x - dis / 2, y + fontHeight + fontHeight + fontHeight + 10, scorePaint);
                } else {
                    canvas.drawText(title4, x - dis / 2, y, textPaint);
                    canvas.drawText(title3, x - dis / 2, y - fontHeight, textPaint);
                    canvas.drawText(title2, x - dis / 2, y - fontHeight - fontHeight, textPaint);
                    canvas.drawText(title1, x - dis / 2, y - fontHeight - fontHeight - fontHeight, textPaint);
                    canvas.drawText(score, x - dis / 2, y + fontHeight + 10, scorePaint);
                }
            }
        }
    }

    /**
     * 绘制平均值区域
     *
     * @param canvas Canvas
     */
    private void drawRegion(Canvas canvas, List<RadarData> list) {
        valuePaint.setStrokeWidth(AppUtils.dip2px(context, 1f));
        Path path = new Path();
        valuePaint.setAlpha(128);
        path.reset();

        //填充模式
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < count; i++) {
            if (dataList.get(i).score >= 100) {
                dataList.get(i).score = 100;
            }
            double percent = list.get(i).score / 100;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制数据区域
     *
     * @param canvas Canvas
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        valuePaint.setStrokeWidth(AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_4)));
        path.reset();

        for (int i = 0; i < count; i++) {
            if (dataList.get(i).score >= 100) {
                dataList.get(i).score = 100;
            }
            double percent = dataList.get(i).score / 100;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_6)), valuePaint);
        }
        path.close();
        //画线模式
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
    }

    private boolean isDataListValid() {
        return dataList != null && dataList.size() >= 3;
    }


    public void setDataList(List<RadarData> dataList, List<RadarData> meanDataList) {
        if (dataList != null && dataList.size() >= 3) {
            this.dataList = dataList;
            this.meanDataList = meanDataList;
            count = dataList.size();//圈数等于数据个数，默认为6
            angle = (float) (Math.PI * 2 / count);
            invalidate();
        }
    }

    public static class RadarData {
        String title;
        double score;

        public RadarData(String title, double score) {
            this.title = title;
            this.score = score;
        }
    }
}
