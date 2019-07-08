package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.CouponModeCardModule;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.CouponModeCardFragment;


@FragmentScope
@Component(modules = CouponModeCardModule.class, dependencies = AppComponent.class)
public interface CouponModeCardComponent {
    void inject(CouponModeCardFragment fragment);
}
