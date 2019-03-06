package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.LoginContract;
import com.lex_mung.client_android.mvp.model.LoginModel;


@Module
public class LoginModule {

    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LoginContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LoginContract.Model provideMeModel(LoginModel model) {
        return model;
    }
}
