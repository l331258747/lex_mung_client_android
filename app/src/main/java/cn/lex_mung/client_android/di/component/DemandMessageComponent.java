package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.DemandMessageModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.DemandMessageFragment;


@FragmentScope
@Component(modules = DemandMessageModule.class, dependencies = AppComponent.class)
public interface DemandMessageComponent {
    void inject(DemandMessageFragment fragment);
}
