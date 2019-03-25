package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.TradingListDetailsModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.TradingListDetailsActivity;


@ActivityScope
@Component(modules = TradingListDetailsModule.class, dependencies = AppComponent.class)
public interface TradingListDetailsComponent {
    void inject(TradingListDetailsActivity activity);
}
