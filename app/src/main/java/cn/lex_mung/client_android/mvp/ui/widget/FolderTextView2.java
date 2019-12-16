package cn.lex_mung.client_android.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

/**
 * 固定行数展开收缩控件
 * Created by evan on 2016/3/3.
 */
@SuppressLint("AppCompatCustomView")
public class FolderTextView2 extends TextView {

    /**
     * 绘制，防止重复进行绘制
     */
    private boolean isDrawed = false;
    /**
     * 内部绘制
     */
    private boolean isInner = false;

    /**
     * 折叠行数
     */
    private int foldLine;

    private boolean isAlways;

    private String UNFOLD_TEXT;

    private int UNFOLD_TEXT_COLOR;

    ForegroundColorSpan foregroundColorSpan;

    /**
     * 全文本
     */
    private String fullText;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;


    public FolderTextView2(Context context) {
        this(context, null);
    }

    public FolderTextView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FolderTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FolderTextView);
        if (attributes != null) {
            foldLine = attributes.getInt(R.styleable.FolderTextView_foldline, 2);
            isAlways = attributes.getBoolean(R.styleable.FolderTextView_always, true);

            UNFOLD_TEXT = attributes.getString(R.styleable.FolderTextView_unfold_text);
            if (TextUtils.isEmpty(UNFOLD_TEXT))
                UNFOLD_TEXT = " 查看详情";

            UNFOLD_TEXT_COLOR = attributes.getResourceId(R.styleable.FolderTextView_unfold_text_color, -1);
            if (UNFOLD_TEXT_COLOR == -1) {
                UNFOLD_TEXT_COLOR = R.color.c_1EC88B;
            }

            foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(context,UNFOLD_TEXT_COLOR));

            attributes.recycle();
        }

    }

    /**
     * 不更新全文本下，进行展开和收缩操作
     *
     * @param text
     */
    private void setUpdateText(CharSequence text) {
        isInner = true;
        setText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(fullText) || !isInner) {
            isDrawed = false;
            fullText = String.valueOf(text);
        }
        super.setText(text, type);
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        mSpacingAdd = add;
        mSpacingMult = mult;
        super.setLineSpacing(add, mult);
    }

    public int getFoldLine() {
        return foldLine;
    }

    private Layout makeTextLayout(String text) {
        return new StaticLayout(text, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(),
                Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDrawed) {
            resetText();
        }
        super.onDraw(canvas);
        isDrawed = true;
        isInner = false;
    }

    private void resetText() {
        String spanText = fullText;

        Layout layout = makeTextLayout(fullText);

        if (isAlways) {
            SpannableString spanStr;

            spanStr = createFoldSpan(spanText);

            setUpdateText(spanStr);
        } else {
            //如果行数大于固定行数
            if (layout.getLineCount() > getFoldLine()) {
                SpannableString spanStr;

                spanStr = createFoldSpan(spanText);

                setUpdateText(spanStr);
            } else {
                setText(fullText);
            }
        }
    }

    /**
     * 创建收缩状态下的Span
     *
     * @param text
     * @return
     */
    private SpannableString createFoldSpan(String text) {
        String destStr = tailorText(text);
        int start = destStr.length() - UNFOLD_TEXT.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 裁剪文本至固定行数
     *
     * @param text 源文本
     * @return
     */
    private String tailorText(String text) {
        String destStr = text + UNFOLD_TEXT;
        Layout layout = makeTextLayout(destStr);

        //如果行数大于固定行数
        if (layout.getLineCount() > getFoldLine()) {
            int index = layout.getLineEnd(getFoldLine());
            if (text.length() < index) {
                index = text.length();
            }
            String subText = text.substring(0, index - 1); //从最后一位逐渐试错至固定行数
            return tailorText(subText);
        } else {
            return destStr;
        }
    }

}