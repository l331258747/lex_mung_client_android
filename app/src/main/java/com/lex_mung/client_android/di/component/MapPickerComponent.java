package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MapPickerModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MapPickerActivity;


@ActivityScope
@Component(modules = MapPickerModule.class, dependencies = AppComponent.class)
public interface MapPickerComponent {
    void inject(MapPickerActivity activity);
}
