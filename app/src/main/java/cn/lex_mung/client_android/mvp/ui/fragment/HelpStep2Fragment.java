package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerHelpStep2Component;
import cn.lex_mung.client_android.di.module.HelpStep2Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep2Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import cn.lex_mung.client_android.mvp.presenter.HelpStep2Presenter;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.HelpStep2Adapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.HelpStep2View;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class HelpStep2Fragment extends BaseFragment<HelpStep2Presenter> implements HelpStep2Contract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.view_help_step_2)
    HelpStep2View helpStep2View;

    HelpStep2Adapter adapter;

    int typeId = -1;

    public int getTypeId() {
        return typeId;
    }

    public static HelpStep2Fragment newInstance(List<SolutionTypeBean> entitys) {
        HelpStep2Fragment fragment = new HelpStep2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.LIST, (Serializable) entitys);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep2Component
                .builder()
                .appComponent(appComponent)
                .helpStep2Module(new HelpStep2Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step2, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();

        mPresenter.setList((List<SolutionTypeBean>)getArguments().getSerializable(BundleTags.LIST));

        mPresenter.getTabPosition(0);

        helpStep2View.setItemOnClick(position -> {
            mPresenter.getTabPosition(position);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        adapter = new HelpStep2Adapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            typeId = adapter.getItem(position).getSolutionTypeId();
            adapter.setSelection(typeId);
        });
    }

    @Override
    public void setAdapter(List<SolutionTypeChildBean> list) {
        adapter.setNewData(list);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_btn:
                if(typeId == -1){
                    showMessage("请选择事件分类");
                    return;
                }
                ((HelpStepActivity) this.getActivity()).setIndex(2);
                break;
        }
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}
