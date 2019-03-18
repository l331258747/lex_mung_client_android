package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.RequirementListContract;
import com.lex_mung.client_android.mvp.model.RequirementListModel;


@Module
public class RequirementListModule {

    private RequirementListContract.View view;

    public RequirementListModule(RequirementListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RequirementListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RequirementListContract.Model provideMeModel(RequirementListModel model) {
        return model;
    }
}
