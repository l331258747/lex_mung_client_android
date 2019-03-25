package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MapModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.MapActivity;


@ActivityScope
@Component(modules = MapModule.class, dependencies = AppComponent.class)
public interface MapComponent {
    void inject(MapActivity activity);
}
