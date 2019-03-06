package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.LawsBusinessCardContract;
import com.lex_mung.client_android.mvp.model.LawsBusinessCardModel;


@Module
public class LawsBusinessCardModule {

    private LawsBusinessCardContract.View view;

    public LawsBusinessCardModule(LawsBusinessCardContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    LawsBusinessCardContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    LawsBusinessCardContract.Model provideMeModel(LawsBusinessCardModel model) {
        return model;
    }
}
