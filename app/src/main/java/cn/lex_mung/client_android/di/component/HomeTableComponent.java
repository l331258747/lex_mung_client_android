package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeTableModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeTableActivity;


@ActivityScope
@Component(modules = HomeTableModule.class, dependencies = AppComponent.class)
public interface HomeTableComponent {
    void inject(HomeTableActivity activity);
}
