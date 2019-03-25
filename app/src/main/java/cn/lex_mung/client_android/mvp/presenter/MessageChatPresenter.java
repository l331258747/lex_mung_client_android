package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.FileUtil;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RxLifecycleUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.contract.MessageChatContract;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import cn.lex_mung.client_android.mvp.ui.activity.MapActivity;
import cn.lex_mung.client_android.mvp.ui.activity.MapPickerActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.MessageChatAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;
import static me.zl.mvp.utils.DrawableProvider.rotateBitmapByDegree;

@ActivityScope
public class MessageChatPresenter extends BasePresenter<MessageChatContract.Model, MessageChatContract.View> {
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    private static final int REQUEST_CODE_PICK_IMAGE = 102;
    private static final int REQUEST_CODE_LOCATION = 103;

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    private MessageChatAdapter mChatAdapter;
    private RequirementAdapter requirementAdapter;
    private MyCountDownTimer myCountDownTimer;

    private File file;//图片文件
    private int id;//律师ID
    private int status;//需求状态  1已接单 0未接单 2已结束
    private List<RequirementStatusEntity> entityList;//需求列表
    private String mTargetId;
    private Conversation mConversation;

    @Inject
    public MessageChatPresenter(MessageChatContract.Model model, MessageChatContract.View rootView) {
        super(model, rootView);
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RequirementStatusEntity> getEntityList() {
        return entityList;
    }

    public Conversation getConversation() {
        return mConversation;
    }

    public void onCreate(RecyclerView recyclerView) {
        JMessageClient.registerEventReceiver(this);
        mTargetId = "lex" + id;
        mConversation = JMessageClient.getSingleConversation(mTargetId, Api.J_PUSH);
        if (mConversation == null) {
            mConversation = Conversation.createSingleConversation(mTargetId, Api.J_PUSH);
        }
        JMessageClient.enterSingleConversation(mTargetId, Api.J_PUSH);

        initAdapter(recyclerView);

        getRequirementStatus();
    }

    private void initAdapter(RecyclerView recyclerView) {
        mChatAdapter = new MessageChatAdapter(mRootView.getActivity(), mImageLoader, recyclerView, mConversation);
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
                Bundle bundle = new Bundle();
                bundle.clear();
                bundle.putString(BundleTags.KEY_TITLE, content.getAddress());
                bundle.putDouble(BundleTags.KEY_LAT, content.getLatitude().doubleValue());
                bundle.putDouble(BundleTags.KEY_LNG, content.getLongitude().doubleValue());
                bundle.putInt(BundleTags.KEY_LEVEL, content.getScale().intValue());
                mRootView.launchActivity(new Intent(mRootView.getActivity(), MapActivity.class), bundle);
            } else if (message.getContentType() == ContentType.image) {
                ImageContent imgContent = (ImageContent) message.getContent();
                String path = imgContent.getLocalThumbnailPath();
                if (!TextUtils.isEmpty(path)) {
                    imgContent.downloadThumbnailImage(message, new DownloadCompletionCallback() {
                        @Override
                        public void onComplete(int status, String desc, File file) {
                            if (status == 0) {
                                mRootView.setImageIcon(file.getAbsolutePath());
                            }
                        }
                    });
                } else {
                    mRootView.setImageIcon(path);
                }
            }
        });
        requirementAdapter = new RequirementAdapter();
        mRootView.initRecyclerView(mChatAdapter, requirementAdapter);
        mRootView.initListener(mChatAdapter);
    }

    public void onEventMainThread(MessageEvent event) {
        final Message message = event.getMessage();
        if (message.getTargetType() == ConversationType.single) {
            UserInfo userInfo = (UserInfo) message.getTargetInfo();
            String targetId = userInfo.getUserName();
            if (targetId.equals(mTargetId)) {
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
            if (targetId.equals(mTargetId)) {
                List<Message> singleOfflineMsgList = event.getOfflineMessageList();
                if (singleOfflineMsgList != null && singleOfflineMsgList.size() > 0) {
                    mChatAdapter.addData(singleOfflineMsgList);
                }
            }
        }
    }

    /**
     * 发送文本消息
     *
     * @param content 内容
     */
    public void sendTextMessage(String content) {
        if (mConversation == null) return;
        if (TextUtils.isEmpty(content)) {
            return;
        }
        Message msg = mConversation.createSendMessage(new TextContent(content));
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(true);
        JMessageClient.sendMessage(msg, options);
        mChatAdapter.addMsgFromReceiptToList(msg);
    }

    /**
     * 发送位置消息
     *
     * @param data Intent
     */
    private void sendLocationMessage(Intent data) {
        if (mConversation == null) return;
        Bundle bundle = data.getExtras();
        if (bundle == null) return;
        try {
            double latitude = bundle.getDouble(BundleTags.KEY_LAT, 0);
            double longitude = bundle.getDouble(BundleTags.KEY_LNG, 0);
            int level = bundle.getInt(BundleTags.KEY_LEVEL, 0);
            String title = bundle.getString(BundleTags.KEY_TITLE);
            LocationContent locationContent = new LocationContent(latitude, longitude, level, title);
            Message message = mConversation.createSendMessage(locationContent);
            MessageSendingOptions options = new MessageSendingOptions();
            options.setNeedReadReceipt(true);
            JMessageClient.sendMessage(message, options);
            mChatAdapter.addMsgFromReceiptToList(message);

            int customMsgId = data.getIntExtra("customMsg", -1);
            if (-1 != customMsgId) {
                Message customMsg = mConversation.getMessage(customMsgId);
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
    private void sendImageMessage(File file) {
        if (mConversation == null) return;
        ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, ImageContent imageContent) {
                if (responseCode == 0) {
                    Message msg = mConversation.createSendMessage(imageContent);
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

    /**
     * 获取录音权限
     */
    public void getRecordAudioPermission() {
        PermissionUtil.recordAudio(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.voiceOrText(mChatAdapter);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法发送语音");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    /**
     * 获取相册和拍照权限
     */
    public void getLaunchCameraPermission(int type) {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                if (type == 1) {//拍照
                    getImageFromCamera();
                } else {//相册
                    getImageFromAlbum();
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法发送图片");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    /**
     * 获取定位权限
     */
    public void getLocationPermission() {
        PermissionUtil.location(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.launchActivity(new Intent(mRootView.getActivity(), MapPickerActivity.class), REQUEST_CODE_LOCATION);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法发送位置信息");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    /**
     * 拍照
     */
    private void getImageFromCamera() {
        Intent intent;
        file = new File(AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/practice_license_" + System.currentTimeMillis() + ".png");
        //拍照图片的路径
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            imageUri = mRootView.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mRootView.launchActivity(intent, REQUEST_CODE_CAPTURE_CAMERA);
    }

    /**
     * 本地相册
     */
    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        mRootView.launchActivity(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 选择图片回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_CODE_LOCATION) {
                if (data == null
                        || data.getExtras() == null) {
                    mRootView.showMessage("获取位置信息失败!请重试");
                    return;
                }
                sendLocationMessage(data);
            } else {
                if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {//从相册
                    if (data == null || data.getData() == null) {
                        mRootView.showMessage("获取照片失败!请重试");
                        return;
                    }
                    Uri uri = data.getData();
                    file = new File(FileUtil.getImageFilePath(mRootView.getActivity(), uri));
                    compressionPhoto();
                } else if (requestCode == REQUEST_CODE_CAPTURE_CAMERA && resultCode == RESULT_OK) {//拍照
                    if (file == null) {
                        mRootView.showMessage("获取照片失败!请重试");
                        return;
                    }
                    if ("samsung".equals(android.os.Build.BRAND)) {//三星手机的特殊处理
                        FileInputStream fis = new FileInputStream(file);
                        int rotate = FileUtil.readPictureDegree(file.getAbsolutePath());
                        if (rotate > 0) {
                            Bitmap bitmap = rotateBitmapByDegree(BitmapFactory.decodeStream(fis), rotate);
                            file = FileUtil.saveFile(bitmap, AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/practice_license_" + System.currentTimeMillis() + ".png");
                        }
                    }
                    compressionPhoto();
                }
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 压缩图片
     */
    private void compressionPhoto() {
        Luban.with(mApplication)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        mRootView.showLoading("压缩图片中...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        mRootView.hideLoading();
                        sendImageMessage(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        LogUtils.warnInfo(e.getMessage());
                    }
                }).launch();
    }

    private void getRequirementStatus() {
        mModel.getRequirementStatus(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RequirementStatusEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RequirementStatusEntity>> baseResponse) {
                        if (mConversation != null) {
                            mRootView.setTitle(mConversation.getTitle());
                        }
                        if (baseResponse.isSuccess()) {
                            entityList = baseResponse.getData();
                            if (entityList != null
                                    && entityList.size() > 0) {
                                status = entityList.get(0).getStatus();
                                long remain = entityList.get(0).getRemain();
                                if (status == 0) {
                                    List<RequirementStatusEntity> list = new ArrayList<>();
                                    list.add(entityList.get(0));
                                    requirementAdapter.setNewData(list);
                                    mRootView.showRequirementAdapterLayout();
                                    mRootView.showWaitAcceptOrderLayout();
                                    if (myCountDownTimer != null) {
                                        myCountDownTimer.cancel();
                                        myCountDownTimer = null;
                                    }
                                    myCountDownTimer = new MyCountDownTimer(remain);
                                    myCountDownTimer.start();
                                } else if (status == 1) {
                                    mChatAdapter.setConversation(mConversation);
                                    mRootView.showAcceptOrderLayout();
                                    if (myCountDownTimer != null) {
                                        myCountDownTimer.cancel();
                                        myCountDownTimer = null;
                                    }
                                    myCountDownTimer = new MyCountDownTimer(remain);
                                    myCountDownTimer.start();
                                } else if (status == 2
                                        || status == 3) {
                                    mChatAdapter.setConversation(mConversation);
                                    mRootView.showOrderEndLayout();
                                    if (myCountDownTimer != null) {
                                        myCountDownTimer.cancel();
                                        myCountDownTimer = null;
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private class MyCountDownTimer extends CountDownTimer {
        SimpleDateFormat sdf;

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
        public void onTick(long l) {
            String s = sdf.format(new Date(l));
            if (status == 0) {
                mRootView.setTime(String.format(mApplication.getString(R.string.text_accept_order_time), s));
            } else if (status == 1) {
                mRootView.setTime(String.format(mApplication.getString(R.string.text_remaining_service_time), s));
            }
        }

        @Override
        public void onFinish() {
            status = 3;
            mRootView.showOrderEndLayout();
            if (myCountDownTimer != null) {
                myCountDownTimer.cancel();
                myCountDownTimer = null;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
