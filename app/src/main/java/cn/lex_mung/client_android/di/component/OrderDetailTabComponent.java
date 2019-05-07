package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderDetailTabModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailTabActivity;


@ActivityScope
@Component(modules = OrderDetailTabModule.class, dependencies = AppComponent.class)
public interface OrderDetailTabComponent {
    void inject(OrderDetailTabActivity activity);
}
