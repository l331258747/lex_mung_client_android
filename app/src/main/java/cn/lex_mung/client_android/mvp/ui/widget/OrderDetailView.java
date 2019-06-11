package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class OrderDetailView extends LinearLayout {

    Context context;
    View view_line_1, view_line_2, view_line_3;
    View view_1, view_2, view_3, view_4;
    TextView tv_1, tv_2, tv_3, tv_4;

    public OrderDetailView(Context context) {
        this(context, null);
    }

    public OrderDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_order_detail, this, true);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);

        view_line_1 = findViewById(R.id.view_line_1);
        view_line_2 = findViewById(R.id.view_line_2);
        view_line_3 = findViewById(R.id.view_line_3);

        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);

        setProgress(0);
    }

    //4位的数组
    public void initView(String[] strs) {
        tv_1.setText(strs[0]);
        tv_2.setText(strs[1]);
        tv_3.setText(strs[2]);
        tv_4.setText(strs[3]);
    }


    public void setProgress(int progress) {
        switch (progress) {
            case 0:

                view_line_1.setVisibility(GONE);
                view_line_2.setVisibility(GONE);
                view_line_3.setVisibility(GONE);

                view_1.setVisibility(VISIBLE);
                view_2.setVisibility(GONE);
                view_3.setVisibility(GONE);
                view_4.setVisibility(GONE);

                break;
            case 1:
                view_line_1.setVisibility(VISIBLE);
                view_line_2.setVisibility(GONE);
                view_line_3.setVisibility(GONE);

                view_1.setVisibility(VISIBLE);
                view_2.setVisibility(VISIBLE);
                view_3.setVisibility(GONE);
                view_4.setVisibility(GONE);

                break;
            case 2:
                view_line_1.setVisibility(VISIBLE);
                view_line_2.setVisibility(VISIBLE);
                view_line_3.setVisibility(GONE);

                view_1.setVisibility(VISIBLE);
                view_2.setVisibility(VISIBLE);
                view_3.setVisibility(VISIBLE);
                view_4.setVisibility(GONE);

                break;
            case 3:
                view_line_1.setVisibility(VISIBLE);
                view_line_2.setVisibility(VISIBLE);
                view_line_3.setVisibility(VISIBLE);

                view_1.setVisibility(VISIBLE);
                view_2.setVisibility(VISIBLE);
                view_3.setVisibility(VISIBLE);
                view_4.setVisibility(VISIBLE);
                break;
        }
    }
}
