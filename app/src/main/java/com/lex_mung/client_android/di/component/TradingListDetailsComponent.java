package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.TradingListDetailsModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.TradingListDetailsActivity;


@ActivityScope
@Component(modules = TradingListDetailsModule.class, dependencies = AppComponent.class)
public interface TradingListDetailsComponent {
    void inject(TradingListDetailsActivity activity);
}
