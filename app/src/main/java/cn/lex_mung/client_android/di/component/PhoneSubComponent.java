package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.PhoneSubModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.PhoneSubActivity;


@ActivityScope
@Component(modules = PhoneSubModule.class, dependencies = AppComponent.class)
public interface PhoneSubComponent {
    void inject(PhoneSubActivity activity);
}
