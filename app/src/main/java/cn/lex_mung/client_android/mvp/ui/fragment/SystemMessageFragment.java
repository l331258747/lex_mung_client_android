package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lex_mung.client_android.app.decoration.SpacesItemDecoration;
import cn.lex_mung.client_android.mvp.ui.adapter.SystemMessageAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerSystemMessageComponent;
import cn.lex_mung.client_android.di.module.SystemMessageModule;
import cn.lex_mung.client_android.mvp.contract.SystemMessageContract;
import cn.lex_mung.client_android.mvp.presenter.SystemMessagePresenter;

import cn.lex_mung.client_android.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

public class SystemMessageFragment extends BaseFragment<SystemMessagePresenter> implements SystemMessageContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    public static SystemMessageFragment newInstance() {
        return new SystemMessageFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSystemMessageComponent
                .builder()
                .appComponent(appComponent)
                .systemMessageModule(new SystemMessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(smartRefreshLayout);
    }

    @OnClick(R.id.tv_all_read)
    public void onViewClicked() {
        new DefaultDialog(mActivity, dialog -> mPresenter.allSetRead(dialog)
                , "全部标记为已读?"
                , getString(R.string.text_confirm)
                , getString(R.string.text_cancel))
                .show();
    }

    @Override
    public void initRecyclerView(SystemMessageAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, AppUtils.dip2px(mActivity, AppUtils.getXmlDef(mActivity, R.dimen.qb_px_35))));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(SystemMessageAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    public void refreshData() {
        try {
            mPresenter.setPageNum(1);
            mPresenter.getSystemMessageList(false);
        } catch (Exception ignored) {
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
