package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.EditInfoModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.EditInfoActivity;

@ActivityScope
@Component(modules = EditInfoModule.class, dependencies = AppComponent.class)
public interface EditInfoComponent {
    void inject(EditInfoActivity activity);
}
