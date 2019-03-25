package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.content.Intent;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MessageChatAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.RequirementAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MessageChatContract {
    interface View extends IView {
        void showWaitAcceptOrderLayout();

        void showAcceptOrderLayout();

        void showOrderEndLayout();

        void voiceOrText(MessageChatAdapter mChatAdapter);

        void showRequirementAdapterLayout();

        void showToAppInfoDialog();

        void setTitle(String nickname);

        void launchActivity(Intent intent, int code);

        void setImageIcon(String absolutePath);

        void initRecyclerView(MessageChatAdapter mChatAdapter, RequirementAdapter requirementAdapter);

        void initListener(MessageChatAdapter mChatAdapter);

        void setTime(String s);

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<RequirementStatusEntity>>> getRequirementStatus(int id);
    }
}
