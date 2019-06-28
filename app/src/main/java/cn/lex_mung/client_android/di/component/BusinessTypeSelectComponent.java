package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.BusinessTypeSelectModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.BusinessTypeSelectActivity;


@ActivityScope
@Component(modules = BusinessTypeSelectModule.class, dependencies = AppComponent.class)
public interface BusinessTypeSelectComponent {
    void inject(BusinessTypeSelectActivity activity);
}
