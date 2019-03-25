package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultDetailsListModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetailsListActivity;

@ActivityScope
@Component(modules = FreeConsultDetailsListModule.class, dependencies = AppComponent.class)
public interface FreeConsultDetailsListComponent {
    void inject(FreeConsultDetailsListActivity activity);
}
