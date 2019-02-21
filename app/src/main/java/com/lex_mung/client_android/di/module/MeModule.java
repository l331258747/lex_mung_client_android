package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.MeContract;
import com.lex_mung.client_android.mvp.model.MeModel;


@Module
public class MeModule {

    private MeContract.View view;

    public MeModule(MeContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MeContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MeContract.Model provideMeModel(MeModel model) {
        return model;
    }
}
