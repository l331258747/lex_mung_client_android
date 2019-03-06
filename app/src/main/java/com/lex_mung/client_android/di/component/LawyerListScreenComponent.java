package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.LawyerListScreenModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.LawyerListScreenActivity;


@ActivityScope
@Component(modules = LawyerListScreenModule.class, dependencies = AppComponent.class)
public interface LawyerListScreenComponent {
    void inject(LawyerListScreenActivity activity);
}
