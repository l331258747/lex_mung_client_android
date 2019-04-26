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

public class RushOrdersView extends LinearLayout {

    Context context;
    ImageView iv_mate,iv_reply,iv_select_1,iv_select_2,iv_select_3,iv_select_4,iv_line_1,iv_line_2,iv_line_3;
    TextView tv_mate,tv_reply;

    public RushOrdersView(Context context) {
        this(context, null);
    }

    public RushOrdersView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RushOrdersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_rush_orders, this, true);

        iv_mate = findViewById(R.id.iv_mate);
        iv_reply = findViewById(R.id.iv_reply);
        iv_select_1 = findViewById(R.id.iv_select_1);
        iv_select_2 = findViewById(R.id.iv_select_2);
        iv_select_3 = findViewById(R.id.iv_select_3);
        iv_select_4 = findViewById(R.id.iv_select_4);
        iv_line_1 = findViewById(R.id.iv_line_1);
        iv_line_2 = findViewById(R.id.iv_line_2);
        iv_line_3 = findViewById(R.id.iv_line_3);
        tv_mate = findViewById(R.id.tv_mate);
        tv_reply = findViewById(R.id.tv_reply);

        tv_mate.setText("匹配律师");
        tv_reply.setText("双方沟通交付");

//        iv_select_1.setOnClickListener(v -> {
//            setProgress(0);
//        });
//        iv_select_2.setOnClickListener(v -> {
//            setProgress(1);
//        });
//        iv_select_3.setOnClickListener(v -> {
//            setProgress(2);
//        });
//        iv_select_4.setOnClickListener(v -> {
//            setProgress(3);
//        });
    }

//    public interface ItemOnClick{
//        void onClick(int position);
//    }
//    ItemOnClick itemOnClick;
//    public void setItemOnClick(ItemOnClick itemOnClick){
//        this.itemOnClick = itemOnClick;
//    }

    public void setProgress(int progress){
//        if(itemOnClick !=null){
//            itemOnClick.onClick(progress);
//        }
        switch (progress){
            case 0:
                iv_select_1.setVisibility(VISIBLE);
                iv_select_2.setVisibility(VISIBLE);
                iv_select_3.setVisibility(VISIBLE);
                iv_select_4.setVisibility(VISIBLE);
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));

                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));

                iv_mate.setVisibility(INVISIBLE);
                iv_reply.setVisibility(INVISIBLE);
                tv_mate.setVisibility(VISIBLE);
                tv_reply.setVisibility(VISIBLE);
                break;
            case 1:
                iv_select_1.setVisibility(VISIBLE);
                iv_select_2.setVisibility(INVISIBLE);
                iv_select_3.setVisibility(VISIBLE);
                iv_select_4.setVisibility(VISIBLE);
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));


                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));

                iv_mate.setVisibility(VISIBLE);
                tv_mate.setVisibility(INVISIBLE);
                iv_reply.setVisibility(INVISIBLE);
                tv_reply.setVisibility(VISIBLE);

                break;
            case 2:
                iv_select_1.setVisibility(VISIBLE);
                iv_select_2.setVisibility(VISIBLE);
                iv_select_3.setVisibility(INVISIBLE);
                iv_select_4.setVisibility(VISIBLE);
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select_un));


                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_ff));

                iv_mate.setVisibility(INVISIBLE);
                tv_mate.setVisibility(VISIBLE);
                iv_reply.setVisibility(VISIBLE);
                tv_reply.setVisibility(INVISIBLE);
                break;
            case 3:
                iv_select_1.setVisibility(VISIBLE);
                iv_select_2.setVisibility(VISIBLE);
                iv_select_3.setVisibility(VISIBLE);
                iv_select_4.setVisibility(VISIBLE);
                iv_select_1.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_2.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_3.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));
                iv_select_4.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_rush_order_select));


                iv_line_1.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));
                iv_line_2.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));
                iv_line_3.setBackgroundColor(ContextCompat.getColor(context,R.color.c_3DD790));

                iv_mate.setVisibility(INVISIBLE);
                tv_mate.setVisibility(VISIBLE);
                iv_reply.setVisibility(INVISIBLE);
                tv_reply.setVisibility(VISIBLE);

                break;
        }

    }



}
