package com.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.content.Intent;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.UploadImageEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface FeedbackContract {
    interface View extends IView {
        void setType(String feedbackTypeName);

        void launchActivity(Intent intent, int code);

        void setIcon(String path);

        void showSelectPhotoDialog();

        Activity getActivity();

        void showToAppInfoDialog();
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<FeedbackTypeEntity>>> getFeedbackType();

        Observable<BaseResponse<UploadImageEntity>> uploadImage(RequestBody body, MultipartBody.Part file);

        Observable<BaseResponse> uploadFeedback(RequestBody body);
    }
}
