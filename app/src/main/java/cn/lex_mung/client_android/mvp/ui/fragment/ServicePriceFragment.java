package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.activity.AccountPayActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ServicePriceAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog2;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog3;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog4;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog5;
import cn.lex_mung.client_android.mvp.ui.dialog.Dial1Dialog;
import cn.lex_mung.client_android.mvp.ui.dialog.DialDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerServicePriceComponent;
import cn.lex_mung.client_android.di.module.ServicePriceModule;
import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.presenter.ServicePricePresenter;

import cn.lex_mung.client_android.R;

import com.umeng.analytics.MobclickAgent;

public class ServicePriceFragment extends BaseFragment<ServicePricePresenter> implements ServicePriceContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

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

    @Override
    public void showDialDialog(ExpertPriceEntity entity) {
//        new DialDialog(mActivity
//                , dialog -> mPresenter.sendCall()
//                , string)
//                .show();

//        new CallFieldDialog(mActivity
//                ,dialog -> mPresenter.sendCall()
//        ,string,"立即拨打",false).show();

        new CallFieldDialog2(mActivity
                , dialog -> {
            mPresenter.sendCall();
            dialog.dismiss();
        }
                , entity).show();
    }

    @Override
    public void showDial1Dialog(String string) {
//        new Dial1Dialog(mActivity
//                , string)
//                .show();
        new CallFieldDialog3(mActivity,string,dialog -> {
            new CallFieldDialog4(mActivity,"现在关闭将无法联系律师\n是否继续关闭").show();
            dialog.dismiss();
        }).show();
    }

    @Override
    public void initRecyclerView(ServicePriceAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToPayDialog(String s) {
//        new DefaultDialog(mActivity
//                , dialog -> {
//            MobclickAgent.onEvent(mActivity, "w_y_shouye_zjzx_detail_chongzhi");
//            launchActivity(new Intent(mActivity, AccountPayActivity.class));
//        }
//                , getString(R.string.text_call_consult_lack_of_balance)
//                , getString(R.string.text_leave_for_top_up)
//                , getString(R.string.text_cancel))
//                .show();
        new CallFieldDialog(mActivity, dialog -> {
            MobclickAgent.onEvent(mActivity, "w_y_shouye_zjzx_detail_chongzhi");
            launchActivity(new Intent(mActivity, AccountPayActivity.class));
        }, s, "充值").show();
    }

    @Override
    public void showToErrorDialog(String s) {
        new CallFieldDialog5(mActivity, dialog -> {
           //TODO 电话
//            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//            startActivity(dialIntent);
//            dialog.dismiss();

        }, s, "联系客服").show();
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
