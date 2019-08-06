package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

/**
 * 专家咨询 余额充足对话框
 */
public class CurrencyDialog2 extends Dialog {
    Context context;
    ExpertPriceEntity entity;

    public CurrencyDialog2(@NonNull Context context,ExpertPriceEntity entity) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.entity = entity;
    }

    OnClickListener onClickListenerYes;

    public CurrencyDialog2 setClickYes(OnClickListener onClickListenerYes) {
        this.onClickListenerYes = onClickListenerYes;
        return this;
    }

    OnClickListener onClickListenerNo;

    public CurrencyDialog2 setClickNo(OnClickListener onClickListenerNo) {
        this.onClickListenerNo = onClickListenerNo;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_expert_call2);
        initView();
        setCancelable(false);
    }

    private void initView() {

        TextView tv_content1 = findViewById(R.id.tv_content1);
        TextView tv_content2 = findViewById(R.id.tv_content2);
        TextView tv_content3 = findViewById(R.id.tv_content3);

        TextView tv_title_txt = findViewById(R.id.tv_title_txt);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_submit = findViewById(R.id.tv_submit);
        ImageView iv_close = findViewById(R.id.iv_close);

//        String string = "律师咨询价格为%1$s%2$s/%3$s";
//        String string1 = "1.【律师全名】律师咨询价格为【通话单价】，您拥有【权益组织】的专属权益，优惠后的价格为【优惠价格】，当前余额可通话【通话时长】，通话时长超过【通话时长】时，系统将自行中断通话，请规划好通话时长，确保余额充足。";
//        String string11 = "1.【律师全名】律师咨询价格为【通话单价】，您的余额可通话【通话时长】，通话时长超过【通话时长】时，系统将自行中断通话，请规划好通话时长，确保余额充足。";
//        String string2 = "2.通话时长不足【保底时长】分钟按【保底时长】分钟计算。";

        String string1 = "1.%1$s律师咨询价格为%2$s，您拥有%3$s的专属权益，优惠后的价格为%4$s，当前余额可通话%5$s，通话时长超过%6$s时，系统将自行中断通话，请规划好通话时长，确保余额充足。";
        String string11 = "1.%1$s律师咨询价格为%2$s，您的余额可通话%3$s，通话时长超过%4$s时，系统将自行中断通话，请规划好通话时长，确保余额充足。";
        String string2 = "2.通话时长不足%1$s分钟按%2$s分钟计算。";
        String string3 = "3.更多细则请查阅<font color=\"#27CB90\">《绿豆圈专家咨询细则》</font>";

        if (!TextUtils.isEmpty(entity.getOrgnizationName())) {//有权益
            tv_content1.setText(String.format(string1,
                    entity.getLawyerName(),
                    entity.getPriceStr(),
                    entity.getOrgnizationName(),
                    entity.getOrgnizationPriceStr(),
                    entity.getFavorableTimeLen(),
                    entity.getFavorableTimeLen()));
        }else{
            tv_content1.setText(String.format(string11,
                    entity.getLawyerName(),
                    entity.getPriceStr(),
                    entity.getOriginTimeLen(),
                    entity.getOriginTimeLen()));
        }
        tv_content2.setText(String.format(string2,
                entity.getMinimumDuration(),
                entity.getMinimumDuration()));
        StringUtils.setHtml(tv_content3, string3);

        String stringTitle = "您正在向%1$s\n发起专家咨询服务";
        tv_title_txt.setText(String.format(stringTitle,entity.getLawyerName()));


        tv_cancel.setOnClickListener(v -> {
            if (onClickListenerNo != null) {
                onClickListenerNo.onClick(this);
            }
            dismiss();
        });

        tv_submit.setOnClickListener(v -> {
            if (onClickListenerYes != null) {
                onClickListenerYes.onClick(this);
            }
            dismiss();
        });

        iv_close.setOnClickListener(v -> dismiss());

        tv_content3.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(entity.getAgreementUrl())){
                Intent intent = new Intent(context, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(BundleTags.URL, entity.getAgreementUrl());
                bundle.putString(BundleTags.TITLE, "绿豆圈专家咨询细则");
                intent.putExtras(bundle);
                AppUtils.startActivity(intent);
            }
        });
    }


    public interface OnClickListener {
        void onClick(CurrencyDialog2 dialog);
    }
}
