package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultDetailsListComponent;
import cn.lex_mung.client_android.di.module.FreeConsultDetailsListModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailsListContract;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultDetailsListPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetailsListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DeleteDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.CharacterHandler;
import me.zl.mvp.utils.DeviceUtils;
//删除
public class FreeConsultDetailsListActivity extends BaseActivity<FreeConsultDetailsListPresenter> implements FreeConsultDetailsListContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.et_reply)
    EditText etReply;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultDetailsListComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultDetailsListModule(new FreeConsultDetailsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_details_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onCreate(bundleIntent.getInt(BundleTags.CONSULTATION_ID)
                , bundleIntent.getInt(BundleTags.LAWYER_ID)
                , bundleIntent.getString(BundleTags.REGION)
                , smartRefreshLayout);
        etReply.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mPresenter.lawyerReply(etReply.getText().toString());
                DeviceUtils.hideSoftKeyboard(etReply);
            }
            return false;
        });
        etReply.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});
    }

    @Override
    public void initRecyclerView(FreeConsultDetailsListAdapter adapter) {
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableOverScrollBounce(true);
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(FreeConsultDetailsListAdapter adapter) {
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setTitle(String format) {
        tvTitle.setText(format);
    }

    @Override
    public void clearInput() {
        etReply.setText("");
    }

    @Override
    public void showDeleteDialog(int consultationReplyId, int position) {
        new DeleteDialog(mActivity
                , dialog -> mPresenter.deleteReply(consultationReplyId, position, dialog)
                , getString(R.string.text_sure_to_delete))
                .show();
    }

    @OnClick(R.id.bt_reply)
    public void onViewClicked() {
        if (isFastClick()) return;
        mPresenter.lawyerReply(etReply.getText().toString());
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
