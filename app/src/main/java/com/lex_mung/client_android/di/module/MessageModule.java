package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.MessageContract;
import com.lex_mung.client_android.mvp.model.MessageModel;


@Module
public class MessageModule {

    private MessageContract.View view;

    public MessageModule(MessageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MessageContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MessageContract.Model provideMeModel(MessageModel model) {
        return model;
    }
}
