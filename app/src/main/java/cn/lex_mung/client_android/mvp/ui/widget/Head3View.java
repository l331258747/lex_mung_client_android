package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.R;
import me.zl.mvp.http.imageloader.ImageLoader;

public class Head3View extends LinearLayout {

    Context context;
    ImageView iv_head1, iv_head2, iv_head3;
    RelativeLayout fl_head1,fl_head2,fl_head3;

    public Head3View(Context context) {
        this(context, null);
    }

    public Head3View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Head3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_head3_view, this, true);

        iv_head1 = findViewById(R.id.iv_head1);
        iv_head2 = findViewById(R.id.iv_head2);
        iv_head3 = findViewById(R.id.iv_head3);
        fl_head1 = findViewById(R.id.fl_head1);
        fl_head2 = findViewById(R.id.fl_head2);
        fl_head3 = findViewById(R.id.fl_head3);
    }

    public void setHeads(ImageLoader mImageLoader, List<String> lists) {
        fl_head1.setVisibility(GONE);
        fl_head2.setVisibility(GONE);
        fl_head3.setVisibility(GONE);

        if (lists == null || lists.size() == 0) {
            return;
        }

        if (lists.size() == 1) {
            fl_head1.setVisibility(VISIBLE);
            setImg(mImageLoader,lists.get(0),iv_head1);
        } else if (lists.size() == 2) {
            fl_head1.setVisibility(VISIBLE);
            fl_head2.setVisibility(VISIBLE);
            setImg(mImageLoader,lists.get(0),iv_head1);
            setImg(mImageLoader,lists.get(1),iv_head2);
        } else {
            fl_head1.setVisibility(VISIBLE);
            fl_head2.setVisibility(VISIBLE);
            fl_head3.setVisibility(VISIBLE);
            setImg(mImageLoader,lists.get(0),iv_head1);
            setImg(mImageLoader,lists.get(1),iv_head2);
            setImg(mImageLoader,lists.get(2),iv_head3);
        }
    }

    public void setImg(ImageLoader mImageLoader, String url, ImageView iv) {
        if (!TextUtils.isEmpty(url)) {
            mImageLoader.loadImage(context
                    , ImageConfigImpl
                            .builder()
                            .url(url)
                            .isCircle(true)
                            .imageView(iv)
                            .build());
        }else{
            iv.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_avatar));
        }
    }
}
