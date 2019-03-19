package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MapSearchModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MapSearchActivity;


@ActivityScope
@Component(modules = MapSearchModule.class, dependencies = AppComponent.class)
public interface MapSearchComponent {
    void inject(MapSearchActivity activity);
}
