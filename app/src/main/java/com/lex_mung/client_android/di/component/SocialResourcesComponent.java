package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.SocialResourcesModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.SocialResourcesFragment;


@FragmentScope
@Component(modules = SocialResourcesModule.class, dependencies = AppComponent.class)
public interface SocialResourcesComponent {
    void inject(SocialResourcesFragment fragment);
}
