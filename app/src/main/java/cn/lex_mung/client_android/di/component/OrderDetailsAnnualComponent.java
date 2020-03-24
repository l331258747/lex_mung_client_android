package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderDetailsAnnualModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsAnnualActivity;


@ActivityScope
@Component(modules = OrderDetailsAnnualModule.class, dependencies = AppComponent.class)
public interface OrderDetailsAnnualComponent {
    void inject(OrderDetailsAnnualActivity activity);
}
