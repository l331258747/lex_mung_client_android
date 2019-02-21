package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.SelectListItemModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.SelectListItemActivity;


@ActivityScope
@Component(modules = SelectListItemModule.class, dependencies = AppComponent.class)
public interface SelectListItemComponent {
    void inject(SelectListItemActivity activity);
}
