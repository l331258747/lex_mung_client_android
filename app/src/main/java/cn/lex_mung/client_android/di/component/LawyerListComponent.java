package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.LawyerListModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.LawyerListActivity;


@ActivityScope
@Component(modules = LawyerListModule.class, dependencies = AppComponent.class)
public interface LawyerListComponent {
    void inject(LawyerListActivity activity);
}
