package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.BuyEquityEvaluateModule;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.EvaluateIntent;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.OrderPrivateLawyersDetailEntity;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import cn.lex_mung.client_android.mvp.ui.widget.EvaluateStarView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

import cn.lex_mung.client_android.di.component.DaggerBuyEquityEvaluateComponent;
import cn.lex_mung.client_android.mvp.contract.BuyEquityEvaluateContract;
import cn.lex_mung.client_android.mvp.presenter.BuyEquityEvaluatePresenter;

import cn.lex_mung.client_android.R;
import me.zl.mvp.utils.CharacterHandler;

public class BuyEquityEvaluateActivity extends BaseActivity<BuyEquityEvaluatePresenter> implements BuyEquityEvaluateContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.iv_lawyer_head)
    ImageView iv_lawyer_head;
    @BindView(R.id.tv_lawyer_name)
    TextView tv_lawyer_name;
    @BindView(R.id.tv_lawyer_area)
    TextView tv_lawyer_area;

    @BindView(R.id.evaluateStarView_major)
    EvaluateStarView evaluateStarView_major;
    @BindView(R.id.evaluateStarView_speed)
    EvaluateStarView evaluateStarView_speed;
    @BindView(R.id.evaluateStarView_attitude)
    EvaluateStarView evaluateStarView_attitude;

    @BindView(R.id.iv_bad)
    ImageView iv_bad;
    @BindView(R.id.iv_commonly)
    ImageView iv_commonly;
    @BindView(R.id.iv_fine)
    ImageView iv_fine;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_btn)
    TextView tv_btn;
    @BindView(R.id.titleView)
    TitleView titleView;

    @BindView(R.id.et_describe)
    EditText et_describe;

    int score;
    EvaluateIntent entity;

    int type;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBuyEquityEvaluateComponent
                .builder()
                .appComponent(appComponent)
                .buyEquityEvaluateModule(new BuyEquityEvaluateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_buy_equity_evaluate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        type = bundleIntent.getInt(BundleTags.TYPE,0);
        entity = (EvaluateIntent) bundleIntent.getSerializable(BundleTags.ENTITY);

        if (!TextUtils.isEmpty(entity.getIconImage())) {
            mImageLoader.loadImage(mActivity
                    , ImageConfigImpl
                            .builder()
                            .url(entity.getIconImage())
                            .isCircle(true)
                            .imageView(iv_lawyer_head)
                            .build());
        } else {
            iv_lawyer_head.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_lawyer_avatar));
        }

        tv_lawyer_name.setText(entity.getLawyerName());
        tv_lawyer_area.setText(entity.getInstitutionName());

        evaluateStarView_major.setNum(5);
        evaluateStarView_major.setClick(true);
        evaluateStarView_speed.setNum(5);
        evaluateStarView_speed.setClick(true);
        evaluateStarView_attitude.setNum(5);
        evaluateStarView_attitude.setClick(true);
        setEvaluateWhole(5);

        et_describe.setFilters(new InputFilter[]{CharacterHandler.emojiFilter});

        if(type == 2){
            tv_tip.setVisibility(View.GONE);
            tv_btn.setText("提交评价");
            titleView.setTitle("服务评价");
        }else{
            tv_tip.setVisibility(View.VISIBLE);
            tv_btn.setText("匿名评价");
            titleView.setTitle("匿名评价");
        }

    }

    @OnClick({R.id.iv_bad, R.id.iv_commonly, R.id.iv_fine, R.id.tv_bad, R.id.tv_commonly, R.id.tv_fine, R.id.tv_btn})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.iv_bad:
            case R.id.tv_bad:
                setEvaluateWhole(1);
                break;
            case R.id.iv_commonly:
            case R.id.tv_commonly:
                setEvaluateWhole(3);
                break;
            case R.id.iv_fine:
            case R.id.tv_fine:
                setEvaluateWhole(5);
                break;
            case R.id.tv_btn:

                if (TextUtils.isEmpty(entity.getOrderId())) {
                    showMessage("单号为空");
                    return;
                }

                if (TextUtils.isEmpty(et_describe.getText().toString())) {
                    showMessage("请输入评价内容");
                    return;
                }

                mPresenter.legalAdviserOrderEvaluate(
                        type,
                        entity.getOrderId(),
                        score,
                        evaluateStarView_major.getNum(),
                        evaluateStarView_speed.getNum(),
                        evaluateStarView_attitude.getNum(),
                        et_describe.getText().toString());
                break;
        }
    }

    public void setEvaluateWhole(int score) {
        iv_bad.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_bad_un));
        iv_commonly.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_commonly_un));
        iv_fine.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine_un));
        switch (score) {
            case 1:
                iv_bad.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_bad));
                break;
            case 3:
                iv_commonly.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_commonly));
                break;
            case 5:
                iv_fine.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine));
                break;
        }
        this.score = score;
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
