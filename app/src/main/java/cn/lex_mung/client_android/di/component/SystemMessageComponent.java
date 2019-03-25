package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.SystemMessageModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.SystemMessageFragment;


@FragmentScope
@Component(modules = SystemMessageModule.class, dependencies = AppComponent.class)
public interface SystemMessageComponent {
    void inject(SystemMessageFragment fragment);
}
