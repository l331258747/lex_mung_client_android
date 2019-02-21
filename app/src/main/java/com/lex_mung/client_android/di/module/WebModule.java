package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.WebContract;
import com.lex_mung.client_android.mvp.model.WebModel;


@Module
public class WebModule {

    private WebContract.View view;

    public WebModule(WebContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WebContract.Model provideMeModel(WebModel model) {
        return model;
    }
}
