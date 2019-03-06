package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.FreeConsultContract;
import com.lex_mung.client_android.mvp.model.FreeConsultModel;


@Module
public class FreeConsultModule {

    private FreeConsultContract.View view;

    public FreeConsultModule(FreeConsultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultContract.Model provideMeModel(FreeConsultModel model) {
        return model;
    }
}
