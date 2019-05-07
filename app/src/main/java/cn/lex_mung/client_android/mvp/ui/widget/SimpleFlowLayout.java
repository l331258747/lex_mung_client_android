package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import cn.lex_mung.client_android.R;

/**横向流式布局
 */
public class SimpleFlowLayout extends ViewGroup {
    private String TAG = SimpleFlowLayout.class.getName();
    // item间距
    private int dividerWidth;
    // 行间距
    private int rowHeight;
    // 子view数量
    private int mChildCount;
    // 边距
    private int padLeft;
    private int padRight;
    private int padTop;
    private int padBottom;

    public SimpleFlowLayout(Context context) {
        this(context, null);
    }

    public SimpleFlowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFlowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleFlowLayout);
        // 默认间距
        int defDW = dp2px(20);
        dividerWidth = (int) typedArray.getDimension(R.styleable.SimpleFlowLayout_hor_divider_width, defDW);
        int defRH = dp2px(20);
        rowHeight = (int) typedArray.getDimension(R.styleable.SimpleFlowLayout_hor_row_height, defRH);
        typedArray.recycle();
    }

    /**
     * 摆放原理：按行摆放，循环遍历子View，当子View叠加宽度至一行时宽度重新变为左padding间距，
     * 高度叠加当前行的item高度
     * 进行换行时总高度=当前所有行高度+自定义行间距，然后以此类推叠加
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 子View的左边距和上边距
        int itemWidth = padLeft;
        int itemHeight = padTop;
        // 记录上一个子View的高度值，当前行所有子view宽度+下一个(i+1)子View的宽度超过一行时，则高度叠加
        int tempHeight = 0;
        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            // 计算当前行总宽度+当前下标子view宽度是否超过父容器行宽度，是则换行，否则直接进行layout摆放
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();
            // 先计算出当前行的view宽度 + 当前子View宽度，是否超过当前行所剩余的宽度
            int fakeW = itemWidth + childW;
            if (fakeW >= getMeasuredWidth() - padRight) {
                // 宽度占满一行，进行换行
                if (i != 0) {
                    itemHeight += rowHeight + tempHeight;
                }
                itemWidth = padLeft;
                fakeW = itemWidth + childW;
            }
            child.layout(itemWidth, itemHeight, fakeW, itemHeight + childH);
            itemWidth += childW + dividerWidth;
            tempHeight = childH;
        }
    }

    /**
     * 测量原理：
     * 当宽度为WrapContent时，则取所有子View里面宽度最长的一个作为总宽度，如果childCount=1，则直接返回子view的宽度 + 左右间距
     * 当高度为WrapContent时，则高度=：计算出所有子View能够摆放的行数 * （行高度） +（行数-1） * 行间距 + 上下间距，如果childCount=1，同上
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        padLeft = getPaddingLeft();
        padRight = getPaddingRight();
        padTop = getPaddingTop();
        padBottom = getPaddingBottom();

        mChildCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (mChildCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }

        width = measureWidth(width, wMode);
        height = measureHeight(width, height, hMode);

        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int width, int wMode) {
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                Log.d(TAG, width + "---" + "EXACTLY");
                return width;
            case MeasureSpec.AT_MOST:
                Log.d(TAG, width + "---" + "AT_MOST");
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, width + "---" + "UNSPECIFIED");
                return calculateWidth(width);
            default:
                return width;
        }
    }

    /**
     * 测量高度
     */
    private int measureHeight(int width, int height, int hMode) {
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                Log.d(TAG, width + "---" + "EXACTLY");
                return height;
            case MeasureSpec.AT_MOST:
                Log.d(TAG, width + "---" + "AT_MOST");
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, width + "---" + "UNSPECIFIED");
                return calculateHeight(width, height);
            default:
                return height;
        }
    }

    /**
     * 计算高度为wrap_content
     * 计算child所占的总行数 * 行宽 + (行数 - 1) * 行间距 + 上下间距
     *
     * @param width  初步测量得到的宽度
     * @param height 初步测量得到的高度
     */
    private int calculateHeight(int width, int height) {
        int useableWidth = width - padLeft - padRight;
        if (mChildCount == 1) {
            int singleHeight = getChildAt(0).getMeasuredHeight() + (padTop + padBottom);
            if (height == 0) {
                return singleHeight;
            }
            return Math.min(height, singleHeight);
        } else {
            // 对每个view测量算出行数
            int itemWidth = 0;
            int line = 1;
            for (int i = 0; i < mChildCount; i++) {
                View child = getChildAt(i);
                int childW = child.getMeasuredWidth();
                int fakeW = itemWidth + childW;
                if (fakeW >= useableWidth) {
                    // 行数+1
                    itemWidth = 0;
                    itemWidth += childW + dividerWidth;
                    line++;
                } else {
                    // 未占满一行，行宽度+=子view宽度
                    itemWidth += childW;
                    if (i != mChildCount - 1) {
                        itemWidth += dividerWidth;
                    }
                }
            }

            line = Math.min(line, mChildCount);
            //  高度=子view高度 * line + 行间距 * (line - 1) + 上下间距
            return getChildAt(0).getMeasuredHeight() * line + rowHeight * (line - 1) + padTop + padBottom;
        }
    }

    /**
     * 计算宽度为wrap_content
     * 求出child中宽最大的，得到最终宽度
     *
     * @param width 初步测量得到的宽度
     */
    private int calculateWidth(int width) {
        if (mChildCount == 1) {
            int singleWidth = getChildAt(0).getMeasuredWidth() + (padLeft + padRight);
            if (width == 0) {
                return singleWidth;
            }
            return Math.min(width, singleWidth);
        } else {
            int result = 0;
            for (int i = 0; i < mChildCount; i++) {
                int childWidth = getChildAt(i).getMeasuredWidth();
                if (result < childWidth) {
                    result = childWidth;
                }
            }
            return Math.min(width, result + (padLeft + padRight));
        }
    }

    /**
     * DP转PX
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getContext().getResources()
                .getDisplayMetrics());
    }
}
