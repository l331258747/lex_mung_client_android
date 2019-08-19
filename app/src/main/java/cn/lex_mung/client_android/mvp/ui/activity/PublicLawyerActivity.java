package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerPublicLawyerComponent;
import cn.lex_mung.client_android.di.module.PublicLawyerModule;
import cn.lex_mung.client_android.mvp.contract.PublicLawyerContract;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.presenter.PublicLawyerPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.HomeLawyerAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.HelpStepDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.RechargeDialog;
import cn.lex_mung.client_android.mvp.ui.widget.RoundImageView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class PublicLawyerActivity extends BaseActivity<PublicLawyerPresenter> implements PublicLawyerContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_equities_bg)
    RoundImageView ivEquitiesBg;
    @BindView(R.id.tv_equities_explain)
    TextView tvEquitiesExplain;
    @BindView(R.id.tv_lawyer_title)
    TextView tvLawyerTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    HomeLawyerAdapter homeLawyerAdapter;
    private int orgId;
    private int levelId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPublicLawyerComponent
                .builder()
                .appComponent(appComponent)
                .publicLawyerModule(new PublicLawyerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_public_lawyer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (bundleIntent != null) {
            orgId = bundleIntent.getInt(BundleTags.ID);
            levelId = bundleIntent.getInt(BundleTags.LEVEL);
        }

        initRecyclerView();

        mPresenter.getEquitiesDetails(orgId,levelId);
        mPresenter.getConsultList(orgId,levelId,false);

    }


    private void initRecyclerView() {
        homeLawyerAdapter = new HomeLawyerAdapter(mImageLoader);
        homeLawyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            LawyerEntity2 entity2 = homeLawyerAdapter.getItem(position);
            if (entity2 == null) return;
            bundle.clear();
            bundle.putInt(BundleTags.ID, entity2.getMemberId());
            launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
        });
        homeLawyerAdapter.setOnItemChildClickListener((adapter1, view, position)->{
            if (isFastClick()) return;
            LawyerEntity2 entity2 = homeLawyerAdapter.getItem(position);
            if (entity2 == null) return;
            showDialog(entity2.getMobile());
        });


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableOverScrollBounce(true);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                        mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                        mPresenter.getConsultList(orgId,levelId,true);
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });

        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(homeLawyerAdapter);
    }

    public void showDialog(String phone){
        if(TextUtils.isEmpty(phone)) return;
        new HelpStepDialog(mActivity,
                dialog -> {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(dialIntent);
                }).setContent("公益律师为社会公共资源，意在帮助更多有需要的人可以享受免费的法律服务，如果您有更多法律诉求请通过找律师板块进行咨询。")
                .setTvTitle("公益律师服务说明")
                .setIvTitle(R.drawable.ic_public_lawyer)
                .setCannelStr("取消")
                .setSubmitStr("联系公益律师").show();

    }


    @Override
    public void setEquitiesDetail(EquitiesDetailsEntity entity) {
        if(!TextUtils.isEmpty(entity.getImage())){
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(entity.getImage())
                            .isCenterCrop(false)
                            .imageView(ivEquitiesBg)
                            .build());
        }else{
            ivEquitiesBg.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_lawyer_avatar));
        }

        tvEquitiesExplain.setText(entity.getRightsInterpret());
    }

    List<LawyerEntity2> list2;
    @Override
    public void setLawyerAdapter(List<LawyerEntity2> list,boolean isAdd) {
        recyclerView.setVisibility(View.VISIBLE);
        tvLawyerTitle.setVisibility(View.VISIBLE);
        if (isAdd) {
            list2.addAll(list);
            homeLawyerAdapter.setNewData(list2);
            smartRefreshLayout.finishLoadMore();
        } else {
            list2 = list;
            homeLawyerAdapter.setNewData(list2);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void hideLawyerLayout() {
        recyclerView.setVisibility(View.GONE);
        tvLawyerTitle.setVisibility(View.GONE);
    }

    @Override
    public void setLawyerTitle(String str) {
        tvLawyerTitle.setText(str);
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
