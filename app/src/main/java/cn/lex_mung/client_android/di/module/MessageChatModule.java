package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MessageChatContract;
import cn.lex_mung.client_android.mvp.model.MessageChatModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

@Module
public class MessageChatModule {

    private MessageChatContract.View view;

    public MessageChatModule(MessageChatContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MessageChatContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MessageChatContract.Model provideMeModel(MessageChatModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
