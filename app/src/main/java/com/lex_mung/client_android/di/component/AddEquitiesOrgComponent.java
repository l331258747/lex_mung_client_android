package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.AddEquitiesOrgModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.AddEquitiesOrgActivity;


@ActivityScope
@Component(modules = AddEquitiesOrgModule.class, dependencies = AppComponent.class)
public interface AddEquitiesOrgComponent {
    void inject(AddEquitiesOrgActivity activity);
}
