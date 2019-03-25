package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.LawyerHomePageModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;

@ActivityScope
@Component(modules = LawyerHomePageModule.class, dependencies = AppComponent.class)
public interface LawyerHomePageComponent {
    void inject(LawyerHomePageActivity activity);
}
