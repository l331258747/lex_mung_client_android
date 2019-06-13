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

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerServicePriceComponent;
import cn.lex_mung.client_android.di.module.ServicePriceModule;
import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.presenter.ServicePricePresenter;
import cn.lex_mung.client_android.mvp.ui.activity.MyAccountActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ServicePriceAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.CallFieldDialog5;
import cn.lex_mung.client_android.mvp.ui.dialog.CurrencyDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.CurrencyDialog2;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.OnlyTextDialog;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

public class ServicePriceFragment extends BaseFragment<ServicePricePresenter> implements ServicePriceContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    LawsHomePagerBaseEntity lawsHomePagerBaseEntity;

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
            lawsHomePagerBaseEntity = (LawsHomePagerBaseEntity) getArguments().getSerializable(BundleTags.ENTITY);
            if (lawsHomePagerBaseEntity == null) return;
            mPresenter.setEntity(lawsHomePagerBaseEntity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();

        if(isGoCall){
            showTestDialog2();
            isGoCall = false;
        }
    }

    @Override
    public void initRecyclerView(ServicePriceAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToErrorDialog(String s) {
        new CallFieldDialog5(mActivity, dialog -> {
            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
            startActivity(dialIntent);
            dialog.dismiss();

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

    //-----电话

    //查询余额不足
    public void showBalanceNoDialog(ExpertPriceEntity entity){
        showOnlyDialog(entity);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mActivity.runOnUiThread(() -> {
                onlyTextDialog.dismiss();
                bundle.clear();
                bundle.putSerializable(BundleTags.ENTITY,entity);
                launchActivity(new Intent(mActivity, MyAccountActivity.class),bundle);
            });
        }).start();
    }

    OnlyTextDialog onlyTextDialog;
    public void showOnlyDialog(ExpertPriceEntity entity){
        String string = "当前余额剩余通话时长不足%1$s分钟，请充值余额。";
        onlyTextDialog = new OnlyTextDialog(mActivity).setContent(String.format(string,entity.getMinimumDuration()));
        onlyTextDialog.show();
    }

    //查看余额充足
    public void showBalanceYesDialog(ExpertPriceEntity entity){
        new CurrencyDialog2(mActivity,entity)
                .setClickYes(dialog -> {
                    mPresenter.sendCall(entity.getCallCenterNo());
                })
                .setClickNo(dialog -> {
                    bundle.clear();
                    bundle.putSerializable(BundleTags.ENTITY,entity);
                    launchActivity(new Intent(mActivity, MyAccountActivity.class),bundle);
                }).show();
    }


    boolean isGoCall = false;
    @Override
    public void GoCall(String str) {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
        startActivity(dialIntent);
        isGoCall = true;
    }

    public void showTestDialog2(){
        new CurrencyDialog(mActivity)
                .showTitleBg(false)
                .setContent("如问题仍然未解决，您可再次拨打。")
                .setContentLineSpacing(R.dimen.qb_px_20)
                .setContentSize(14)
                .setSubmitStr("已解决")
                .setCannelStr("再次致电")
                .setClickNo(dialog -> {
                    if (lawsHomePagerBaseEntity == null) return;
                        mPresenter.setEntity(lawsHomePagerBaseEntity);
                }).show();
    }
}
