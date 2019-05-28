package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HelpStepChildModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HelpStepChildActivity;


@ActivityScope
@Component(modules = HelpStepChildModule.class, dependencies = AppComponent.class)
public interface HelpStepChildComponent {
    void inject(HelpStepChildActivity activity);
}
