package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStep6Module;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep6Fragment;


@FragmentScope
@Component(modules = HelpStep6Module.class, dependencies = AppComponent.class)
public interface HelpStep6Component {
    void inject(HelpStep6Fragment fragment);
}
