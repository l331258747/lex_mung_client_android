package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.MyTradingListModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.MyTradingListActivity;


@ActivityScope
@Component(modules = MyTradingListModule.class, dependencies = AppComponent.class)
public interface MyTradingListComponent {
    void inject(MyTradingListActivity activity);
}
