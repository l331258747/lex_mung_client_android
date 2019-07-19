package cn.lex_mung.client_android.mvp.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.other.PayTypeEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.PayTypeAdapter;
import me.zl.mvp.utils.AppUtils;

public class PayTypeView2 extends LinearLayout {

    Context context;
    RecyclerView recyclerView;

    PayTypeAdapter adapter;
    List<PayTypeEntity> list;

    boolean isFast;//用来判断是否初始化，第一次添加集团卡的时候变

    public PayTypeView2(Context context) {
        this(context, null);
    }

    public PayTypeView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayTypeView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.view_pay_type2, this, true);

        recyclerView = findViewById(R.id.recycler_view);


        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
        adapter = new PayTypeAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            changeData(position);
            if (itemOnClick != null) {
                itemOnClick.onClick(list.get(position).getType(), list.get(position).getGroupId());
            }
        });

        isFast = false;
    }

    public void setPayTYpeData(List<PayTypeEntity> entitys) {
        list = new ArrayList<>();
        PayTypeEntity entity = new PayTypeEntity();
        entity.setTitle("微信支付");
        entity.setType(1);
        entity.setBalance(-1);
        entity.setIcon(R.drawable.ic_pay_wx);
        entity.setSelected(false);

        PayTypeEntity entity2 = new PayTypeEntity();
        entity2.setTitle("支付宝支付");
        entity2.setType(2);
        entity2.setBalance(-1);
        entity2.setIcon(R.drawable.ic_pay_zfb);
        entity2.setSelected(false);

        list.add(entity);
        list.add(entity2);

        if (entitys != null && entitys.size() > 0) {
            for (int i = 0; i < entitys.size(); i++) {
                list.add(entitys.get(i));
            }
        }

        adapter.setNewData(list);
    }

    public void setSelect(double money) {
        if (list == null) return;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 6) {
                if (money <= list.get(i).getBalance()) {
                    changeData(i);
                    itemOnClick.onClick(list.get(i).getType(), list.get(i).getGroupId());
                    return;
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 4) {
                if (money <= list.get(i).getBalance()) {
                    changeData(i);
                    itemOnClick.onClick(list.get(i).getType(), list.get(i).getGroupId());
                    return;
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 3) {
                if (money <= list.get(i).getBalance()) {
                    changeData(i);
                    itemOnClick.onClick(list.get(i).getType(), list.get(i).getGroupId());
                    return;
                }
            }
        }

        changeData(0);
        itemOnClick.onClick(list.get(0).getType(), list.get(0).getGroupId());
    }

    public double getTypeBalance(int payType, int groupId) {
        if(list == null) return 0;

        for (int i = 0; i < list.size(); i++) {
            if(payType == 6){
                if (payType == list.get(i).getType() && list.get(i).getGroupId() == groupId) {
                    return list.get(i).getBalance();
                }
            }else{
                if (payType == list.get(i).getType()) {
                    return list.get(i).getBalance();
                }
            }
        }
        return 0;
    }


    private void changeData(int position) {
        if(list == null) return;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
        list.get(position).setSelected(true);
        adapter.notifyDataSetChanged();
    }

    public interface ItemOnClick {
        void onClick(int type, int type6Id);
    }

    ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
}
