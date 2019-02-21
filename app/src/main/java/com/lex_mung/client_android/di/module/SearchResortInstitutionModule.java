package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.SearchResortInstitutionContract;
import com.lex_mung.client_android.mvp.model.SearchResortInstitutionModel;


@Module
public class SearchResortInstitutionModule {

    private SearchResortInstitutionContract.View view;

    public SearchResortInstitutionModule(SearchResortInstitutionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchResortInstitutionContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchResortInstitutionContract.Model provideMeModel(SearchResortInstitutionModel model) {
        return model;
    }
}
