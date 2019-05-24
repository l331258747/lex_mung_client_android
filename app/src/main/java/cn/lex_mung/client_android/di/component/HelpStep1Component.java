package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStep1Module;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep1Fragment;


@FragmentScope
@Component(modules = HelpStep1Module.class, dependencies = AppComponent.class)
public interface HelpStep1Component {
    void inject(HelpStep1Fragment fragment);
}
