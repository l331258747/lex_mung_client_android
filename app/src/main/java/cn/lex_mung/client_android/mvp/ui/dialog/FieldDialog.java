package cn.lex_mung.client_android.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageFieldDialogAdapter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

/**
 * 擅长领域对话框
 */
public class FieldDialog extends Dialog {

    private ImageLoader mImageLoader;
    private LawsHomePagerBaseEntity.ChildBean bean;

    public FieldDialog(@NonNull Context context, LawsHomePagerBaseEntity.ChildBean bean, ImageLoader mImageLoader) {
        super(context, R.style.alert_dialog);
        this.bean = bean;
        this.mImageLoader = mImageLoader;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_field_dialog);
        initView();
        setCancelable(false);
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.iv_text);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mImageLoader.loadImage(getContext()
                , ImageConfigImpl
                        .builder()
                        .isCenterCrop(false)
                        .url(bean.getTitleIconImage())
                        .imageView(imageView)
                        .build());
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        PersonalHomePageFieldDialogAdapter fieldDialogAdapter = new PersonalHomePageFieldDialogAdapter(bean.getChild());
        recyclerView.setAdapter(fieldDialogAdapter);
        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
    }
}
