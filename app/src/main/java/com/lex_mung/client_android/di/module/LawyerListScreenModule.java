package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.LawyerListScreenContract;
import com.lex_mung.client_android.mvp.model.LawyerListScreenModel;


@Module
public class LawyerListScreenModule {

    private LawyerListScreenContract.View view;

    public LawyerListScreenModule(LawyerListScreenContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LawyerListScreenContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LawyerListScreenContract.Model provideMeModel(LawyerListScreenModel model) {
        return model;
    }
}
