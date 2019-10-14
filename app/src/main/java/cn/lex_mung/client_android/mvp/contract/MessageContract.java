package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MessageContract {
    interface View extends IView {
        void setDemandMessageCount(String s);

        void setOrderMessageCount(String s);

        void setSystemMessageCount(String s);

        void hideDemandMessageCount();

        void hideOrderMessageCount();

        void hideSystemMessageCount();

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount();
    }
}
