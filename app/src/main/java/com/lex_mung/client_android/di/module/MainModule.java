package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.MainContract;
import com.lex_mung.client_android.mvp.model.MainModel;

@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMeModel(MainModel model) {
        return model;
    }
}
