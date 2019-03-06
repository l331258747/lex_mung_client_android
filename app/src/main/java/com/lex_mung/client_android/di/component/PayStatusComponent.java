package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.PayStatusModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;


@ActivityScope
@Component(modules = PayStatusModule.class, dependencies = AppComponent.class)
public interface PayStatusComponent {
    void inject(PayStatusActivity activity);
}
