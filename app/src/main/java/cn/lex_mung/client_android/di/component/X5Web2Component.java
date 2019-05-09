package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.X5Web2Module;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.X5Web2Activity;


@ActivityScope
@Component(modules = X5Web2Module.class, dependencies = AppComponent.class)
public interface X5Web2Component {
    void inject(X5Web2Activity activity);
}
