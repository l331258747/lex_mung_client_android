package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.ValueCallback;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.List;

import cn.lex_mung.client_android.app.DataHelperTags;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.X5WebCommonContract;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.PermissionUtil;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;


@ActivityScope
public class X5WebCommonPresenter extends BasePresenter<X5WebCommonContract.Model, X5WebCommonContract.View> {
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

    private boolean isLogin = false;//是否登录

    private static final int REQUEST_CODE_PICK_IMAGE = 102;

    @Inject
    public X5WebCommonPresenter(X5WebCommonContract.Model model, X5WebCommonContract.View rootView) {
        super(model, rootView);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void onResume() {
        isLogin = DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS);
    }

    public void getLaunchCameraPermission() {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.uploadPicture();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法打开摄像头/本地相册");
                clearUploadMessage();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
                clearUploadMessage();
            }
        }, mRxPermissions, mErrorHandler);
    }

    //这里需要注意的是，当取消拍照或者未选择照片的时候，uploadMessage 或者 uploadMessageAboveL 要返回 null.因为 valueCallback 持有的是 WebView，
    // 在 onReceiveValue 没有回传值的情况下，WebView 无法进行下一步操作，会导致取消选择文件后，点击 <input type = 'file'>,不会再响应。
    private void clearUploadMessage(){
        if (mRootView.getUploadMessage() != null) {
            mRootView.getUploadMessage().onReceiveValue(null);
            mRootView.setUploadMessage(null);
        }
        if (mRootView.getUploadMessageAboveL() != null) {
            mRootView.getUploadMessageAboveL().onReceiveValue(null);
            mRootView.setUploadMessageAboveL(null);

        }
    }

    private File saveFile;//裁剪完成的图片
    /**
     * 选择图片回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mRootView.getUploadMessage() == null && mRootView.getUploadMessageAboveL() == null) {
            return;
        }

        //取消拍照或者图片选择时,返回null,否则<input file> 就是没有反应
        if (resultCode != RESULT_OK) {
            clearUploadMessage();
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {//从相册
            if (data == null || data.getData() == null) {
                mRootView.showMessage("获取照片失败!请重试");
                return;
            }
            crop(data.getData());
        } else if (requestCode == UCrop.REQUEST_CROP) {//裁剪
            try {
                Uri resultUri = UCrop.getOutput(data);
                String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), resultUri.getLastPathSegment());
                saveFile = new File(downloadsDirectoryPath, filename);
                FileInputStream inStream = new FileInputStream(new File(resultUri.getPath()));
                FileOutputStream outStream = new FileOutputStream(saveFile);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();
                mRootView.compressionImage(saveFile);
            } catch (Exception ignored) {
                mRootView.showMessage("获取照片失败!请重试");
            }
        }
    }



    /**
     * 裁剪
     */
    private void crop(Uri uri) {
        String destinationFileName = "user_avatar_" + System.currentTimeMillis() + ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(mRootView.getActivity().getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(500, 500);
        uCrop = uCrop.withMaxResultSize(500, 500);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        uCrop.start(mRootView.getActivity());
    }


    /**
     * 本地相册
     */
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        mRootView.launchActivity(intent, REQUEST_CODE_PICK_IMAGE);
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
