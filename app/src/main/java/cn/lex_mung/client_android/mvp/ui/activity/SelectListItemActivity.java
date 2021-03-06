package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.SelectListItemModule;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.model.entity.SelectList;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.ui.adapter.SelectListItemAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerSelectListItemComponent;
import cn.lex_mung.client_android.mvp.contract.SelectListItemContract;
import cn.lex_mung.client_android.mvp.presenter.SelectListItemPresenter;

import cn.lex_mung.client_android.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.CERTIFICATION_INFO.CERTIFICATION_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.CERTIFICATION_INFO.EDIT_LAWS_OFFICE;
import static cn.lex_mung.client_android.app.EventBusTags.FEEDBACK_INFO.FEEDBACK_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.FEEDBACK_INFO.FEEDBACK_INFO_TYPE;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static cn.lex_mung.client_android.app.EventBusTags.OTHER_INFO.EDIT_HONOR;
import static cn.lex_mung.client_android.app.EventBusTags.OTHER_INFO.EDIT_QUALIFICATION;
import static cn.lex_mung.client_android.app.EventBusTags.OTHER_INFO.EDIT_SOCIAL;
import static cn.lex_mung.client_android.app.EventBusTags.OTHER_INFO.OTHER_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

public class SelectListItemActivity extends BaseActivity<SelectListItemPresenter> implements SelectListItemContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<SelectList> lists = new ArrayList<>();

    private SelectListItemAdapter adapter;
    private int type;
    private String title;
    private int id;
    private boolean flag;//判断是fragment，还是activity 律师列表进入

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectListItemComponent
                .builder()
                .appComponent(appComponent)
                .selectListItemModule(new SelectListItemModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_list_item;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null
                && bundleIntent.containsKey(BundleTags.LIST)
                && bundleIntent.containsKey(BundleTags.TYPE)) {
            lists.addAll((Collection<? extends SelectList>) bundleIntent.getSerializable(BundleTags.LIST));
            type = bundleIntent.getInt(BundleTags.TYPE);
            title = bundleIntent.getString(BundleTags.TITLE, "");
            id = bundleIntent.getInt(BundleTags.ID, -1);
            flag = bundleIntent.getBoolean(BundleTags.FLAG, false);
        }
        adapter = new SelectListItemAdapter(type, lists);
        switch (type) {
            case 0:
                tvTitle.setText("律师职务");
                break;
            case 1:
                tvTitle.setText("社会职务");
                break;
            case 2:
                tvTitle.setText("荣誉名称");
                break;
            case 3:
                tvTitle.setText("资格证书");
                break;
            case 4:
                tvTitle.setText("选择问题类型");
                break;
            case 5:
                adapter = new SelectListItemAdapter(type, id, lists);
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                break;
        }
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            switch (type) {
                case 0:
                    AppUtils.post(CERTIFICATION_INFO, EDIT_LAWS_OFFICE, adapter.getItem(position));
                    break;
                case 1:
                    AppUtils.post(OTHER_INFO, EDIT_SOCIAL, adapter.getItem(position));
                    break;
                case 2:
                    AppUtils.post(OTHER_INFO, EDIT_HONOR, adapter.getItem(position));
                    break;
                case 3:
                    AppUtils.post(OTHER_INFO, EDIT_QUALIFICATION, adapter.getItem(position));
                    break;
                case 4:
                    AppUtils.post(FEEDBACK_INFO, FEEDBACK_INFO_TYPE, adapter.getItem(position));
                    break;
                case 5:
                    SolutionTypeBean bean = (SolutionTypeBean) adapter.getItem(position);
                    if (bean == null) return;
                    if (id == bean.getId()) {
                        bean.setId(0);
                        bean.setText("");
                    }
                    if (flag) {
                        AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_TYPE, bean);
                    } else {
                        AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_TYPE, bean);
                    }
                    break;
            }
            killMyself();
        });
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
