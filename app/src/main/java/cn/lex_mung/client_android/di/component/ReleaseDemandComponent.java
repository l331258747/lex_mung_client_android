package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.ReleaseDemandModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.ReleaseDemandActivity;


@ActivityScope
@Component(modules = ReleaseDemandModule.class, dependencies = AppComponent.class)
public interface ReleaseDemandComponent {
    void inject(ReleaseDemandActivity activity);
}
