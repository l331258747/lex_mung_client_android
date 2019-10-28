package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.lex_mung.client_android.R;

public class TextRadioAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    int position;

    public TextRadioAdapter() {
        super(R.layout.item_text_radio);
    }

    public void setPosition(int position){
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);

        if(helper.getLayoutPosition() == position){
            helper.setImageDrawable(R.id.iv_img,ContextCompat.getDrawable(mContext,R.drawable.ic_radio_yes));
        }else{
            helper.setImageDrawable(R.id.iv_img,ContextCompat.getDrawable(mContext,R.drawable.ic_radio_un));
        }
    }
}
