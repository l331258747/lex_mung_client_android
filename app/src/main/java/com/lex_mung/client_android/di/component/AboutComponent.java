package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.AboutModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.AboutActivity;


@ActivityScope
@Component(modules = AboutModule.class, dependencies = AppComponent.class)
public interface AboutComponent {
    void inject(AboutActivity activity);
}
