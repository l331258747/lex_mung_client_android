package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.OrderDetailsContract;
import com.lex_mung.client_android.mvp.model.OrderDetailsModel;


@Module
public class OrderDetailsModule {

    private OrderDetailsContract.View view;

    public OrderDetailsModule(OrderDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsContract.Model provideMeModel(OrderDetailsModel model) {
        return model;
    }
}
