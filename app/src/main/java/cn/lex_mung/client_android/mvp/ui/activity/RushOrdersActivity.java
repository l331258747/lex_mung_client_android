package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.di.component.DaggerRushOrdersComponent;
import cn.lex_mung.client_android.di.module.RushOrdersModule;
import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import cn.lex_mung.client_android.mvp.presenter.RushOrdersPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.widget.CompletedView;
import cn.lex_mung.client_android.mvp.ui.widget.RushOrdersView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StatusBarUtil;

/**
 * 抢单
 */
public class RushOrdersActivity extends BaseActivity<RushOrdersPresenter> implements RushOrdersContract.View {

    @BindView(R.id.view_rush_orders)
    RushOrdersView rushOrdersView;
    @BindView(R.id.tasks_view)
    CompletedView completedView;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.cl_rush_rush)
    LinearLayout clRushRush;
    @BindView(R.id.cl_rush_error)
    LinearLayout clRushError;
    @BindView(R.id.cl_rush_reply)
    ConstraintLayout clRushReply;

    List<String> noticeItems;
    private Thread Progress;
    private int mTotalProgress = 120;
    private int mCurrentProgress = 0;
    private boolean isStop;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRushOrdersComponent
                .builder()
                .appComponent(appComponent)
                .rushOrdersModule(new RushOrdersModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rush_orders;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.c_ff), 0);

        initTextBanner();
        noticeItems = getbanners();
        completedView.setTotalProgress(mTotalProgress);
        Progress = new Thread(new ProgressRunable());

        isDelete();
        setPosition(1);
    }

    @OnClick({
            R.id.tv_custom_call,
            R.id.tv_lawyer_call
    })
    public void onViewClicked(View view) {
        Intent dialIntent;
        switch (view.getId()) {
            case R.id.tv_custom_call:
                dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
            case R.id.tv_lawyer_call:
                dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));//TODO 律师电话
                startActivity(dialIntent);
                break;
        }
    }

    //TODO 用来测试用的点击变化 之后要删除点击事件
    private void isDelete(){
        rushOrdersView.setItemOnClick(position -> {
            isStop = true;
            viewFlipper.stopFlipping();

            switch (position){
                case 0:
                    clRushRush.setVisibility(View.GONE);
                    clRushError.setVisibility(View.VISIBLE);
                    clRushReply.setVisibility(View.GONE);

                    break;
                case 1:
                    clRushRush.setVisibility(View.VISIBLE);
                    clRushError.setVisibility(View.GONE);
                    clRushReply.setVisibility(View.GONE);

                    addNotice();
                    mCurrentProgress = 0;
                    isStop = false;
                    Progress.start();

                    break;
                case 2:
                    clRushRush.setVisibility(View.GONE);
                    clRushError.setVisibility(View.GONE);
                    clRushReply.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    break;
            }
        });
    }

    //设置进度
    private void setPosition(int i) {
//        isStop = true;
//        viewFlipper.stopFlipping();
//        if(Progress.isInterrupted()){
//            Progress.interrupt();
//        }
//
//        switch (i){
//            case 0:
//                clRushRush.setVisibility(View.GONE);
//                clRushError.setVisibility(View.VISIBLE);
//                clRushReply.setVisibility(View.GONE);
//                break;
//            case 1:
//                clRushRush.setVisibility(View.VISIBLE);
//                clRushError.setVisibility(View.GONE);
//                clRushReply.setVisibility(View.GONE);
//
//                //倒计时开始，律师轮播
//                addNotice();
//                mCurrentProgress = 0;
//                isStop = false;
//                Progress.start();
//
//                break;
//            case 2:
//                clRushRush.setVisibility(View.GONE);
//                clRushError.setVisibility(View.GONE);
//                clRushReply.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                break;
//        }

        rushOrdersView.setProgress(i);//TODO 去除
    }

    public void initTextBanner() {
        viewFlipper.setAutoStart(false);
        viewFlipper.setFlipInterval(2000); // ms
    }

    public List<String> getbanners() {
        List<String> strings = new ArrayList<>();
        strings.add("加工费");
        strings.add("加工费3");
        strings.add("加工费4");
        return strings;
    }

    /**
     * 公告
     */
    public void addNotice() {
        int size = noticeItems.size();
        viewFlipper.removeAllViews();

        if (size == 0) {
            viewFlipper.stopFlipping();
            return;
        }

        if (size == 1) {
            View view = View.inflate(mActivity, R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(noticeItems.get(0));
            viewFlipper.addView(view);
            viewFlipper.stopFlipping();
            return;
        }

        viewFlipper.startFlipping();
        for (int i = 0; i < size; i++) {
            View view = View.inflate(mActivity, R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(noticeItems.get(i));
            viewFlipper.addView(view);
        }
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                if (isStop)
                    return;
                mCurrentProgress += 1;
                completedView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
        if(viewFlipper != null){
            viewFlipper.stopFlipping();
        }
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
    public void onBackPressed() {
        //TODO 抢单订单详情页面
        finish();


    }
}
