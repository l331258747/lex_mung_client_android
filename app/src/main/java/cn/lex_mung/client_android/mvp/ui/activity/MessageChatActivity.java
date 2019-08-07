package cn.lex_mung.client_android.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.di.module.MessageChatModule;
import cn.lex_mung.client_android.mvp.ui.adapter.MessageChatAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.DefaultDialog;
import cn.lex_mung.client_android.mvp.ui.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.jpush.im.android.api.JMessageClient;
import me.zl.mvp.base.BaseActivity;
import me.zl.mvp.di.component.AppComponent;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DeviceUtils;

import cn.lex_mung.client_android.di.component.DaggerMessageChatComponent;
import cn.lex_mung.client_android.mvp.contract.MessageChatContract;
import cn.lex_mung.client_android.mvp.presenter.MessageChatPresenter;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.widget.RecordVoiceButton;
import com.zl.mvp.http.imageloader.glide.ImageConfigImpl;

import java.io.Serializable;

import javax.inject.Inject;

import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.SET_CUSTOMER_UN_READ_MESSAGE_NUM;
import static cn.lex_mung.client_android.app.EventBusTags.MESSAGE_INFO.UN_READ_MESSAGE_NUM;

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
        tvRight.setText("需求列表");
        if (bundleIntent != null) {
            mPresenter.setId(bundleIntent.getInt(BundleTags.ID));
        }
        mPresenter.onCreate(recyclerView);
    }

    @Override
    public void setImageIcon(String absolutePath) {
        mImageLoader.loadImage(mActivity
                , ImageConfigImpl
                        .builder()
                        .url(absolutePath)
                        .imageView(ivIcon)
                        .isCenterCrop(false)
                        .build());
    }

    @Override
    public void initRecyclerView(MessageChatAdapter mChatAdapter, RequirementAdapter requirementAdapter) {
        AppUtils.configRecyclerView(recyclerView, new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(mChatAdapter);
        AppUtils.configRecyclerView(recyclerViewRequirement, new LinearLayoutManager(mActivity));
        recyclerViewRequirement.setAdapter(requirementAdapter);
    }

    @OnTextChanged(value = R.id.et_chat, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            btSend.setVisibility(View.VISIBLE);
            ivMultimedia.setVisibility(View.GONE);
        } else {
            btSend.setVisibility(View.GONE);
            ivMultimedia.setVisibility(View.VISIBLE);
        }
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void initListener(MessageChatAdapter mChatAdapter) {
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

    @Override
    public void setTime(String s) {
        tvTime.setText(s);
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
        if (isFastClick()) return;
        switch (view.getId()) {
            case R.id.tv_right:
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) mPresenter.getEntityList());
                launchActivity(new Intent(mActivity, RequirementListActivity.class), bundle);
                break;
            case R.id.iv_voice_or_text://切换语音和文本输入
                mPresenter.getRecordAudioPermission();
                break;
            case R.id.bt_send://发送文本
                mPresenter.sendTextMessage(etChat.getText().toString());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
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
    public void showRequirementAdapterLayout() {
        recyclerViewRequirement.setVisibility(View.VISIBLE);
    }

    @Override
    public void voiceOrText(MessageChatAdapter mChatAdapter) {
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
        rlOrder.setVisibility(View.GONE);
        rlSend.setVisibility(View.VISIBLE);
        groupTime.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        recyclerViewRequirement.setVisibility(View.GONE);
    }

    @Override
    public void showOrderEndLayout() {
        rlOrder.setVisibility(View.VISIBLE);
        rlSend.setVisibility(View.GONE);
        btOrderWait.setVisibility(View.GONE);
        btOrderClose.setVisibility(View.VISIBLE);
        groupTime.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        recyclerViewRequirement.setVisibility(View.GONE);
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
    public void launchActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
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
        JMessageClient.unRegisterEventReceiver(this);
        AppUtils.postInt(UN_READ_MESSAGE_NUM, SET_CUSTOMER_UN_READ_MESSAGE_NUM, JMessageClient.getAllUnReadMsgCount());
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPresenter.getConversation() != null) {
            mPresenter.getConversation().resetUnreadCount();
        }
        JMessageClient.exitConversation();
        AppManager.getAppManager().killActivity(MessageChatActivity.class);
    }
}
