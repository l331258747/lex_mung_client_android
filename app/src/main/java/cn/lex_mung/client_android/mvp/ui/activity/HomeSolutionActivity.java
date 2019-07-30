package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.di.module.HomeSolutionModule;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeSolutionAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerHomeSolutionComponent;
import cn.lex_mung.client_android.mvp.contract.HomeSolutionContract;
import cn.lex_mung.client_android.mvp.presenter.HomeSolutionPresenter;

import cn.lex_mung.client_android.R;

public class HomeSolutionActivity extends BaseActivity<HomeSolutionPresenter> implements HomeSolutionContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view_1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view_2)
    RecyclerView recyclerView2;
    @BindView(R.id.recycler_view_3)
    RecyclerView recyclerView3;

    HomeSolutionAdapter adapter1;
    HomeSolutionAdapter adapter2;
    HomeSolutionAdapter adapter3;
    List<CommonSolutionEntity> person = new ArrayList<>();
    List<CommonSolutionEntity> criminal = new ArrayList<>();
    List<CommonSolutionEntity> commercial = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeSolutionComponent
                .builder()
                .appComponent(appComponent)
                .homeSolutionModule(new HomeSolutionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_solution;
    }

    @Override
    public void initAdapter(List<CommonSolutionEntity> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getParentType().equals("个人类"))
                person.add(datas.get(i));
            if (datas.get(i).getParentType().equals("商事类"))
                commercial.add(datas.get(i));
            if (datas.get(i).getParentType().equals("刑事类"))
                criminal.add(datas.get(i));

        }
        setDataView(adapter1, recyclerView1, person);
        setDataView(adapter2, recyclerView2, commercial);
        setDataView(adapter3, recyclerView3, criminal);
    }

    public void setDataView(HomeSolutionAdapter itemAdapter, RecyclerView recyclerView, List<CommonSolutionEntity> datas) {
        itemAdapter = new HomeSolutionAdapter(mImageLoader);
        AppUtils.configRecyclerView(recyclerView, new GridLayoutManager(mActivity, 4));
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        itemAdapter.setNewData(datas);

        itemAdapter.setOnItemClickListener((adapter1, view, position)->{
            if (isFastClick()) return;
            CommonSolutionEntity entity = (CommonSolutionEntity) adapter1.getItem(position);
            if (entity == null) return;

            showMessage("id:" + entity.getId() + ",name:" + entity.getAlias());

        });


    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getDatas();
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
