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

public class EvaluateStarView extends LinearLayout {
    Context context;

    ImageView iv_1,iv_2,iv_3,iv_4,iv_5;
    TextView tv_1;

    int num;
    boolean isClick;

    public EvaluateStarView(Context context) {
        this(context, null);
    }

    public EvaluateStarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaluateStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_evaluate_star_view, this, true);

        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_5 = findViewById(R.id.iv_5);
        tv_1 = findViewById(R.id.tv_1);

        if(isClick){
            iv_1.setOnClickListener(v -> {
                setNum(1);
            });
            iv_2.setOnClickListener(v -> {
                setNum(2);
            });
            iv_3.setOnClickListener(v -> {
                setNum(3);

            });
            iv_4.setOnClickListener(v -> {
                setNum(4);
            });
            iv_5.setOnClickListener(v -> {
                setNum(5);
            });
        }

        setNum(5);
    }

    public EvaluateStarView setClick(boolean isClick){
        this.isClick = isClick;
        return this;
    }

    public void setNum(int num){
        if(num < 1) num = 1;
        if(num > 5) num = 5;
        this.num = num;

        iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_un));
        iv_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_un));
        iv_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_un));
        iv_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_un));
        iv_5.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_un));

        switch (num){
            case 1:
                iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                tv_1.setText("很差");
                break;
            case 2:
                iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                tv_1.setText("一般");
                break;
            case 3:
                iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                tv_1.setText("一般");
                break;
            case 4:
                iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                tv_1.setText("满意");
                break;
            case 5:
                iv_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                iv_5.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star));
                tv_1.setText("满意");
                break;
        }
    }

    public int getNum(){
        return num;
    }

}
