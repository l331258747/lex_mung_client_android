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

public class HelpStepView extends LinearLayout {

    Context context;
    TextView tv_1,tv_2,tv_3,tv_4;
    ImageView iv_select_1,iv_select_2,iv_select_3,iv_select_4;
    ImageView iv_line_1,iv_line_2,iv_line_3;

    public HelpStepView(Context context) {
        this(context, null);
    }

    public HelpStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_help_step, this, true);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);

        iv_select_1 = findViewById(R.id.iv_select_1);
        iv_select_2 = findViewById(R.id.iv_select_2);
        iv_select_3 = findViewById(R.id.iv_select_3);
        iv_select_4 = findViewById(R.id.iv_select_4);

        iv_line_1 = findViewById(R.id.iv_line_1);
        iv_line_2 = findViewById(R.id.iv_line_2);
        iv_line_3 = findViewById(R.id.iv_line_3);

        setProgress(0);

    }

    //3位的数组
    public void initView(String[] strs) {
        tv_1.setText(strs[0]);
        tv_2.setText(strs[1]);
        tv_3.setText(strs[2]);
        tv_4.setText(strs[3]);
    }


    public void setProgress(int progress){
        switch (progress){
            case 0:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));//实心绿
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_737373));//空心灰
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_737373));//空心灰
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));//实心灰

                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));

                tv_1.setBackground(ContextCompat.getDrawable(context,R.drawable.round_40_1ec78a_all));
                tv_1.setTextColor(ContextCompat.getColor(context,R.color.c_ff));

                tv_2.setBackground(null);
                tv_2.setTextColor(ContextCompat.getColor(context,R.color.c_737373));
                tv_3.setBackground(null);
                tv_3.setTextColor(ContextCompat.getColor(context,R.color.c_737373));
                tv_4.setBackground(null);
                tv_4.setTextColor(ContextCompat.getColor(context,R.color.c_737373));

                break;
            case 1:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));//实心绿
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_1ec78a));//空心绿
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_737373));//空心灰
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));//实心灰

                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));

                tv_1.setBackground(null);
                tv_1.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));

                tv_2.setBackground(ContextCompat.getDrawable(context,R.drawable.round_40_1ec78a_all));
                tv_2.setTextColor(ContextCompat.getColor(context,R.color.c_ff));

                tv_3.setBackground(null);
                tv_3.setTextColor(ContextCompat.getColor(context,R.color.c_737373));
                tv_4.setBackground(null);
                tv_4.setTextColor(ContextCompat.getColor(context,R.color.c_737373));

                break;
            case 2:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));//实心绿
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_1ec78a));//空心绿
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_1ec78a));//空心绿
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_737373_all));//实心灰

                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_737373));

                tv_1.setBackground(null);
                tv_1.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                tv_2.setBackground(null);
                tv_2.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));

                tv_3.setBackground(ContextCompat.getDrawable(context,R.drawable.round_40_1ec78a_all));
                tv_3.setTextColor(ContextCompat.getColor(context,R.color.c_ff));

                tv_4.setBackground(null);
                tv_4.setTextColor(ContextCompat.getColor(context,R.color.c_737373));
                break;
            case 3:
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));//实心绿
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_1ec78a));//空心绿
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_00_all_1ec78a));//空心绿
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.round_100_1ec78a_all));//实心绿

                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_1EC78A));

                tv_1.setBackground(null);
                tv_1.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                tv_2.setBackground(null);
                tv_2.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));
                tv_3.setBackground(null);
                tv_3.setTextColor(ContextCompat.getColor(context,R.color.c_1EC78A));

                tv_4.setBackground(ContextCompat.getDrawable(context,R.drawable.round_40_1ec78a_all));
                tv_4.setTextColor(ContextCompat.getColor(context,R.color.c_ff));

                break;
        }

    }



}
