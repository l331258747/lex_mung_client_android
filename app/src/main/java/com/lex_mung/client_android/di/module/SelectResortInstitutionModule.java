package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.SelectResortInstitutionContract;
import com.lex_mung.client_android.mvp.model.SelectResortInstitutionModel;


@Module
public class SelectResortInstitutionModule {

    private SelectResortInstitutionContract.View view;

    public SelectResortInstitutionModule(SelectResortInstitutionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SelectResortInstitutionContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SelectResortInstitutionContract.Model provideMeModel(SelectResortInstitutionModel model) {
        return model;
    }
}
