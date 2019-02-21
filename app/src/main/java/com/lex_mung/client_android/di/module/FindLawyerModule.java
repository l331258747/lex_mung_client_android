package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.FindLawyerContract;
import com.lex_mung.client_android.mvp.model.FindLawyerModel;


@Module
public class FindLawyerModule {

    private FindLawyerContract.View view;

    public FindLawyerModule(FindLawyerContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    FindLawyerContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    FindLawyerContract.Model provideMeModel(FindLawyerModel model) {
        return model;
    }
}
