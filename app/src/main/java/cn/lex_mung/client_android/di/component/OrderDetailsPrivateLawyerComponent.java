package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderDetailsPrivateLawyerModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderDetailsPrivateLawyerActivity;


@ActivityScope
@Component(modules = OrderDetailsPrivateLawyerModule.class, dependencies = AppComponent.class)
public interface OrderDetailsPrivateLawyerComponent {
    void inject(OrderDetailsPrivateLawyerActivity activity);
}
