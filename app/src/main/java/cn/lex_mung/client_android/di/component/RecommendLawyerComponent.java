package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.RecommendLawyerModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.RecommendLawyerActivity;


@ActivityScope
@Component(modules = RecommendLawyerModule.class, dependencies = AppComponent.class)
public interface RecommendLawyerComponent {
    void inject(RecommendLawyerActivity activity);
}
