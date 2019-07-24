package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface X5WebCommonContract {
    interface View extends IView {
        void uploadPicture();

        void showToAppInfoDialog();

        Activity getActivity();

        void launchActivity(Intent intent, int code);

        void compressionImage(File file);

        ValueCallback<Uri> getUploadMessage();

        ValueCallback<Uri[]> getUploadMessageAboveL();

        void setUploadMessage(ValueCallback<Uri> valueCallback);

        void setUploadMessageAboveL(ValueCallback<Uri[]> valueCallback);
    }

    interface Model extends IModel {

    }
}
