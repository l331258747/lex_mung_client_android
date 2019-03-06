package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.FreeConsultModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.FreeConsultActivity;

@ActivityScope
@Component(modules = FreeConsultModule.class, dependencies = AppComponent.class)
public interface FreeConsultComponent {
    void inject(FreeConsultActivity activity);
}
