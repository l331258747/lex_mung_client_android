package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.OrderMessageModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.OrderMessageFragment;


@FragmentScope
@Component(modules = OrderMessageModule.class, dependencies = AppComponent.class)
public interface OrderMessageComponent {
    void inject(OrderMessageFragment fragment);
}
