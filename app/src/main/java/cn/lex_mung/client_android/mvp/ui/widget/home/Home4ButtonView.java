package cn.lex_mung.client_android.mvp.ui.widget.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class Home4ButtonView extends RelativeLayout {

    ImageView iv_img,iv_tag;
    TextView tv_title,tv_content1,tv_content2;

    public Home4ButtonView(Context context) {
        this(context,null);
    }

    public Home4ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Home4ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_home_4button, this, true);

        iv_img = findViewById(R.id.iv_img);
        iv_tag = findViewById(R.id.iv_tag);
        tv_title = findViewById(R.id.tv_title);
        tv_content1 = findViewById(R.id.tv_content1);
        tv_content2 = findViewById(R.id.tv_content2);

    }

    public ImageView getIv_tag() {
        return iv_tag;
    }

    public TextView getTv_content2() {
        return tv_content2;
    }

    public ImageView getIv_img() {
        return iv_img;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public TextView getTv_content1() {
        return tv_content1;
    }
}
