package cn.lex_mung.client_android.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.LawyerListModule;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.ConsultTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListSortScreenAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerLIstFieldScreenAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListRegionScreenAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;
import me.zl.mvp.utils.DeviceUtils;

import cn.lex_mung.client_android.di.component.DaggerLawyerListComponent;
import cn.lex_mung.client_android.mvp.contract.LawyerListContract;
import cn.lex_mung.client_android.mvp.presenter.LawyerListPresenter;

import cn.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class LawyerListActivity extends BaseActivity<LawyerListPresenter> implements LawyerListContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_sort)
    ImageView ivSort;
    @BindView(R.id.tv_field)
    TextView tvField;
    @BindView(R.id.iv_field)
    ImageView ivField;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.iv_region)
    ImageView ivRegion;
    @BindView(R.id.tv_screen)
    TextView tvScreen;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isExpand1 = false;
    private boolean isExpand2 = false;
    private boolean isExpand3 = false;
    private int pos = 0;
    private int position1 = 0;
    private int position2 = 0;
    private int position22 = -1;
    private int position3 = 0;
    private int position33 = -1;

    private EasyDialog easyDialog;

    private LawyerListAdapter lawyerListAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLawyerListComponent
                .builder()
                .appComponent(appComponent)
                .lawyerListModule(new LawyerListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("w_y_shouye_zjzx_detail");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("w_y_shouye_zjzx_detail");
        MobclickAgent.onPause(mActivity);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lawyer_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null
                && bundleIntent.containsKey(BundleTags.ORG_ID)
                && bundleIntent.containsKey(BundleTags.LEVEL)) {
            mPresenter.setOrgId(bundleIntent.getInt(BundleTags.ORG_ID));
            mPresenter.setOrgLevId(bundleIntent.getInt(BundleTags.LEVEL));
        }
        mPresenter.onCreate();
        initAdapter();
        initRecyclerView();
        lawyerListAdapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
        etSearch.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPresenter.setLawyerName(etSearch.getText().toString());
                DeviceUtils.hideSoftKeyboard(etSearch);
            }
            return false;
        });
    }

    private void initAdapter() {
        lawyerListAdapter = new LawyerListAdapter(mImageLoader);
        lawyerListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            LawyerEntity.LawyerBean.ListBean bean = lawyerListAdapter.getItem(position);
            if (bean == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getMemberId());
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                    mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                    mPresenter.getConsultList(true, false);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.setPageNum(1);
                mPresenter.getConsultList(false, false);
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(lawyerListAdapter);
    }

    @OnClick({R.id.tv_search, R.id.ll_sort, R.id.ll_field, R.id.ll_region, R.id.ll_screen})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_search:
                mPresenter.setLawyerName(etSearch.getText().toString());
                DeviceUtils.hideSoftKeyboard(etSearch);
                break;
            case R.id.ll_sort:
                if (!isExpand1) {
                    isExpand1 = true;
                    endAnimator(ivSort);
                    showSelectDialog();
                }
                break;
            case R.id.ll_field:
                if (!isExpand2) {
                    isExpand2 = true;
                    endAnimator(ivField);
                    showSelectFieldDialog();
                }
                break;
            case R.id.ll_region:
                if (!isExpand3) {
                    isExpand3 = true;
                    endAnimator(ivRegion);
                    showSelectRegionDialog();
                }
                break;
            case R.id.ll_screen:
                bundle.clear();
                bundle.putBoolean(BundleTags.FLAG, true);
                bundle.putInt(BundleTags.REGION_ID_1, mPresenter.getRegionId1());
                bundle.putInt(BundleTags.REGION_ID_2, mPresenter.getRegionId2());
                bundle.putSerializable(BundleTags.LIST, (Serializable) mPresenter.getList());
                launchActivity(new Intent(mActivity, LawyerListScreenActivity.class), bundle);
                break;
        }
    }

    @Override
    public void setAdapter(List<LawyerEntity.LawyerBean.ListBean> list, boolean isAdd) {
        if (isAdd) {
            lawyerListAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            smartRefreshLayout.setNoMoreData(false);
            lawyerListAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
            recyclerView.scrollToPosition(0);
            smartRefreshLayout.finishRefresh();
            lawyerListAdapter.setNewData(list);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void setScreenColor(int color) {
        tvScreen.setTextColor(color);
    }

    private void startAnimator(ImageView iv) {
        ObjectAnimator ra = ObjectAnimator.ofFloat(iv, "rotation", 180f, 0f);
        ra.setDuration(100);
        ra.start();
    }

    private void endAnimator(ImageView iv) {
        ObjectAnimator ra = ObjectAnimator.ofFloat(iv, "rotation", 0f, 180f);
        ra.setDuration(100);
        ra.start();
    }

    /**
     * 初始化dialog
     */
    private void initDialog(View layout) {
        easyDialog = new EasyDialog(mActivity)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(AppUtils.getColor(mActivity, R.color.c_00))
                .setLocationByAttachedView(view)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mActivity, R.color.c_00))
                .show();
    }

    /**
     * 关闭dialog
     */
    private void dismiss() {
        if (easyDialog != null) {
            easyDialog.dismiss();
        }
    }

    @SuppressLint("InflateParams")
    public void showSelectDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_recycler_view_dialog, null);
        initDialog(layout);
        RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);
        int pos = position1;
        easyDialog.setOnDialogDismiss(() -> {
            isExpand1 = false;
            startAnimator(ivSort);
        });
        List<ConsultTypeEntity> list = new ArrayList<>();
        list.add(new ConsultTypeEntity(0, getString(R.string.text_synthesis_sort)));
        list.add(new ConsultTypeEntity(1, getString(R.string.text_newest_enter)));
        list.add(new ConsultTypeEntity(2, getString(R.string.text_newest_active)));
        LawyerListSortScreenAdapter lawyerListSortScreenAdapter = new LawyerListSortScreenAdapter(list, pos);
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(lawyerListSortScreenAdapter);
        lawyerListSortScreenAdapter.setOnItemClickListener((adapter, view, position) -> {
            ConsultTypeEntity entity = lawyerListSortScreenAdapter.getItem(position);
            if (entity == null || lawyerListSortScreenAdapter.getPosition() == position) {
                dismiss();
                return;
            }
            position1 = position;
            mPresenter.setSort(entity.getId());
            tvSort.setText(entity.getTypeName());
            lawyerListSortScreenAdapter.notifyDataSetChanged();
            mPresenter.setPageNum(1);
            mPresenter.getConsultList(false, true);
            dismiss();
        });
    }

    @SuppressLint("InflateParams")
    private void showSelectFieldDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_recycler_view_dialog, null);
        initDialog(layout);
        easyDialog.setOnDialogDismiss(() -> {
            isExpand2 = false;
            startAnimator(ivField);
        });

        RecyclerView recyclerView1 = layout.findViewById(R.id.recycler_view_1);
        RecyclerView recyclerView2 = layout.findViewById(R.id.recycler_view_2);

        LawyerLIstFieldScreenAdapter adapter1 = new LawyerLIstFieldScreenAdapter(mPresenter.getFieldList(), position2, true);
        LawyerLIstFieldScreenAdapter adapter2 = new LawyerLIstFieldScreenAdapter(mPresenter.getFieldList().get(position2).getChildren(), position22, false);

        AppUtils.configRecyclerView(recyclerView1, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerView2, new LinearLayoutManager(mActivity));

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

        recyclerView1.scrollToPosition(position2);
        recyclerView2.scrollToPosition(position22);
        adapter1.setOnItemClickListener((adapter3, view, position) -> {
            if (position == 0) {
                if (mPresenter.getFieldId() == 0) {
                    dismiss();
                    return;
                }
                position2 = 0;
                position22 = -1;

                mPresenter.setFieldId(0);
                tvField.setText(R.string.text_all_field);

                mPresenter.setPageNum(1);
                mPresenter.getConsultList(false, true);
                dismiss();
                return;
            }
            BusinessTypeEntity entity = adapter1.getItem(position);
            if (entity == null || position2 == position) {
                return;
            }
            pos = position;
            position22 = -1;
            adapter1.setPosition(position);
            adapter1.notifyDataSetChanged();
            adapter2.setPosition(-1);
            adapter2.setNewData(entity.getChildren());
        });
        adapter2.setOnItemClickListener((adapter3, view, position) -> {
            BusinessTypeEntity entity = adapter2.getItem(position);
            if (entity == null || position22 == position) {
                dismiss();
                return;
            }
            position2 = pos;
            position22 = position;
            adapter2.setPosition(position);
            adapter2.notifyDataSetChanged();

            mPresenter.setFieldId(entity.getBusinessTypeId());
            tvField.setText(entity.getBusinessTypeName());

            mPresenter.setPageNum(1);
            mPresenter.getConsultList(false, true);

            dismiss();
        });
    }

    @SuppressLint("InflateParams")
    private void showSelectRegionDialog() {
        View layout = getLayoutInflater().inflate(R.layout.layout_two_recycler_view_dialog, null);
        initDialog(layout);
        easyDialog.setOnDialogDismiss(() -> {
            isExpand3 = false;
            startAnimator(ivRegion);
        });

        RecyclerView recyclerView1 = layout.findViewById(R.id.recycler_view_1);
        RecyclerView recyclerView2 = layout.findViewById(R.id.recycler_view_2);

        LawyerListRegionScreenAdapter adapter1 = new LawyerListRegionScreenAdapter(mPresenter.getRegionList(), position3, true);
        LawyerListRegionScreenAdapter adapter2 = new LawyerListRegionScreenAdapter(mPresenter.getRegionList().get(position3).getChild(), position33, false);

        AppUtils.configRecyclerView(recyclerView1, new LinearLayoutManager(mActivity));
        AppUtils.configRecyclerView(recyclerView2, new LinearLayoutManager(mActivity));

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

        recyclerView1.scrollToPosition(position3);
        recyclerView2.scrollToPosition(position33);

        adapter1.setOnItemClickListener((adapter3, view, position) -> {
            if (position == 0) {
                if (mPresenter.getRegionId2() == 0) {
                    dismiss();
                    return;
                }
                position3 = 0;
                position33 = -1;

                mPresenter.setRegionId(0, 0);
                tvRegion.setText(R.string.text_all_region);

                mPresenter.setPageNum(1);
                mPresenter.getConsultList(false, true);
                dismiss();
                return;
            }
            RegionEntity entity = adapter1.getItem(position);
            if (entity == null || position3 == position) {
                return;
            }

            pos = position;
            position33 = -1;
            adapter1.setPosition(position);
            adapter1.notifyDataSetChanged();
            adapter2.setPosition(-1);
            adapter2.setNewData(mPresenter.getRegionList().get(position).getChild());
            recyclerView2.scrollToPosition(position33);
        });
        adapter2.setOnItemClickListener((adapter3, view, position) -> {
            RegionEntity entity = adapter2.getItem(position);
            if (entity == null || position33 == position) {
                dismiss();
                return;
            }

            position3 = pos;
            position33 = position;
            adapter2.setPosition(position);
            adapter2.notifyDataSetChanged();

            mPresenter.setRegionId(Objects.requireNonNull(adapter1.getItem(position3)).getRegionId(), entity.getRegionId());
            tvRegion.setText(entity.getName());

            mPresenter.setPageNum(1);
            mPresenter.getConsultList(false, true);

            dismiss();
        });
    }

    @Override
    public void showLoading(@NonNull String message) {
        loading = LoadingDialog.getInstance().init(mActivity, message, false);
        loading.show();
    }

    @Override
    public void hideLoading() {
        if (loading != null
                && loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        AppUtils.makeText(mActivity, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        AppUtils.startActivity(intent);
    }

    @Override
    public void launchActivity(Intent intent, Bundle bundle) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launchActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
