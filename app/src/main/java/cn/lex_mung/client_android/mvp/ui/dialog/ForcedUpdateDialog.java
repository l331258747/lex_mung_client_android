package cn.lex_mung.client_android.mvp.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
        tvSize = findViewById(R.id.tv_size);
        tvStatus = findViewById(R.id.tv_status);
        tvPercent = findViewById(R.id.tv_percent);
        progressBar = findViewById(R.id.progress_bar);
        llLayout = findViewById(R.id.ll_layout);
        rlLayout = findViewById(R.id.rl_layout);

        tvTitle.setText(String.format(getContext().getString(R.string.text_update_version), versionName));
        tvContent.setText(tip);
        tvCancel.setText(getContext().getString(R.string.text_exit));
        tvConfirm.setText(getContext().getString(R.string.text_upgrade_now));

        tvCancel.setOnClickListener(v -> {
            dismiss();
            AppUtils.exitApp();
        });
        tvConfirm.setOnClickListener(v -> {
            llLayout.setVisibility(View.GONE);
            rlLayout.setVisibility(View.VISIBLE);
            onClickListener.onClick();
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
    }
}