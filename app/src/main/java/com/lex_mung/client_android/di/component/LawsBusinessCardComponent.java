package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.LawsBusinessCardModule;

import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.ui.fragment.LawsBusinessCardFragment;

@FragmentScope
@Component(modules = LawsBusinessCardModule.class, dependencies = AppComponent.class)
public interface LawsBusinessCardComponent {
    void inject(LawsBusinessCardFragment fragment);
}
