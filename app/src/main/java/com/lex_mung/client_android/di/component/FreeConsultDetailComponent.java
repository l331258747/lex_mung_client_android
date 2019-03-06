package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.FreeConsultDetailModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.FreeConsultDetailActivity;

@ActivityScope
@Component(modules = FreeConsultDetailModule.class, dependencies = AppComponent.class)
public interface FreeConsultDetailComponent {
    void inject(FreeConsultDetailActivity activity);
}
