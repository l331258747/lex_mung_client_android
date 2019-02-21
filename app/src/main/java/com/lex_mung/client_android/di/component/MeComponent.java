package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MeModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.MeFragment;


@FragmentScope
@Component(modules = MeModule.class, dependencies = AppComponent.class)
public interface MeComponent {
    void inject(MeFragment fragment);
}
