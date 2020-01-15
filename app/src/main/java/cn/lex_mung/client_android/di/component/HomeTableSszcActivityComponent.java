package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeTableSszcActivityModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeTableSszcActivityActivity;


@ActivityScope
@Component(modules = HomeTableSszcActivityModule.class, dependencies = AppComponent.class)
public interface HomeTableSszcActivityComponent {
    void inject(HomeTableSszcActivityActivity activity);
}
