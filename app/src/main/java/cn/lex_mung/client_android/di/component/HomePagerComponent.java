package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomePagerModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.HomePagerFragment;

@FragmentScope
@Component(modules = HomePagerModule.class, dependencies = AppComponent.class)
public interface HomePagerComponent {
    void inject(HomePagerFragment fragment);
}
