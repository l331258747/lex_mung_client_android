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

public class HelpStepChildView extends LinearLayout {

    Context context;
    ImageView iv_1,iv_2,iv_3;
    TextView tv_1,tv_2,tv_3;
    ImageView iv_select_1,iv_select_2,iv_select_3;

    public HelpStepChildView(Context context) {
        this(context, null);
    }

    public HelpStepChildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpStepChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_help_step_child, this, true);

        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);

        iv_select_1 = findViewById(R.id.iv_select_1);
        iv_select_2 = findViewById(R.id.iv_select_2);
        iv_select_3 = findViewById(R.id.iv_select_3);

        setProgress(0);

    }


    public void setProgress(int progress){
        switch (progress){
            case 0:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));

                iv_1.setVisibility(VISIBLE);
                iv_2.setVisibility(INVISIBLE);
                iv_3.setVisibility(INVISIBLE);

                tv_1.setVisibility(INVISIBLE);
                tv_2.setVisibility(VISIBLE);
                tv_3.setVisibility(VISIBLE);

                break;
            case 1:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));

                iv_1.setVisibility(INVISIBLE);
                iv_2.setVisibility(VISIBLE);
                iv_3.setVisibility(INVISIBLE);

                tv_1.setVisibility(VISIBLE);
                tv_2.setVisibility(INVISIBLE);
                tv_3.setVisibility(VISIBLE);

                break;
            case 2:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));


                iv_1.setVisibility(INVISIBLE);
                iv_2.setVisibility(INVISIBLE);
                iv_3.setVisibility(VISIBLE);

                tv_1.setVisibility(VISIBLE);
                tv_2.setVisibility(VISIBLE);
                tv_3.setVisibility(INVISIBLE);
                break;
        }

    }



}
