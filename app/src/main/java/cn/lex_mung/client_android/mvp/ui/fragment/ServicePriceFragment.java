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
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.activity.AccountPayActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.ReleaseDemandActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.BusinessConfigurationAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.Dial1Dialog;
import cn.lex_mung.client_android.mvp.ui.dialog.DialDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerServicePriceComponent;
import cn.lex_mung.client_android.di.module.ServicePriceModule;
import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.presenter.ServicePricePresenter;

import cn.lex_mung.client_android.R;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import javax.inject.Inject;

public class ServicePriceFragment extends BaseFragment<ServicePricePresenter> implements ServicePriceContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private BusinessConfigurationAdapter adapter;

    public static ServicePriceFragment newInstance(LawsHomePagerBaseEntity entity) {
        ServicePriceFragment fragment = new ServicePriceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleTags.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerServicePriceComponent
                .builder()
                .appComponent(appComponent)
                .servicePriceModule(new ServicePriceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_price, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
        if (getArguments() != null) {
            LawsHomePagerBaseEntity entity = (LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY);
            if (entity == null) return;
            mPresenter.setEntity(entity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private void initAdapter() {
        adapter = new BusinessConfigurationAdapter(mImageLoader);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            if (mPresenter.isLogin()) {
                BusinessEntity entity = adapter.getItem(position);
                if (entity == null) return;
                if (view.getId() == R.id.item_tv_release) {
                    if (entity.getRequirementType() == 1) {//发需求
                        MobclickAgent.onEvent(mActivity, "w_y__shouye_jjfa_list_fbxq");
                        bundle.clear();
                        bundle.putInt(BundleTags.ID, entity.getRequireTypeId());
                        bundle.putInt(BundleTags.TYPE, entity.getType());
                        bundle.putString(BundleTags.TITLE, entity.getRequireTypeName());
                        bundle.putSerializable(BundleTags.ENTITY, mPresenter.getEntity());
                        launchActivity(new Intent(mActivity, ReleaseDemandActivity.class), bundle);
                    } else {//电话咨询
                        MobclickAgent.onEvent(mActivity, "w_y_shouye_zjzx_detail_boda");
                        mPresenter.expertPrice();
                    }
                }
            } else {
                bundle.clear();
                bundle.putInt(BundleTags.TYPE, 1);
                launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
            }

        });
        adapter.setShow(false);
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<BusinessEntity> data) {
        adapter.setNewData(data);
    }

    @Override
    public void showDialDialog(String string) {
        new DialDialog(mActivity
                , dialog -> mPresenter.sendCall()
                , string)
                .show();
    }

    @Override
    public void showDial1Dialog(String string) {
        new Dial1Dialog(mActivity
                , string)
                .show();
    }

    @Override
    public void showToPayDialog() {
        new DefaultDialog(mActivity
                , dialog -> {
            MobclickAgent.onEvent(mActivity, "w_y_shouye_zjzx_detail_chongzhi");
            launchActivity(new Intent(mActivity, AccountPayActivity.class));
        }
                , getString(R.string.text_call_consult_lack_of_balance)
                , getString(R.string.text_leave_for_top_up)
                , getString(R.string.text_cancel))
                .show();
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
