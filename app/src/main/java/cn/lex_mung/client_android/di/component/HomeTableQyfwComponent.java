package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeTableQyfwModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeTableQyfwActivity;


@ActivityScope
@Component(modules = HomeTableQyfwModule.class, dependencies = AppComponent.class)
public interface HomeTableQyfwComponent {
    void inject(HomeTableQyfwActivity activity);
}
