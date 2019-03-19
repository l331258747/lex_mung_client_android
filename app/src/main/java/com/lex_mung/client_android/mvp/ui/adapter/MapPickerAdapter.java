package com.lex_mung.client_android.mvp.ui.adapter;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lex_mung.client_android.R;

import java.util.List;

public class MapPickerAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    private int pos;

    public MapPickerAdapter(List<PoiItem> list) {
        super(R.layout.item_map_picker, list);
    }

    public MapPickerAdapter() {
        super(R.layout.item_map_picker);
        pos = -1;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.item_tv_title, item.getTitle());
        helper.setText(R.id.item_tv_address, item.getSnippet());
        if (pos == helper.getLayoutPosition()) {
            helper.setVisible(R.id.item_iv_select, true);
        } else {
            helper.setVisible(R.id.item_iv_select, false);
        }
    }
}