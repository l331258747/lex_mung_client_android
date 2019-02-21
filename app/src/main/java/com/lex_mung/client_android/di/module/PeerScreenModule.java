package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.PeerScreenContract;
import com.lex_mung.client_android.mvp.model.PeerScreenModel;


@Module
public class PeerScreenModule {

    private PeerScreenContract.View view;

    public PeerScreenModule(PeerScreenContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PeerScreenContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PeerScreenContract.Model provideMeModel(PeerScreenModel model) {
        return model;
    }
}
