package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.AccountWithdrawalContract;
import com.lex_mung.client_android.mvp.model.AccountWithdrawalModel;


@Module
public class AccountWithdrawalModule {

    private AccountWithdrawalContract.View view;

    public AccountWithdrawalModule(AccountWithdrawalContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AccountWithdrawalContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AccountWithdrawalContract.Model provideMeModel(AccountWithdrawalModel model) {
        return model;
    }
}
