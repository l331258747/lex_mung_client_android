package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.ServicePriceModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.ServicePriceFragment;


@FragmentScope
@Component(modules = ServicePriceModule.class, dependencies = AppComponent.class)
public interface ServicePriceComponent {
    void inject(ServicePriceFragment fragment);
}
