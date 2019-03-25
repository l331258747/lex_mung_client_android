package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.LawsCaseModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.LawsCaseFragment;

@FragmentScope
@Component(modules = LawsCaseModule.class, dependencies = AppComponent.class)
public interface LawsCaseComponent {
    void inject(LawsCaseFragment fragment);
}
