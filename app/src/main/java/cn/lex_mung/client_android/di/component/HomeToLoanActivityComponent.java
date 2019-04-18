package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.HomeToLoanActivityModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.HomeToLoanActivityActivity;


@ActivityScope
@Component(modules = HomeToLoanActivityModule.class, dependencies = AppComponent.class)
public interface HomeToLoanActivityComponent {
    void inject(HomeToLoanActivityActivity activity);
}
