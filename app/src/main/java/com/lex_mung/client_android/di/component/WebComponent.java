package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.WebModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.WebActivity;


@ActivityScope
@Component(modules = WebModule.class, dependencies = AppComponent.class)
public interface WebComponent {
    void inject(WebActivity activity);
}
