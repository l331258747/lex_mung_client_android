package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

public class PayTypeView extends LinearLayout implements View.OnClickListener {

    Context context;
    RelativeLayout rl_wx, rl_zfb, rl_balance, rl_member, rl_group;
    TextView tv_balance_count, tv_member_count, tv_group_count,tv_pay_price;
    ImageView iv_select_wx, iv_select_zfb, iv_select_balance, iv_select_member, iv_select_group;


    int type = 1;

    public PayTypeView(Context context) {
        this(context, null);
    }

    public PayTypeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.view_pay_type, this, true);

        rl_wx = findViewById(R.id.rl_wx);
        rl_zfb = findViewById(R.id.rl_zfb);
        rl_balance = findViewById(R.id.rl_balance);
        rl_member = findViewById(R.id.rl_member);
        rl_group = findViewById(R.id.rl_group);

        tv_balance_count = findViewById(R.id.tv_balance_count);
        tv_member_count = findViewById(R.id.tv_member_count);
        tv_group_count = findViewById(R.id.tv_group_count);

        iv_select_wx = findViewById(R.id.iv_select_wx);
        iv_select_zfb = findViewById(R.id.iv_select_zfb);
        iv_select_balance = findViewById(R.id.iv_select_balance);
        iv_select_member = findViewById(R.id.iv_select_member);
        iv_select_group = findViewById(R.id.iv_select_group);

        tv_pay_price = findViewById(R.id.tv_pay_price);

        rl_wx.setOnClickListener(this);
        rl_zfb.setOnClickListener(this);
        rl_balance.setOnClickListener(this);
        rl_member.setOnClickListener(this);
        rl_group.setOnClickListener(this);

        rl_member.setVisibility(GONE);
        rl_group.setVisibility(GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_wx:
                iv_select_wx.setImageResource(R.drawable.ic_show_select);
                iv_select_zfb.setImageResource(R.drawable.ic_hide_select);
                iv_select_balance.setImageResource(R.drawable.ic_hide_select);
                iv_select_member.setImageResource(R.drawable.ic_hide_select);
                iv_select_group.setImageResource(R.drawable.ic_hide_select);
                type = 1;
                break;
            case R.id.rl_zfb:
                iv_select_wx.setImageResource(R.drawable.ic_hide_select);
                iv_select_zfb.setImageResource(R.drawable.ic_show_select);
                iv_select_balance.setImageResource(R.drawable.ic_hide_select);
                iv_select_member.setImageResource(R.drawable.ic_hide_select);
                iv_select_group.setImageResource(R.drawable.ic_hide_select);
                type = 2;
                break;
            case R.id.rl_balance:
                iv_select_wx.setImageResource(R.drawable.ic_hide_select);
                iv_select_zfb.setImageResource(R.drawable.ic_hide_select);
                iv_select_balance.setImageResource(R.drawable.ic_show_select);
                iv_select_member.setImageResource(R.drawable.ic_hide_select);
                iv_select_group.setImageResource(R.drawable.ic_hide_select);
                type = 3;
                break;
            case R.id.rl_member:
                iv_select_wx.setImageResource(R.drawable.ic_hide_select);
                iv_select_zfb.setImageResource(R.drawable.ic_hide_select);
                iv_select_balance.setImageResource(R.drawable.ic_hide_select);
                iv_select_member.setImageResource(R.drawable.ic_show_select);
                iv_select_group.setImageResource(R.drawable.ic_hide_select);
                type = 4;
                break;
            case R.id.rl_group:
                iv_select_wx.setImageResource(R.drawable.ic_hide_select);
                iv_select_zfb.setImageResource(R.drawable.ic_hide_select);
                iv_select_balance.setImageResource(R.drawable.ic_hide_select);
                iv_select_member.setImageResource(R.drawable.ic_hide_select);
                iv_select_group.setImageResource(R.drawable.ic_show_select);
                type = 5;
                break;
        }
        if(itemOnClick != null){
            itemOnClick.onClick(type);
        }
    }

    public void setBalancePrice(String balance){
        tv_balance_count.setText(balance);
    }
    public void setMemberPrice(String member){
        tv_member_count.setText(member);
    }
    public void setGroupPrice(String group){
        tv_group_count.setText(group);
    }

    public void setOrderPrice(String price){
        tv_pay_price.setText(price);
    }

    public interface ItemOnClick {
        void onClick(int type);
    }

    ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }



}
