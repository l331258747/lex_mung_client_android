package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.MyOrderModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.MyOrderActivity;


@ActivityScope
@Component(modules = MyOrderModule.class, dependencies = AppComponent.class)
public interface MyOrderComponent {
    void inject(MyOrderActivity activity);
}
