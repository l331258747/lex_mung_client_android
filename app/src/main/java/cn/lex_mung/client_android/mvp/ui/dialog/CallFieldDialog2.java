package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

/**
 * 立即拨打dialog
 */
public class CallFieldDialog2 extends Dialog {

    private CallFieldDialog2.OnClickListener clickListener;
    private ExpertPriceEntity entity;
    private Context context;

    public interface OnClickListener {
        void onClick(CallFieldDialog2 dialog);
    }

    public CallFieldDialog2(@NonNull Context context, CallFieldDialog2.OnClickListener onClickListener, ExpertPriceEntity entity) {
        super(context, R.style.alert_dialog);
        this.clickListener = onClickListener;
        this.entity = entity;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_field_dialog2);
        initView();
        setCancelable(false);
    }

    private void initView() {
        TextView tvContent = findViewById(R.id.tv_content);
        TextView tvContent2 = findViewById(R.id.tv_content2);
        TextView tvContent3 = findViewById(R.id.tv_content3);
        String string = "律师咨询价格为%1$s元/%2$s";
        String string2 = "您拥有%1$s的专属权益，优惠后价格为<font color=\"#3dd790\">%2$s元/%3$s</font>";
        String string3 = "您的通话我们将按照秒为单位进行计费，前%1$s免费通话，您当前账户余额可通话时长%2$s";
        StringUtils.setHtml(tvContent,String.format(string,AppUtils.formatAmount(context, entity.getLawyerPrice()),entity.getPriceUnit()));
        if (!TextUtils.isEmpty(entity.getOrgnizationName())) {//有权益
            tvContent2.setVisibility(View.VISIBLE);
            StringUtils.setHtml(tvContent2,String.format(string2,entity.getOrgnizationName(),AppUtils.formatAmount(context, entity.getFavorablePrice()),entity.getPriceUnit()));
            StringUtils.setHtml(tvContent3,String.format(string3,entity.getFreeTime(),entity.getFavorableTimeLen()));
        }else{
            tvContent2.setVisibility(View.GONE);
            StringUtils.setHtml(tvContent3,String.format(string3,entity.getFreeTime(),entity.getOriginTimeLen()));
        }

        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        TextView btn = findViewById(R.id.tv_btn);
        btn.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        });
    }
}
