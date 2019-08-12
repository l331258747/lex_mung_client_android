package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderDetailsExpertModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsExpertActivity;


@ActivityScope
@Component(modules = OrderDetailsExpertModule.class, dependencies = AppComponent.class)
public interface OrderDetailsExpertComponent {
    void inject(OrderDetailsExpertActivity activity);
}
