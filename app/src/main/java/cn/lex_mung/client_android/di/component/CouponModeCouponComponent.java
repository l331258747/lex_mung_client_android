package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.CouponModeCouponModule;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCouponFragment;


@FragmentScope
@Component(modules = CouponModeCouponModule.class, dependencies = AppComponent.class)
public interface CouponModeCouponComponent {
    void inject(CouponModeCouponFragment fragment);
}
