package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MapModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MapActivity;


@ActivityScope
@Component(modules = MapModule.class, dependencies = AppComponent.class)
public interface MapComponent {
    void inject(MapActivity activity);
}
