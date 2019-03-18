package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.RequirementListModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.RequirementListActivity;


@ActivityScope
@Component(modules = RequirementListModule.class, dependencies = AppComponent.class)
public interface RequirementListComponent {
    void inject(RequirementListActivity activity);
}
