package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeTableQygwModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeTableQygwActivity;


@ActivityScope
@Component(modules = HomeTableQygwModule.class, dependencies = AppComponent.class)
public interface HomeTableQygwComponent {
    void inject(HomeTableQygwActivity activity);
}
