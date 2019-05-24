package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStep4Module;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep4Fragment;


@FragmentScope
@Component(modules = HelpStep4Module.class, dependencies = AppComponent.class)
public interface HelpStep4Component {
    void inject(HelpStep4Fragment fragment);
}
