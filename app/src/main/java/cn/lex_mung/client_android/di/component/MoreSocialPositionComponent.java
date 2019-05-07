package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MoreSocialPositionModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.MoreSocialPositionActivity;


@ActivityScope
@Component(modules = MoreSocialPositionModule.class, dependencies = AppComponent.class)
public interface MoreSocialPositionComponent {
    void inject(MoreSocialPositionActivity activity);
}
