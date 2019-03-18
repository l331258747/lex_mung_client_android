package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.DiscountWayContract;
import com.lex_mung.client_android.mvp.model.DiscountWayModel;


@Module
public class DiscountWayModule {

    private DiscountWayContract.View view;

    public DiscountWayModule(DiscountWayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DiscountWayContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DiscountWayContract.Model provideMeModel(DiscountWayModel model) {
        return model;
    }
}
