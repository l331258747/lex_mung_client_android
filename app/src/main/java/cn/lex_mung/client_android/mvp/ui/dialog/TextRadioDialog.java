package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.adapter.TextRadioAdapter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class TextRadioDialog extends Dialog {

    Context context;

    TextView tv_content, tv_submit,tv_cancel,tv_title;
    RecyclerView recyclerView;

    int pos = -1;

    public TextRadioDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
        this.context = context;
    }

    List<String> lists;

    public TextRadioDialog setLists(List<String> lists){
        this.lists = lists;
        return this;
    }

    String titleStr;

    public TextRadioDialog setTitleStr(String titleStr){
        this.titleStr = titleStr;
        return this;
    }

    String submitStr;

    public TextRadioDialog setSubmitStr(String submitStr) {
        this.submitStr = submitStr;
        return this;
    }

    String cancelStr;

    public TextRadioDialog setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
        return this;
    }

    String contentStr;

    public TextRadioDialog setContent(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }

    String contentHtmlStr;
    public TextRadioDialog setContentHtmlStr(String contentHtmlStr){
        this.contentHtmlStr = contentHtmlStr;
        return this;
    }

    OnClickListener onSubmitClickListener;
    public TextRadioDialog setSubmitOnClickListener(OnClickListener onSubmitClickListener){
        this.onSubmitClickListener = onSubmitClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_text_radio);
        initView();
        setCancelable(false);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_title = findViewById(R.id.tv_title);
        recyclerView = findViewById(R.id.recycler_view);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        if(lists != null && lists.size() > 0){
            pos = 0;
            TextRadioAdapter adapter = new TextRadioAdapter();
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                pos = position;
                adapter.setPosition(position);
                adapter.notifyDataSetChanged();
            });
            recyclerView.setNestedScrollingEnabled(false);
            AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            adapter.setNewData(lists);
        }

        if(!TextUtils.isEmpty(titleStr)){
            tv_title.setText(titleStr);
        }

        if (!TextUtils.isEmpty(submitStr)) {
            tv_submit.setText(submitStr);
        }

        if (!TextUtils.isEmpty(cancelStr)) {
            tv_cancel.setText(cancelStr);
        }

        if (!TextUtils.isEmpty(contentStr)) {
            tv_content.setText(contentStr);
        }

        if (!TextUtils.isEmpty(contentHtmlStr)) {
            StringUtils.setHtml(tv_content,contentHtmlStr);
        }

        tv_submit.setOnClickListener(v -> {
            dismiss();
            if(onSubmitClickListener != null){
                onSubmitClickListener.onClick(pos);
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });

    }

    public interface OnClickListener {
        void onClick(int position);

    }
}
