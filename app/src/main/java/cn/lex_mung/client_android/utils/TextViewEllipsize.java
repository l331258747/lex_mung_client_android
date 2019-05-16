package cn.lex_mung.client_android.utils;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class TextViewEllipsize {

    /**
     * 给TextView的超长文本省略号之后加上点击文本
     *
     * @param tv           TextView
     * @param originalText 原始的超长文本
     * @param endStr       要添加在末尾的文本
     * @param endStrColor  末尾文本颜色
     */
    public static void setEllipsize(@NonNull final TextView tv, @Nullable final String originalText, @Nullable final String endStr, @ColorInt final int endStrColor) {
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                TextPaint paint = tv.getPaint();
                // TextView最大支持文本宽度 - 要添加在末尾的文本宽度
                float avail = (tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight()) * tv.getMaxLines() - paint.getTextSize() * (endStr == null ? 0 : endStr.length() + 1);
                // 获取带省略号的文本
                String string = TextUtils.ellipsize(TextUtils.isEmpty(originalText) ? tv.getText() : originalText, paint, avail, TextUtils.TruncateAt.END).toString();
                // 带省略号的文本 + 添加在末尾的文本
                string = string + endStr;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
                // 点击事件和添加在末尾的文本效果
                spannableStringBuilder.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(@NonNull View widget) {

                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        // 添加在末尾的文本颜色
                        ds.setColor(endStrColor);
                        // 添加在末尾的文本去掉下划线
                        ds.setUnderlineText(false);
                        // 清除阴影
                        ds.clearShadowLayer();
                    }
                }, string.length() - endStr.length(), string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // 重新设置TextView文本
                tv.setText(spannableStringBuilder);
                // 去除默认点击后背景变色效果
                tv.setHighlightColor(Color.TRANSPARENT);
                // 让点击事件可以生效
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                // 取消关注，让点击事件与ListView的Item点击事件不再冲突
                tv.setFocusable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }
}
