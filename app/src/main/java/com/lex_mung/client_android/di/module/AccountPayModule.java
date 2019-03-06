package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.AccountPayContract;
import com.lex_mung.client_android.mvp.model.AccountPayModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

@Module
public class AccountPayModule {

    private AccountPayContract.View view;

    public AccountPayModule(AccountPayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AccountPayContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AccountPayContract.Model provideMeModel(AccountPayModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
