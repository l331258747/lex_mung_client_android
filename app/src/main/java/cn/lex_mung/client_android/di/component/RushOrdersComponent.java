package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.RushOrdersModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.RushOrdersActivity;


@ActivityScope
@Component(modules = RushOrdersModule.class, dependencies = AppComponent.class)
public interface RushOrdersComponent {
    void inject(RushOrdersActivity activity);
}
