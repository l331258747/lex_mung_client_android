package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.ReleaseDemandHistoryModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.ReleaseDemandHistoryActivity;


@ActivityScope
@Component(modules = ReleaseDemandHistoryModule.class, dependencies = AppComponent.class)
public interface ReleaseDemandHistoryComponent {
    void inject(ReleaseDemandHistoryActivity activity);
}
