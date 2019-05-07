package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.TabOrderContractModule;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.TabOrderContractFragment;


@FragmentScope
@Component(modules = TabOrderContractModule.class, dependencies = AppComponent.class)
public interface TabOrderContractComponent {
    void inject(TabOrderContractFragment fragment);
}
