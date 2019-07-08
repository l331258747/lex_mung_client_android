package cn.lex_mung.client_android.di.component;

import cn.lex_mung.client_android.mvp.ui.activity.MyCardActivity;
import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MyCardModule;

import me.zl.mvp.di.scope.ActivityScope;


@ActivityScope
@Component(modules = MyCardModule.class, dependencies = AppComponent.class)
public interface MyCardComponent {
    void inject(MyCardActivity activity);
}
