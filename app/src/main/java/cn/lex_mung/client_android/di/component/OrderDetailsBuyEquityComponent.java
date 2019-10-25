package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderDetailsBuyEquityModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsBuyEquityActivity;


@ActivityScope
@Component(modules = OrderDetailsBuyEquityModule.class, dependencies = AppComponent.class)
public interface OrderDetailsBuyEquityComponent {
    void inject(OrderDetailsBuyEquityActivity activity);
}
