package cn.lex_mung.client_android.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;
import cn.lex_mung.client_android.mvp.ui.activity.SelectResortInstitutionActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.HelpStep4Adapter;
import cn.lex_mung.client_android.mvp.ui.adapter.ResortInstitutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHelpStep4Component;
import cn.lex_mung.client_android.di.module.HelpStep4Module;
import cn.lex_mung.client_android.mvp.contract.HelpStep4Contract;
import cn.lex_mung.client_android.mvp.presenter.HelpStep4Presenter;

import cn.lex_mung.client_android.R;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;

public class HelpStep4Fragment extends BaseFragment<HelpStep4Presenter> implements HelpStep4Contract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    HelpStep4Adapter adapter;

    public static HelpStep4Fragment newInstance() {
        HelpStep4Fragment fragment = new HelpStep4Fragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHelpStep4Component
                .builder()
                .appComponent(appComponent)
                .helpStep4Module(new HelpStep4Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_step4, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();

        mPresenter.getList();
    }


    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        adapter = new HelpStep4Adapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            //TODO 选择

        });
    }

    @Override
    public void setAdapter(List<String> list) {
        adapter.setNewData(list);
    }

    @OnClick({R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_btn:
                showMessage("完结");
                break;
        }
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
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
