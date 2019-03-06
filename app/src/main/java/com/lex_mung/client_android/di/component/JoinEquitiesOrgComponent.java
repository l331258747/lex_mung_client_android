package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.JoinEquitiesOrgModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.JoinEquitiesOrgActivity;


@ActivityScope
@Component(modules = JoinEquitiesOrgModule.class, dependencies = AppComponent.class)
public interface JoinEquitiesOrgComponent {
    void inject(JoinEquitiesOrgActivity activity);
}
