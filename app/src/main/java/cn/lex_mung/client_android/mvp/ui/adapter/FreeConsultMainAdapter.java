package cn.lex_mung.client_android.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.utils.TextViewEllipsize;

public class FreeConsultMainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    TextView tv_content;
    Context context;

    public FreeConsultMainAdapter(Context context) {
        super(R.layout.item_free_main);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        tv_content = helper.getView(R.id.tv_content);
        TextViewEllipsize.setEllipsize(tv_content
                ,"熬枯受淡还告诉阿斯蒂峰峻阿里斯顿借款方拉时代峻"
                ,"查看详情"
                ,ContextCompat.getColor(context,R.color.c_26CD8D));
    }
}