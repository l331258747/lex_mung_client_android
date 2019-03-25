package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MessageModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.MessageActivity;


@ActivityScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    void inject(MessageActivity activity);
}
