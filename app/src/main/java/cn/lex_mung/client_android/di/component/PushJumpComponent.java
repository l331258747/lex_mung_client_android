package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.PushJumpModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.PushJumpActivity;


@ActivityScope
@Component(modules = PushJumpModule.class, dependencies = AppComponent.class)
public interface PushJumpComponent {
    void inject(PushJumpActivity activity);
}
