package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultListModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultListActivity;


@ActivityScope
@Component(modules = FreeConsultListModule.class, dependencies = AppComponent.class)
public interface FreeConsultListComponent {
    void inject(FreeConsultListActivity activity);
}
