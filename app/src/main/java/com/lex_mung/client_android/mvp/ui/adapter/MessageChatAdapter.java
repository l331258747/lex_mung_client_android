package com.lex_mung.client_android.mvp.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.app.TimeFormat;
import com.lex_mung.client_android.mvp.ui.dialog.DeleteDialog;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.utils.AppUtils;

public class MessageChatAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    private static final int PAGE_MESSAGE_COUNT = 18;

    private final int TYPE_SEND_TXT = 0;//发送的文本
    private final int TYPE_RECEIVE_TXT = 1;//收到的文本
    private final int TYPE_SEND_VOICE = 2;//发送的语音
    private final int TYPE_RECEIVER_VOICE = 3;//收到的语音
    private final int TYPE_CUSTOM_TXT = 4;//自定义消息
    private final int TYPE_SEND_LOCATION = 5;//发送的位置消息
    private final int TYPE_RECEIVER_LOCATION = 6;//收到的位置消息
    private final int TYPE_SEND_IMAGE = 7;//发送图片
    private final int TYPE_RECEIVER_IMAGE = 8;//收到图片

    private ImageLoader mImageLoader;
    private Animation mSendingAnim;
    private Conversation mConversation;
    private RecyclerView recyclerView;

    private int mOffset = PAGE_MESSAGE_COUNT;
    private List<Integer> mIndexList = new ArrayList<>();//语音索引
    private boolean autoPlay = false;//自动播放
    private int nextPlayPosition = 0;
    private int mPosition = -1;// 和mSetData一起组成判断播放哪条录音的依据
    private boolean mSetData = false;
    private final MediaPlayer mp = new MediaPlayer();
    private AnimationDrawable mVoiceAnimation;
    private FileInputStream mFIS;
    private boolean isEnd = false;

    public MessageChatAdapter(Context context, ImageLoader mImageLoader, RecyclerView recyclerView, Conversation conversation) {
        super(null);
        mSendingAnim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        mSendingAnim.setInterpolator(lin);
        this.mConversation = conversation;
        this.recyclerView = recyclerView;
        this.mImageLoader = mImageLoader;

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            if (audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(true);
            } else {
                audioManager.setSpeakerphoneOn(false);
            }
        }
        mp.setAudioStreamType(AudioManager.STREAM_RING);
        mp.setOnErrorListener((mp, what, extra) -> false);

        setMultiTypeDelegate(new MultiTypeDelegate<Message>() {
            @Override
            protected int getItemType(Message msg) {
                switch (msg.getContentType()) {
                    case text:
                        return msg.getDirect() == MessageDirect.send ? TYPE_SEND_TXT
                                : TYPE_RECEIVE_TXT;
                    case voice:
                        return msg.getDirect() == MessageDirect.send ? TYPE_SEND_VOICE
                                : TYPE_RECEIVER_VOICE;
                    case custom:
                        return TYPE_CUSTOM_TXT;
                    case location:
                        return msg.getDirect() == MessageDirect.send ? TYPE_SEND_LOCATION
                                : TYPE_RECEIVER_LOCATION;
                    case image:
                        return msg.getDirect() == MessageDirect.send ? TYPE_SEND_IMAGE
                                : TYPE_RECEIVER_IMAGE;
                }
                return TYPE_CUSTOM_TXT;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(TYPE_SEND_TXT, R.layout.item_message_chat_send_text)
                .registerItemType(TYPE_RECEIVE_TXT, R.layout.item_message_chat_receive_text)
                .registerItemType(TYPE_SEND_VOICE, R.layout.item_message_chat_send_voice)
                .registerItemType(TYPE_RECEIVER_VOICE, R.layout.item_message_chat_receive_voice)
                .registerItemType(TYPE_CUSTOM_TXT, R.layout.item_message_chat_custom)
                .registerItemType(TYPE_SEND_LOCATION, R.layout.item_message_chat_send_location)
                .registerItemType(TYPE_RECEIVER_LOCATION, R.layout.item_message_chat_receive_location)
                .registerItemType(TYPE_SEND_IMAGE, R.layout.item_message_chat_send_image)
                .registerItemType(TYPE_RECEIVER_IMAGE, R.layout.item_message_chat_receive_image)
        ;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setConversation(Conversation mConversation) {
        this.mConversation = mConversation;
        if (mConversation == null) return;
        if (mConversation.getAllMessage().size() > 18) {
            setUpFetchEnable(true);
            setStartUpFetchPosition(2);
        }
        List<Message> list = mConversation.getMessagesFromNewest(0, mOffset);
        if (list != null
                && list.size() > 0) {
            Collections.reverse(list);
            setNewData(list);
            recyclerView.scrollToPosition(getItemCount() - 1);
        }
    }

    public void dropDownToRefresh() {
        if (recyclerView.isComputingLayout()) {
            recyclerView.postDelayed(this::add, 300);
        } else {
            add();
        }
    }

    private void add() {
        if (mConversation != null) {
            List<Message> msgList = mConversation.getMessagesFromNewest(mData.size(), PAGE_MESSAGE_COUNT);
            if (msgList != null) {
                for (Message msg : msgList) {
                    addData(0, msg);
                }
                if (msgList.size() > 0) {
                    mOffset = msgList.size();
                } else {
                    mOffset = 0;
                }

                notifyDataSetChanged();
            } else {
                isEnd = true;
            }
        }
        isEnd = true;
    }


    public Message getLastMsg() {
        if (getItemCount() > 0) {
            return getItem(getItemCount() - 1);
        } else {
            return null;
        }
    }

    public void addMsgToList(Message msg) {
        addData(msg);
        if (getItemCount() > 18) {
            setUpFetchEnable(true);
            setStartUpFetchPosition(2);
        }
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    public void addMsgFromReceiptToList(Message msg) {
        addData(msg);
        if (getItemCount() > 18) {
            setUpFetchEnable(true);
            setStartUpFetchPosition(2);
        }
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message msg) {
        try {
            int position = helper.getLayoutPosition();
            long nowDate = msg.getCreateTime();

            UserInfo userInfo = msg.getFromUser();

            if (mOffset == PAGE_MESSAGE_COUNT) {
                if (position == 0 || position % PAGE_MESSAGE_COUNT == 0) {
                    helper.setText(R.id.item_tv_time, TimeFormat.getTime(nowDate));
                    helper.getView(R.id.item_tv_time).setVisibility(View.VISIBLE);
                } else {
                    long lastDate = mData.get(position - 1).getCreateTime();
                    if (nowDate - lastDate > 300000) {// 如果两条消息之间的间隔超过五分钟则显示时间
                        helper.setText(R.id.item_tv_time, TimeFormat.getTime(nowDate));
                        helper.getView(R.id.item_tv_time).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.item_tv_time).setVisibility(View.INVISIBLE);
                    }
                }
            } else {
                if (position == 0
                        || position == mOffset
                        || (position - mOffset) % PAGE_MESSAGE_COUNT == 0) {
                    helper.setText(R.id.item_tv_time, TimeFormat.getTime(nowDate));
                    helper.getView(R.id.item_tv_time).setVisibility(View.VISIBLE);
                } else {
                    long lastDate = mData.get(position - 1).getCreateTime();
                    if (nowDate - lastDate > 300000) {// 如果两条消息之间的间隔超过五分钟则显示时间
                        helper.setText(R.id.item_tv_time, TimeFormat.getTime(nowDate));
                        helper.getView(R.id.item_tv_time).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.item_tv_time).setVisibility(View.INVISIBLE);
                    }
                }
            }
            if (helper.getView(R.id.item_iv_avatar) != null) {
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
                    userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int status, String desc, Bitmap bitmap) {
                            if (status == 0) {
                                ((ImageView) helper.getView(R.id.item_iv_avatar)).setImageBitmap(bitmap);
                            } else {
                                if (helper.getItemViewType() == TYPE_RECEIVER_VOICE
                                        || helper.getItemViewType() == TYPE_RECEIVE_TXT
                                        || helper.getItemViewType() == TYPE_RECEIVER_IMAGE
                                        || helper.getItemViewType() == TYPE_RECEIVER_LOCATION) {
                                    helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_lawyer_avatar);
                                } else {
                                    helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
                                }
                            }
                        }
                    });
                } else {
                    if (helper.getItemViewType() == TYPE_RECEIVER_VOICE
                            || helper.getItemViewType() == TYPE_RECEIVE_TXT
                            || helper.getItemViewType() == TYPE_RECEIVER_IMAGE
                            || helper.getItemViewType() == TYPE_RECEIVER_LOCATION) {
                        helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_lawyer_avatar);
                    } else {
                        helper.setImageResource(R.id.item_iv_avatar, R.drawable.ic_avatar);
                    }
                }
            }
            switch (helper.getItemViewType()) {
                case TYPE_SEND_TXT:
                case TYPE_RECEIVE_TXT:
                    handleTextMsg(helper, msg);
                    break;
                case TYPE_SEND_VOICE:
                case TYPE_RECEIVER_VOICE:
                    handleVoiceMsg(helper, msg, position);
                    break;
                case TYPE_CUSTOM_TXT:
                    if (msg.getContent() instanceof CustomContent) {
                        CustomContent customContent = (CustomContent) msg.getContent();
                        helper.setText(R.id.item_tv_content, customContent.getStringValue("content"));
                    }
                    break;
                case TYPE_RECEIVER_LOCATION:
                case TYPE_SEND_LOCATION:
                    handleLocationMsg(helper, msg);
                    break;
                case TYPE_RECEIVER_IMAGE:
                case TYPE_SEND_IMAGE:
                    handleImgMsg(helper, msg);
                    break;
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 显示文字消息
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void handleTextMsg(BaseViewHolder helper, Message msg) {
        final String content = ((TextContent) msg.getContent()).getText();
        helper.setText(R.id.item_tv_content, content);
        if (msg.getDirect() == MessageDirect.send) {// 检查发送状态，发送方有重发机制
            switch (msg.getStatus()) {
                case created:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    break;
                case send_success:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    break;
                case send_fail:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    break;
                case send_going:
                    sendingTextOrVoice(helper, msg);
                    break;
                default:
            }
        }
        if (helper.getView(R.id.item_iv_fail_resend) != null) {
            helper.getView(R.id.item_iv_fail_resend).setOnClickListener(v -> showResendDialog(helper, msg));
        }
    }

    /**
     * 显示位置消息
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void handleLocationMsg(BaseViewHolder helper, Message msg) {
        try {
            LocationContent content = (LocationContent) msg.getContent();
            if (msg.getDirect() == MessageDirect.send) {
                switch (msg.getStatus()) {
                    case created:
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                        break;
                    case send_success:
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_sending).clearAnimation();
                        break;
                    case send_fail:
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                        helper.getView(R.id.item_iv_sending).clearAnimation();
                        break;
                    case send_going:
                        sendingTextOrVoice(helper, msg);
                        break;
                    default:
                }
            }
            helper.addOnClickListener(R.id.item_ll_content);
            String title = content.getAddress().split("&")[0];
            helper.setText(R.id.item_tv_title, title);
        } catch (Exception ignored) {
        }
    }

    /**
     * 显示图片
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void handleImgMsg(BaseViewHolder helper, Message msg) {
        try {
            ImageContent imgContent = (ImageContent) msg.getContent();
            String path = imgContent.getLocalThumbnailPath();
            if (!TextUtils.isEmpty(path)) {
                imgContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int status, String desc, File file) {
                        if (status == 0) {
                            mImageLoader.loadImage(mContext
                                    , ImageConfigImpl
                                            .builder()
                                            .url(file.getAbsolutePath())
                                            .imageView(helper.getView(R.id.item_iv_icon))
                                            .isCenterCrop(false)
                                            .build());
                        }
                    }
                });
            } else {
                mImageLoader.loadImage(mContext
                        , ImageConfigImpl
                                .builder()
                                .url(path)
                                .imageView(helper.getView(R.id.item_iv_icon))
                                .isCenterCrop(false)
                                .build());
            }

            if (msg.getDirect() == MessageDirect.receive) {
                switch (msg.getStatus()) {
                    case receive_fail:
                        helper.setImageResource(R.id.item_iv_icon, R.drawable.ic_chat_no_image);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                        helper.getView(R.id.item_iv_fail_resend).setOnClickListener(v -> imgContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
                            @Override
                            public void onComplete(int i, String s, File file) {
                                if (i == 0) {
                                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                                    notifyDataSetChanged();
                                }
                            }
                        }));
                        break;
                    case send_success:
                        helper.addOnClickListener(R.id.item_iv_icon);
                    default:
                }
            } else {
                switch (msg.getStatus()) {
                    case created:
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                        break;
                    case send_success:
                        helper.addOnClickListener(R.id.item_iv_icon);
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_sending).clearAnimation();
                        break;
                    case send_fail:
                        helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                        helper.getView(R.id.item_iv_sending).clearAnimation();
                        break;
                    case send_going:
                        sendingImage(helper, msg);
                        break;
                    default:
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void sendingImage(BaseViewHolder helper, Message msg) {
        helper.getView(R.id.item_iv_sending).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
        helper.getView(R.id.item_iv_sending).startAnimation(mSendingAnim);

        if (!msg.isSendCompleteCallbackExists()) {
            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(final int status, String desc) {
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    if (status == 803008) {
                        CustomContent customContent = new CustomContent();
                        customContent.setBooleanValue("blackList", true);
                        Message customMsg = mConversation.createSendMessage(customContent);
                        addMsgToList(customMsg);
                    } else if (status != 0) {
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    }
                    Message message = mConversation.getMessage(msg.getId());
                    mData.set(mData.indexOf(msg), message);
                }
            });

        }
    }

    /**
     * 显示语音消息
     *
     * @param helper   BaseViewHolder
     * @param msg      BaseViewHolder
     * @param position position
     */
    private void handleVoiceMsg(BaseViewHolder helper, Message msg, int position) {
        VoiceContent content = (VoiceContent) msg.getContent();
        MessageDirect msgDirect = msg.getDirect();
        int length = content.getDuration();
        String lengthStr = length + "s";
        helper.setText(R.id.item_tv_voice_size, lengthStr);
        //控制语音长度显示，长度增幅随语音长度逐渐缩小
        int width = (int) (-0.04 * length * length + 4.526 * length + 75.214);
        ((TextView) helper.getView(R.id.item_tv_voice_size)).setWidth(width);
        helper.setImageResource(R.id.item_iv_voice, R.drawable.ic_voice_3);
        if (msgDirect == MessageDirect.send) {
            switch (msg.getStatus()) {
                case created:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.VISIBLE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
                    break;
                case send_success:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    break;
                case send_fail:
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    break;
                case send_going:
                    sendingTextOrVoice(helper, msg);
                    break;
                default:
            }
        } else {
            switch (msg.getStatus()) {
                case receive_success:
                    if (msg.getContent().getBooleanExtra("isRead") == null
                            || !msg.getContent().getBooleanExtra("isRead")) {
                        mConversation.updateMessageExtra(msg, "isRead", false);
                        if (mIndexList.size() > 0) {
                            if (!mIndexList.contains(position)) {
                                addToListAndSort(position);
                            }
                        } else {
                            addToListAndSort(position);
                        }
                        if (nextPlayPosition == position && autoPlay) {
                            playVoice(helper, position);
                        }
                    }
                    break;
                case receive_fail:
                    // 接收失败，从服务器上下载
                    content.downloadVoiceFile(msg,
                            new DownloadCompletionCallback() {
                                @Override
                                public void onComplete(int status, String desc, File file) {

                                }
                            });
                    break;
                case receive_going:
                    break;
                default:
            }
        }

        if (helper.getView(R.id.item_iv_fail_resend) != null) {
            helper.getView(R.id.item_iv_fail_resend).setOnClickListener(arg0 -> showResendDialog(helper, msg));
        }
        helper.getView(R.id.item_ll_content).setOnClickListener(new BtnOrTxtListener(helper, position));
    }

    /**
     * 显示正在发送文字或语音
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void sendingTextOrVoice(BaseViewHolder helper, Message msg) {
        helper.getView(R.id.item_iv_sending).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);
        helper.getView(R.id.item_iv_sending).startAnimation(mSendingAnim);
        if (!msg.isSendCompleteCallbackExists()) {//消息正在发送，重新注册一个监听消息发送完成的Callback
            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(final int status, final String desc) {
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    if (status == 803008) {
                        CustomContent customContent = new CustomContent();
                        customContent.setBooleanValue("blackList", true);
                        Message customMsg = mConversation.createSendMessage(customContent);
                        addMsgToList(customMsg);
                    } else if (status != 0) {
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    /**
     * 重新发送文字和语音消息
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void resendTextOrVoice(BaseViewHolder helper, Message msg) {
        helper.getView(R.id.item_iv_sending).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.GONE);

        helper.getView(R.id.item_iv_sending).startAnimation(mSendingAnim);

        if (!msg.isSendCompleteCallbackExists()) {
            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(final int status, String desc) {
                    helper.getView(R.id.item_iv_sending).setVisibility(View.GONE);
                    helper.getView(R.id.item_iv_sending).clearAnimation();
                    if (status != 0) {
                        helper.getView(R.id.item_iv_fail_resend).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(true);
        JMessageClient.sendMessage(msg, options);
    }

    /**
     * 重发对话框
     *
     * @param helper BaseViewHolder
     * @param msg    Message
     */
    private void showResendDialog(BaseViewHolder helper, Message msg) {
        Dialog dialog = new DeleteDialog(mContext
                , dialog1 -> {
            resendTextOrVoice(helper, msg);
            dialog1.dismiss();
        }
                , "是否重发该消息？");
        dialog.show();
    }

    private void addToListAndSort(int position) {
        mIndexList.add(position);
        Collections.sort(mIndexList);
    }

    /**
     * 播放语音
     *
     * @param helper   BaseViewHolder
     * @param position position
     */
    private void playVoice(BaseViewHolder helper, int position) {
        // 记录播放录音的位置
        mPosition = position;
        Message msg = mData.get(position);
        if (autoPlay) {
            mConversation.updateMessageExtra(msg, "isRead", true);
            if (mVoiceAnimation != null) {
                mVoiceAnimation.stop();
                mVoiceAnimation = null;
            }
            helper.setImageResource(R.id.item_iv_voice, R.drawable.ic_voice_3);
            mVoiceAnimation = (AnimationDrawable) ((ImageView) helper.getView(R.id.item_iv_voice)).getDrawable();
        }
        try {
            mp.reset();
            VoiceContent vc = (VoiceContent) msg.getContent();
            mFIS = new FileInputStream(vc.getLocalPath());
            mp.setDataSource(mFIS.getFD());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();
            mp.setOnPreparedListener(mp -> {
                mVoiceAnimation.start();
                mp.start();
            });
            mp.setOnCompletionListener(mp -> {
                mVoiceAnimation.stop();
                mp.reset();
                mSetData = false;
                helper.setImageResource(R.id.item_iv_voice, R.drawable.ic_voice_3);
                if (autoPlay) {
                    int curCount = mIndexList.indexOf(position);
                    if (curCount + 1 >= mIndexList.size()) {
                        nextPlayPosition = -1;
                        autoPlay = false;
                    } else {
                        nextPlayPosition = mIndexList.get(curCount + 1);
                        notifyDataSetChanged();
                    }
                    mIndexList.remove(curCount);
                }
            });
        } catch (Exception e) {
            AppUtils.makeText(mContext, "文件丢失,正在尝试重新获取");
            VoiceContent vc = (VoiceContent) msg.getContent();
            vc.downloadVoiceFile(msg, new DownloadCompletionCallback() {
                @Override
                public void onComplete(int status, String desc, File file) {
                    if (status == 0) {
                        AppUtils.makeText(mContext, "获取完成");
                    } else {
                        AppUtils.makeText(mContext, "获取失败");
                    }
                }
            });
        } finally {
            try {
                if (mFIS != null) {
                    mFIS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class BtnOrTxtListener implements View.OnClickListener {

        private int position;
        private BaseViewHolder helper;

        BtnOrTxtListener(BaseViewHolder helper, int position) {
            this.position = position;
            this.helper = helper;
        }

        @Override
        public void onClick(View v) {
            Message msg = mData.get(position);
            MessageDirect msgDirect = msg.getDirect();
            switch (msg.getContentType()) {
                case voice:
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        AppUtils.makeText(mContext, "暂无外部存储");
                        return;
                    }
                    if (mVoiceAnimation != null) {// 如果之前存在播放动画，无论这次点击触发的是暂停还是播放，停止上次播放的动画
                        mVoiceAnimation.stop();
                    }

                    if (mp.isPlaying() && mPosition == position) {// 播放中点击了正在播放的Item 则暂停播放
                        helper.setImageResource(R.id.item_iv_voice, R.drawable.animation_voice_receive);
                        mVoiceAnimation = (AnimationDrawable) ((ImageView) helper.getView(R.id.item_iv_voice)).getDrawable();
                        helper.setImageResource(R.id.item_iv_voice, R.drawable.ic_voice_3);
                        mp.pause();
                        mSetData = true;
                    } else if (msgDirect == MessageDirect.send) {// 开始播放录音
                        helper.setImageResource(R.id.item_iv_voice, R.drawable.animation_voice_receive);
                        mVoiceAnimation = (AnimationDrawable) ((ImageView) helper.getView(R.id.item_iv_voice)).getDrawable();

                        if (mSetData && mPosition == position) {// 继续播放之前暂停的录音
                            mVoiceAnimation.start();
                            mp.start();
                        } else {// 否则重新播放该录音或者其他录音
                            playVoice(helper, position);
                        }
                    } else {// 语音接收方特殊处理，自动连续播放未读语音
                        try {
                            if (mSetData && mPosition == position) { // 继续播放之前暂停的录音
                                if (mVoiceAnimation != null) {
                                    mVoiceAnimation.start();
                                }
                                mp.start();
                            } else {// 否则开始播放另一条录音
                                if (msg.getContent().getBooleanExtra("isRead") == null
                                        || !msg.getContent().getBooleanExtra("isRead")) {// 选中的录音是否已经播放过，如果未播放，自动连续播放这条语音之后未播放的语音
                                    autoPlay = true;
                                    playVoice(helper, position);
                                    // 否则直接播放选中的语音
                                } else {
                                    helper.setImageResource(R.id.item_iv_voice, R.drawable.animation_voice_receive);
                                    mVoiceAnimation = (AnimationDrawable) ((ImageView) helper.getView(R.id.item_iv_voice)).getDrawable();
                                    playVoice(helper, position);
                                }
                            }
                        } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
    }
}