package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.TradingListModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.TradingListActivity;


@ActivityScope
@Component(modules = TradingListModule.class, dependencies = AppComponent.class)
public interface TradingListComponent {
    void inject(TradingListActivity activity);
}
