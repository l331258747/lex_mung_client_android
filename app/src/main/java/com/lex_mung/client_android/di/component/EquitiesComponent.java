package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.EquitiesModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.EquitiesFragment;


@FragmentScope
@Component(modules = EquitiesModule.class, dependencies = AppComponent.class)
public interface EquitiesComponent {
    void inject(EquitiesFragment fragment);
}
