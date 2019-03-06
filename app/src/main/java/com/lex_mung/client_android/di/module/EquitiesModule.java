package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.EquitiesContract;
import com.lex_mung.client_android.mvp.model.EquitiesModel;


@Module
public class EquitiesModule {

    private EquitiesContract.View view;

    public EquitiesModule(EquitiesContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    EquitiesContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    EquitiesContract.Model provideMeModel(EquitiesModel model) {
        return model;
    }
}
