package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MyAccountModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MyAccountActivity;


@ActivityScope
@Component(modules = MyAccountModule.class, dependencies = AppComponent.class)
public interface MyAccountComponent {
    void inject(MyAccountActivity activity);
}
