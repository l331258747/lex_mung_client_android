package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.AppUtils;

//专家咨询拨打电话 返回弹窗
public class CurrencyDialog extends Dialog {
    Context context;

    TextView tv_content, tv_cancel, tv_submit,tv_content2,tv_title_txt;
    ImageView iv_close,iv_title_bg;


    public CurrencyDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    OnClickListener onClickListenerYes;
    public CurrencyDialog setClickYes(OnClickListener onClickListenerYes){
        this.onClickListenerYes = onClickListenerYes;
        return this;
    }

    OnClickListener onClickListenerNo;
    public CurrencyDialog setClickNo(OnClickListener onClickListenerNo){
        this.onClickListenerNo = onClickListenerNo;
        return this;
    }

    int titleBg;
    public CurrencyDialog setTitleBg(int resourceId){
        this.titleBg = resourceId;
        return this;
    }

    boolean showTitleBg = true;
    public CurrencyDialog showTitleBg(boolean showTitleBg){
        this.showTitleBg = showTitleBg;
        return this;
    }

    String titleTxt;
    public CurrencyDialog setTitleTxt(String titleTxt){
        this.titleTxt = titleTxt;
        return this;
    }

    String cancelStr;
    public CurrencyDialog setCannelStr(String cancelStr){
        this.cancelStr = cancelStr;
        return this;
    }

    String submitStr;
    public CurrencyDialog setSubmitStr(String submitStr){
        this.submitStr = submitStr;
        return this;
    }

    String contentStr;
    public CurrencyDialog setContent(String contentStr){
        this.contentStr = contentStr;
        return this;
    }

    int contentSize;
    public CurrencyDialog setContentSize(int contentSize){
        this.contentSize = contentSize;
        return this;
    }

    int contentLineSpacing;
    public CurrencyDialog setContentLineSpacing(int contentLineSpacing){
        this.contentLineSpacing = contentLineSpacing;
        return this;
    }

    String contentStr2;
    public CurrencyDialog setContent2(String contentStr2){
        this.contentStr2 = contentStr2;
        return this;
    }
    int content2Size;
    public CurrencyDialog setContent2Size(int content2Size){
        this.content2Size = content2Size;
        return this;
    }

    int content2LineSpacing;
    public CurrencyDialog setContent2LineSpacing(int content2LineSpacing){
        this.content2LineSpacing = content2LineSpacing;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_expert_call);
        initView();
        setCancelable(false);
    }

    private void initView() {
        iv_title_bg = findViewById(R.id.iv_title_bg);
        tv_title_txt = findViewById(R.id.tv_title_txt);
        tv_content = findViewById(R.id.tv_content);
        tv_content2 = findViewById(R.id.tv_content2);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_submit = findViewById(R.id.tv_submit);
        iv_close = findViewById(R.id.iv_close);

        if(titleBg > 0){
            iv_title_bg.setBackground(ContextCompat.getDrawable(context,titleBg));
        }

        if(!showTitleBg){
            iv_title_bg.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(titleTxt)){
            tv_title_txt.setText(titleTxt);
        }

        if(!TextUtils.isEmpty(cancelStr)){
            tv_cancel.setText(cancelStr);
        }

        if(!TextUtils.isEmpty(submitStr)){
            tv_submit.setText(submitStr);
        }

        if(!TextUtils.isEmpty(contentStr)){
            tv_content.setText(contentStr);
        }

        if(contentSize > 0){
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
        }

        if(contentLineSpacing > 0){
            tv_content.setLineSpacing(AppUtils.getDimens(context,contentLineSpacing),1);
        }

        if(!TextUtils.isEmpty(contentStr2)){
            tv_content2.setText(contentStr2);
            tv_content2.setVisibility(View.VISIBLE);
        }

        if(content2Size > 0){
            tv_content2.setTextSize(TypedValue.COMPLEX_UNIT_SP, content2Size);
        }

        if(content2LineSpacing > 0){
            tv_content2.setLineSpacing(AppUtils.getDimens(context,content2LineSpacing),1);
        }

        tv_cancel.setOnClickListener(v -> {
            if(onClickListenerNo != null){
                onClickListenerNo.onClick(this);
            }
            dismiss();
        });

        tv_submit.setOnClickListener(v -> {
            if(onClickListenerYes != null){
                onClickListenerYes.onClick(this);
            }
            dismiss();
        });

        iv_close.setOnClickListener(v -> dismiss());
    }


    public interface OnClickListener {
        void onClick(CurrencyDialog dialog);
    }
}
