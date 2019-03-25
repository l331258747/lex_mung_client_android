package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.TradingListModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.TradingListActivity;


@ActivityScope
@Component(modules = TradingListModule.class, dependencies = AppComponent.class)
public interface TradingListComponent {
    void inject(TradingListActivity activity);
}
