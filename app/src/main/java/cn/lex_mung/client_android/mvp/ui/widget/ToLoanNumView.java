package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class ToLoanNumView extends LinearLayout {

    Context context;
    TextView tv_num_1, tv_num_2, tv_num_3, tv_num_4, tv_num_5;

    public ToLoanNumView(Context context) {
        this(context, null);
    }

    public ToLoanNumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToLoanNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_tolan_num, this, true);

        tv_num_1 = findViewById(R.id.tv_num_1);
        tv_num_2 = findViewById(R.id.tv_num_2);
        tv_num_3 = findViewById(R.id.tv_num_3);
        tv_num_4 = findViewById(R.id.tv_num_4);
        tv_num_5 = findViewById(R.id.tv_num_5);

        tv_num_1.setText("0");
        tv_num_2.setText("0");
        tv_num_3.setText("0");
        tv_num_4.setText("0");
        tv_num_5.setText("0");

    }

    public void setNum(int num) {
        String s = num + "";
        setNum(s);
    }

    public void setNum(String string) {
        if(TextUtils.isEmpty(string)) return;

        byte[] bytes = string.getBytes();

        switch (string.length()) {
            case 1:
                tv_num_5.setText(bytes[0]);
                break;
            case 2:
                tv_num_5.setText(bytes[1]);
                tv_num_4.setText(bytes[0]);
                break;
            case 3:
                tv_num_5.setText(bytes[2]);
                tv_num_4.setText(bytes[1]);
                tv_num_3.setText(bytes[0]);
                break;
            case 4:
                tv_num_5.setText(bytes[3]);
                tv_num_4.setText(bytes[2]);
                tv_num_3.setText(bytes[1]);
                tv_num_2.setText(bytes[0]);
                break;
            case 5:
                tv_num_5.setText(bytes[4]);
                tv_num_4.setText(bytes[3]);
                tv_num_3.setText(bytes[2]);
                tv_num_2.setText(bytes[1]);
                tv_num_1.setText(bytes[0]);
                break;
        }
    }

}
