package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeSolutionModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeSolutionActivity;


@ActivityScope
@Component(modules = HomeSolutionModule.class, dependencies = AppComponent.class)
public interface HomeSolutionComponent {
    void inject(HomeSolutionActivity activity);
}
