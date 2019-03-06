package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.ActiveModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.ActiveFragment;

@FragmentScope
@Component(modules = ActiveModule.class, dependencies = AppComponent.class)
public interface ActiveComponent {
    void inject(ActiveFragment fragment);
}
