package com.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.content.Intent;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MessageChatContract {
    interface View extends IView {
        void showWaitAcceptOrderLayout();

        void showAcceptOrderLayout();

        void showOrderEndLayout();

        void setCountDown(long remain);

        void voiceOrText();

        void sendSystemMessage(String content);

        void showRequirementAdapter(List<RequirementStatusEntity> list);

        void showToAppInfoDialog();

        void setTitle(String nickname);

        void launchActivity(Intent intent, int code);

        void sendImageMessage(File file);

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<RequirementStatusEntity>>> getRequirementStatus(int id);
    }
}
