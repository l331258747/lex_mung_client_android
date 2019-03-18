package com.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.di.module.MessageChatModule;
import com.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import com.lex_mung.client_android.mvp.ui.adapter.MessageChatAdapter;
import com.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;
import com.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import com.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;

import com.lex_mung.client_android.di.component.DaggerMessageChatComponent;
import com.lex_mung.client_android.mvp.contract.MessageChatContract;
import com.lex_mung.client_android.mvp.presenter.MessageChatPresenter;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.ui.widget.RecordVoiceButton;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import static com.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_CUSTOMER_UN_READ_MESSAGE_NUM;
import static com.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UN_READ_MESSAGE_NUM;

public class MessageChatActivity extends BaseActivity<MessageChatPresenter> implements MessageChatContract.View {
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.group_time)
    Group groupTime;
    @BindView(R.id.recycler_view_requirement)
    RecyclerView recyclerViewRequirement;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.iv_voice_or_text)
    ImageView ivVoiceOrText;
    @BindView(R.id.bt_voice)
    RecordVoiceButton btVoice;
    @BindView(R.id.et_chat)
    EditText etChat;
    @BindView(R.id.rl_input)
    RelativeLayout rlInput;
    @BindView(R.id.rl_send)
    RelativeLayout rlSend;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.iv_multimedia)
    ImageView ivMultimedia;

    @BindView(R.id.bt_order_wait)
    Button btOrderWait;
    @BindView(R.id.bt_order_close)
    Button btOrderClose;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.ll_multimedia)
    LinearLayout llMultimedia;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    private MyCountDownTimer myCountDownTimer;

    private MessageChatAdapter mChatAdapter;
    private RequirementAdapter requirementAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageChatComponent
                .builder()
                .appComponent(appComponent)
                .messageChatModule(new MessageChatModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_chat;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        JMessageClient.registerEventReceiver(this);
        tvRight.setText("需求列表");
        if (bundleIntent != null) {
            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
        }
        initAdapter();
        initRecyclerView();
        initListener();
    }

    private void initAdapter() {
        mChatAdapter = new MessageChatAdapter(mActivity, mImageLoader, recyclerView, mPresenter.getConversation());
        mChatAdapter.setUpFetchListener(() -> {
            mChatAdapter.setUpFetching(true);
            mChatAdapter.dropDownToRefresh();
            mChatAdapter.setUpFetching(false);
            mChatAdapter.setUpFetchEnable(!mChatAdapter.isEnd());
        });
        mChatAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Message message = mChatAdapter.getItem(position);
            if (message == null) return;
            if (message.getContentType() == ContentType.location) {
                LocationContent content = (LocationContent) message.getContent();
                bundle.clear();
                bundle.putString(BundleTags.KEY_TITLE, content.getAddress());
                bundle.putDouble(BundleTags.KEY_LAT, content.getLatitude().doubleValue());
                bundle.putDouble(BundleTags.KEY_LNG, content.getLongitude().doubleValue());
                bundle.putInt(BundleTags.KEY_LEVEL, content.getScale().intValue());
                launchActivity(new Intent(mActivity, MapActivity.class), bundle);
            } else if (message.getContentType() == ContentType.image) {
                ImageContent imgContent = (ImageContent) message.getContent();
                String path = imgContent.getLocalThumbnailPath();
                if (!TextUtils.isEmpty(path)) {
                    imgContent.downloadThumbnailImage(message, new DownloadCompletionCallback() {
                        @Override
                        public void onComplete(int status, String desc, File file) {
                            if (status == 0) {
                                mImageLoader.loadImage(mActivity
                                        , ImageConfigImpl
                                                .builder()
                                                .url(file.getAbsolutePath())
                                                .imageView(ivIcon)
                                                .isCenterCrop(false)
                                                .build());
                            }
                        }
                    });
                } else {
                    mImageLoader.loadImage(mActivity
                            , ImageConfigImpl
                                    .builder()
                                    .url(path)
                                    .imageView(ivIcon)
                                    .isCenterCrop(false)
                                    .build());
                }
            }
        });
        requirementAdapter = new RequirementAdapter();
    }

    private void initRecyclerView() {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(mChatAdapter);
        AppUtils.configRecyclerView(recyclerViewRequirement, new LinearLayoutManager(mActivity));
        recyclerViewRequirement.setAdapter(requirementAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btSend.setVisibility(View.VISIBLE);
                    ivMultimedia.setVisibility(View.GONE);
                } else {
                    btSend.setVisibility(View.GONE);
                    ivMultimedia.setVisibility(View.VISIBLE);
                }
            }
        });
        etChat.setOnTouchListener((v, event) -> {
            llMultimedia.setVisibility(View.GONE);
            DeviceUtils.showSoftKeyboard(mActivity, etChat);
            if (mChatAdapter.getItemCount() > 0) {
                new Handler().postDelayed(() -> recyclerView.scrollToPosition(mChatAdapter.getItemCount() - 1), 200);
            }
            return false;
        });
        recyclerView.setOnTouchListener((v, event) -> {
            llMultimedia.setVisibility(View.GONE);
            return false;
        });
    }


    public void onEventMainThread(MessageEvent event) {
        final Message message = event.getMessage();
        if (message.getTargetType() == ConversationType.single) {
            UserInfo userInfo = (UserInfo) message.getTargetInfo();
            String targetId = userInfo.getUserName();
            if (targetId.equals(mPresenter.getTargetId())) {
                Message lastMsg = mChatAdapter.getLastMsg();
                if (lastMsg == null || message.getId() != lastMsg.getId()) {
                    mChatAdapter.addMsgToList(message);
                } else {
                    mChatAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 当在聊天界面断网再次连接时收离线事件刷新
     */
    public void onEventMainThread(OfflineMessageEvent event) {
        Conversation conversation = event.getConversation();
        if (conversation == null) return;
        if (conversation.getType().equals(ConversationType.single)) {
            UserInfo userInfo = (UserInfo) conversation.getTargetInfo();
            String targetId = userInfo.getUserName();
            if (targetId.equals(mPresenter.getTargetId())) {
                List<Message> singleOfflineMsgList = event.getOfflineMessageList();
                if (singleOfflineMsgList != null && singleOfflineMsgList.size() > 0) {
                    mChatAdapter.addData(singleOfflineMsgList);
                }
            }
        }
    }

    @OnClick({R.id.tv_right
            , R.id.iv_voice_or_text
            , R.id.bt_send
            , R.id.iv_multimedia
            , R.id.ll_photo_album
            , R.id.ll_camera
            , R.id.ll_location
            , R.id.iv_icon
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (isFastClick()) return;
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) mPresenter.getEntityList());
                launchActivity(new Intent(mActivity, RequirementListActivity.class), bundle);
                break;
            case R.id.iv_voice_or_text://切换语音和文本输入
                if (isFastClick()) return;
                mPresenter.getRecordAudioPermission();
                break;
            case R.id.bt_send://发送文本
                if (isFastClick()) return;
                if (mPresenter.getConversation() == null) return;
                String mcgContent = etChat.getText().toString();
                if (TextUtils.isEmpty(mcgContent)) {
                    return;
                }
                Message msg = mPresenter.getConversation().createSendMessage(new TextContent(mcgContent));
                //设置需要已读回执
                MessageSendingOptions options = new MessageSendingOptions();
                options.setNeedReadReceipt(true);
                JMessageClient.sendMessage(msg, options);
                mChatAdapter.addMsgFromReceiptToList(msg);
                etChat.setText("");
                break;
            case R.id.iv_multimedia://多媒体
                llMultimedia.setVisibility(View.VISIBLE);
                DeviceUtils.hideSoftKeyboard(etChat);
                break;
            case R.id.ll_photo_album:
                mPresenter.getLaunchCameraPermission(2);
                break;
            case R.id.ll_camera:
                mPresenter.getLaunchCameraPermission(1);
                break;
            case R.id.ll_location:
                mPresenter.getLocationPermission();
                break;
            case R.id.iv_icon:
                ivIcon.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void launchActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1000
                    && data != null
                    && data.getExtras() != null) {
                sendLocationMessage(data);
            } else {
                mPresenter.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 发送位置消息
     *
     * @param data Intent
     */
    private void sendLocationMessage(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle == null) return;
        try {
            double latitude = bundle.getDouble(BundleTags.KEY_LAT, 0);
            double longitude = bundle.getDouble(BundleTags.KEY_LNG, 0);
            int level = bundle.getInt(BundleTags.KEY_LEVEL, 0);
            String title = bundle.getString(BundleTags.KEY_TITLE);
            LocationContent locationContent = new LocationContent(latitude, longitude, level, title);
            Message message = mPresenter.getConversation().createSendMessage(locationContent);
            MessageSendingOptions options = new MessageSendingOptions();
            options.setNeedReadReceipt(true);
            JMessageClient.sendMessage(message, options);
            mChatAdapter.addMsgFromReceiptToList(message);

            int customMsgId = data.getIntExtra("customMsg", -1);
            if (-1 != customMsgId) {
                Message customMsg = mPresenter.getConversation().getMessage(customMsgId);
                mChatAdapter.addMsgToList(customMsg);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 发送图片
     *
     * @param file 图片文件
     */
    @Override
    public void sendImageMessage(File file) {
        ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, ImageContent imageContent) {
                if (responseCode == 0) {
                    Message msg = mPresenter.getConversation().createSendMessage(imageContent);
                    MessageSendingOptions options = new MessageSendingOptions();
                    options.setNeedReadReceipt(true);
                    JMessageClient.sendMessage(msg, options);
                    msg.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            mChatAdapter.addMsgToList(msg);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addMsgFromReceiptToList(Message msg) {
        mChatAdapter.addMsgFromReceiptToList(msg);
    }

    @Override
    public void setTitle(String nickname) {
        if (bundleIntent.containsKey(BundleTags.TITLE)
                && !TextUtils.isEmpty(bundleIntent.getString(BundleTags.TITLE))) {
            tvTitle.setText(bundleIntent.getString(BundleTags.TITLE));
        } else {
            tvTitle.setText(nickname);
        }
    }

    @Override
    public void showRequirementAdapter(List<RequirementStatusEntity> list) {
        recyclerViewRequirement.setVisibility(View.VISIBLE);
        requirementAdapter.setNewData(list);
    }

    @Override
    public void sendSystemMessage(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        mPresenter.setConversation(Conversation.createSingleConversation(mPresenter.getTargetId(), null));
        Message msg = mPresenter.getConversation().createSendCustomMessage(map);
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(true);
        JMessageClient.sendMessage(msg, options);
        mChatAdapter.addMsgFromReceiptToList(msg);
    }

    @Override
    public void voiceOrText() {
        if (mPresenter.getConversation() == null) return;
        if (rlInput.isShown()) {
            showVoice();
            ivVoiceOrText.setImageResource(R.drawable.down_text_bg);
        } else {
            showText();
            ivVoiceOrText.setImageResource(R.drawable.down_voice_bg);
        }
        btVoice.initConv(mPresenter.getConversation(), mChatAdapter, this);
    }

    protected void showVoice() {
        rlInput.setVisibility(View.GONE);
        btVoice.setVisibility(View.VISIBLE);
        reset();
    }

    protected void showText() {
        rlInput.setVisibility(View.VISIBLE);
        btVoice.setVisibility(View.GONE);
    }

    public void reset() {
        DeviceUtils.closeSoftKeyboard(this);
    }

    @Override
    public void showWaitAcceptOrderLayout() {
        rlOrder.setVisibility(View.VISIBLE);
        groupTime.setVisibility(View.VISIBLE);
        recyclerViewRequirement.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAcceptOrderLayout() {
        mChatAdapter.setConversation(mPresenter.getConversation());
        rlOrder.setVisibility(View.GONE);
        rlSend.setVisibility(View.VISIBLE);
        groupTime.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        recyclerViewRequirement.setVisibility(View.GONE);
    }

    @Override
    public void showOrderEndLayout() {
        mChatAdapter.setConversation(mPresenter.getConversation());
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
        rlOrder.setVisibility(View.VISIBLE);
        rlSend.setVisibility(View.GONE);
        btOrderWait.setVisibility(View.GONE);
        btOrderClose.setVisibility(View.VISIBLE);
        groupTime.setVisibility(View.GONE);

        tvRight.setVisibility(View.VISIBLE);
        recyclerViewRequirement.setVisibility(View.GONE);
    }

    @Override
    public void setCountDown(long remain) {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
        myCountDownTimer = new MyCountDownTimer(remain);
        myCountDownTimer.start();
    }

    public void setToBottom() {
        recyclerView.clearFocus();
    }

    private DefaultDialog defaultDialog;

    @Override
    public void showToAppInfoDialog() {
        if (defaultDialog == null) {
            defaultDialog = new DefaultDialog(mActivity, dialog -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
                    , getString(R.string.text_manual_open_permissions)
                    , getString(R.string.text_to_open)
                    , getString(R.string.text_cancel));
        }
        if (!defaultDialog.isShowing()) {
            defaultDialog.show();
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
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
        JMessageClient.unRegisterEventReceiver(this);
        AppUtils.postInt(UN_READ_MESSAGE_NUM, SET_CUSTOMER_UN_READ_MESSAGE_NUM, JMessageClient.getAllUnReadMsgCount());
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnBtn();
        AppManager.getAppManager().killActivity(MessageChatActivity.class);
    }

    private void returnBtn() {
        if (mPresenter.getConversation() != null) {
            mPresenter.getConversation().resetUnreadCount();
        }
        JMessageClient.exitConversation();
    }

    private class MyCountDownTimer extends CountDownTimer {
        SimpleDateFormat sdf;

        @SuppressLint("SimpleDateFormat")
        MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
            if (millisInFuture > 1000 * 60 * 60 * 24) {
                sdf = new SimpleDateFormat("dd天 HH:mm:ss", Locale.getDefault());
            } else if (millisInFuture > 1000 * 60 * 60) {
                sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            } else {
                sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
            }
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        }

        @Override
        @SuppressLint({"SetTextI18n"})
        public void onTick(long l) {
            String s = sdf.format(new Date(l));
            if (mPresenter.getStatus() == 0) {
                tvTime.setText("接单倒计时 " + s);
            } else if (mPresenter.getStatus() == 1) {
                tvTime.setText("剩余服务时间 " + s);
            }
        }

        @Override
        public void onFinish() {
            if (mPresenter.getStatus() == 0) {
                sendSystemMessage("律师可能正在忙没有看到您的需求，请找其它律师！");
            }
            mPresenter.setStatus(3);
            showOrderEndLayout();
            groupTime.setVisibility(View.GONE);
        }
    }
}
