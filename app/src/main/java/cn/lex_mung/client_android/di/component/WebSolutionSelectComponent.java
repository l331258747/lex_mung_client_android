package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.WebSolutionSelectModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.WebSolutionSelectActivity;


@ActivityScope
@Component(modules = WebSolutionSelectModule.class, dependencies = AppComponent.class)
public interface WebSolutionSelectComponent {
    void inject(WebSolutionSelectActivity activity);
}
