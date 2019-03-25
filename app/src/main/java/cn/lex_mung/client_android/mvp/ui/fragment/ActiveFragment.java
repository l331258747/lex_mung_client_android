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

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.ActiveAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerActiveComponent;
import cn.lex_mung.client_android.di.module.ActiveModule;
import cn.lex_mung.client_android.mvp.contract.ActiveContract;
import cn.lex_mung.client_android.mvp.presenter.ActivePresenter;

import cn.lex_mung.client_android.R;

import java.util.List;

import javax.inject.Inject;

public class ActiveFragment extends BaseFragment<ActivePresenter> implements ActiveContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ActiveAdapter activeAdapter;

    public static ActiveFragment newInstance(LawsHomePagerBaseEntity entity) {
        ActiveFragment fragment = new ActiveFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerActiveComponent
                .builder()
                .appComponent(appComponent)
                .activeModule(new ActiveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            initAdapter();
            initRecyclerView();
            activeAdapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
            mPresenter.setEntity((LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY));
        }
    }

    private void initAdapter() {
        activeAdapter = new ActiveAdapter(mImageLoader);
        activeAdapter.setOnItemClickListener((adapter, view, position) -> {
//            LawsHomePagerBaseEntity.DynamicInfoBean entity = activeAdapter.getItem(position);
//            if (entity == null) return;
//            switch (entity.getLawyerDynamicType()) {
//                case 2:
//                    bundle.clear();
//                    bundle.putInt(BundleTags.ID, entity.getRelatedId());
//                    launchActivity(new Intent(mActivity, FreeConsultDetailActivity.class), bundle);
//                    break;
//            }
        });
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(activeAdapter);
    }

    @Override
    public void setAdapter(List<LawsHomePagerBaseEntity.DynamicInfoBean> activityInfo) {
        activeAdapter.setNewData(activityInfo);
        activeAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void noDataLayout() {
        activeAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
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
