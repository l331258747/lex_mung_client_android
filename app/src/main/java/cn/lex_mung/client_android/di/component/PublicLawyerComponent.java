package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.PublicLawyerModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.PublicLawyerActivity;


@ActivityScope
@Component(modules = PublicLawyerModule.class, dependencies = AppComponent.class)
public interface PublicLawyerComponent {
    void inject(PublicLawyerActivity activity);
}
