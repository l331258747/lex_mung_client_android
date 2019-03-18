package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MessageChatModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MessageChatActivity;


@ActivityScope
@Component(modules = MessageChatModule.class, dependencies = AppComponent.class)
public interface MessageChatComponent {
    void inject(MessageChatActivity activity);
}
