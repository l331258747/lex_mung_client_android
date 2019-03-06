package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.FreeConsultDetailContract;
import com.lex_mung.client_android.mvp.model.FreeConsultDetailModel;


@Module
public class FreeConsultDetailModule {

    private FreeConsultDetailContract.View view;

    public FreeConsultDetailModule(FreeConsultDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetailContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetailContract.Model provideMeModel(FreeConsultDetailModel model) {
        return model;
    }
}
