package cn.lex_mung.client_android.di.component;

import cn.lex_mung.client_android.di.module.HelpStep3Module;
import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.HelpStep3Fragment;


@FragmentScope
@Component(modules = HelpStep3Module.class, dependencies = AppComponent.class)
public interface HelpStep3Component {
    void inject(HelpStep3Fragment fragment);
}
