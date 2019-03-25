package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.IntegrityModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.IntegrityFragment;

@FragmentScope
@Component(modules = IntegrityModule.class, dependencies = AppComponent.class)
public interface IntegrityComponent {
    void inject(IntegrityFragment fragment);
}
