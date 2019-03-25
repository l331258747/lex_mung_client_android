package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FindLawyerModule;

import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.ui.fragment.FindLawyerFragment;

@FragmentScope
@Component(modules = FindLawyerModule.class, dependencies = AppComponent.class)
public interface FindLawyerComponent {
    void inject(FindLawyerFragment fragment);
}
