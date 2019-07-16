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
            if(itemOnClick != null){
                itemOnClick.onClick(list.get(position).getType(),list.get(position).getGroupId());
            }
        });

        setPayTypeData();
    }

    private void setPayTypeData(){
        list = new ArrayList<>();
        PayTypeEntity entity = new PayTypeEntity();
        entity.setTitle("微信支付");
        entity.setType(1);
        entity.setBalance(-1);
        entity.setIcon(R.drawable.ic_pay_wx);
        entity.setSelected(true);

        PayTypeEntity entity2 = new PayTypeEntity();
        entity2.setTitle("支付宝支付");
        entity2.setType(2);
        entity2.setBalance(-1);
        entity2.setIcon(R.drawable.ic_pay_zfb);
        entity2.setSelected(false);

        list.add(entity);
        list.add(entity2);

        adapter.setNewData(list);
    }


    public void addPayTypeData(PayTypeEntity entity){
        if(entity.getType() == 4){//如果是会员卡，只能有一张，先判断是否存在，存在：替换，不存在：添加
            if(isHasTypeData(4)){
                for (int i = 0;i<list.size();i++){
                    if(list.get(i).getType() == 4){
                        entity.setSelected(list.get(i).isSelected());//保持选中状态
                        list.set(i,entity);
                    }
                }
            }else{
                list.add(entity);
            }
        }else{
            list.add(entity);
        }
        adapter.notifyDataSetChanged();
    }

    public void removePayTYpeData(int type){
        if(isHasTypeData(type)){
            for (int i=0;i<list.size();i++){
                if(list.get(i).getType() == type){
                    if(list.get(i).isSelected()){
                        list.remove(i);
                        changeData(0);//当为删除项为选中状态时，默认选中第一项
                    }else{
                        list.remove(i);
                        adapter.notifyDataSetChanged();//非选中状态，保持原来选中项
                    }
                }
            }

        }
    }

    public boolean isHasTypeData(int type){
        for (int i=0;i<list.size();i++){
            if(type == list.get(i).getType()){
                return true;
            }
        }
        return false;
    }

    private void changeData(int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
        list.get(position).setSelected(true);
        adapter.notifyDataSetChanged();
    }




    public interface ItemOnClick {
        void onClick(int type,int type6Id);
    }

    ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
}
