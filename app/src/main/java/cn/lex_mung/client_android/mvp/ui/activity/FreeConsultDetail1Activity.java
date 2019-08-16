package cn.lex_mung.client_android.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultDetail1Component;
import cn.lex_mung.client_android.di.module.FreeConsultDetail1Module;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1Contract;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultDetail1Presenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1Adapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EmptyView2;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
import cn.lex_mung.client_android.utils.GsonUtil;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

public class FreeConsultDetail1Activity extends BaseActivity<FreeConsultDetail1Presenter> implements FreeConsultDetail1Contract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    ImageView iv_head;
    TextView tv_name,tv_area,tv_type,tv_content,tv_comment,tv_time,tv_basic_info;
    LinearLayout ll_empty;
    EmptyView2 emptyView2;
    RecyclerView recycler_view_lawyer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultDetail1Component
                .builder()
                .appComponent(appComponent)
                .freeConsultDetail1Module(new FreeConsultDetail1Module(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_detail1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        View layout = getLayoutInflater().inflate(R.layout.layout_free_consult_detail_title, null);
        getTitleView(layout);
        mPresenter.setTitleLayout(layout);
        mPresenter.setConsultationId(bundleIntent.getInt(BundleTags.ID,0));
        mPresenter.setMe(bundleIntent.getBoolean(BundleTags.IS_SHOW,false));
        mPresenter.onCreate(smartRefreshLayout);
    }

    public void getTitleView(View layout){
        iv_head = layout.findViewById(R.id.iv_head);
        tv_name = layout.findViewById(R.id.tv_name);
        tv_area = layout.findViewById(R.id.tv_area);
        tv_type = layout.findViewById(R.id.tv_type);
        tv_content = layout.findViewById(R.id.tv_content);
        tv_comment = layout.findViewById(R.id.tv_comment);
        tv_basic_info = layout.findViewById(R.id.tv_basic_info);
        tv_time = layout.findViewById(R.id.tv_time);
        ll_empty = layout.findViewById(R.id.ll_empty);
        emptyView2 = layout.findViewById(R.id.emptyView2);
        recycler_view_lawyer = layout.findViewById(R.id.recycler_view_lawyer);


        emptyView2.getBtn().setOnClickListener(v -> {
            String str = DataHelper.getStringSF(mActivity,DataHelperTags.QUICK_URL);
            HomeChildEntity entity = GsonUtil.convertString2Object(str,HomeChildEntity.class);
            if(!TextUtils.isEmpty(str) && entity != null){
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getJumpurl());
                bundle.putString(BundleTags.TITLE, entity.getTitle());
                if(entity.getShowShare() == 1){
                    bundle.putBoolean(BundleTags.IS_SHARE, true);
                    bundle.putString(BundleTags.SHARE_URL, entity.getShareUrl());
                    bundle.putString(BundleTags.SHARE_TITLE, entity.getShareTitle());
                    bundle.putString(BundleTags.SHARE_DES, entity.getShareDescription());
                    bundle.putString(BundleTags.SHARE_IMAGE, entity.getShareImg());
                }
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            }
        });
    }

    public void setData(FreeConsultEntity entity){
        if(!TextUtils.isEmpty(entity.getMemberIconImage())){
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(entity.getMemberIconImage())
                            .imageView(iv_head)
                            .isCircle(true)
                            .build());
        }else{
            iv_head.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_avatar));
        }


        tv_name.setText(entity.getMemberName());
        tv_area.setText(entity.getRegion());
        tv_type.setText(entity.getCategoryName());
        tv_content.setText(entity.getContent());
        tv_comment.setText(entity.getReplyCountStr());
        tv_time.setText(entity.getDateAddedStr());
        tv_basic_info.setText(entity.getCategoryNameTitle());
    }

    @Override
    public void initRecyclerView(FreeConsultDetail1Adapter adapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
//        adapter.setEmptyView(R.layout.layout_loading_view, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void setEmptyView(boolean isShow) {
        ll_empty.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    public void setLawyerList(LawyerListAdapter lawyerListAdapter){
        AppUtils.configRecyclerView(recycler_view_lawyer, new LinearLayoutManager(mActivity));
        recycler_view_lawyer.setAdapter(lawyerListAdapter);
    }

    @Override
    public Activity getActivity() {
        return mActivity;
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

    @Override
    public void onResume() {
        super.onResume();
        BuryingPointHelp.getInstance().onActivityResumed(mActivity, "free_test_detail_page",getPair());
    }

    @Override
    public void onPause() {
        super.onPause();
        BuryingPointHelp.getInstance().onActivityPaused(mActivity, "free_test_detail_page",getPair());
    }
}
