package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.FeedbackModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.FeedbackActivity;

@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);
}
