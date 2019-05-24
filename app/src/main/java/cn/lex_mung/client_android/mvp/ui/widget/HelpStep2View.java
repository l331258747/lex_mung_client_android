package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class HelpStep2View extends LinearLayout {

    Context context;
    TextView tv_basic_info, tv_practice_experience, tv_service_price;
    ImageView iv_basic_info, iv_practice_experience, iv_service_price;

    public HelpStep2View(Context context) {
        this(context, null);
    }

    public HelpStep2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpStep2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_help_step2, this, true);

        tv_basic_info = findViewById(R.id.tv_basic_info);
        tv_practice_experience = findViewById(R.id.tv_practice_experience);
        tv_service_price = findViewById(R.id.tv_service_price);

        iv_basic_info = findViewById(R.id.iv_basic_info);
        iv_practice_experience = findViewById(R.id.iv_practice_experience);
        iv_service_price = findViewById(R.id.iv_service_price);


        setIndex(0);

        tv_basic_info.setOnClickListener(v -> {
            setIndex(0);
        });
        tv_practice_experience.setOnClickListener(v -> {
            setIndex(1);
        });
        tv_service_price.setOnClickListener(v -> {
            setIndex(2);
        });
    }

    public interface ItemOnClick {
        void onClick(int position);
    }

    ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setIndex(int index) {
        if (itemOnClick != null) {
            itemOnClick.onClick(index);
        }
        switch (index) {
            case 0:
                switchPager(16, 14, 14, Typeface.BOLD, Typeface.NORMAL, Typeface.NORMAL, View.VISIBLE, View.GONE, View.GONE);
                break;
            case 1:
                switchPager(14, 16, 14, Typeface.NORMAL, Typeface.BOLD, Typeface.NORMAL, View.GONE, View.VISIBLE, View.GONE);
                break;
            case 2:
                switchPager(14, 14, 16, Typeface.NORMAL, Typeface.NORMAL, Typeface.BOLD, View.GONE, View.GONE, View.VISIBLE);
                break;
        }
    }

    /**
     * 切换页面
     */
    private void switchPager(int i1, int i2, int i3, int t1, int t2, int t3, int v1, int v2, int v3) {
        tv_basic_info.setTextSize(i1);
        tv_practice_experience.setTextSize(i2);
        tv_service_price.setTextSize(i3);
        tv_basic_info.setTypeface(Typeface.defaultFromStyle(t1));
        tv_practice_experience.setTypeface(Typeface.defaultFromStyle(t2));
        tv_service_price.setTypeface(Typeface.defaultFromStyle(t3));
        iv_basic_info.setVisibility(v1);
        iv_practice_experience.setVisibility(v2);
        iv_service_price.setVisibility(v3);
    }


}
