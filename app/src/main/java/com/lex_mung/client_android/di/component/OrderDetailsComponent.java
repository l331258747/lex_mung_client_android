package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.OrderDetailsModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.OrderDetailsActivity;


@ActivityScope
@Component(modules = OrderDetailsModule.class, dependencies = AppComponent.class)
public interface OrderDetailsComponent {
    void inject(OrderDetailsActivity activity);
}
