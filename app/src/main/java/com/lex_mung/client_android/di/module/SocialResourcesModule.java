package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.SocialResourcesContract;
import com.lex_mung.client_android.mvp.model.SocialResourcesModel;


@Module
public class SocialResourcesModule {

    private SocialResourcesContract.View view;

    public SocialResourcesModule(SocialResourcesContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SocialResourcesContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SocialResourcesContract.Model provideMeModel(SocialResourcesModel model) {
        return model;
    }
}
