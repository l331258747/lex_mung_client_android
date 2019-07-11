package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import cn.lex_mung.client_android.R;

import me.zl.mvp.utils.AppUtils;

public class RoundImageView extends AppCompatImageView {
    private float width, height;
    private Path path;
    private int round;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        round = AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_20));
        round = AppUtils.dip2px(context, AppUtils.getXmlDef(context, R.dimen.qb_px_20));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.moveTo(round, 0);
        path.lineTo(width - round, 0);
        path.quadTo(width, 0, width, round);
        path.lineTo(width, height - round);
        path.quadTo(width, height, width - round, height);
        path.lineTo(round, height);
        path.quadTo(0, height, 0, height - round);
        path.lineTo(0, round);
        path.quadTo(0, 0, round, 0);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}
