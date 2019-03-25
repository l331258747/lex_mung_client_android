package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MyLikeModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.MyLikeActivity;


@ActivityScope
@Component(modules = MyLikeModule.class, dependencies = AppComponent.class)
public interface MyLikeComponent {
    void inject(MyLikeActivity activity);
}
