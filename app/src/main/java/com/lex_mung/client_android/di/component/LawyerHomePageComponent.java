package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.LawyerHomePageModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;

@ActivityScope
@Component(modules = LawyerHomePageModule.class, dependencies = AppComponent.class)
public interface LawyerHomePageComponent {
    void inject(LawyerHomePageActivity activity);
}
