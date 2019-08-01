package cn.lex_mung.client_android.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
public class FolderTextView extends TextView {

    private static final String FOLD_TEXT = " 收缩";
    private static final String UNFOLD_TEXT = " 查看详情";

    /**
     * 收缩状态
     */
//    private boolean isFold = false;

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

    /**
     * 全文本
     */
    private String fullText;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;


    public FolderTextView(Context context) {
        this(context, null);
    }

    public FolderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FolderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(R.styleable.FolderTextView);
        foldLine = a.getInt(R.styleable.FolderTextView_foldline, 3);

        a.recycle();
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

    public void setFoldLine(int foldLine) {
        this.foldLine = foldLine;
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

        SpannableString spanStr;

        //收缩状态
//        if (isFold) {
//            spanStr = createUnFoldSpan(spanText);
//        } else { //展开状态
//            spanStr = createFoldSpan(spanText);
//        }
        spanStr = createFoldSpan(spanText);

        setUpdateText(spanStr);
//        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 创建展开状态下的Span
     *
     * @param text 源文本
     * @return
     */
    private SpannableString createUnFoldSpan(String text) {
        String destStr = text + FOLD_TEXT;
        int start = destStr.length() - FOLD_TEXT.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
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

    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.c_3DD790));

    public void setEndColor(int colorId){
        //c_4A90E2
        foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(colorId));
    }
}