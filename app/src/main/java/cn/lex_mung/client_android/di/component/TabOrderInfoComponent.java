package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.TabOrderInfoModule;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.TabOrderInfoFragment;


@FragmentScope
@Component(modules = TabOrderInfoModule.class, dependencies = AppComponent.class)
public interface TabOrderInfoComponent {
    void inject(TabOrderInfoFragment fragment);
}
