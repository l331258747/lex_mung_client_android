package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.SolutionLIstModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.SolutionLIstFragment;


@FragmentScope
@Component(modules = SolutionLIstModule.class, dependencies = AppComponent.class)
public interface SolutionLIstComponent {
    void inject(SolutionLIstFragment fragment);
}
