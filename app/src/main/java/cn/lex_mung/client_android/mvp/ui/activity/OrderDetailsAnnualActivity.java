package cn.lex_mung.client_android.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.lex_mung.client_android.di.component.DaggerOrderDetailsAnnualComponent;
import cn.lex_mung.client_android.di.module.OrderDetailsAnnualModule;
import cn.lex_mung.client_android.mvp.contract.OrderDetailsAnnualContract;
import cn.lex_mung.client_android.mvp.model.entity.corporate.CorporateDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;
import cn.lex_mung.client_android.mvp.model.entity.payEquity.EvaluateIntent;
import cn.lex_mung.client_android.mvp.presenter.OrderDetailsAnnualPresenter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderDetailsExpertInfoAdapter;
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
import static cn.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_ANNUAL_DETAIL;

public class OrderDetailsAnnualActivity extends BaseActivity<OrderDetailsAnnualPresenter> implements OrderDetailsAnnualContract.View {
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

    @BindView(R.id.ll_info_pay_express)
    LinearLayout ll_info_pay_express;
    @BindView(R.id.tv_info_pay_express)
    TextView tv_info_pay_express;

    @BindView(R.id.ll_info_talk_time)
    LinearLayout ll_info_talk_time;
    @BindView(R.id.tv_info_talk_time)
    TextView tv_info_talk_time;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

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
    @BindView(R.id.iv_evaluate_bad)
    ImageView iv_evaluate_bad;
    @BindView(R.id.iv_evaluate_commonly)
    ImageView iv_evaluate_commonly;
    @BindView(R.id.iv_evaluate_fine)
    ImageView iv_evaluate_fine;

    @BindView(R.id.tv_btn_left)
    TextView tv_btn_left;
    @BindView(R.id.tv_btn_right)
    TextView tv_btn_right;
    @BindView(R.id.tv_btn_top)
    TextView tv_btn_top;

    int id;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailsAnnualComponent
                .builder()
                .appComponent(appComponent)
                .orderDetailsAnnualModule(new OrderDetailsAnnualModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details_annual;
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
            mPresenter.setId(id = bundleIntent.getInt(BundleTags.ID));
        }

        String[] strs = {"预约律师", "优选律师", "律师回电", "评价服务"};
        orderDetailView.initView(strs);

        mPresenter.corporateDetail();
    }

    public void setOrderDetailView(int index) {
        if (index == -1) {
            orderDetailView.setProgress(0);
        } else {
            orderDetailView.setProgress(index);
        }
    }

    @Override
    public void setEntity(CorporateDetailEntity entity) {

        if (entity.getRequireTypeId() == 131) {//文书服务
            //orderStatus	订单状态：1待接单（空） 2待服务（1） 3服务中（1） 4待确认（1） 5待评价（3） 6已完成（3） 7待处理（3） 8已关闭（3） 9已取消（0）
            int isReceipt;
            int orderStatus = entity.getReceiptStatus();

            if (orderStatus == 50 || orderStatus == 60 || orderStatus == 70 || orderStatus == 80) {
                isReceipt = 3;
            } else if (orderStatus == 20 || orderStatus == 30 || orderStatus == 40) {
                isReceipt = 1;
            } else {
                isReceipt = 0;
            }

            setRightTv(id, entity.getOrderNo(), "", isReceipt);
        }

        //订单编号
        if (!TextUtils.isEmpty(entity.getOrderNo())) {
            ll_info_no.setVisibility(View.VISIBLE);
            tv_info_no.setText(entity.getOrderNo());
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
        if (!TextUtils.isEmpty(entity.getReceiptStatusName())) {
            ll_info_order_status.setVisibility(View.VISIBLE);
            tv_info_order_status.setText(entity.getReceiptStatusName());
        } else {
            tv_info_order_status.setVisibility(View.GONE);
        }

        //发布用户
        if (!TextUtils.isEmpty(entity.getMemberName())) {
            ll_info_release_user.setVisibility(View.VISIBLE);
            tv_info_release_user.setText(entity.getMemberName());
        } else {
            tv_info_release_user.setVisibility(View.GONE);
        }

        //服务类型
        if (!TextUtils.isEmpty(entity.getSolutionTypeName())) {
            ll_info_server_type.setVisibility(View.VISIBLE);
            tv_info_server_type.setText(entity.getSolutionTypeName());
        } else {
            tv_info_server_type.setVisibility(View.GONE);
        }

        //通话时长
        if (!TextUtils.isEmpty(entity.getCallTimeStr())) {
            ll_info_talk_time.setVisibility(View.VISIBLE);
            tv_info_talk_time.setText(entity.getCallTimeStr());
        } else {
            ll_info_talk_time.setVisibility(View.GONE);
        }

        //通话记录
        List<QuickTimeBean> lists = entity.getQuickTime();
        if (lists == null || lists.size() == 0)
            recyclerView.setVisibility(View.GONE);
        else {
            recyclerView.setVisibility(View.VISIBLE);
            OrderDetailsExpertInfoAdapter adapter = new OrderDetailsExpertInfoAdapter();
            AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
            adapter.setNewData(lists);
        }

        //130电话咨询，131文书服务，132律师函，133诉讼代理
        if (entity.getRequireTypeId() == 130) {

        } else if (entity.getRequireTypeId() == 131) {

        } else if (entity.getRequireTypeId() == 132) {
            //快递费
            if (!TextUtils.isEmpty(entity.getExpressFeeStr())) {
                ll_info_pay_express.setVisibility(View.VISIBLE);
                tv_info_pay_express.setText(entity.getExpressFeeStr());
            } else {
                tv_info_pay_express.setVisibility(View.GONE);
            }
        } else if (entity.getRequireTypeId() == 133) {

        }

        setStatus(entity);

        setLawyerLayout(entity.getLawyerId(), entity.getLawyerName(), entity.getLawyerArea(), entity.getIconImage());

        setEvaluateLayout(entity);
    }


    public void payExpress(CorporateDetailEntity entity) {
        bundle.clear();
        bundle.putInt(BundleTags.ID, entity.getCorporateServerId());
        bundle.putString(BundleTags.TITLE, "快递费");
        bundle.putString(BundleTags.REQUIRE_TYPE_NAME, "快递费");
        bundle.putFloat(BundleTags.MONEY, entity.getExpressFeeFloat());
        bundle.putString(BundleTags.ORDER_NO, entity.getOrderNo());
        bundle.putInt(BundleTags.TYPE, 6);
        launchActivity(new Intent(mActivity, RushLoanPayActivity.class), bundle);
    }

    //状态（头部按钮）
    public void setStatus(CorporateDetailEntity entity) {

        ll_top_message.setVisibility(View.GONE);

        tv_btn_right.setOnClickListener(null);
        tv_btn_left.setOnClickListener(null);
        tv_btn_top.setOnClickListener(null);

        tv_btn_right.setVisibility(View.GONE);
        tv_btn_left.setVisibility(View.GONE);
        tv_btn_top.setVisibility(View.GONE);

        //orderStatus	订单状态：1	待接单（1） 2	待服务（2） 3	服务中（2） 4	待确认（2） 5	待评价（3） 6	已完成（3） 7	待处理（2） 8	已关闭（0） 9	已取消（0）
        int orderStatus = entity.getReceiptStatus();

        if (orderStatus == 10) {
            //头部进度
            setOrderDetailView(1);
            //按钮
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText("取消订单");
            tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
            tv_btn_left.setOnClickListener(v -> {
                mPresenter.corporateCancel();
            });
        } else if (orderStatus == 20 || orderStatus == 30 || orderStatus == 40 || orderStatus == 50 || orderStatus == 70) {
            //头部进度
            setOrderDetailView(2);
            //头部消息
            if (orderStatus == 20 || orderStatus == 30) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("如服务律师未主动回电，您也可以点击联系律师拨打电话。");
            } else if (orderStatus == 40) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("律师已完成服务，如您不进行操作，1小时后系统将自动确认完成！");
            } else if (orderStatus == 50) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您已确认完成服务，如不进行评价，24小时后系统将采用默认评价!");
            } else if (orderStatus == 70) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您的投诉反馈正在处理中，请耐心等候！");
            }

            //按钮
            if (orderStatus == 20 || orderStatus == 30) {

                if (entity.getRequireTypeId() == 132) {
                    if (TextUtils.isEmpty(entity.getPayOrderNo())) {
                        tv_btn_left.setVisibility(View.VISIBLE);
                        tv_btn_left.setText("支付快递费");
                        tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                        tv_btn_left.setOnClickListener(v -> {
                            payExpress(entity);
                        });
                    } else {
                        tv_btn_left.setVisibility(View.VISIBLE);
                        tv_btn_left.setText(entity.getExpressFeeStr2());
                        tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                    }

                    tv_btn_right.setVisibility(View.VISIBLE);
                    tv_btn_right.setText("联系服务律师");
                    tv_btn_right.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                    tv_btn_right.setOnClickListener(v -> {
                        mPresenter.corporateUserphone();
                    });
                } else {
                    tv_btn_left.setVisibility(View.VISIBLE);
                    tv_btn_left.setText("联系服务律师");
                    tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                    tv_btn_left.setOnClickListener(v -> {
                        mPresenter.corporateUserphone();
                    });
                }
            }
            if (orderStatus == 40) {
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
                    mPresenter.corporateComplete();
                });
                if (entity.getRequireTypeId() == 132) {
                    if (TextUtils.isEmpty(entity.getPayOrderNo())) {
                        tv_btn_top.setVisibility(View.VISIBLE);
                        tv_btn_top.setText("支付快递费");
                        tv_btn_top.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                        tv_btn_top.setOnClickListener(v -> {
                            payExpress(entity);
                        });
                    } else {
                        tv_btn_top.setVisibility(View.VISIBLE);
                        tv_btn_top.setText(entity.getExpressFeeStr2());
                        tv_btn_top.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                    }
                }
            }
            if (orderStatus == 50) {

                if (entity.getRequireTypeId() == 132 && !TextUtils.isEmpty(entity.getPayOrderNo())) {

                    tv_btn_left.setVisibility(View.VISIBLE);
                    tv_btn_left.setText(entity.getExpressFeeStr2());
                    tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));

                    tv_btn_right.setVisibility(View.VISIBLE);
                    tv_btn_right.setText("匿名评价律师服务");
                    tv_btn_right.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                    tv_btn_right.setOnClickListener(v -> {

                        EvaluateIntent evaluateIntent = new EvaluateIntent();
                        evaluateIntent.setIconImage(entity.getIconImage());
                        evaluateIntent.setInstitutionName(entity.getInstitutionName());
                        evaluateIntent.setLawyerName(entity.getEvaluateLawyerName());
                        evaluateIntent.setOrderId(entity.getOrderNo());

                        bundle.clear();
                        bundle.putSerializable(BundleTags.ENTITY, evaluateIntent);
                        bundle.putInt(BundleTags.TYPE, 2);
                        launchActivity(new Intent(mActivity, BuyEquityEvaluateActivity.class), bundle);
                    });
                } else {
                    tv_btn_left.setVisibility(View.VISIBLE);
                    tv_btn_left.setText("匿名评价律师服务");
                    tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                    tv_btn_left.setOnClickListener(v -> {

                        EvaluateIntent evaluateIntent = new EvaluateIntent();
                        evaluateIntent.setIconImage(entity.getIconImage());
                        evaluateIntent.setInstitutionName(entity.getInstitutionName());
                        evaluateIntent.setLawyerName(entity.getEvaluateLawyerName());
                        evaluateIntent.setOrderId(entity.getOrderNo());

                        bundle.clear();
                        bundle.putSerializable(BundleTags.ENTITY, evaluateIntent);
                        bundle.putInt(BundleTags.TYPE, 2);
                        launchActivity(new Intent(mActivity, BuyEquityEvaluateActivity.class), bundle);
                    });
                }

            }
            if (orderStatus == 70) {
                if (entity.getRequireTypeId() == 132) {
                    if (TextUtils.isEmpty(entity.getPayOrderNo())) {
                        tv_btn_left.setVisibility(View.VISIBLE);
                        tv_btn_left.setText("支付快递费");
                        tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_1ec88b_all));
                        tv_btn_left.setOnClickListener(v -> {
                            payExpress(entity);
                        });
                    } else {
                        tv_btn_left.setVisibility(View.VISIBLE);
                        tv_btn_left.setText(entity.getExpressFeeStr2());
                        tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                    }

                    tv_btn_right.setVisibility(View.VISIBLE);
                    tv_btn_right.setText("投诉反馈待处理");
                    tv_btn_right.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                } else {
                    tv_btn_left.setVisibility(View.VISIBLE);
                    tv_btn_left.setText("投诉反馈待处理");
                    tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
                }
            }
        } else if (orderStatus == 60) {
            //头部进度
            setOrderDetailView(3);
            //按钮
            if (entity.getRequireTypeId() == 132 && !TextUtils.isEmpty(entity.getPayOrderNo())) {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText(entity.getExpressFeeStr2());
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));

                tv_btn_right.setVisibility(View.VISIBLE);
                tv_btn_right.setText("服务已完成");
                tv_btn_right.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            } else {
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("服务已完成");
                tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            }

        } else {
            //头部进度
            setOrderDetailView(0);
            //头部消息
            if (orderStatus == 80) {
                ll_top_message.setVisibility(View.VISIBLE);
                tv_top_message.setText("您的订单已关闭，平台将不会扣除相应权益！");
            }
            //按钮
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText("已关闭");
            tv_btn_left.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.round_40_d7d7d7_all));
            if (orderStatus == 80)
                tv_btn_left.setText("已关闭");
            if (orderStatus == 90)
                tv_btn_left.setText("已取消");
        }
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

    //评价
    public void setEvaluateLayout(CorporateDetailEntity entity) {
        if (entity == null) return;

        if (entity.getEvaluateType() == 0) {
            fl_evaluate_info.setVisibility(View.GONE);
        } else {
            fl_evaluate_info.setVisibility(View.VISIBLE);

            iv_evaluate_bad.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_bad_un));
            iv_evaluate_commonly.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_commonly_un));
            iv_evaluate_fine.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine_un));

            if (entity.getGeneralEvaluation() == 1) {
                iv_evaluate_bad.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_bad));
            } else if (entity.getGeneralEvaluation() == 3) {
                iv_evaluate_commonly.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_commonly));
            } else if (entity.getGeneralEvaluation() == 5) {
                iv_evaluate_fine.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine));
            } else {
                iv_evaluate_fine.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_evaluate_fine));
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

    /**
     * 评价
     */
    @Subscriber(tag = REFRESH)
    private void refreshDetail(Message message) {
        switch (message.what) {
            case REFRESH_ANNUAL_DETAIL:
                mPresenter.corporateDetail();
                break;
        }
    }


    public void setRightTv(int id, String orderNo, String phone, int isReceipt) {
        titleView.setRightTv("合同");
        titleView.getRightTv().setTextColor(ContextCompat.getColor(mActivity, R.color.c_ff));
        titleView.getRightTv().setOnClickListener(v -> {
            bundle.clear();
            bundle.putInt(BundleTags.ID, id);//传递状态，1为可以发合同，0位展示空页面。
            bundle.putString(BundleTags.ORDER_NO, orderNo);
            bundle.putString(BundleTags.MOBILE, phone);
            bundle.putInt(BundleTags.STATE, isReceipt);
            bundle.putInt(BundleTags.TYPE, 2);
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
                    mPresenter.corporateUncomplete(mPresenter.getComplaintEntitys().get(position).getComplaintContent());
                }).show();
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
