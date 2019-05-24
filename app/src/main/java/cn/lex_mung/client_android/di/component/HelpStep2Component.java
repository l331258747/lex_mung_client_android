package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStep2Module;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep2Fragment;


@FragmentScope
@Component(modules = HelpStep2Module.class, dependencies = AppComponent.class)
public interface HelpStep2Component {
    void inject(HelpStep2Fragment fragment);
}
