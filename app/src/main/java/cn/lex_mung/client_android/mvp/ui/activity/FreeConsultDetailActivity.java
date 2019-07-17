package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerFreeConsultDetailComponent;
import cn.lex_mung.client_android.di.module.FreeConsultDetailModule;
import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailContract;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.presenter.FreeConsultDetailPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetailsAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
//删除
public class FreeConsultDetailActivity extends BaseActivity<FreeConsultDetailPresenter> implements FreeConsultDetailContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_reply_count)
    TextView tvReplyCount;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_acre)
    TextView tvAcre;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private FreeConsultDetailsAdapter freeConsultDetailsAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFreeConsultDetailComponent
                .builder()
                .appComponent(appComponent)
                .freeConsultDetailModule(new FreeConsultDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_free_consult_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
        if (bundleIntent != null) {
            if (bundleIntent.containsKey(BundleTags.ENTITY)) {
                mPresenter.setData((FreeConsultEntity) bundleIntent.getSerializable(BundleTags.ENTITY));
            } else {
                mPresenter.setConsultationId(bundleIntent.getInt(BundleTags.ID));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getFreeConsultDetail();
    }

    @SuppressLint("InflateParams")
    private void initAdapter() {
        freeConsultDetailsAdapter = new FreeConsultDetailsAdapter(mImageLoader);

        freeConsultDetailsAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = freeConsultDetailsAdapter.getItem(position);
            if (entity == null) return;
            mPresenter.setPosition(position);
            bundle.clear();
            bundle.putInt(BundleTags.CONSULTATION_ID, entity.getConsultationId());
            bundle.putInt(BundleTags.LAWYER_ID, entity.getLawyerId());
            bundle.putString(BundleTags.REGION, mPresenter.getRegion());
            launchActivity(new Intent(mActivity, FreeConsultDetailsListActivity.class), bundle);
        });
        freeConsultDetailsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (isFastClick()) return;
            FreeConsultReplyListEntity entity = freeConsultDetailsAdapter.getItem(position);
            if (entity == null) return;
            switch (view.getId()) {
                case R.id.item_tv_consult:
                    bundle.clear();
                    bundle.putInt(BundleTags.ID, entity.getLawyerId());
                    launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
                    break;
            }
        });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableOverScrollBounce(true);
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mPresenter.getPageNum() < mPresenter.getTotalNum()) {
                mPresenter.setPageNum(mPresenter.getPageNum() + 1);
                mPresenter.getFreeConsultReplyList(true);
            } else {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(freeConsultDetailsAdapter);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void setAdapter(List<FreeConsultReplyListEntity> list, boolean isAdd) {
        freeConsultDetailsAdapter.setLawyerId(mPresenter.getEntity().getMemberId());
        if (isAdd) {
            freeConsultDetailsAdapter.addData(list);
            smartRefreshLayout.finishLoadMore();
        } else {
            smartRefreshLayout.finishRefresh();
            freeConsultDetailsAdapter.setNewData(list);
            if (mPresenter.getTotalNum() == mPresenter.getPageNum()) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void refreshReplyCount(int count) {
        if (count == 0) {
            mPresenter.setPageNum(1);
            mPresenter.getFreeConsultReplyList(false);
            return;
        }
        if (mPresenter.getPosition() > -1) {
            FreeConsultReplyListEntity entity = freeConsultDetailsAdapter.getItem(mPresenter.getPosition());
            if (entity == null) return;
            entity.setReplyCount(count + 1);
            freeConsultDetailsAdapter.notifyItemChanged(mPresenter.getPosition(), entity);
        }
    }

    @Override
    public void setName(String memberName) {
        tvName.setText(memberName);
    }

    @Override
    public void setAvatar(String memberIconImage) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(memberIconImage)
                        .imageView(ivAvatar)
                        .isCircle(true)
                        .build());
    }

    @Override
    public void setAvatar(int icAvatar) {
        ivAvatar.setImageResource(icAvatar);
    }

    @Override
    public void setRegion(String region) {
        tvAcre.setText(region);
    }

    @Override
    public void setCategoryName(String categoryName) {
        tvType.setText(categoryName);
    }

    @Override
    public void setContent(String content) {
        tvContent.setText(content);
    }

    @Override
    public void setTime(String s) {
        tvTime.setText(s);
    }

    @Override
    public void setReplyCount(String count) {
        tvReplyCount.setText(count);
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
