package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.RechargeCouponAdapter;
import me.zl.mvp.utils.AppUtils;

/**
 * 服务助手对话框
 */
public class RechargeDialog extends Dialog {
    Context context;

    ImageView iv_title_name;
    TextView tv_title_name,tv_balance,tv_give_balance,tv_submit,tv_tip;
    RelativeLayout rl_give_balance;
    LinearLayout ll_coupon;
    RecyclerView recyclerView;

    public RechargeDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    int ivTitleResId = -1;
    public RechargeDialog setIvTitle(int ivTitleResId) {
        this.ivTitleResId = ivTitleResId;
        return this;
    }

    String tvTitle;
    public RechargeDialog setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
        return this;
    }

    String tvBalance;
    public RechargeDialog setBalance(String tvBalance) {
        this.tvBalance = tvBalance;
        return this;
    }

    String tvGiveBalance;
    public RechargeDialog setGiveBalance(String tvGiveBalance) {
        this.tvGiveBalance = tvGiveBalance;
        return this;
    }

    String tvTip;
    public RechargeDialog setTip(String tvTip) {
        this.tvTip = tvTip;
        return this;
    }

    List<RechargeCouponEntity> entities;
    public RechargeDialog setEntities(List<RechargeCouponEntity> entities){
        this.entities = entities;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_recharge);
        initView();
        setCancelable(false);
    }

    private void initView() {
        iv_title_name = findViewById(R.id.iv_title_name);
        tv_title_name = findViewById(R.id.tv_title_name);
        tv_balance = findViewById(R.id.tv_balance);
        tv_give_balance = findViewById(R.id.tv_give_balance);
        tv_submit = findViewById(R.id.tv_submit);
        rl_give_balance = findViewById(R.id.rl_give_balance);
        ll_coupon = findViewById(R.id.ll_coupon);
        recyclerView = findViewById(R.id.recycler_view);
        tv_tip = findViewById(R.id.tv_tip);

        if(ivTitleResId != -1){
            iv_title_name.setImageResource(ivTitleResId);
        }

        if(!TextUtils.isEmpty(tvTitle)){
            tv_title_name.setText(tvTitle);
        }

        if(!TextUtils.isEmpty(tvBalance)){
            tv_balance.setText(tvBalance);
            tv_balance.setVisibility(View.VISIBLE);
        }

        rl_give_balance.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(tvGiveBalance)){
            tv_give_balance.setText(tvGiveBalance);
            rl_give_balance.setVisibility(View.VISIBLE);
        }

        tv_tip.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(tvTip)){
            tv_tip.setText(tvTip);
            tv_tip.setVisibility(View.VISIBLE);
        }

        ll_coupon.setVisibility(View.GONE);
        if(entities != null && entities.size() > 0){
            ll_coupon.setVisibility(View.VISIBLE);

            RechargeCouponAdapter adapter = new RechargeCouponAdapter();
            AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            adapter.setNewData(entities);
        }

        tv_submit.setOnClickListener(v -> {
            dismiss();
        });
    }

}
