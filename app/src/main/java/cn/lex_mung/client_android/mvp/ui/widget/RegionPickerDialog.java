package cn.lex_mung.client_android.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.RegionUtils;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.RegionPickerAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import me.zl.mvp.utils.AppUtils;

public class RegionPickerDialog {
    private Context mContext;

    private List<RegionEntity> list = new ArrayList<>();

    private EasyDialog easyDialog;
    private RegionPickerAdapter provinceAdapter;
    private RegionPickerAdapter cityAdapter;
    private RegionPickerAdapter areaAdapter;

    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvArea;
    private View viewProvince;
    private View viewCity;
    private View viewArea;
    private RecyclerView recyclerView;

    private int posProvince = -1;
    private int posCity = -1;
    private int posArea = -1;

    private onClickLisenter onClickLisenter;

    @SuppressLint("InflateParams")
    public RegionPickerDialog(Context context, View viewBottom,onClickLisenter onClickLisenter) {
        mContext = context;
        this.onClickLisenter = onClickLisenter;
        View layout = ((Activity) mContext).getLayoutInflater().inflate(R.layout.layout_region_picker_dialog, null);
        initDialog(layout, viewBottom);
        initView(layout);
        initAdapter();
        initData();
    }

    /**
     * 初始化dialog
     */
    private void initDialog(View layout, View viewBottom) {
        easyDialog = new EasyDialog(mContext)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(mContext, R.color.c_00))
                .setLocationByAttachedView(viewBottom)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mContext, R.color.c_50));
    }

    /**
     * 初始化view
     *
     * @param layout 布局
     */
    private void initView(View layout) {
        tvProvince = layout.findViewById(R.id.tv_province);
        tvCity = layout.findViewById(R.id.tv_city);
        tvArea = layout.findViewById(R.id.tv_area);
        viewProvince = layout.findViewById(R.id.view_province);
        viewCity = layout.findViewById(R.id.view_city);
        viewArea = layout.findViewById(R.id.view_area);
        recyclerView = layout.findViewById(R.id.recycler_view);
        tvProvince.setOnClickListener(v -> {
            recyclerView.setAdapter(provinceAdapter);
            provinceAdapter.setNewData(list);
            recyclerView.scrollToPosition(posProvince);

            tvProvince.setTextColor(AppUtils.getColor(mContext, R.color.c_ea5514));
            tvCity.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvArea.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            viewProvince.setVisibility(View.VISIBLE);
            viewCity.setVisibility(View.GONE);
            viewArea.setVisibility(View.GONE);
        });
        tvCity.setOnClickListener(v -> {
            recyclerView.setAdapter(cityAdapter);
            cityAdapter.setNewData(list.get(posProvince).getChild());
            recyclerView.scrollToPosition(posCity);

            tvProvince.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvCity.setTextColor(AppUtils.getColor(mContext, R.color.c_ea5514));
            tvArea.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            viewProvince.setVisibility(View.GONE);
            viewCity.setVisibility(View.VISIBLE);
            viewArea.setVisibility(View.GONE);
        });
        tvArea.setOnClickListener(v -> {
            recyclerView.setAdapter(areaAdapter);
            areaAdapter.setNewData(list.get(posProvince).getChild().get(posCity).getChild());
            recyclerView.scrollToPosition(posArea);

            tvProvince.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvCity.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvArea.setTextColor(AppUtils.getColor(mContext, R.color.c_ea5514));
            viewProvince.setVisibility(View.GONE);
            viewCity.setVisibility(View.GONE);
            viewArea.setVisibility(View.VISIBLE);
        });
        layout.findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        provinceAdapter = new RegionPickerAdapter();
        cityAdapter = new RegionPickerAdapter();
        areaAdapter = new RegionPickerAdapter();
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mContext));
        recyclerView.setAdapter(provinceAdapter);

        provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            posProvince = position;
            posCity = -1;
            provinceAdapter.setPos(posProvince);
            provinceAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(cityAdapter);
            cityAdapter.setPos(posCity);
            cityAdapter.setNewData(list.get(posProvince).getChild());

            tvProvince.setText(list.get(posProvince).getName());
            tvCity.setText("请选择");

            tvProvince.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvCity.setTextColor(AppUtils.getColor(mContext, R.color.c_ea5514));
            tvArea.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));

            tvCity.setVisibility(View.VISIBLE);
            tvArea.setVisibility(View.GONE);
            viewProvince.setVisibility(View.GONE);
            viewCity.setVisibility(View.VISIBLE);
            viewArea.setVisibility(View.GONE);
        });
        cityAdapter.setOnItemClickListener((adapter, view, position) -> {
            posCity = position;
            posArea = -1;
            cityAdapter.setPos(posCity);
            cityAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(areaAdapter);
            areaAdapter.setPos(posArea);
            areaAdapter.setNewData(list.get(posProvince).getChild().get(posCity).getChild());

            tvCity.setText(list.get(posProvince).getChild().get(posCity).getName());
            tvArea.setText("请选择");

            tvProvince.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvCity.setTextColor(AppUtils.getColor(mContext, R.color.c_323232));
            tvArea.setTextColor(AppUtils.getColor(mContext, R.color.c_ea5514));

            tvArea.setVisibility(View.VISIBLE);
            viewProvince.setVisibility(View.GONE);
            viewCity.setVisibility(View.GONE);
            viewArea.setVisibility(View.VISIBLE);
        });
        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            posArea = position;
            areaAdapter.setPos(posArea);
            areaAdapter.notifyDataSetChanged();
            tvArea.setText(list.get(posProvince).getChild().get(posCity).getChild().get(posArea).getName());
            onClickLisenter.onClick(tvProvince.getText().toString(),tvCity.getText().toString(),tvArea.getText().toString());
            dismiss();
        });
    }

    public interface onClickLisenter{
        void onClick(String province,String city,String area);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list.addAll(RegionUtils.getInstance(mContext).getList());
        provinceAdapter.setNewData(list);
    }

    /**
     * 显示dialog
     */
    public void show() {
        if (easyDialog != null) {
            easyDialog.show();
        }
    }

    /**
     * 关闭dialog
     */
    public void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }
}
