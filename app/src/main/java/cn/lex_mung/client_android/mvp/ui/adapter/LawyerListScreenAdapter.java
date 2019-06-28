package cn.lex_mung.client_android.mvp.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerListScreenActivity;

import me.zl.mvp.utils.AppUtils;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

public class LawyerListScreenAdapter extends BaseQuickAdapter<LawyerListScreenEntity, BaseViewHolder> {
    private LawyerListScreenActivity activity;
    private boolean isFlag;

    private int[] colors = {R.drawable.round_100_1ec88b_all,
            R.drawable.round_100_f6c41e_all,
            R.drawable.round_100_c270ff_all,
            R.drawable.round_100_f02457_all};

    public LawyerListScreenAdapter(boolean isFlag) {
        super(null);
        this.isFlag = isFlag;
        setMultiTypeDelegate(new MultiTypeDelegate<LawyerListScreenEntity>() {
            @Override
            protected int getItemType(LawyerListScreenEntity item) {
                return item.getIsTile();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.item_lawyer_list_screen_1)
                .registerItemType(1, R.layout.item_lawyer_list_screen_2)
                .registerItemType(2, R.layout.item_lawyer_list_screen_3);
    }

    public void setActivity(LawyerListScreenActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, LawyerListScreenEntity item) {
        try {
            helper.setText(R.id.item_tv_title, item.getText());
            helper.setImageResource(R.id.iv_point,colors[helper.getLayoutPosition() % 4]);
            switch (helper.getItemViewType()) {
                case 0:
                    ((TextView) helper.getView(R.id.item_tv_content)).setHint(String.format(mContext.getString(R.string.text_please_select_1), item.getText()));
                    helper.setText(R.id.item_tv_content, item.getContent());
                    helper.getView(R.id.item_1).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    RecyclerView recyclerView = helper.getView(R.id.recycler_view);
                    recyclerView.setVisibility(View.VISIBLE);
                    AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mContext, 3));
                    LawyerListScreenChildAdapter adapter = new LawyerListScreenChildAdapter(item.getItems(), item.getPos());
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener((adapter1, view1, position) -> {
                        SolutionTypeBean bean = adapter.getItem(position);
                        if (bean == null) return;
                        adapter.setPos(position);
                        activity.setPos(helper.getLayoutPosition());
                        item.setPos(position);
                        item.setId(bean.getId());
                        item.setContent(bean.getText());
                        adapter.notifyDataSetChanged();
                        if ("requireTypeId".equals(item.getPropKey())
                                && helper.getLayoutPosition() + 1 < getItemCount()) {
                            notifyItemChanged(helper.getLayoutPosition() + 1);
                        }
                        if (isFlag) {
                            AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, bean);
                        } else {
                            AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_INSTITUTIONS, bean);
                        }
                    });
                    break;
                case 2:
                    EditText etMinPrice = helper.getView(R.id.item_tv_min_price);
                    EditText etMaxPrice = helper.getView(R.id.item_tv_max_price);
                    etMinPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!TextUtils.isEmpty(s)) {
                                item.setMinPrice(Double.valueOf(s.toString()));
                            }
                            else{
                                item.setMinPrice(0);
                            }
                        }
                    });
                    etMaxPrice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!TextUtils.isEmpty(s)) {
                                item.setMaxPrice(Double.valueOf(s.toString()));
                            }else{
                                item.setMaxPrice(0);
                            }
                        }
                    });
                    if (item.getMinPrice() > 0) {
                        etMinPrice.setText(AppUtils.formatAmount(mContext, item.getMinPrice()));
                    }
                    if (item.getMaxPrice() > 0) {
                        etMaxPrice.setText(AppUtils.formatAmount(mContext, item.getMaxPrice()));
                    }
                    if ("requireTypeId".equals(getData().get(helper.getLayoutPosition() - 1).getPropKey())) {
                        if (getData().get(helper.getLayoutPosition() - 1).getPos() == 0) {
                            etMinPrice.setBackgroundResource(R.drawable.round_100_f4f4f4_all);
                            etMaxPrice.setBackgroundResource(R.drawable.round_100_f4f4f4_all);
                            etMinPrice.setFocusable(false);
                            etMinPrice.setFocusableInTouchMode(false);
                            etMaxPrice.setFocusable(false);
                            etMaxPrice.setFocusableInTouchMode(false);
                        } else {
                            etMinPrice.setBackgroundResource(R.drawable.round_100_ffffff_all_717171);
                            etMaxPrice.setBackgroundResource(R.drawable.round_100_ffffff_all_717171);
                            etMinPrice.setFocusable(true);
                            etMinPrice.setFocusableInTouchMode(true);
                            etMaxPrice.setFocusable(true);
                            etMaxPrice.setFocusableInTouchMode(true);
                        }
                    }
                    break;
            }
        } catch (Exception ignored) {
        }
    }
}
