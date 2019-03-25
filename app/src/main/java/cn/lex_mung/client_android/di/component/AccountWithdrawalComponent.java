package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.AccountWithdrawalModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.AccountWithdrawalActivity;

@ActivityScope
@Component(modules = AccountWithdrawalModule.class, dependencies = AppComponent.class)
public interface AccountWithdrawalComponent {
    void inject(AccountWithdrawalActivity activity);
}
