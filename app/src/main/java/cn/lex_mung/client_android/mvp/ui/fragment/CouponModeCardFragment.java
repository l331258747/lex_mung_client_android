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
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerCouponModeCardComponent;
import cn.lex_mung.client_android.di.module.CouponModeCardModule;
import cn.lex_mung.client_android.mvp.contract.CouponModeCardContract;
import cn.lex_mung.client_android.mvp.presenter.CouponModeCardPresenter;
import cn.lex_mung.client_android.mvp.ui.activity.CouponModeActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.CouponModeCardAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class CouponModeCardFragment extends BaseFragment<CouponModeCardPresenter> implements CouponModeCardContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static CouponModeCardFragment newInstance() {
        CouponModeCardFragment fragment = new CouponModeCardFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCouponModeCardComponent
                .builder()
                .appComponent(appComponent)
                .couponModeCardModule(new CouponModeCardModule(this))
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
        mPresenter.setOrgId(getCouponModeActivity().getOrgId());
        mPresenter.onCreate(smartRefreshLayout);
    }

    public CouponModeActivity getCouponModeActivity(){
        return (CouponModeActivity) getActivity();
    }

    @Override
    public void showDetailDialog(String string) {
        new SingleTextDialog(mActivity)
                .setContent(string)
                .setSubmitStr("知道了").show();
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

    @Override
    public void initRecyclerView(CouponModeCardAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(CouponModeCardAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }
}
