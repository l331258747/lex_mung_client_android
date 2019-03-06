package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.AccountPayModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.AccountPayActivity;


@ActivityScope
@Component(modules = AccountPayModule.class, dependencies = AppComponent.class)
public interface AccountPayComponent {
    void inject(AccountPayActivity activity);
}
