package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrderCouponModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrderCouponActivity;


@ActivityScope
@Component(modules = OrderCouponModule.class, dependencies = AppComponent.class)
public interface OrderCouponComponent {
    void inject(OrderCouponActivity activity);
}
