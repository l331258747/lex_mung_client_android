package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.DiscountWayModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.DiscountWayActivity;


@ActivityScope
@Component(modules = DiscountWayModule.class, dependencies = AppComponent.class)
public interface DiscountWayComponent {
    void inject(DiscountWayActivity activity);
}
