package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.X5WebCommonModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.X5WebCommonActivity;


@ActivityScope
@Component(modules = X5WebCommonModule.class, dependencies = AppComponent.class)
public interface X5WebCommonComponent {
    void inject(X5WebCommonActivity activity);
}
