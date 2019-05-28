package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.lex_mung.client_android.R;

public class WebSolutionActivity extends WebActivity {
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.tv_btn)
    TextView tv_btn;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       super.initData(savedInstanceState);
       ll_bottom.setVisibility(View.VISIBLE);
        tv_btn.setOnClickListener(v -> {
            launchActivity(new Intent(mActivity,WebSolutionSelectActivity.class));
        });

    }

}
