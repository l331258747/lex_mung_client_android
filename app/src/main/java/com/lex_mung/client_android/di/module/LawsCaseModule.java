package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.LawsCaseContract;
import com.lex_mung.client_android.mvp.model.LawsCaseModel;


@Module
public class LawsCaseModule {

    private LawsCaseContract.View view;

    public LawsCaseModule(LawsCaseContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    LawsCaseContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    LawsCaseContract.Model provideMeModel(LawsCaseModel model) {
        return model;
    }
}
