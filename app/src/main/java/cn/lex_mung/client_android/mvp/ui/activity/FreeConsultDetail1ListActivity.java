package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultDetail1ListComponent;
import cn.lex_mung.client_android.di.module.FreeConsultDetail1ListModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1ListContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultDetail1ListPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1ListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DeleteDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class FreeConsultDetail1ListActivity extends BaseActivity<FreeConsultDetail1ListPresenter> implements FreeConsultDetail1ListContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    boolean isShow;
    int consultationId;
    int lawyerId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultDetail1ListComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultDetail1ListModule(new FreeConsultDetail1ListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_detail1_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setConsultationId(consultationId = bundleIntent.getInt(BundleTags.ID));
        mPresenter.setLawyerId(lawyerId = bundleIntent.getInt(BundleTags.LAWYER_ID));
        mPresenter.setReplyNum(bundleIntent.getInt(BundleTags.NUM));
        isShow = bundleIntent.getBoolean(BundleTags.IS_SHOW,false);
        tvBtn.setText(isShow?"追问":"发布免费文字咨询");
        mPresenter.onCreate(smartRefreshLayout);
    }

    @OnClick({
            R.id.tv_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn:
                if (DataHelper.getBooleanSF(mActivity, DataHelperTags.IS_LOGIN_SUCCESS)) {
                    if(isShow){
                        bundle.clear();
                        bundle.putInt(BundleTags.ID,consultationId);
                        bundle.putInt(BundleTags.LAWYER_ID,lawyerId);
                        launchActivity(new Intent(mActivity, FreeConsultReplyActivity.class),bundle);
                    }else{
                        launchActivity(new Intent(mActivity, FreeConsultActivity.class));
                    }
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }

                break;
        }
    }

    @Override
    public void showDeleteDialog(int consultationReplyId, int position) {
        new DeleteDialog(mActivity
                , dialog -> mPresenter.deleteReply(consultationReplyId, position, dialog)
                , getString(R.string.text_sure_to_delete))
                .show();
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void initRecyclerView(FreeConsultDetail1ListAdapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(FreeConsultDetail1ListAdapter adapter) {
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
        finish();
    }
}
