package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.JoinEquitiesOrgContract;
import com.lex_mung.client_android.mvp.model.JoinEquitiesOrgModel;


@Module
public class JoinEquitiesOrgModule {

    private JoinEquitiesOrgContract.View view;

    public JoinEquitiesOrgModule(JoinEquitiesOrgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    JoinEquitiesOrgContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    JoinEquitiesOrgContract.Model provideMeModel(JoinEquitiesOrgModel model) {
        return model;
    }
}
