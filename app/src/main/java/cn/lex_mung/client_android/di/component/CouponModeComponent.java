package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.CouponModeModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.CouponModeActivity;


@ActivityScope
@Component(modules = CouponModeModule.class, dependencies = AppComponent.class)
public interface CouponModeComponent {
    void inject(CouponModeActivity activity);
}
