package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStep5Module;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep5Fragment;


@FragmentScope
@Component(modules = HelpStep5Module.class, dependencies = AppComponent.class)
public interface HelpStep5Component {
    void inject(HelpStep5Fragment fragment);
}
