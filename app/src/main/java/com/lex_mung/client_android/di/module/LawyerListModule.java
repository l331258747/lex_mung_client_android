package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.LawyerListContract;
import com.lex_mung.client_android.mvp.model.LawyerListModel;


@Module
public class LawyerListModule {

    private LawyerListContract.View view;

    public LawyerListModule(LawyerListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LawyerListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LawyerListContract.Model provideMeModel(LawyerListModel model) {
        return model;
    }
}
