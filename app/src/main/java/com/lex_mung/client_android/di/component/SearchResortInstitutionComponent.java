package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.SearchResortInstitutionModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.SearchResortInstitutionActivity;


@ActivityScope
@Component(modules = SearchResortInstitutionModule.class, dependencies = AppComponent.class)
public interface SearchResortInstitutionComponent {
    void inject(SearchResortInstitutionActivity activity);
}
