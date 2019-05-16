package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultMainModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultMainActivity;


@ActivityScope
@Component(modules = FreeConsultMainModule.class, dependencies = AppComponent.class)
public interface FreeConsultMainComponent {
    void inject(FreeConsultMainActivity activity);
}
