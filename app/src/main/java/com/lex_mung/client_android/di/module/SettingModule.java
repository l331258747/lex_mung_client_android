package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.SettingContract;
import com.lex_mung.client_android.mvp.model.SettingModel;


@Module
public class SettingModule {

    private SettingContract.View view;

    public SettingModule(SettingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SettingContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SettingContract.Model provideMeModel(SettingModel model) {
        return model;
    }
}
