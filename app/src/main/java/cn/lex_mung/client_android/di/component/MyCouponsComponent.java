package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MyCouponsModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.MyCouponsActivity;


@ActivityScope
@Component(modules = MyCouponsModule.class, dependencies = AppComponent.class)
public interface MyCouponsComponent {
    void inject(MyCouponsActivity activity);
}
