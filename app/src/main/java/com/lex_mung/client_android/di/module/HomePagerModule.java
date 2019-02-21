package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.HomePagerContract;
import com.lex_mung.client_android.mvp.model.HomePagerModel;

@Module
public class HomePagerModule {

    private HomePagerContract.View view;

    public HomePagerModule(HomePagerContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HomePagerContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HomePagerContract.Model provideMeModel(HomePagerModel model) {
        return model;
    }
}
