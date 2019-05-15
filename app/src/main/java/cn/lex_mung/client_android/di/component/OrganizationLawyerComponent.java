package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.OrganizationLawyerModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.OrganizationLawyerActivity;


@ActivityScope
@Component(modules = OrganizationLawyerModule.class, dependencies = AppComponent.class)
public interface OrganizationLawyerComponent {
    void inject(OrganizationLawyerActivity activity);
}
