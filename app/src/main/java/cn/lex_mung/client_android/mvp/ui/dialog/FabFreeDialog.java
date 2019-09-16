package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.lex_mung.client_android.R;

public class FabFreeDialog extends Dialog {

    Context context;
    OnClickListener onClickListener;

    ImageView fab_help,fab_custom,fab_close;

    public FabFreeDialog(Context context, OnClickListener onClickListener) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_fab_free, null);
        setContentView(layout);

        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initView();
        setCancelable(false);
    }

    private void initView() {
        fab_help = findViewById(R.id.fab_help);
        fab_custom = findViewById(R.id.fab_custom);
        fab_close = findViewById(R.id.fab_close);

        fab_close.setOnClickListener(v -> {
            dismiss();
            onClickListener.onCloseClick();
        });

        fab_help.setOnClickListener(v -> {
            dismiss();
            onClickListener.onHelpClick();
        });

        fab_custom.setOnClickListener(v -> {
            dismiss();
            onClickListener.onCustomClick();
        });
    }

    public interface OnClickListener {
        void onCloseClick();
        void onHelpClick();
        void onCustomClick();
    }

}
