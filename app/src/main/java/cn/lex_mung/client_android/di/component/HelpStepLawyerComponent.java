package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStepLawyerModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepLawyerActivity;


@ActivityScope
@Component(modules = HelpStepLawyerModule.class, dependencies = AppComponent.class)
public interface HelpStepLawyerComponent {
    void inject(HelpStepLawyerActivity activity);
}
