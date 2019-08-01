package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.SolutionDetailModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.SolutionDetailActivity;


@ActivityScope
@Component(modules = SolutionDetailModule.class, dependencies = AppComponent.class)
public interface SolutionDetailComponent {
    void inject(SolutionDetailActivity activity);
}
