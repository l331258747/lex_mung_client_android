package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.RushLoanPayModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.RushLoanPayActivity;


@ActivityScope
@Component(modules = RushLoanPayModule.class, dependencies = AppComponent.class)
public interface RushLoanPayComponent {
    void inject(RushLoanPayActivity activity);
}
