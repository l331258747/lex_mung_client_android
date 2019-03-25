package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.SelectListItemModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.SelectListItemActivity;


@ActivityScope
@Component(modules = SelectListItemModule.class, dependencies = AppComponent.class)
public interface SelectListItemComponent {
    void inject(SelectListItemActivity activity);
}
