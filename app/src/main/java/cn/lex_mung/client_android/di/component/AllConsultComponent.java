package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.AllConsultModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.AllConsultActivity;


@ActivityScope
@Component(modules = AllConsultModule.class, dependencies = AppComponent.class)
public interface AllConsultComponent {
    void inject(AllConsultActivity activity);
}
