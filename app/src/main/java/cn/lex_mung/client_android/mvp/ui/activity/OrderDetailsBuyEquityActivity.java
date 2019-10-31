package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.component.DaggerOrderDetailsBuyEquityComponent;
import cn.lex_mung.client_android.di.module.OrderDetailsBuyEquityModule;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsBuyEquityContract;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.LegalAdviserOrderDetailEntity;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsBuyEquityPresenter;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.TextRadioDialog;
import cn.lex_mung.client_android.mvp.ui.widget.EvaluateStarView;
import cn.lex_mung.client_android.mvp.ui.widget.OrderDetailView;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;
import me.zl.mvp.utils.StatusBarUtil;

import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_BUY_EQUITY_DETAIL;

public class OrderDetailsBuyEquityActivity extends BaseActivity<OrderDetailsBuyEquityPresenter> implements OrderDetailsBuyEquityContract.View {

    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.orderDetailView)
    OrderDetailView orderDetailView;

    @BindView(R.id.ll_top_message)
    LinearLayout ll_top_message;
    @BindView(R.id.tv_top_message)
    TextView tv_top_message;

    @BindView(R.id.group_lawyer)
    Group groupLawyer;
    @BindView(R.id.view_lawyer)
    View viewLawyer;
    @BindView(R.id.iv_head_tip)
    ImageView ivHeadTip;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_content)
    TextView tvNameContent;
    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.ll_info_no)
    LinearLayout ll_info_no;
    @BindView(R.id.tv_info_no)
    TextView tv_info_no;

    @BindView(R.id.ll_info_time)
    LinearLayout ll_info_time;
    @BindView(R.id.tv_info_time)
    TextView tv_info_time;

    @BindView(R.id.ll_info_server_name)
    LinearLayout ll_info_server_name;
    @BindView(R.id.tv_info_server_name)
    TextView tv_info_server_name;

    @BindView(R.id.ll_info_order_status)
    LinearLayout ll_info_order_status;
    @BindView(R.id.tv_info_order_status)
    TextView tv_info_order_status;

    @BindView(R.id.ll_info_release_user)
    LinearLayout ll_info_release_user;
    @BindView(R.id.tv_info_release_user)
    TextView tv_info_release_user;

    @BindView(R.id.ll_info_server_type)
    LinearLayout ll_info_server_type;
    @BindView(R.id.tv_info_server_type)
    TextView tv_info_server_type;

    @BindView(R.id.ll_info_talk_time)
    LinearLayout ll_info_talk_time;
    @BindView(R.id.tv_info_talk_time)
    TextView tv_info_talk_time;

    @BindView(R.id.ll_info_talk_record)
    LinearLayout ll_info_talk_record;
    @BindView(R.id.tv_info_talk_record)
    TextView tv_info_talk_record;

    @BindView(R.id.ll_info_meet_time)
    LinearLayout ll_info_meet_time;
    @BindView(R.id.tv_info_meet_time)
    TextView tv_info_meet_time;

    @BindView(R.id.ll_info_meet_duration)
    LinearLayout ll_info_meet_duration;
    @BindView(R.id.tv_info_meet_duration)
    TextView tv_info_meet_duration;

    @BindView(R.id.ll_info_meet_place)
    LinearLayout ll_info_meet_place;
    @BindView(R.id.tv_info_meet_place)
    TextView tv_info_meet_place;

    @BindView(R.id.fl_evaluate_info)
    FrameLayout fl_evaluate_info;

    @BindView(R.id.evaluateStarView_major)
    EvaluateStarView evaluateStarView_major;
    @BindView(R.id.evaluateStarView_speed)
    EvaluateStarView evaluateStarView_speed;
    @BindView(R.id.evaluateStarView_attitude)
    EvaluateStarView evaluateStarView_attitude;
    @BindView(R.id.tv_evaluate_info_content)
    TextView tv_evaluate_info_content;
    @BindView(R.id.iv_evaluate_whole)
    ImageView iv_evaluate_whole;
    @BindView(R.id.tv_evaluate_whole)
    TextView tv_evaluate_whole;

    @BindView(R.id.tv_btn_left)
    TextView tv_btn_left;
    @BindView(R.id.tv_btn_right)
    TextView tv_btn_right;

    @BindView(R.id.fl_500)
    FrameLayout fl_500;
    @BindView(R.id.tv_500_info_title)
    TextView tv_500_info_title;
    @BindView(R.id.tv_500_info_price)
    TextView tv_500_info_price;
    @BindView(R.id.tv_500_info_content)
    TextView tv_500_info_content;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsBuyEquityComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsBuyEquityModule(new OrderDetailsBuyEquityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details_buy_equity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleView.getTitleTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
        StatusBarUtil.setTransparentForImageView(mActivity, null);

        int statusBarHeight = DeviceUtils.getStatusBarHeight(mActivity);
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
        lp1.topMargin = statusBarHeight;
        titleView.setLayoutParams(lp1);

        if (bundleIntent != null) {
            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
        }

        String[] strs = {"预约律师", "优选律师", "律师回电", "评价服务"};
        orderDetailView.initView(strs);

        mPresenter.legalAdviserOrderDetail();
    }

    public void setOrderDetailView(int index) {
        if (index == -1) {
            orderDetailView.setProgress(0);
        } else {
            orderDetailView.setProgress(index);
        }
    }

    @Override
    public void setEntity(LegalAdviserOrderDetailEntity entity) {

        //124电话咨询   订单编号 订单日期  服务名称  订单状态  发布用户  服务类型  （通话时长  通话记录）
        // 125合同文书  订单编号 订单日期  服务名称  订单状态  服务类型
        // 126线下见面  订单编号 订单日期  服务名称  订单状态  服务类型  （见面时间  见面时长  见面地点）

        if (entity.getRequireTypeId() == 125) {
            //orderStatus	订单状态：1待接单（空） 2待服务（1） 3服务中（1） 4待确认（1） 5待评价（3） 6已完成（3） 7待处理（3） 8已关闭（3） 9已取消（0）
            int isReceipt;
            int orderStatus = entity.getReceiptStatus();

            if (orderStatus == 5 || orderStatus == 6 || orderStatus == 7 || orderStatus == 8) {
                isReceipt = 3;
            } else if (orderStatus == 2 || orderStatus == 3 || orderStatus == 4) {
                isReceipt = 1;
            } else {
                isReceipt = 0;
            }

            setRightTv(entity.getOrderId(), "", isReceipt);
        }

        //订单编号
        if (!TextUtils.isEmpty(entity.getOrderId())) {
            ll_info_no.setVisibility(View.VISIBLE);
            tv_info_no.setText(entity.getOrderId());
        } else {
            ll_info_no.setVisibility(View.GONE);
        }
        //订单日期
        if (!TextUtils.isEmpty(entity.getCreateDate())) {
            ll_info_time.setVisibility(View.VISIBLE);
            tv_info_time.setText(entity.getCreateDate());
        } else {
            tv_info_time.setVisibility(View.GONE);
        }

        //服务名称
        if (!TextUtils.isEmpty(entity.getServerName())) {
            ll_info_server_name.setVisibility(View.VISIBLE);
            tv_info_server_name.setText(entity.getServerName());
        } else {
            tv_info_server_name.setVisibility(View.GONE);
        }
        //订单状态
        if (!TextUtils.isEmpty(entity.getReceiptStatusStr())) {
            ll_info_order_status.setVisibility(View.VISIBLE);
            tv_info_order_status.setText(entity.getReceiptStatusStr());
        } else {
            tv_info_order_status.setVisibility(View.GONE);
        }

        //服务类型
        if (!TextUtils.isEmpty(entity.getTypeAliasName())) {
            ll_info_server_type.setVisibility(View.VISIBLE);
            tv_info_server_type.setText(entity.getTypeAliasName());
        } else {
            tv_info_server_type.setVisibility(View.GONE);
        }

        //发布用户
        if (!TextUtils.isEmpty(entity.getMemberName())) {
            ll_info_release_user.setVisibility(View.VISIBLE);
            tv_info_release_user.setText(entity.getMemberName());
        } else {
            tv_info_release_user.setVisibility(View.GONE);
        }

        if (entity.getRequireTypeId() == 124) {
            ll_info_talk_time.setVisibility(View.GONE);

            ll_info_talk_record.setVisibility(View.VISIBLE);
            if (entity.getQuickTime() == null || entity.getQuickTime().size() == 0) {
                tv_info_talk_record.setText("无");
            } else {
                if (!TextUtils.isEmpty(entity.getQuickTime().get(0).getBeginTime()))
                    tv_info_talk_record.setText(entity.getQuickTime().get(0).getBeginTime() + "\n通话：" + entity.getQuickTime().get(0).getCalllength());
            }
        } else if (entity.getRequireTypeId() == 125) {

        } else if (entity.getRequireTypeId() == 126) {
            //见面时间
            if (!TextUtils.isEmpty(entity.getMeetingTime())) {
                ll_info_meet_time.setVisibility(View.VISIBLE);
                tv_info_meet_time.setText(entity.getMeetingTime());
            } else {
                ll_info_meet_time.setVisibility(View.GONE);
            }
            //见面时长
            if (!TextUtils.isEmpty(entity.getMeetingDuration())) {
                ll_info_meet_duration.setVisibility(View.VISIBLE);
                tv_info_meet_duration.setText(entity.getMeetingDuration());
            } else {
                tv_info_meet_duration.setVisibility(View.GONE);
            }
            //见面地点
            if (!TextUtils.isEmpty(entity.getRegion())) {
                ll_info_meet_place.setVisibility(View.VISIBLE);
                tv_info_meet_place.setText(entity.getRegion());
            } else {
                tv_info_meet_place.setVisibility(View.GONE);
            }
        }

        setStatus(entity);

        setLawyerLayout(entity.getLawyerId(), entity.getLawyerName(), entity.getLawyerArea(), entity.getIconImage());

        set500Layout(entity);
        setEvaluateLayout(entity);
    }

    //评价
    public void setEvaluateLayout(LegalAdviserOrderDetailEntity entity) {
        if (entity.getEvaluateType() == 0) {
            fl_evaluate_info.setVisibility(View.GONE);
        } else {
            fl_evaluate_info.setVisibility(View.VISIBLE);
            if (entity.getGeneralEvaluation() == 1) {
                tv_evaluate_whole.setText("很差");
                iv_evaluate_whole.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_bad));
            } else if (entity.getGeneralEvaluation() == 3) {
                tv_evaluate_whole.setText("一般");
                iv_evaluate_whole.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_commonly));
            } else if (entity.getGeneralEvaluation() == 5) {
                tv_evaluate_whole.setText("超赞");
                iv_evaluate_whole.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine));
            } else {
                tv_evaluate_whole.setText("超赞");
                iv_evaluate_whole.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine));
            }

            tv_evaluate_info_content.setText(entity.getEvaluationContent());

            evaluateStarView_major.setClick(false);
            evaluateStarView_speed.setClick(false);
            evaluateStarView_attitude.setClick(false);
            evaluateStarView_major.setNum(entity.getProfessionalKnowledge());
            evaluateStarView_speed.setNum(entity.getResponseSpeed());
            evaluateStarView_attitude.setNum(entity.getServiceAttitude());

        }
    }

    //诉讼无忧
    public void set500Layout(LegalAdviserOrderDetailEntity entity) {
        if (TextUtils.isEmpty(entity.getPayOrderNo())) {
            fl_500.setVisibility(View.GONE);
        } else {
            if (entity.getLawsui() == null) {
                fl_500.setVisibility(View.GONE);
                return;
            }

            fl_500.setVisibility(View.VISIBLE);
            tv_500_info_title.setText(entity.getLawsui().getTitle());
            tv_500_info_price.setText(entity.getLawsui().getAmountStr());
            tv_500_info_content.setText(entity.getLawsui().getDescribe());
            fl_500.setOnClickListener(v -> {
                if (isFastClick()) return;
                if (TextUtils.isEmpty(entity.getLawsui().getDescLink())) return;
                bundle.clear();
                bundle.putString(BundleTags.URL, entity.getLawsui().getDescLink());
                bundle.putString(BundleTags.TITLE, entity.getLawsui().getTitle());
                launchActivity(new Intent(mActivity, WebActivity.class), bundle);
            });
        }
    }

    //状态（头部按钮）
    public void setStatus(LegalAdviserOrderDetailEntity entity) {

        ll_top_message.setVisibility(View.GONE);

        tv_btn_right.setOnClickListener(null);
        tv_btn_left.setOnClickListener(null);

        tv_btn_right.setVisibility(View.GONE);
        tv_btn_left.setVisibility(View.GONE);

        //orderStatus	订单状态：1	待接单（1） 2	待服务（2） 3	服务中（2） 4	待确认（2） 5	待评价（3） 6	已完成（3） 7	待处理（2） 8	已关闭（0） 9	已取消（0）
        int orderStatus = entity.getReceiptStatus();

        if (orderStatus == 1) {
            //头部进度
            setOrderDetailView(1);
            //按钮
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText("取消订单");
            tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
            tv_btn_left.setOnClickListener(v -> {
                mPresenter.legalAdviserOrderCancel();
            });
        } else if (orderStatus == 2 || orderStatus == 3 || orderStatus == 4 || orderStatus == 7) {
            //头部进度
            setOrderDetailView(2);
            //头部消息
            if (orderStatus == 2 || orderStatus == 3) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("如服务律师未主动回电，您也可以点击联系律师拨打电话。");
            } else if (orderStatus == 4) {
                if (entity.getRequireTypeId() == 124) {
                    ll_top_message.setVisibility(View.VISIBLE);
                    tv_top_message.setText("律师已完成服务，如您不进行操作，1小时后系统将自动确认完成！");
                } else {
                    ll_top_message.setVisibility(View.VISIBLE);
                    tv_top_message.setText("律师已完成服务，如您不进行操作，24小时后系统将自动确认完成！");
                }
            } else if (orderStatus == 7) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您的投诉反馈正在处理中，请耐心等候！");
            }

            //按钮
            if (orderStatus == 2 || orderStatus == 3) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("联系服务律师");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                tv_btn_left.setOnClickListener(v -> {
                    mPresenter.legalAdviserOrderUserPhone();
                });
            }
            if (orderStatus == 4) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("未完成服务");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                tv_btn_left.setOnClickListener(v -> {
                    mPresenter.legalAdviserOrderComplaint();
                });
                tv_btn_right.setVisibility(View.VISIBLE);
                tv_btn_right.setText("确认完成服务");
                tv_btn_right.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                tv_btn_right.setOnClickListener(v -> {
                    mPresenter.legalAdviserOrderComplete();
                });
            }
            if (orderStatus == 7) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("投诉反馈待处理");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            }
        } else if (orderStatus == 5 || orderStatus == 6) {
            //头部进度
            setOrderDetailView(3);
            //头部消息
            if (orderStatus == 5) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您已确认完成服务，如不进行评价，24小时后系统将采用默认评价!");
            }
            //按钮
            if (orderStatus == 5) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("匿名评价律师服务");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                tv_btn_left.setOnClickListener(v -> {
                    bundle.clear();
                    bundle.putSerializable(BundleTags.ENTITY, entity);
                    launchActivity(new Intent(mActivity, BuyEquityEvaluateActivity.class), bundle);
                });
            }
            if (orderStatus == 6) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("服务已完成");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            }
        } else {
            //头部进度
            setOrderDetailView(0);
            //头部消息
            if (orderStatus == 8) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您的订单已关闭，平台将不会扣除相应权益！");
            }
            //按钮
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText("已关闭");
            tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            if (orderStatus == 8)
                tv_btn_left.setText("已关闭");
            if (orderStatus == 9)
                tv_btn_left.setText("已取消");
        }
    }

    //无法接案对话框
    public void refuseDialog(List<String> lists) {
        new TextRadioDialog(mActivity)
                .setTitleStr("无法接案")
                .setContent("您的反馈对我们很重要，专属客服在投诉后会通过电话与您联系，此过程将会对律师保密。")
                .setLists(lists)
                .setCancelStr("取消")
                .setSubmitStr("确认")
                .setSubmitOnClickListener(position -> {
                    if (isFastClick()) return;
                    if (mPresenter.getComplaintEntitys() == null || mPresenter.getComplaintEntitys().size() == 0)
                        return;
                    if (position == -1) return;
                    mPresenter.legalAdviserOrderUnComplete(mPresenter.getComplaintEntitys().get(position).getComplaintId());
                }).show();
    }

    //律师显示
    public void setLawyerLayout(int id, String name, String nameContent, String headUrl) {
        if (id > 0) {
            groupLawyer.setVisibility(View.VISIBLE);
            ivHeadTip.setVisibility(View.VISIBLE);
            tvName.setText(name);
            tvNameContent.setText(nameContent);
            if (!TextUtils.isEmpty(headUrl)) {
                mImageLoader.loadImage(mActivity
                        , ImageConfigImpl
                                .builder()
                                .url(headUrl)
                                .imageRadius(AppUtils.dip2px(mActivity, 5))
                                .imageView(ivHead)
                                .build());
            } else {
                ivHead.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_lawyer_avatar));
            }

            viewLawyer.setOnClickListener(v -> {
                bundle.clear();
                bundle.putInt(BundleTags.ID, id);
                launchActivity(new Intent(mActivity, LawyerHomePageActivity.class), bundle);
            });
        } else {
            groupLawyer.setVisibility(View.GONE);
            ivHeadTip.setVisibility(View.GONE);
        }
    }

    /**
     * 更新优惠券
     */
    @Subscriber(tag = REFRESH)
    private void selectPlace(Message message) {
        switch (message.what) {
            case REFRESH_BUY_EQUITY_DETAIL:
                mPresenter.legalAdviserOrderDetail();
                break;
        }
    }

    public void setRightTv(String orderNo, String phone, int isReceipt) {
        titleView.setRightTv("合同");
        titleView.getRightTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
        titleView.getRightTv().setOnClickListener(v -> {
            bundle.clear();
            bundle.putString(BundleTags.ORDER_NO, orderNo);//传递状态，1为可以发合同，0位展示空页面。
            bundle.putString(BundleTags.MOBILE, phone);
            bundle.putInt(BundleTags.STATE, isReceipt);
            bundle.putInt(BundleTags.TYPE, 1);
            launchActivity(new Intent(mActivity, OrderContractActivity.class), bundle);
        });
    }

    @OnClick({R.id.iv_call})
    public void onViewClicked(View view) {
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.iv_call:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-811-3060"));
                startActivity(dialIntent);
                break;
        }
    }

    @Override
    public void call(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            launchActivity(intent);
        } catch (Exception ignored) {
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

}
