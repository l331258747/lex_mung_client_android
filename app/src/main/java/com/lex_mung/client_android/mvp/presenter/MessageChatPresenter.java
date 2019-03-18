package com.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
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

import com.lex_mung.client_android.mvp.contract.MessageChatContract;
import com.lex_mung.client_android.mvp.model.api.Api;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import com.lex_mung.client_android.mvp.ui.activity.MapPickerActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static me.zl.mvp.utils.DrawableProvider.rotateBitmapByDegree;

@ActivityScope
public class MessageChatPresenter extends BasePresenter<MessageChatContract.Model, MessageChatContract.View> {
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    private static final int REQUEST_CODE_PICK_IMAGE = 102;

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

    private File file;//图片文件

    private int id;
    private int status;//需求状态  1已接单 0未接单 2已结束
    private List<RequirementStatusEntity> entityList;

    private String mTargetId;
    private Conversation mConversation;

    @Inject
    public MessageChatPresenter(MessageChatContract.Model model, MessageChatContract.View rootView) {
        super(model, rootView);
    }

    public void setId(int id) {
        this.id = id;
        mTargetId = "lex" + id;
        mConversation = JMessageClient.getSingleConversation(mTargetId, Api.J_PUSH);
        if (mConversation == null) {
            mConversation = Conversation.createSingleConversation(mTargetId, Api.J_PUSH);
        }
        JMessageClient.enterSingleConversation(mTargetId, Api.J_PUSH);
        getRequirementStatus();
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        if (entityList.size() > 0) {
            entityList.get(0).setStatus(3);
        }
    }

    public List<RequirementStatusEntity> getEntityList() {
        return entityList;
    }

    public String getTargetId() {
        return mTargetId;
    }

    public void setConversation(Conversation mConversation) {
        this.mConversation = mConversation;
    }

    public Conversation getConversation() {
        return mConversation;
    }

    /**
     * 获取录音权限
     */
    public void getRecordAudioPermission() {
        PermissionUtil.recordAudio(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.voiceOrText();
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
                mRootView.launchActivity(new Intent(mRootView.getActivity(), MapPickerActivity.class), 1000);
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
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int rotate = FileUtil.readPictureDegree(file.getAbsolutePath());
                if (rotate > 0) {
                    Bitmap bitmap = rotateBitmapByDegree(BitmapFactory.decodeStream(fis), rotate);
                    file = FileUtil.saveFile(bitmap, AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/practice_license_" + System.currentTimeMillis() + ".png");
                }
            }
            compressionPhoto();
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
                        mRootView.sendImageMessage(file);
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
                                    mRootView.showRequirementAdapter(list);
                                    mRootView.showWaitAcceptOrderLayout();
                                    mRootView.setCountDown(remain);
                                } else if (status == 1) {
                                    mRootView.showAcceptOrderLayout();
                                    mRootView.setCountDown(remain);
                                } else if (status == 2
                                        || status == 3) {
                                    mRootView.showOrderEndLayout();
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
