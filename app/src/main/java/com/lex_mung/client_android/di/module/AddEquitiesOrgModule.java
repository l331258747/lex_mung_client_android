package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.AddEquitiesOrgContract;
import com.lex_mung.client_android.mvp.model.AddEquitiesOrgModel;


@Module
public class AddEquitiesOrgModule {

    private AddEquitiesOrgContract.View view;

    public AddEquitiesOrgModule(AddEquitiesOrgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddEquitiesOrgContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddEquitiesOrgContract.Model provideMeModel(AddEquitiesOrgModel model) {
        return model;
    }
}
