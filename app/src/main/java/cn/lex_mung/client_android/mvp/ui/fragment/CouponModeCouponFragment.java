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

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import cn.lex_mung.client_android.mvp.ui.activity.CouponModeActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.CouponModeCouponAdapter2;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerCouponModeCouponComponent;
import cn.lex_mung.client_android.di.module.CouponModeCouponModule;
import cn.lex_mung.client_android.mvp.contract.CouponModeCouponContract;
import cn.lex_mung.client_android.mvp.presenter.CouponModeCouponPresenter;

import cn.lex_mung.client_android.R;

public class CouponModeCouponFragment extends BaseFragment<CouponModeCouponPresenter> implements CouponModeCouponContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static CouponModeCouponFragment newInstance() {
        CouponModeCouponFragment fragment = new CouponModeCouponFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCouponModeCouponComponent
                .builder()
                .appComponent(appComponent)
                .couponModeCouponModule(new CouponModeCouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setRequireTypeId(getCouponModeActivity().getRequireTypeId());
        mPresenter.setLMemberId(getCouponModeActivity().getlMemberId());
        mPresenter.setMemberId(getCouponModeActivity().getMemberId());
        mPresenter.setCouponType(getCouponModeActivity().getSelectedType());
        mPresenter.setCouponId(getCouponModeActivity().getCouponId());

        mPresenter.onCreate(smartRefreshLayout);
    }

    public CouponModeActivity getCouponModeActivity(){
        return (CouponModeActivity) getActivity();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void initRecyclerView(CouponModeCouponAdapter2 adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(CouponModeCouponAdapter2 adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
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
