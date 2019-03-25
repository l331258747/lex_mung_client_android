package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FastConsultModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.FastConsultActivity;

@ActivityScope
@Component(modules = FastConsultModule.class, dependencies = AppComponent.class)
public interface FastConsultComponent {
    void inject(FastConsultActivity activity);
}
