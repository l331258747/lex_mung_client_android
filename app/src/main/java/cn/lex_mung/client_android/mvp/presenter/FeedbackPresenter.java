package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;

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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import javax.inject.Inject;

import com.google.gson.Gson;
import cn.lex_mung.client_android.mvp.contract.FeedbackContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static cn.lex_mung.client_android.app.EventBusTags.FEEDBACK_INFO.FEEDBACK_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.FEEDBACK_INFO.FEEDBACK_INFO_TYPE;
import static me.zl.mvp.utils.DrawableProvider.rotateBitmapByDegree;


@ActivityScope
public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View> {
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

    private List<FeedbackTypeEntity> feedbackTypeEntities;

    private int feedbackTypeId;//上传的问题类型
    private File file;//需要上传的图片
    private String url;//提交到服务器的URL

    @Inject
    public FeedbackPresenter(FeedbackContract.Model model, FeedbackContract.View rootView) {
        super(model, rootView);
    }

    public List<FeedbackTypeEntity> getFeedbackTypeEntities() {
        return feedbackTypeEntities;
    }

    public void getFeedbackType() {
        mModel.getFeedbackType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FeedbackTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<FeedbackTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            feedbackTypeEntities = baseResponse.getData();
                        }
                    }
                });
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = FEEDBACK_INFO)
    private void receiveUpdateUserDeatils(Message message) {
        switch (message.what) {
            case FEEDBACK_INFO_TYPE:
                FeedbackTypeEntity entity = (FeedbackTypeEntity) message.obj;
                feedbackTypeId = entity.getFeedbackTypeId();
                mRootView.setType(entity.getFeedbackTypeName());
                break;
        }
    }

    public void uploadFeedback(String input) {
        if (feedbackTypeId == 0) {
            mRootView.showMessage("请选择问题类型");
            return;
        }
        if (TextUtils.isEmpty(input)) {
            mRootView.showMessage("请输入问题详情");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("feedbackTypeId", feedbackTypeId);
        map.put("feedbackContent", input);
        if (file != null && !TextUtils.isEmpty(url)) {
            map.put("image", url);
        }
        mModel.uploadFeedback(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showMessage("提交成功，感谢您的反馈");
                        }
                        mRootView.killMyself();
                    }
                });
    }

    public void getLaunchCameraPermission() {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.showSelectPhotoDialog();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法打开摄像头/本地相册");
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
    public void getImageFromCamera() {
        Intent intent;
        file = new File(AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/feedback_" + System.currentTimeMillis() + ".png");
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
    public void getImageFromAlbum() {
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
            mRootView.setIcon(file.getPath());
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
                    file = FileUtil.saveFile(bitmap, AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/feedback_" + System.currentTimeMillis() + ".png");
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
                        mRootView.showLoading("");
                    }

                    @Override
                    public void onSuccess(File file) {
                        mRootView.hideLoading();
                        uploadImage(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        LogUtils.warnInfo(e.getMessage());
                    }
                }).launch();
    }

    /**
     * 上传图片
     *
     * @param file 压缩后的图片
     */
    private void uploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "11");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        mModel.uploadImage(requestBody, MultipartBody.Part.createFormData("file", file.getName(), requestFile))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UploadImageEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UploadImageEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.setIcon(file.getPath());
                            url = baseResponse.getData().getDburl();
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
