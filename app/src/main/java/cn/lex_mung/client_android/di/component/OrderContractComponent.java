package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderContractModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderContractActivity;


@ActivityScope
@Component(modules = OrderContractModule.class, dependencies = AppComponent.class)
public interface OrderContractComponent {
    void inject(OrderContractActivity activity);
}
