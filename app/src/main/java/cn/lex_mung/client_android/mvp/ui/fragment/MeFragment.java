package cn.lex_mung.client_android.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerMeComponent;
import cn.lex_mung.client_android.di.module.MeModule;
import cn.lex_mung.client_android.mvp.contract.MeContract;
import cn.lex_mung.client_android.mvp.presenter.MePresenter;
import cn.lex_mung.client_android.mvp.ui.activity.AboutActivity;
import cn.lex_mung.client_android.mvp.ui.activity.EditInfoActivity;
import cn.lex_mung.client_android.mvp.ui.activity.LoginActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MyAccountActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MyCardActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MyLikeActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MyOrderActivity;
import cn.lex_mung.client_android.mvp.ui.activity.OrderCouponActivity;
import cn.lex_mung.client_android.mvp.ui.activity.SettingActivity;
import cn.lex_mung.client_android.mvp.ui.activity.WebActivity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.zl.mvp.base.BaseFragment;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_region)
    TextView tvUserRegion;
    @BindView(R.id.tv_edit_info)
    TextView tvEditInfo;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMeComponent
                .builder()
                .appComponent(appComponent)
                .meModule(new MeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me1, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isCreated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!getUserVisibleHint()) return;
        mPresenter.loginStatusDispose();
    }

    private boolean isCreated = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            mPresenter.loginStatusDispose();
            if(mPresenter.getAboutEntity() == null)
                mPresenter.getAbout();
        }
    }

    @OnClick({
            R.id.tv_edit_info
            , R.id.view_order
            , R.id.view_account
            , R.id.view_coupons
            , R.id.view_like
            , R.id.view_setting
            , R.id.view_newbie_guide
            , R.id.view_about
            , R.id.view_call
            , R.id.view_card
            , R.id.tv_user_name
    })
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_user_name:
                if (mPresenter.isLogin()) {
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class),bundle);
                }
                break;
            case R.id.tv_edit_info:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, EditInfoActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class),bundle);
                }
                break;
            case R.id.view_order:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, MyOrderActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_account:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, MyAccountActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_coupons:
                if (mPresenter.isLogin()) {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, OrderCouponActivity.class), bundle);
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
            case R.id.view_like:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, MyLikeActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.view_setting:
                launchActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.view_newbie_guide:
                if (mPresenter.getAboutEntity() == null) {
                    mPresenter.getAbout();
                    return;
                }
                bundle.clear();
                bundle.putString(BundleTags.URL, mPresenter.getAboutEntity().getGuideUrl());
                bundle.putString(BundleTags.TITLE, getString(R.string.text_newbie_guide));
                bundle.putBoolean(BundleTags.STATE,true);
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
                break;
            case R.id.view_about:
                if (mPresenter.getAboutEntity() == null) {
                    mPresenter.getAbout();
                    return;
                }
                bundle.clear();
                bundle.putSerializable(BundleTags.ENTITY, mPresenter.getAboutEntity());
                launchActivity(new Intent(mActivity, AboutActivity.class), bundle);
                break;
            case R.id.view_call:
                if (mPresenter.getAboutEntity() == null) {
                    mPresenter.getAbout();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse(String.format(getString(R.string.text_tel), mPresenter.getAboutEntity().getKefuPhone()));
                intent.setData(data);
                launchActivity(intent);
                break;
            case R.id.view_card:
                if (mPresenter.isLogin()) {
                    launchActivity(new Intent(mActivity, MyCardActivity.class));
                } else {
                    bundle.clear();
                    bundle.putInt(BundleTags.TYPE, 1);
                    launchActivity(new Intent(mActivity, LoginActivity.class), bundle);
                }
                break;
        }
    }

    @Override
    public void showLoginLayout() {
        tvUserName.setVisibility(View.VISIBLE);
        tvUserRegion.setVisibility(View.VISIBLE);
        tvEditInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginLayout() {
        ivAvatar.setImageResource(R.drawable.ic_avatar);
        tvUserName.setVisibility(View.VISIBLE);
        tvUserRegion.setVisibility(View.VISIBLE);
        tvUserName.setText("立即登录");
        tvUserRegion.setText("时光不回头，当下最重要。");
        tvEditInfo.setVisibility(View.GONE);
    }

    @Override
    public void setAvatar(String iconImage) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(iconImage)
                        .imageView(ivAvatar)
                        .isCircle(true)
                        .build());
    }

    @Override
    public void setName(String memberName) {
        tvUserName.setText(memberName);
    }

    @Override
    public void setRegion(String region) {
        tvUserRegion.setText(region);
    }

    @Override
    public void setOrg(String organizationName) {
    }

    @Override
    public void setAge(String age) {
    }

    @Override
    public void hideOrgLayout() {
    }

    @Override
    public void hideAgeLayout() {
    }

    @Override
    public void setSex(int bg, int color, int icon) {
    }

    @Override
    public void hideSexIcon() {
    }

    @Override
    public void showSexIcon() {
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
