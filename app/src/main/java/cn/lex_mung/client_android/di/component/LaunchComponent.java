package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.LaunchModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.LaunchActivity;

@ActivityScope
@Component(modules = LaunchModule.class, dependencies = AppComponent.class)
public interface LaunchComponent {
    void inject(LaunchActivity activity);
}
