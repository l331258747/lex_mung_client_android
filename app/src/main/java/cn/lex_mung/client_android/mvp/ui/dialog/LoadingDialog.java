package cn.lex_mung.client_android.mvp.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.R;

/**
 * 加载中
 */
public class LoadingDialog {
    private volatile static LoadingDialog INSTANCE;

    //获取单例
    public static LoadingDialog getInstance() {
        if (INSTANCE == null) {
            synchronized (LoadingDialog.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoadingDialog();
                }
            }
        }
        return INSTANCE;
    }

    @SuppressLint("InflateParams")
    public Dialog init(Context context, String msg, boolean flag) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        ConstraintLayout layout = view.findViewById(R.id.dialog_view);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(flag);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        TextView tipText = view.findViewById(R.id.load_text);
        if (!TextUtils.isEmpty(msg)) {
            tipText.setText(msg);
        }
        view.findViewById(R.id.iv_close).setOnClickListener(v -> loadingDialog.dismiss());

        return loadingDialog;
    }
}
