package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MyEntrustListModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.MyEntrustListActivity;


@ActivityScope
@Component(modules = MyEntrustListModule.class, dependencies = AppComponent.class)
public interface MyEntrustListComponent {
    void inject(MyEntrustListActivity activity);
}
