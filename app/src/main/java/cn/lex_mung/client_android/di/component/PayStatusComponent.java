package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.PayStatusModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;


@ActivityScope
@Component(modules = PayStatusModule.class, dependencies = AppComponent.class)
public interface PayStatusComponent {
    void inject(PayStatusActivity activity);
}
