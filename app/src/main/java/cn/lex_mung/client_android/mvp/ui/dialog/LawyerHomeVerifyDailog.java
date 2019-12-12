package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.other.LawyerHomeVerifyDailogEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerHomeVerifyDialogAdapter;
import me.zl.mvp.utils.AppUtils;

public class LawyerHomeVerifyDailog extends Dialog {
    Context context;

    TextView tv_cancel;
    RecyclerView recyclerView;

    List<LawyerHomeVerifyDailogEntity> entities;

    public LawyerHomeVerifyDailog(@NonNull Context context, List<LawyerHomeVerifyDailogEntity> entities) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.entities = entities;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lawyer_home_verify);
        setCancelable(false);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(v -> dismiss());


        LawyerHomeVerifyDialogAdapter adapter = new LawyerHomeVerifyDialogAdapter();
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(entities);

    }

}
