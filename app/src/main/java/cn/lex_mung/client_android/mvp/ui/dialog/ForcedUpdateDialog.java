package cn.lex_mung.client_android.mvp.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.AppUtils;

public class ForcedUpdateDialog extends Dialog {
    private String tip;
    private String versionName;
    private NumberProgressBar progressBar;
    private TextView tvSize;
    private TextView tvStatus;
    private TextView tvPercent;
    private LinearLayout llLayout;
    private RelativeLayout rlLayout;
    TextView tvClose;

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick();
    }

    public ForcedUpdateDialog(@NonNull Context context, String versionName, String tip) {
        super(context, R.style.alert_dialog);
        this.tip = tip;
        this.versionName = versionName;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_version_dialog);
        initView();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvContent = findViewById(R.id.tv_content);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        tvClose = findViewById(R.id.tv_close);
        tvSize = findViewById(R.id.tv_size);
        tvStatus = findViewById(R.id.tv_status);
        tvPercent = findViewById(R.id.tv_percent);
        progressBar = findViewById(R.id.progress_bar);
        llLayout = findViewById(R.id.ll_layout);
        rlLayout = findViewById(R.id.rl_layout);

        tvTitle.setText(String.format("是否更新到绿豆圈%s版本？", versionName));
        tvContent.setText(tip);
        tvCancel.setText("退出");
        tvConfirm.setText("立即升级");

        tvCancel.setOnClickListener(v -> {
            dismiss();
            AppUtils.exitApp();
        });
        tvConfirm.setOnClickListener(v -> {
            llLayout.setVisibility(View.GONE);
            rlLayout.setVisibility(View.VISIBLE);
            onClickListener.onClick();
        });

        tvClose.setVisibility(View.GONE);
        tvClose.setOnClickListener(v -> {
            dismiss();
            AppUtils.exitApp();
        });
    }

    public void setProgress(int max, int total, String percent) {
        progressBar.setMax(max);
        progressBar.setProgress(total);
        tvPercent.setText(percent);
    }

    public void setSize(String str) {
        tvSize.setText(str);
    }

    public void setStatus(String str) {
        tvStatus.setText(str);
        if(TextUtils.equals("失败",str)){
            tvClose.setVisibility(View.VISIBLE);
        }else{
            tvClose.setVisibility(View.GONE);
        }

    }
}
