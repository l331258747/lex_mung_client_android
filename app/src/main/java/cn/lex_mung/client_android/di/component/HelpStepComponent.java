package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStepModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepActivity;


@ActivityScope
@Component(modules = HelpStepModule.class, dependencies = AppComponent.class)
public interface HelpStepComponent {
    void inject(HelpStepActivity activity);
}
